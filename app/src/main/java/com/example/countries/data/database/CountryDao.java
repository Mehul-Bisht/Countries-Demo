package com.example.countries.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import io.reactivex.Observable;

@Dao
public interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CountryMapper> countries);

    @Query("DELETE FROM countries")
    void deleteAll();

    @Query("SELECT * FROM countries")
    Observable<List<CountryMapper>> getCountries();
}
