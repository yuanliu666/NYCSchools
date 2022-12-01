package com.liu966.nycschools.data.service;

import com.liu966.nycschools.data.api.NYCSchoolsAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SchoolService {

  private static final String URL = "https://data.cityofnewyork.us/Education/";

  private final NYCSchoolsAPI schoolsAPI;

  private SchoolService() {
    Retrofit mRetrofit = new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(URL).build();

    schoolsAPI = mRetrofit.create(NYCSchoolsAPI.class);
  }

  public static SchoolService getInstance() {
    return SingletonHolder.singleton;
  }

  public NYCSchoolsAPI getSchoolsAPI() {
    return schoolsAPI;
  }

  private static final class SingletonHolder {

    static final SchoolService singleton = new SchoolService();
  }
}
