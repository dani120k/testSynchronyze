package syncronization.last_test;

import org.springframework.beans.factory.annotation.Autowired;
import syncronization.model.OrgUnit;
import syncronization.model.OrgUnitService;
import syncronization.model.UserInfo;

import java.util.HashMap;

public class OrgUnitHashMap extends HashMap<String, UserInfo> {
    private OrgUnit orgUnit;
    @Autowired
    private OrgUnitService orgUnitService;

    public OrgUnitHashMap(OrgUnit orgUnit){
        this.orgUnit = orgUnit;
        importFromList();
    }

    public OrgUnit getOrgUnit(){
        return orgUnit;
    }

    public String getOrgUnitName(){
        return orgUnit.getOrgUnitName();
    }

    private void importFromList(){
        for(UserInfo userInfo : orgUnit.getUserInfos()) {
            this.put(userInfo.getEmail(), userInfo);
        }
    }
}
