package syncronization.model;

public class ContactsPhone {

    /**
     * @param phone phone number.
     * @param type type of current phone number.
     *            (Could be secret_pone (protected), mobile, work or else).
     */
    //FIXME change type to enum?
    public ContactsPhone(String phone, String type) {
        this.phoneNumber = phone;
        this.phoneType = type;
    }

    public ContactsPhone() {

    }

    /**
     * current phone number.
     */
    private String phoneNumber;

    /**
     * @return string value of phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber set string value of phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    /**
     * type of current phone number.
     *            (Could be secret_pone (protected), mobile, work or else).
     */
    private String phoneType;

    /**
     * @return get phone type.
     */
    public String getPhoneType() {
        return phoneType;
    }

    /**
     * @param phoneType set current phone type.
     */
    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }
}