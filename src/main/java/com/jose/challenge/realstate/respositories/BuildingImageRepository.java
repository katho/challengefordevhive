package com.jose.challenge.realstate.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jose.challenge.realstate.entities.BuildingImageEntity;
import java.util.*;

public interface BuildingImageRepository extends JpaRepository<BuildingImageEntity, Long>{
    List<BuildingImageEntity> findByBuildingId(long id);
}
