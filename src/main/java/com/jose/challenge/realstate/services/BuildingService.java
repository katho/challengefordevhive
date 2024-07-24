package com.jose.challenge.realstate.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jose.challenge.realstate.dtos.BuildingDto;
import com.jose.challenge.realstate.entities.BuildingEntity;
import com.jose.challenge.realstate.entities.BuildingImageEntity;
import com.jose.challenge.realstate.respositories.BuildingImageRepository;
import com.jose.challenge.realstate.respositories.BuildingRepository;
import java.util.*;
import org.apache.tomcat.util.codec.binary.Base64;


@Service
public class BuildingService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingImageRepository buildingImageRespository;

    public String createNewbuilding(BuildingDto buildingDto){
        String result = "Fallo al crear registro";
        BuildingEntity entity = new BuildingEntity();
        List<String> imageList = buildingDto.getImageList();
        List<BuildingEntity> lEntities = buildingRepository.findAll();
        long newId = 1;
        if(!lEntities.isEmpty()){
            Collections.sort(lEntities, Comparator.comparing(BuildingEntity::getId));
            newId += lEntities.getLast().getId();
        }
        logger.info("newId: "+newId);
        entity.setAddressStreet(buildingDto.getAddressStreet());
        entity.setCapacity(buildingDto.getCapacity());
        entity.setId(newId);
        entity.setName(buildingDto.getName());
        entity.setPhoneNumber(buildingDto.getPhoneNumber());
        List<BuildingImageEntity> imageEntities = buildingImageRespository.findAll();
        long newImageId = 1;
        if(!imageEntities.isEmpty()){
            Collections.sort(imageEntities, Comparator.comparing(BuildingImageEntity::getId));
            newImageId += imageEntities.getLast().getId();
        }
        List<BuildingImageEntity> toSave = new ArrayList<>();
        
        try{
            logger.info("entity: "+entity.toString());
            BuildingEntity saved = buildingRepository.saveAndFlush(entity);
            logger.info("Saved");
             
            for(String item: imageList){
                BuildingImageEntity buildingImageEntity = new BuildingImageEntity();
                buildingImageEntity.setBuildingId(saved.getId());
                buildingImageEntity.setId(newImageId);
                buildingImageEntity.setImageData(Base64.decodeBase64(item));
                buildingImageRespository.saveAndFlush(buildingImageEntity);
                newImageId++;
            }
            result = "Registro creado con éxito";
            logger.info(result);
        }catch(Exception ex){
            logger.info("createNewbuilding:", ex);
        }
        return result;
    }

    public List<BuildingDto> getAllBuildings(){
        List<BuildingDto> lBuildingDtos = new ArrayList<>();
        List<BuildingEntity> recoverEntities = buildingRepository.findAll();
        if(!recoverEntities.isEmpty()){
            Collections.sort(recoverEntities, Comparator.comparing(BuildingEntity::getId));
            for(BuildingEntity entity: recoverEntities){
                BuildingDto dto = new BuildingDto();
                dto.setAddressStreet(entity.getAddressStreet());
                dto.setCapacity(entity.getCapacity());
                dto.setId(entity.getId());
                dto.setName(entity.getName());
                dto.setPhoneNumber(entity.getPhoneNumber());
                lBuildingDtos.add(dto);
            }
        }
        List<BuildingImageEntity> lImageEntities = new ArrayList();
        
        lImageEntities = buildingImageRespository.findAll();
        for(BuildingDto dto: lBuildingDtos){
            List<String> filteredImages = new ArrayList();
            for(BuildingImageEntity image: lImageEntities){
                if(dto.getId() == image.getBuildingId()){
                    filteredImages.add(image.generateBase64Image());
                }
            }
            dto.setImageList(filteredImages);
        }
        
        return lBuildingDtos;
    }

    public BuildingDto getOneBuildingById(long id){
        BuildingDto buildingDto =  new BuildingDto();
        Optional<BuildingEntity> entity = buildingRepository.findById(id);
        if(entity.isPresent())
        {
            buildingDto.setAddressStreet(entity.get().getAddressStreet());
            buildingDto.setCapacity(entity.get().getCapacity());
            buildingDto.setId(entity.get().getId());
            buildingDto.setName(entity.get().getName());
            buildingDto.setPhoneNumber(entity.get().getPhoneNumber());
        }
        List<BuildingImageEntity> lImageEntities = new ArrayList();
        List<String> filteredImages = new ArrayList();
        
        lImageEntities = buildingImageRespository.findAll();
        for(BuildingImageEntity image: lImageEntities){
            if(buildingDto.getId() == image.getBuildingId()){
                filteredImages.add(image.generateBase64Image());
            }
        }
        buildingDto.setImageList(filteredImages);

        return buildingDto;
    }

    public String updateBuilding(BuildingDto buildingDto){
        String result = "Fallo al actualizar registro";
        logger.info("Id: "+buildingDto.getId());
        Optional<BuildingEntity> entity = buildingRepository.findById(buildingDto.getId());
        logger.info("entity: "+entity.isPresent());
        if(entity.isPresent())
        {
            entity.get().setAddressStreet(buildingDto.getAddressStreet());
            entity.get().setCapacity(buildingDto.getCapacity());
            entity.get().setName(buildingDto.getName());
            entity.get().setPhoneNumber(buildingDto.getPhoneNumber());
            buildingRepository.saveAndFlush(entity.get());
            logger.info("Update ok");
            
            List<String> toWrite = buildingDto.getImageList();
            List<BuildingImageEntity> lBuildingImageEntities = new ArrayList<>();
            
            if(!toWrite.isEmpty()){
                for(String item: toWrite){
                    BuildingImageEntity imageEntity = new BuildingImageEntity();
                    imageEntity.setBuildingId(buildingDto.getId());
                    imageEntity.setImageData(Base64.decodeBase64(item));
                    buildingImageRespository.save(imageEntity);
                }
            }
            
            result = "Registro actualizado";
        }

        return result;
    }


    public String deleteBuilding(long id){
        String result = "Fallo al actualizar registro";
        
        try{
            buildingRepository.deleteById(id);
            result = "Registro borrado";
        }catch(Exception ex){
            logger.info("deleteBuilding: ", ex);
        }

        return result;
    }

}
