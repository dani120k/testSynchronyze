package syncronization.importFrom.normalMapper;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import syncronization.model.Domain;
import syncronization.model.UserInfo;

public class DomainContextMapper implements ContextMapper<Domain> {
    LdapTemplate ldapTemplate;

    public DomainContextMapper(LdapTemplate ldapTemplate){
        this.ldapTemplate = ldapTemplate;
    }

    public Domain mapFromContext(Object ctx) {
        Domain user = ldapTemplate.getObjectDirectoryMapper().mapFromLdapDataEntry((DirContextOperations) ctx, Domain.class);
        return user;
    }
}
