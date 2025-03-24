public class Person {
    private String name;
    private String address;    
    private String mobileNumber;

    public Person() {
    }

    public Person(String name, String address, String mobileNumber) {
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;        
    }

    public String getname() {
        return this.name;
    }

    public String getaddress() {
        return this.address;
    }

    public String getmobileNo() {
        return this.mobileNumber;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setMobileNo(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}