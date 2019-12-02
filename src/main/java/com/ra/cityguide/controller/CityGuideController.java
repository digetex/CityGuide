package com.ra.cityguide.controller;

import com.ra.cityguide.Entity.City;
import com.ra.cityguide.bot.Bot;
import com.ra.cityguide.repositories.CityRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("city")
public class CityGuideController {
    private final CityRepo cityRepo;
    private Bot bot = new Bot();

    @Autowired
    public CityGuideController(CityRepo cityRepo) {
        this.cityRepo = cityRepo;
    }

    @GetMapping
    public List<City> list() {
        return cityRepo.findAll();
    }

    @GetMapping("{id}")
    public City getOne(@PathVariable("id") City city) {
        if (bot.CheckUsersNotNull()) {
            bot.sendCity("Здравствуйте. Вы выбрали город " + city.getName());
            bot.sendCity("Советуем вам посетить: " + city.getGoodplaces());
            bot.sendCity("И лучше не суйтесь в: " + city.getBadplaces());
        }
        return city;
    }

    @PostMapping
    public City create(@RequestBody City city) {
        return cityRepo.save(city);
    }

    @PutMapping("{id}")
    public City update(
            @PathVariable("id") City cityFromDb,
            @RequestBody City city
    ) {
        BeanUtils.copyProperties(city, cityFromDb, "id");

        return cityRepo.save(cityFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") City city) {
        cityRepo.delete(city);
    }
}