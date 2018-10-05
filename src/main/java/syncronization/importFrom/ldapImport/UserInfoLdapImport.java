package syncronization.importFrom.ldapImport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.core.support.LdapOperationsCallback;
import org.springframework.ldap.core.support.SingleContextSource;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import syncronization.UserContextMapper;
import syncronization.importFrom.normalMapper.OrgUnitContextMapper;
import syncronization.model.UserInfo;

import javax.naming.directory.SearchControls;
import java.util.LinkedList;
import java.util.List;

public class UserInfoLdapImport {

    private final LdapTemplate ldapTemplate;
    private final ContextSource ldapContextSource;
    private final UserContextMapper contextMapper;
    private LdapQuery query;

    public UserInfoLdapImport(LdapTemplate ldapTemplate, ContextSource ldapContextSource, UserContextMapper userContextMapper, LdapQuery query){
        this.ldapTemplate = ldapTemplate;
        this.ldapContextSource = ldapContextSource;
        this.contextMapper = userContextMapper;
        this.query = query;
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
                    System.out.println(userInfo.toString());
                }
                result.addAll(page);
            } while (processor.hasMore());

            return result;
        };
    }

    private SearchControls searchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        searchControls.setReturningObjFlag(true);
        return searchControls;
    }

    private PagedResultsDirContextProcessor processor() {
        return new PagedResultsDirContextProcessor(1000);
    }



    public List<UserInfo> getOnlyActiveGoodWayMapped(UserContextMapper mapper, String base) {
        return SingleContextSource.doWithSingleContext(
                ldapContextSource,
                getGoodCallback(
                        base,
                        query.filter().toString(),
                        searchControls(),
                        mapper
                        , processor()
                )
        );
    }
}
