package syncronization.model;

import com.google.gson.Gson;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;

import javax.naming.Name;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "domain")
@Entry(objectClasses = { "top", "domain" })
public class Domain {
    @org.springframework.ldap.odm.annotations.Id
    @Transient
    private Name dn;

    @Override
    public boolean equals(Object obj) {
        Domain newDomain = (Domain)obj;
        if (this.domainName.equals(newDomain.getDomainName()) &&
                this.domainType == newDomain.getDomainType())
            return true;
        else
            return false;
    }

    public List<Domain> getReferences() {
        return references;
    }

    public void setReferences(List<Domain> references) {
        this.references = references;
    }
    @Transient
    @org.springframework.ldap.odm.annotations.Transient
    private List<Domain> references;
    public String getCN() {
        return CN;
    }

    public void setCN(String CN) {
        this.CN = CN;
        this.domainName = CN;
        this.domainType = 0L;
    }
    @Transient
    @Attribute(name="cn")
    private String CN;

    public String getDistName() {
        return DistName;
    }

    public void setDistName(String distName) {
        DistName = distName;
    }
    @Transient
    @Attribute(name="distinguishedName")
    private String DistName;

    //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @org.springframework.ldap.odm.annotations.Transient
    private Long id;


    @Column(name = "domain_name")
    @Attribute(name="cn")
    private String domainName;

    //TODO описать это
    //0 - ручной, 1 - внешний
    @Column(name = "domain_type")
    @org.springframework.ldap.odm.annotations.Transient
    private Long domainType = 1L;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="domain_id", referencedColumnName="id", updatable = false)
    @org.springframework.ldap.odm.annotations.Transient
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

        //for(OrgUnit orgUnit : orgUnits) {
           // orgUnit.setUserInfos(null);
       // }


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
