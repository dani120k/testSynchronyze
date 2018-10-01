package syncronization.importFrom.normalMapper;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import syncronization.model.Domain;
import syncronization.model.OrgUnit;

public class OrgUnitContextMapper implements ContextMapper<OrgUnit> {
    LdapTemplate ldapTemplate;

    public OrgUnitContextMapper(LdapTemplate ldapTemplate){
        this.ldapTemplate = ldapTemplate;
    }

    public OrgUnit mapFromContext(Object ctx) {
        OrgUnit orgUnit = ldapTemplate.getObjectDirectoryMapper().mapFromLdapDataEntry((DirContextOperations) ctx, OrgUnit.class);
        return orgUnit;
    }
}
