package syncronization.model;

import com.google.gson.Gson;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;

import javax.naming.Name;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "user_info")
@Entry(objectClasses = { "person", "top" })
public class UserInfo {
    @org.springframework.ldap.odm.annotations.Id
    @Transient
    private Name dn;

    public UserInfo() {
    }

    public void setAfterProperties(){
        if (lastName == null)
            this.lastName = "lastName";
        if (midleName == null)
            this.midleName = "middleName";
        if (phones == null)
            this.phones = this.email;
        if (position == null)
            this.position = "position";
    }

    @Override
    public boolean equals(Object obj) {
        UserInfo newUserInfo = (UserInfo)obj;
        if (newUserInfo.getEmail().equals(email) && newUserInfo.getFirstName().equals(firstName) &&
                newUserInfo.getLastName().equals(lastName) && newUserInfo.getMidleName().equals(midleName) &&
                newUserInfo.getPhones().equals(phones) && newUserInfo.getPosition().equals(position))
            return true;
        else
            return false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @Attribute(name = "cn")
    private String firstName;

    @Column(name = "last_name")
    @Attribute(name = "sn")
    private String lastName;

    @Column(name = "midle_name")
    @Attribute(name = "middleName")
    private String midleName;

    //FIXME
    //https://stackoverflow.com/questions/25738569/jpa-map-json-column-to-java-object
    @Column(name = "phones")
    @Attribute(name = "phone")
    private String phones;

    @Column(name = "email")
    @Attribute(name="distinguishedName") //change to mail
    private String email;

    @Column(name = "org_unit_id")
    @org.springframework.ldap.odm.annotations.Transient
    private Long orgUnitId;

    @Column(name = "position")
    @Attribute(name="title")
    private String position;

    //Здесь есть неразрешенная ошибка
    //OneToOne не хочет маппиться - хз в чем дело, ошибка заведена
    //Будем разбираться
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="user_info_id", referencedColumnName="id", updatable = false)
    @org.springframework.ldap.odm.annotations.Transient
    private Collection<UserCredentials> userCredentials;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }


    @Transient
    @org.springframework.ldap.odm.annotations.Transient
    private boolean interactionAllowed;

    @Transient
    @org.springframework.ldap.odm.annotations.Transient
    private List<Timestamp> requestCreationTimestamps;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMidleName() {
        return midleName;
    }

    public void setMidleName(String midleName) {
        this.midleName = midleName;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getOrgUnitId() {
        return orgUnitId;
    }

    public void setOrgUnitId(Long orgUnitId) {
        this.orgUnitId = orgUnitId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public boolean isInteractionAllowed() {
        return interactionAllowed;
    }

    public void setInteractionAllowed(boolean interactionAllowed) {
        this.interactionAllowed = interactionAllowed;
    }


    public UserCredentials getUserCredentials() {
        if(userCredentials != null && userCredentials.size() !=0 ) {
            List<UserCredentials> userList = new ArrayList<UserCredentials>(userCredentials );
            return userList.get(0);
        }
        return null;
    }

    public void setUserCredentials(UserCredentials userCredentials) {
        List<UserCredentials> userList = new ArrayList<UserCredentials>();
        userList.add(0, userCredentials);
        this.userCredentials = new HashSet<>(userList);
    }

    public List<Timestamp> getRequestCreationTimestamps() {
        return requestCreationTimestamps;
    }

    public void setRequestCreationTimestamps(List<Timestamp> requestCreationDates) {
        this.requestCreationTimestamps = requestCreationDates;
    }

    public void addRequestTimestamp(Timestamp timestamp) {

        if(this.requestCreationTimestamps == null) {
            this.requestCreationTimestamps = new ArrayList<>();
        }

        requestCreationTimestamps.add(timestamp);
    }
}
