package syncronization.hashMaps;

import syncronization.model.OrgUnit;
import syncronization.model.UserInfo;

import java.util.HashMap;

public class OrgUnitHashMap extends HashMap<String, UserInfo> {
    private OrgUnit orgUnit;

    public OrgUnitHashMap(OrgUnit orgUnit){
        this.orgUnit = orgUnit;
        importFromList();
    }

    public OrgUnit getOrgUnit(){
        return orgUnit;
    }

    private void importFromList(){
        for(UserInfo userInfo : orgUnit.getUserInfos()) {
            this.put(userInfo.getEmail(), userInfo);
        }
    }
}
