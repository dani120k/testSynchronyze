package syncronization.importFrom.interfaces;

import org.springframework.ldap.core.LdapTemplate;
import syncronization.model.Admin;
import syncronization.model.Domain;
import syncronization.model.OrgUnit;
import syncronization.model.UserInfo;

import java.util.List;

public interface IStructure {

    List<Domain> getListOfRootDomain();

    List<OrgUnit> getListOfOrgUnits(Domain currDomain, LdapTemplate ldapTemplate);

    List<UserInfo> getListOfUsers(OrgUnit orgUnit, LdapTemplate ldapTemplate);

    void importThisTree(Domain domain, Admin admin);

    List<Domain> getTreeRootDomain(Domain domain);

    void getListOfChildDomains(Domain domain);
}
