package com.ra.cityguide.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "city")
@Data
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String Name;

    @Column(name = "goodplaces")
    private String Goodplaces;

    @Column(name = "badplaces")
    private String Badplaces;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setGoodplaces(String goodplaces) {
        Goodplaces = goodplaces;
    }

    public void setBadplaces(String badplaces) {
        Badplaces = badplaces;
    }

    public String getName() {
        return Name;
    }


    public String getGoodplaces() {
        return Goodplaces;
    }


    public String getBadplaces() {
        return Badplaces;
    }



}
