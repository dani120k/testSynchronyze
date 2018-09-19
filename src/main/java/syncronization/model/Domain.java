package syncronization.model;

import com.google.gson.Gson;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "domain")
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "domain_name")
    private String domainName;

    //TODO описать это
    //0 - ручной, 1 - внешний
    @Column(name = "domain_type")
    private Long domainType;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="domain_id", referencedColumnName="id", updatable = false)
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

        for(OrgUnit orgUnit : orgUnits) {
            orgUnit.setUserInfos(null);
        }


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
