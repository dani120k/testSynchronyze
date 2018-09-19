package syncronization.update;

import org.springframework.security.core.userdetails.User;
import syncronization.model.UserInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class UserInfoHashTable extends HashMap<String, UserInfo> {
    public void importing(Collection<UserInfo> userInfos){
        for(UserInfo userInfo : userInfos){
            this.put(userInfo.getEmail(), userInfo);
        }
    }
}
