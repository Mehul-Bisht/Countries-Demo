package com.example.countries.data.network;

import com.example.countries.util.Constant;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private RetrofitService() { }

    private volatile static Retrofit retrofit;

    public static Retrofit getInstance() {

        if(retrofit == null) {
            synchronized (RetrofitService.class) {
                if(retrofit == null) {

                    retrofit = new Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }

        return retrofit;
    }
}
