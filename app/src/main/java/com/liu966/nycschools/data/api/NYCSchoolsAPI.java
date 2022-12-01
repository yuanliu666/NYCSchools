package com.liu966.nycschools.data.api;

import com.liu966.nycschools.data.model.NYCSchool;
import com.liu966.nycschools.data.model.SATQueryResult;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NYCSchoolsAPI {

  @GET("https://data.cityofnewyork.us/resource/s3k6-pzi2.json")
  Call<List<NYCSchool>> getSchools();

  @GET("https://data.cityofnewyork.us/resource/f9bf-2cp4.json")
  Call<List<SATQueryResult>> getSATScores(@Query("dbn") String dbn);
}
