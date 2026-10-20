
package com.example.kursovaya;

public class Customer {

    private String phone;
    private String fullName;
    private String email;
    private String address;

    public Customer() {
    }

    public Customer(String phone, String fullName,
                    String email, String address) {
        this.phone = phone;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
