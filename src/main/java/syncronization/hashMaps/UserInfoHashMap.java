package syncronization.hashMaps;

import syncronization.model.OrgUnit;
import syncronization.model.UserInfo;

import java.util.Collection;
import java.util.HashMap;

public class UserInfoHashMap extends HashMap<String, UserInfo> {
    private OrgUnit orgUnit;

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
