package syncronization.importFrom.ldapImport;

import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.core.support.LdapOperationsCallback;
import org.springframework.ldap.core.support.SingleContextSource;
import org.springframework.ldap.query.LdapQuery;
import syncronization.importFrom.normalMapper.DomainContextMapper;
import syncronization.importFrom.normalMapper.OrgUnitContextMapper;
import syncronization.model.Domain;
import syncronization.model.OrgUnit;

import javax.naming.directory.SearchControls;
import java.util.LinkedList;
import java.util.List;

public class OrgUnitLdapImport {
    private final LdapTemplate ldapTemplate;
    private final LdapContextSource ldapContextSource;
    private final OrgUnitContextMapper contextMapper;
    private final LdapQuery query;

    public OrgUnitLdapImport(LdapTemplate ldapTemplate, LdapContextSource ldapContextSource, OrgUnitContextMapper orgUnitContextMapper, LdapQuery query){
        this.ldapTemplate = ldapTemplate;
        this.ldapContextSource = ldapContextSource;
        this.contextMapper = orgUnitContextMapper;
        this.query = query;
    }


    private LdapOperationsCallback<List<OrgUnit>> getGoodCallback(String base,
                                                                   String filter,
                                                                   SearchControls searchControls,
                                                                   ContextMapper<OrgUnit> contextMapper,
                                                                   PagedResultsDirContextProcessor processor) {
        return operations -> {
            List<OrgUnit> result = new LinkedList<>();

            do {
                List<OrgUnit> page = operations.search(
                        base,
                        filter,
                        searchControls,
                        contextMapper,
                        processor
                );
                for(OrgUnit orgUnit : page) {
                    System.out.println(orgUnit.toString());
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



    public List<OrgUnit> getOnlyActiveGoodWayMapped(LdapContextSource ldapContextSource, OrgUnitContextMapper mapper) {
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
