package com.example.countries.data.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "countries")
public class CountryMapper {

    @NonNull
    @PrimaryKey
    String name = "";
    String capital;
    String flag;
    String region;
    String subregion;
    Long population;
    String borders;
    String languages;

    @Override
    public boolean equals(Object obj) {

        if(obj == this) return true;
        if(!(obj instanceof CountryMapper)){
            return false;
        }

        CountryMapper mapper = (CountryMapper) obj;

        return this.name.equals(mapper.name) &&
                this.capital.equals(mapper.capital) &&
                this.flag.equals(mapper.flag) &&
                this.region.equals(mapper.region) &&
                this.subregion.equals(mapper.subregion) &&
                this.population.equals(mapper.population);
    }

    public String getBorders() {
        return borders;
    }

    public void setBorders(String borders) {
        this.borders = borders;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
