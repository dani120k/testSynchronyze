package syncronization.last_test;

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

    public List<OrgUnit> getAllOrgUnits(){
        Collection<OrgUnitHashMap> orgUnits = this.values();
        System.out.println("size is " + orgUnits.size());
        List<OrgUnit> orgUnits1 = new ArrayList<>();
        for(OrgUnitHashMap orgUnit : orgUnits){
            orgUnits1.add(orgUnit.getOrgUnit());
        }
        return orgUnits1;
    }

    public String getDomainName(){
        return domain.getDomainName();
    }

    private void importFromList(){
        System.out.println("size of real org units is " + domain.getOrgUnits().size());
        for(OrgUnit orgUnit : domain.getOrgUnits()){
            this.put(orgUnit.getOrgUnitName(), new OrgUnitHashMap(orgUnit));
        }

    }
}
