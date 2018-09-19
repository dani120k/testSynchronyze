package syncronization;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import syncronization.model.UserInfo;

public class UserContextMapper implements ContextMapper<UserInfo> {
    LdapTemplate ldapTemplate;

    public UserContextMapper(LdapTemplate ldapTemplate){
        this.ldapTemplate = ldapTemplate;
    }

    public UserInfo mapFromContext(Object ctx) {
        UserInfo user = ldapTemplate.getObjectDirectoryMapper().mapFromLdapDataEntry((DirContextOperations) ctx, UserInfo.class);
        return user;
    }
}