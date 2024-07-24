package com.jose.challenge.realstate.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jose.challenge.realstate.entities.BuildingEntity;

@Repository
public interface BuildingRepository extends JpaRepository<BuildingEntity, Long>{

}
