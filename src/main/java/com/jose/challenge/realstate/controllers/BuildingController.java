package com.jose.challenge.realstate.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import com.jose.challenge.realstate.dtos.BuildingDto;
import com.jose.challenge.realstate.entities.BuildingEntity;
import com.jose.challenge.realstate.services.BuildingService;

@Controller
@RequestMapping("/v1/api")
public class BuildingController {
    @Autowired 
    private BuildingService buildingService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/home")
    public String getHome(Model modelo) {
        List<BuildingDto> lBuildingDtos = new ArrayList<>();
        BuildingDto buildingDto =  new BuildingDto();
        lBuildingDtos = buildingService.getAllBuildings();
        modelo.addAttribute("mensaje","hola desde thymeleaf");
        modelo.addAttribute("dto",new BuildingDto());
        
        modelo.addAttribute("lista",lBuildingDtos);
        logger.info("size|: "+lBuildingDtos.size());
       
        return "home";
    }

    @GetMapping("/detail")
    public String getBuildDetail(Model modelo) {
        
        return "detail";
    }

    @GetMapping("/inmuebles")
    public List<BuildingEntity> getAllBuildings(){
        List<BuildingEntity> list = new ArrayList<>();
        return list;
    }

    @GetMapping(path = "/inmuebles/detail")
    public String goToDetail(@RequestParam String id, Model model){
        System.out.println("id: "+id);
        BuildingDto dto = new BuildingDto();
        dto = buildingService.getOneBuildingById(Long.valueOf(id));

        
        model.addAttribute("dto", dto);
        
        return "detail";
    }

    @PostMapping("/inmueblecrear")
    public String createNewBuilding(@ModelAttribute("dto") BuildingDto building, @RequestParam("file") MultipartFile file){
        String result = "Fallo al guardar";
        byte [] arrBye;
        String mFile = "";
        try{
            arrBye = file.getBytes();
            mFile = Base64.encodeBase64String(arrBye);
            List<String> list =  new ArrayList<>();
            list.add(mFile);
            building.setImageList(list);
            
        }catch(Exception ex){
            logger.info(""+ex);
            
        }

        
        result = buildingService.createNewbuilding(building);
        logger.info("result|: "+result);
        return "registerresult";
    }


    @PostMapping("/inmuebles/update")
    public String updateBuilding(@ModelAttribute("dto") BuildingDto dto, @RequestParam("file") MultipartFile file){
        
        String result = "Fallo al guardar";
        byte [] arrBye;
        String mFile = "";
        try{
            arrBye = file.getBytes();
            mFile = Base64.encodeBase64String(arrBye);
            List<String> list =  new ArrayList<>();
            list.add(mFile);
            dto.setImageList(list);
            
        }catch(Exception ex){
            logger.info(""+ex);
            
        }

        
        result = buildingService.updateBuilding(dto);
        logger.info("result|: "+result);
        return "redirect:/v1/api/home";
    }

    @PostMapping("/inmuebles/{id}")
    public String deleteBuilding(@RequestParam long id){
        String result = "Fallo al guardar";
        return result;
    }

}
