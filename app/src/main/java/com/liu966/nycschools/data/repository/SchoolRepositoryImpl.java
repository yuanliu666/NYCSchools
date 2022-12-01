package com.liu966.nycschools.data.repository;

import androidx.collection.LruCache;
import com.liu966.nycschools.data.model.SATQueryResult;

public class SchoolRepositoryImpl implements SchoolRepository {

  private static SchoolRepositoryImpl instance;

  private final SchoolRemoteDataSource schoolRemoteDataSource;

  // Using a cache of size 10
  private final LruCache<String, SATQueryResult> cache = new LruCache<>(10);

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

  @Override
  public void getSchoolSATScores(String dbn, LoadSchoolSATScoreCallback callback) {
    if (callback == null) {
      return;
    }

    maybeGetSATScoreFromCache(dbn, callback);
  }

  // Attempt to get result from cache. If miss, query remote and save into cache.
  private void maybeGetSATScoreFromCache(String dbn, LoadSchoolSATScoreCallback callback) {
    if (cache.get(dbn) != null) {
      SATQueryResult result = cache.get(dbn);
      callback.onScoreLoaded(result);
    } else {
      schoolRemoteDataSource.getSchoolSATScores(dbn, new LoadSchoolSATScoreCallback() {
        @Override
        public void onScoreLoaded(SATQueryResult satQueryResult) {
          callback.onScoreLoaded(satQueryResult);
          cache.put(dbn, satQueryResult);
        }

        @Override
        public void onDataNotAvailable() {
          callback.onDataNotAvailable();
        }

        @Override
        public void onError(String msg) {
          callback.onError(msg);
        }
      });
    }
  }
}
