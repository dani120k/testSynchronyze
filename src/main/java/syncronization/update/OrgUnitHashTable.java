package syncronization.update;

import syncronization.model.OrgUnit;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class OrgUnitHashTable extends HashMap<String, OrgUnit> {
    private UserInfoHashTable userInfoHashTable;

    public OrgUnitHashTable(){
        userInfoHashTable = new UserInfoHashTable();
    }

    public UserInfoHashTable getUserInfoHashTable() {
        return userInfoHashTable;
    }

    public void setUserInfoHashTable(UserInfoHashTable userInfoHashTable) {
        this.userInfoHashTable = userInfoHashTable;
    }

    public void importing(List<OrgUnit> orgUnitList){
        for(OrgUnit orgUnit : orgUnitList){
            this.put(orgUnit.getOrgUnitName(), orgUnit);
            userInfoHashTable.importing(orgUnit.getUserInfos());
        }
    }
}
