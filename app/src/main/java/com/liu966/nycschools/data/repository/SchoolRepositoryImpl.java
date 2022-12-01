package com.liu966.nycschools.data.repository;

public class SchoolRepositoryImpl implements SchoolRepository {

  private static SchoolRepositoryImpl instance;

  private final SchoolRemoteDataSource schoolRemoteDataSource;

  private SchoolRepositoryImpl(SchoolRemoteDataSource schoolRemoteDataSource) {
    this.schoolRemoteDataSource = schoolRemoteDataSource;
  }

  public static SchoolRepositoryImpl getInstance(SchoolRemoteDataSource schoolRemoteDataSource) {
    if (instance == null) {
      instance = new SchoolRepositoryImpl(schoolRemoteDataSource);
    }
    return instance;
  }

  @Override
  public void getSchools(LoadSchoolsCallback callback) {
    if (callback == null) {
      return;
    }

    schoolRemoteDataSource.getSchools(callback);
  }
}
