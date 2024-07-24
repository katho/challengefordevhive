package com.jose.challenge.realstate.entities;

import org.apache.tomcat.util.codec.binary.Base64;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;


@Entity(name = "buildingimage")

public class BuildingImageEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private long buildingId;

    @Lob
    private byte[] imageData;
    
    public String generateBase64Image() {
        return Base64.encodeBase64String(this.imageData);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public long getId() {
        return id;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public byte[] getImageData() {
        return imageData;
    }



}
