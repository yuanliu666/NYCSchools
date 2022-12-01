package com.liu966.nycschools.data.repository;

import com.liu966.nycschools.data.domain.School;
import java.util.List;

public interface SchoolRepository {

  void getMovies(LoadSchoolsCallback callback);

  interface LoadSchoolsCallback {

    void onSchoolsLoaded(List<School> schools);

    void onDataNotAvailable();

    void onError(String msg);
  }
}
