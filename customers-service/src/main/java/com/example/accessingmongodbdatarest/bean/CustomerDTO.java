package com.example.accessingmongodbdatarest.bean;

public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Long age;

    public CustomerDTO(String firstName, String lastName, String email, String address, Long age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.age = age;
    }
    public String getFirstName() { return firstName;}
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() {  return email;  }
    public void setEmail(String email) {this.email = email; }
    public String getAddress() { return address;}
    public void setAddress(String address) { this.address = address; }
    public Long getAge() { return age; }
    public void setAge(Long age) {this.age = age; }
}

