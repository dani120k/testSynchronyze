package syncronization.hashMaps;

import org.springframework.beans.factory.annotation.Autowired;
import syncronization.model.OrgUnit;
import syncronization.model.UserInfo;
import syncronization.model.UserInfoService;

import java.util.Collection;
import java.util.HashMap;

public class UserInfoHashMap extends HashMap<String, UserInfo> {
    private OrgUnit orgUnit;
    @Autowired
    UserInfoService userInfoService;

    public UserInfoHashMap(OrgUnit orgUnit){
        this.orgUnit = orgUnit;
        importFromList();
    }

    private void importFromList(){
        try {
            Collection<UserInfo> userInfos = orgUnit.getUserInfos();
            if (userInfos != null) {
                for (UserInfo userInfo : userInfos) {
                    this.put(userInfo.getEmail(), userInfo);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
