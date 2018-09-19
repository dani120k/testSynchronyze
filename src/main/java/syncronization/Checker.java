package syncronization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.core.support.LdapOperationsCallback;
import org.springframework.ldap.core.support.SingleContextSource;
import org.springframework.ldap.query.LdapQueryBuilder;
import syncronization.model.UserInfo;

import javax.naming.directory.SearchControls;
import java.util.LinkedList;
import java.util.List;

public class Checker {

    private final LdapTemplate ldapTemplate;
    private final LdapContextSource ldapContextSource;
    private final UserContextMapper contextMapper;

    @Autowired
    public Checker(LdapTemplate ldapTemplate, LdapContextSource ldapContextSource, UserContextMapper contextMapper) {
        this.ldapTemplate = ldapTemplate;
        this.ldapContextSource = ldapContextSource;
        this.contextMapper = contextMapper;
    }

    private String onlyActiveFilter() {
        return LdapQueryBuilder.query()
                .where("objectCategory").is("person")
                .filter().encode();
    }

    private LdapOperationsCallback<List<UserInfo>> getGoodCallback(String base,
                                                                   String filter,
                                                                   SearchControls searchControls,
                                                                   ContextMapper<UserInfo> contextMapper,
                                                                   PagedResultsDirContextProcessor processor) {
        return operations -> {
            List<UserInfo> result = new LinkedList<>();

            do {
                List<UserInfo> page = operations.search(
                        base,
                        filter,
                        searchControls,
                        contextMapper,
                        processor
                );
                for(UserInfo userInfo : page) {
                    System.out.println(page.toString());
                }
                result.addAll(page);
            } while (processor.hasMore());

            return result;
        };
    }

    private SearchControls searchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        return searchControls;
    }

    private PagedResultsDirContextProcessor processor() {
        return new PagedResultsDirContextProcessor(1000);
    }



    public List<UserInfo> getOnlyActiveGoodWayMapped(LdapContextSource ldapContextSource, UserContextMapper mapper) {
        return SingleContextSource.doWithSingleContext(
                ldapContextSource,
                getGoodCallback(
                        "",
                        onlyActiveFilter(),
                        searchControls(),
                        mapper
                        , processor()
                )
        );
    }
}
