package com.example.countries.data;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.countries.data.database.CountryDao;
import com.example.countries.data.database.CountryDb;
import com.example.countries.data.database.CountryMapper;
import com.example.countries.data.network.Country;
import com.example.countries.data.network.RetrofitService;
import com.example.countries.data.network.Service;
import com.example.countries.util.Resource;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Completable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Repository {

    private Context context;

    private Repository(){}

    private final Retrofit retrofit = RetrofitService.getInstance();
    private final Service service = retrofit.create(Service.class);

    private final MutableLiveData<Resource<List<CountryMapper>>> _data = new MutableLiveData<Resource<List<CountryMapper>>>();
    public LiveData<Resource<List<CountryMapper>>> data() {
        return _data;
    }

    public void getFromCache(Context context) {

        this.context= context;
        CountryDb database = CountryDb.getInstance(context);
        CountryDao dao = database.getDao();
        dao.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(observerCached());
    }

    public void getCountries() {

        service.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer());
    }

    Observer<List<Country>> observer() {

        return new Observer<List<Country>>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) { }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onNext(@NotNull List<Country> countries) {

                CountryDb database = CountryDb.getInstance(context);
                CountryDao dao = database.getDao();

                List<CountryMapper> countryMapperList = countries.stream().map(country -> {

                    CountryMapper countryMapper = new CountryMapper();
                    countryMapper.setName(country.getName());
                    countryMapper.setCapital(country.getCapital());
                    countryMapper.setFlag(country.getFlag());
                    countryMapper.setRegion(country.getRegion());
                    countryMapper.setSubregion(country.getSubregion());
                    countryMapper.setPopulation(country.getPopulation());

                    StringBuilder borders = new StringBuilder();

                    for(int i = 0; i < country.getBorders().size(); i++) {
                        borders.append(country.getBorders().get(i));
                        if(i != country.getBorders().size() - 1) {
                            borders.append(", ");
                        } else {
                            borders.append(".");
                        }
                    }

                    countryMapper.setBorders(borders.toString());

                    StringBuilder languages = new StringBuilder();

                    for(int i = 0; i < country.getLanguages().size(); i++) {
                        String str = country.getLanguages().get(i).getName() + " (" + country.getLanguages().get(i).getSymbol() + ")";
                        languages.append(str);
                        if(i != country.getLanguages().size() - 1) {
                            languages.append(",  ");
                        } else {
                            languages.append(".");
                        }
                    }

                    countryMapper.setLanguages(languages.toString());

                    return countryMapper;
                }).collect(Collectors.toList());

                Completable.fromAction(() -> dao
                        .insertAll(countryMapperList))
                        .subscribeOn(Schedulers.io())
                        .subscribe();
            }

            @Override
            public void onError(@NotNull Throwable e) {
                _data.postValue(new Resource.Error<List<CountryMapper>>(null,"Trying to load cached data.."));
            }

            @Override
            public void onComplete() { }
        };
    }

    Observer<List<CountryMapper>> observerCached() {

        return new Observer<List<CountryMapper>>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                _data.postValue(new Resource.Loading<List<CountryMapper>>(null));
            }

            @Override
            public void onNext(@NotNull List<CountryMapper> countryMappers) {
                _data.postValue(new Resource.Success<List<CountryMapper>>(countryMappers));
            }

            @Override
            public void onError(@NotNull Throwable e) {
                _data.postValue(new Resource.Error<List<CountryMapper>>(null, "An unknown error occurred!"));
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void deleteAll() {

        CountryDb database = CountryDb.getInstance(context);
        CountryDao dao = database.getDao();

        Completable.fromAction(() -> dao
                .deleteAll())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    private volatile static Repository repository;

    public static Repository getInstance() {

        if(repository == null) {
            synchronized (Repository.class) {
                if(repository == null) {

                    repository = new Repository();
                }
            }
        }

        return repository;
    }
}
