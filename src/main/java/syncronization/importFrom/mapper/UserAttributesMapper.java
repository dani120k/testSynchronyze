package syncronization.importFrom.mapper;

import org.springframework.ldap.core.AttributesMapper;
import syncronization.model.UserInfo;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class UserAttributesMapper implements AttributesMapper<UserInfo> {
    public UserInfo mapFromAttributes(Attributes attrs) throws NamingException {
        UserInfo userInfo = new UserInfo();
        try
        {
            userInfo.setFirstName((String)attrs.get("cn").get());
            if (attrs.get("mail") == null){
                userInfo.setEmail(userInfo.getFirstName());
            } else {
                userInfo.setEmail((String) attrs.get("mail").get());
            }
            userInfo.setFirstName((String)attrs.get("givenName").get());
            userInfo.setPhones((String)attrs.get("mobile").get());
            userInfo.setLastName((String)attrs.get("sn").get());
            userInfo.setPosition((String)attrs.get("title").get());
        }
        catch(Exception ex) {
            return userInfo;
        }

        return userInfo;
    }
}