package com.liu966.nycschools.data;

import com.liu966.nycschools.data.api.NYCSchoolsAPI;
import com.liu966.nycschools.data.repository.SchoolRemoteDataSource;
import com.liu966.nycschools.data.repository.SchoolRepository;
import com.liu966.nycschools.data.repository.SchoolRepositoryImpl;
import com.liu966.nycschools.data.service.SchoolService;

public class DataManager {

  private DataManager() {
  }

  public static DataManager getInstance() {
    return LazyHolder.INSTANCE;
  }

  public SchoolRepository getSchoolRepository() {
    NYCSchoolsAPI schoolsAPI = SchoolService.getInstance().getSchoolsAPI();
    SchoolRemoteDataSource schoolRemoteDataSource = SchoolRemoteDataSource.getInstance(schoolsAPI);
    return SchoolRepositoryImpl.getInstance(schoolRemoteDataSource);
  }

  private static class LazyHolder {

    static final DataManager INSTANCE = new DataManager();
  }
}
