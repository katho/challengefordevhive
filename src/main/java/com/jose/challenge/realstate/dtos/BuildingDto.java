package com.jose.challenge.realstate.dtos;

import lombok.Data;
import java.util.*;


public class BuildingDto {

    private long id;
    private String name;
    private String addressStreet;
    private String phoneNumber;
    private String capacity;
    private List<String> imageList;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddressStreet() {
        return addressStreet;
    }
    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getCapacity() {
        return capacity;
    }
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
    public List<String> getImageList() {
        return imageList;
    }
    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
    @Override
    public String toString() {
        return "BuildingDto [id=" + id + ", name=" + name + ", addressStreet=" + addressStreet + ", phoneNumber="
                + phoneNumber + ", capacity=" + capacity + ", imageList=" + imageList + "]";
    }

    

}
