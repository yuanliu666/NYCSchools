package com.liu966.nycschools.data.repository;

import com.liu966.nycschools.data.api.NYCSchoolsAPI;
import com.liu966.nycschools.data.domain.School;
import com.liu966.nycschools.data.model.NYCSchool;
import com.liu966.nycschools.data.model.SATQueryResult;
import com.liu966.nycschools.data.repository.SchoolRepository.LoadSchoolSATScoreCallback;
import com.liu966.nycschools.data.repository.SchoolRepository.LoadSchoolsCallback;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolRemoteDataSource implements SchoolDataSource {

  private static SchoolRemoteDataSource instance;
  private final NYCSchoolsAPI schoolsAPI;

  private SchoolRemoteDataSource(NYCSchoolsAPI schoolsAPI) {
    this.schoolsAPI = schoolsAPI;
  }

  public static SchoolRemoteDataSource getInstance(NYCSchoolsAPI schoolsAPI) {
    if (instance == null) {
      instance = new SchoolRemoteDataSource(schoolsAPI);
    }
    return instance;
  }

  @Override
  public void getSchools(LoadSchoolsCallback callback) {
    schoolsAPI.getSchools().enqueue(new Callback<List<NYCSchool>>() {
      @Override
      public void onResponse(Call<List<NYCSchool>> call, Response<List<NYCSchool>> response) {
        List<NYCSchool> res = response.body();
        if (res != null) {
          List<School> schools = new ArrayList<>();
          for (NYCSchool school : res) {
            schools.add(NYCSchool.toSchool(school));
          }

          if (!schools.isEmpty()) {
            callback.onSchoolsLoaded(schools);
            return;
          }
        }
        callback.onDataNotAvailable();
      }

      @Override
      public void onFailure(Call<List<NYCSchool>> call, Throwable t) {
        callback.onError(t.getLocalizedMessage());
      }
    });
  }

  @Override
  public void getSchoolSATScores(String dbn, LoadSchoolSATScoreCallback callback) {
    schoolsAPI.getSATScores(dbn).enqueue(new Callback<List<SATQueryResult>>() {
      @Override
      public void onResponse(Call<List<SATQueryResult>> call,
          Response<List<SATQueryResult>> response) {
        if (response.body() != null && !response.body().isEmpty()) {
          callback.onScoreLoaded(response.body().get(0));
        } else {
          callback.onDataNotAvailable();
        }
      }

      @Override
      public void onFailure(Call<List<SATQueryResult>> call, Throwable t) {
        callback.onError(t.getLocalizedMessage());
      }
    });
  }
}
