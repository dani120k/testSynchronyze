package syncronization.model;

import com.google.gson.Gson;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
public class HierarchyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;


    @Column(name = "group_name")
    private String groupName;

    @Column(name = "parent_id")
    private Long parentId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="group_id", referencedColumnName="group_id",updatable = false)
    private List<OrgUnit> orgUnits;



    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<OrgUnit> getOrgUnits() {
        return orgUnits;
    }

    public void setOrgUnits(List<OrgUnit> orgUnits) {
        this.orgUnits = orgUnits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
