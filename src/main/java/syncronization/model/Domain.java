package syncronization.model;


import com.google.gson.Gson;

import java.util.List;

public class Domain {

    private Long id;
    private String domainName;
    //0 - ручной, 1 - внешний
    private Long domainType;

    private List<OrgUnit> orgUnits;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Long getDomainType() {
        return domainType;
    }

    public void setDomainType(Long domainType) {
        this.domainType = domainType;
    }

    @Override
    public String toString() {

        //Так как нам нужны только домены и отделы
        //мы просто отсекаем все, что в отделах находится

        /*for(OrgUnit orgUnit : orgUnits) {
            orgUnit.setContactUsers(null);
        }
*/

        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public List<OrgUnit> getOrgUnits() {
        return orgUnits;
    }

    public void setOrgUnits(List<OrgUnit> orgUnits) {
        this.orgUnits = orgUnits;
    }
}
