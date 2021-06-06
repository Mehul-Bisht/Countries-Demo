package com.example.countries.ui;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.countries.data.Repository;
import com.example.countries.data.database.CountryMapper;
import com.example.countries.data.network.Country;
import com.example.countries.util.Resource;
import java.util.List;

public class MainViewModel extends ViewModel {

    private final Repository repository = Repository.getInstance();

    LiveData<Resource<List<CountryMapper>>> getData(Context context) {

        repository.getFromCache(context);
        repository.getCountries();
        return repository.data();
    }

    void delete() {
        repository.deleteAll();
    }
}
