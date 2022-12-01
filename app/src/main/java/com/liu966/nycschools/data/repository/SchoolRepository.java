package com.liu966.nycschools.data.repository;

import com.liu966.nycschools.data.domain.School;
import com.liu966.nycschools.data.model.SATQueryResult;
import java.util.List;

public interface SchoolRepository {

  void getSchools(LoadSchoolsCallback callback);

  void getSchoolSATScores(String dbn, LoadSchoolSATScoreCallback callback);

  interface LoadSchoolsCallback {

    void onSchoolsLoaded(List<School> schools);

    void onDataNotAvailable();

    void onError(String msg);
  }

  interface LoadSchoolSATScoreCallback {

    void onScoreLoaded(SATQueryResult satQueryResult);

    void onDataNotAvailable();

    void onError(String msg);
  }
}
