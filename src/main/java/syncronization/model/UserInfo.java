package syncronization.model;

import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class UserInfo {

    public UserInfo() {
    }

    private Long id;
    private String firstName;
    private String lastName;

    private String midleName;

    private String phones;

    private String email;
    private Long orgUnitId;
    private String position;

    private Collection<UserCredentials> userCredentials;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }


    private boolean interactionAllowed;

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
