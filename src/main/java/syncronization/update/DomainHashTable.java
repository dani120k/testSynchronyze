package syncronization.update;

import syncronization.model.Domain;
import syncronization.model.OrgUnit;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class DomainHashTable extends HashMap<String, Domain> {
    private OrgUnitHashTable orgUnitHashTable;

    public OrgUnitHashTable getOrgUnitHashTable() {
        return orgUnitHashTable;
    }

    public void setOrgUnitHashTable(OrgUnitHashTable orgUnitHashTable){
        this.orgUnitHashTable = orgUnitHashTable;
    }

    public DomainHashTable(){
        orgUnitHashTable = new OrgUnitHashTable();
    }

    public void importing(List<Domain> domains){
        for(Domain domain: domains){
            this.put(domain.getDomainName(), domain);
            orgUnitHashTable.importing(domain.getOrgUnits());
        }
    }
}
