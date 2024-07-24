package com.jose.challenge.realstate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jose.challenge.realstate.dtos.BuildingDto;
import com.jose.challenge.realstate.entities.BuildingImageEntity;
import com.jose.challenge.realstate.respositories.BuildingImageRepository;
import com.jose.challenge.realstate.respositories.BuildingRepository;

import java.util.*;

@Service
public class BuildingImageService {

    @Autowired
    private BuildingImageRepository buildingRepository;

    public List<BuildingImageEntity> getImagesById(long id){
        List<BuildingImageEntity> recoveredImages = new ArrayList<>();
        List<BuildingImageEntity> filteredImages = new ArrayList<>();

        recoveredImages = buildingRepository.findAll();
        if(!recoveredImages.isEmpty()){
            Collections.sort(recoveredImages, Comparator.comparing(BuildingImageEntity::getId));
            for(BuildingImageEntity entity: recoveredImages){
                if(entity.getBuildingId() == id){
                    filteredImages.add(entity);
                }
            }
        }

        return filteredImages;
    }
}
