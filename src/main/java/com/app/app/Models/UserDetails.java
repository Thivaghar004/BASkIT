package com.app.app.Models;

import jakarta.persistence.*;



@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "phone_no")
    private Long phoneNo;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @Column(name = "address")
    private String address;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {

        this.userId = userId;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDetails(String address, Location location, String password, String email, String name, Long phoneNo, Long userId) {
        this.address = address;
        this.location = location;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phoneNo = phoneNo;
        this.userId = userId;
    }

    public UserDetails() {
    }
}