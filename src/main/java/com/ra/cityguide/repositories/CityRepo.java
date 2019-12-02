package com.ra.cityguide.repositories;

import com.ra.cityguide.Entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo extends JpaRepository<City, Long> {

    @Query(value= "SELECT * FROM CITY WHERE NAME=?1", nativeQuery = true)
    City findByNameList(String name);
 }
