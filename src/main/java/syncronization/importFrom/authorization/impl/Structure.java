package syncronization.importFrom.authorization.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;
import org.springframework.ldap.support.LdapUtils;
import syncronization.UserContextMapper;
import syncronization.importFrom.authorization.IStructure;
import syncronization.importFrom.ldapImport.OrgUnitLdapImport;
import syncronization.importFrom.ldapImport.UserInfoLdapImport;
import syncronization.importFrom.mapper.DomainAttributesMapper;
import syncronization.importFrom.normalMapper.OrgUnitContextMapper;
import syncronization.model.Admin;
import syncronization.model.Domain;
import syncronization.model.OrgUnit;
import syncronization.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

public class Structure implements IStructure {
    private static final Logger LOG = LoggerFactory.getLogger(Structure.class);
    private LdapTemplate ldapTemplate;
    private int resultSizeLimit = 1000;

    public Structure(LdapTemplate ldapTemplate, Admin admin){
        this.ldapTemplate = ldapTemplate;
        auth(ldapTemplate, admin);
    }

    public Domain getCurrentDomain(){
        String[] attrs = new String[]{"distinguishedName", "cn"};
        LdapQuery query = query()
                .searchScope(SearchScope.SUBTREE)
                .base("")
                .countLimit(resultSizeLimit)
                .attributes(attrs)
                .where("objectCategory").is("domainDNS")
                .and("objectClass").is("domainDNS")
                .and("dc").like("*");
        try {
            List<Domain> domains = ldapTemplate.search(query, new DomainAttributesMapper());
            if (domains.size() != 1)
                throw new ArrayStoreException();
            return domains.get(0);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Domain> getListOfRootDomain(){
        Domain rootDomain = getCurrentDomain();
        List<Domain> trustedRootDomains = getTreeRootDomain(rootDomain);
        return trustedRootDomains;
    }

    @Override
    public List<Domain> getTreeRootDomain(Domain rootDomain){
        String[] attrs = new String[]{"distinguishedName", "cn"};
        LdapQuery query = query()
                .searchScope(SearchScope.SUBTREE)
                .countLimit(resultSizeLimit)
                .attributes(attrs)
                .base(LdapUtils.emptyLdapName())
                .where("objectCategory").is("trustedDomain")
                .and("objectClass").is("trustedDomain")
                .and("cn").not().like("*."+rootDomain.getCN());

        try {
            List<Domain> treeDomains = ldapTemplate.search(query, new DomainAttributesMapper());
            treeDomains.add(rootDomain);
            return treeDomains;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return new ArrayList<>();//
        }
    }

    @Override
    public void getListOfChildDomains(Domain currDomain){
        String[] attrs = new String[]{"distinguishedName", "cn"};
        LdapQuery query = query()
                .searchScope(SearchScope.SUBTREE)
                .countLimit(resultSizeLimit)
                .attributes(attrs)
                .base(LdapUtils.emptyLdapName())
                .where("objectCategory").is("trustedDomain")
                .and("objectClass").is("trustedDomain")
                .and("cn").like("*."+currDomain.getCN())
                .and("cn").not().like("*.*."+currDomain.getCN());
        try {
            List<Domain> childs = ldapTemplate.search(query, new DomainAttributesMapper());
            System.out.println("size of child Domain " + childs.size());
            //try{Thread.sleep(1000000);}catch (Exception ex){}
            currDomain.setReferences(childs);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }

    @Override
    public List<OrgUnit> getListOfOrgUnits(Domain currDomain, LdapTemplate ldapTemplate){
        LdapQuery query = query()
                .base("")
                .where("objectCategory").is("organizationalUnit");
        try {
            OrgUnitContextMapper orgUnitContextMapper = new OrgUnitContextMapper(ldapTemplate);
            OrgUnitLdapImport orgUnitLdapImport = new OrgUnitLdapImport(ldapTemplate, ldapTemplate.getContextSource(), orgUnitContextMapper, query);
            List<OrgUnit> orgUnits = orgUnitLdapImport.getOnlyActiveGoodWayMapped();
            currDomain.setOrgUnits(orgUnits);
            return orgUnits;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return new ArrayList<>();
        }
    }

    private String getOuFromDn(String dn){
        try {
            int index = dn.indexOf(",DC");
            String answer = "";
            for (int i = 0; i < index; i++)
                answer += dn.toCharArray()[i];
            return answer;
        }catch (Exception ex){
            LOG.error(ex.getMessage());
            return "";
        }
    }

    @Override
    public List<UserInfo> getListOfUsers(OrgUnit orgUnit, LdapTemplate ldapTemplate){
        LdapQuery query = query()
                .base(getOuFromDn(orgUnit.getOrgUnitName()))
                .where("objectCategory").is("user");
        try {
            UserContextMapper userContextMapper = new UserContextMapper(ldapTemplate);
            UserInfoLdapImport userInfoLdapImport = new UserInfoLdapImport(ldapTemplate, ldapTemplate.getContextSource(), userContextMapper, query);
            List<UserInfo> userInfos = userInfoLdapImport.getOnlyActiveGoodWayMapped(userContextMapper, getOuFromDn(orgUnit.getOrgUnitName()));

            orgUnit.setUserInfos(userInfos);
            return userInfos;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean auth(LdapTemplate ldapTemplate, Admin admin){
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("userPrincipalName", admin.getUserPrincipalName()));
        try {
            ldapTemplate.authenticate("", filter.toString(), admin.getPassword());
            return true;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return false;
        }
    }

    private LdapTemplate createLdapTemplate(Domain currentDomain, Admin admin){
        LdapContextSource source = new LdapContextSource();
        source.setUrl("ldap://" + currentDomain.getCN());
        source.setBase(currentDomain.getDistName());
        source.setUserDn(admin.getUserPrincipalName());
        source.setPassword(admin.getPassword());
        source.afterPropertiesSet();
        LdapTemplate ldapTemplate = new LdapTemplate(source);
        ldapTemplate.setIgnorePartialResultException(true);
        return ldapTemplate;
    }

    @Override
    public void importThisTree(Domain currentDomain, Admin admin){
        LdapTemplate ldapTemplate = createLdapTemplate(currentDomain, admin);
        if (auth(ldapTemplate, admin)) {
            System.out.println("auth accept to " + currentDomain.getCN());
            getListOfChildDomains(currentDomain);
            List<OrgUnit> listOfOrgUnits = getListOfOrgUnits(currentDomain, ldapTemplate);
            for (OrgUnit orgUnit : listOfOrgUnits) {
                getListOfUsers(orgUnit, ldapTemplate);
            }
            for (Domain childDomain : currentDomain.getReferences())
                importThisTree(childDomain, admin);
        }
        else
            System.out.println("auth decline to " + currentDomain.getCN());
    }
}
