package syncronization.test;

import syncronization.model.Domain;
import syncronization.model.OrgUnit;
import syncronization.model.UserInfo;

import java.util.Hashtable;
import java.util.List;

public class ImportHashTable {
    Hashtable<String, Domain> domainHashtable;
    Hashtable<String, OrgUnit> orgUnitHashtable = new Hashtable<>();
    Hashtable<String, UserInfo> userInfoHashtable = new Hashtable<>();

    public Hashtable<String, Domain> getDomainHashtable(){
        return domainHashtable;
    }

    public Hashtable<String, OrgUnit> getOrgUnitHashtable(){
        return orgUnitHashtable;
    }

    public Hashtable<String, UserInfo> getUserInfoHashtable(){
        return userInfoHashtable;
    }


    public void importHashTable(List<Domain> domainList){
        domainHashtable = new Hashtable<>();
        for(Domain domain : domainList) {
            domainHashtable.put(domain.getDomainName(), domain);
            importOrgUnits(domain);
        }
    }

    public void importOrgUnits(Domain domain){
        for(OrgUnit orgUnit : domain.getOrgUnits()){
            orgUnitHashtable.put(orgUnit.getOrgUnitName(), orgUnit);
            importUsers(orgUnit);
        }
    }

    public void importUsers(OrgUnit orgUnit){
        for(UserInfo userInfo : orgUnit.getUserInfos()) {
            userInfoHashtable.put(userInfo.getEmail(), userInfo);
        }
    }


}
