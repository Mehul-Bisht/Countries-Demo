package com.example.countries.data.network;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Service {

    @GET("Asia")
    Observable<List<Country>> getCountries();
}
