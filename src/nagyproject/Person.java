package nagyproject;

import javafx.beans.property.SimpleStringProperty;

public class Person {

    private final SimpleStringProperty name;
    private final SimpleStringProperty phonenumber;
    private final SimpleStringProperty email;
    private final SimpleStringProperty contactID;

    public Person(Integer contactID, String name, String phonenumber, String email ) {
        this.name = new SimpleStringProperty(name);
        this.phonenumber = new SimpleStringProperty(phonenumber);
        this.email = new SimpleStringProperty(email);
        this.contactID = new SimpleStringProperty(String.valueOf(contactID));
    }

    public Person() {
        this.name = new SimpleStringProperty("");
        this.phonenumber = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.contactID = new SimpleStringProperty("");
    }
    
  public Person(String name, String phonenumber, String email) {
        this.name = new SimpleStringProperty(name);
        this.phonenumber = new SimpleStringProperty(phonenumber);
        this.email = new SimpleStringProperty(email);
        this.contactID = new SimpleStringProperty("");
    }
    //setters
    public void setcontactID(String bcontactID) {
        contactID.set(bcontactID);
    }

    public void setName(String bName) {
        name.set(bName);
    }

    public void setNumber(String bNumber) {
        phonenumber.set(bNumber);
    }

    public void setEmail(String bEmail) {
        name.set(bEmail);
    }
    /////////////getters

    public String getName() {
        return name.get();
    }

    public String getcontactID() {
        return contactID.get();
    }

    public String getPhonenumber() {
        return phonenumber.get();
    }

    public String getEmail() {
        return email.get();
    }

}
