package com.liu966.nycschools.data.api;

import com.liu966.nycschools.data.model.NYCSchool;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NYCSchoolsAPI {

  @GET("https://data.cityofnewyork.us/resource/s3k6-pzi2.json")
  Call<List<NYCSchool>> getSchools();

}
