package syncronization.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ContactUser {

    /**
     * id of user in contacts list (db).
     */
    private Long id;

    /**
     * @return id of current user.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id set id for current user.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * id of organization unit that user belongs to.
     */
    private Long orgUnitId;

    /**
     * @return get organization unit that user belongs to.
     */
    public Long getOrgUnitId() {
        return orgUnitId;
    }

    /**
     * @param orgUnitId set organization unit that user belongs to.
     */
    public void setOrgUnitId(Long orgUnitId) {
        this.orgUnitId = orgUnitId;
    }

    /**
     * user login (it is set in time of registration).
     */
    private String login;

    /**
     * @return user login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login set user login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * email of registered user.
     */
    private String email;

    /**
     * @return get current user email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email set email of current user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * A flag that shows ability to write and call to this user.
     */
    private Boolean interactionAllowed;

    /**
     * @return flag that shows ability to write and call to this user.
     */
    public Boolean getInteractionAllowed() {
        return interactionAllowed;
    }

    /**
     * @param interactionAllowed set flag that shows ability to write and call to this user.
     */
    public void setInteractionAllowed(Boolean interactionAllowed) {
        this.interactionAllowed = interactionAllowed;
    }

    /**
     * First name of current user.
     */
    private String firstName;

    /**
     * @return first name of current user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName set first name of current user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /**
     * last name of current user.
     */
    private String lastName;

    /**
     * @return get last name of current user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName set last name of current user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * List of phones, that belong to current user.
     */
    private List<ContactsPhone> contactPhones;



    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * @return get list  of phones, that belong to current user.
     */
    public List<ContactsPhone> getContactPhones() {
        return contactPhones;
    }

    /**
     * @param contactPhones set  list of phones, that belong to current user.
     */
    public void setContactPhones(List<ContactsPhone> contactPhones) {
        this.contactPhones = contactPhones;
    }

    /**
     * @param number phone number.
     * @param type phone type (in develop).
     */
    public void  addPhone(String number, String type) {
        if (contactPhones == null) {
            contactPhones = new ArrayList<>();
        }

        contactPhones.add(new ContactsPhone(number, type));
    }

}

