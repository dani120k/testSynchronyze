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
import syncronization.importFrom.normalMapper.OrgUnitContextMapper;
import syncronization.model.OrgUnit;
import syncronization.model.UserInfo;

import javax.naming.directory.SearchControls;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OrgUnitLdapImport {

    private final LdapTemplate ldapTemplate;
    private final ContextSource ldapContextSource;
    private final OrgUnitContextMapper mapper;
    private LdapQuery query;

    public OrgUnitLdapImport(LdapTemplate ldapTemplate, ContextSource ldapContextSource, OrgUnitContextMapper userContextMapper, LdapQuery query){
        this.ldapTemplate = ldapTemplate;
        this.ldapContextSource = ldapContextSource;
        this.mapper = userContextMapper;
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
                System.out.println("size of part is " + page.size());
                try{Thread.sleep(10000);}catch (Exception ex){ex.printStackTrace();}
                result.addAll(page);
            } while (processor.hasMore());
            for(OrgUnit orgUnit : result){
                System.out.println(orgUnit.toString());
            }
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



    public List<OrgUnit> getOnlyActiveGoodWayMapped() {

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
