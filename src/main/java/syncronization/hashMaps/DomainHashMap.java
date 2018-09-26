package syncronization.hashMaps;

import syncronization.model.Domain;
import syncronization.model.OrgUnit;
import java.util.*;

public class DomainHashMap extends HashMap<String, OrgUnitHashMap> {
    Domain domain;

    public DomainHashMap(Domain domain){
        this.domain = domain;
        importFromList();
    }

    public Domain getDomain(){
        return domain;
    }

    private void importFromList(){
        System.out.println("size of real org units is " + domain.getOrgUnits().size());
        for(OrgUnit orgUnit : domain.getOrgUnits()){
            this.put(orgUnit.getOrgUnitName(), new OrgUnitHashMap(orgUnit));
        }

    }
}
