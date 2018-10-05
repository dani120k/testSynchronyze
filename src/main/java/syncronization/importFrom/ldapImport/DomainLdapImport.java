package syncronization.importFrom.ldapImport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.core.support.LdapOperationsCallback;
import org.springframework.ldap.core.support.SingleContextSource;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import syncronization.importFrom.normalMapper.DomainContextMapper;
import syncronization.model.Domain;
import syncronization.model.UserInfo;

import javax.naming.directory.SearchControls;
import java.util.LinkedList;
import java.util.List;

public class DomainLdapImport {
    private final LdapTemplate ldapTemplate;
    private final LdapContextSource ldapContextSource;
    private final DomainContextMapper contextMapper;
    private final LdapQuery query;

    public DomainLdapImport(LdapTemplate ldapTemplate, LdapContextSource ldapContextSource, DomainContextMapper domainContextMapper, LdapQuery query){
        this.ldapTemplate = ldapTemplate;
        this.ldapContextSource = ldapContextSource;
        this.contextMapper = domainContextMapper;
        this.query = query;
    }


    private LdapOperationsCallback<List<Domain>> getGoodCallback(String base,
                                                                   String filter,
                                                                   SearchControls searchControls,
                                                                   ContextMapper<Domain> contextMapper,
                                                                   PagedResultsDirContextProcessor processor) {
        return operations -> {
            List<Domain> result = new LinkedList<>();

            do {
                try {
                    List<Domain> page = operations.search(
                            base,
                            filter,
                            searchControls,
                            contextMapper,
                            processor
                    );
                    for (Domain domain : page) {
                        System.out.println(domain.toString());
                    }
                    result.addAll(page);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
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



    public List<Domain> getOnlyActiveGoodWayMapped(LdapContextSource ldapContextSource, DomainContextMapper mapper) {
        return SingleContextSource.doWithSingleContext(
                ldapContextSource,
                getGoodCallback(
                        "",
                        query.filter().toString(),
                        searchControls(),
                        mapper
                        , processor()
                )
        );
    }
}
