package com.liu966.nycschools.data.repository;

import com.liu966.nycschools.data.repository.SchoolRepository.LoadSchoolSATScoreCallback;
import com.liu966.nycschools.data.repository.SchoolRepository.LoadSchoolsCallback;

public interface SchoolDataSource {

  void getSchools(LoadSchoolsCallback callback);

  void getSchoolSATScores(String dbn, LoadSchoolSATScoreCallback callback);
}
