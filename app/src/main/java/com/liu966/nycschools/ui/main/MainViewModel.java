package com.liu966.nycschools.ui.main;

import androidx.lifecycle.MutableLiveData;
import com.liu966.nycschools.data.domain.School;
import com.liu966.nycschools.data.repository.SchoolRepository;
import com.liu966.nycschools.data.repository.SchoolRepository.LoadSchoolsCallback;
import com.liu966.nycschools.ui.base.BaseViewModel;
import java.util.List;

public class MainViewModel extends BaseViewModel {

  private final MutableLiveData<List<School>> schoolsLiveData = new MutableLiveData<>();
  private final MutableLiveData<String> showErrorMessageLiveData = new MutableLiveData<>();
  private final MutableLiveData<Void> showLoadingLiveData = new MutableLiveData<>();
  private final MutableLiveData<Void> hideLoadingLiveData = new MutableLiveData<>();
  private final MutableLiveData<School> navigateToDetailsLiveData = new MutableLiveData<>();

  private final SchoolRepository schoolRepository;
  private final SchoolCallback schoolCallback = new SchoolCallback();

  MainViewModel(SchoolRepository schoolRepository) {
    this.schoolRepository = schoolRepository;
  }

  public void loadSchools() {
    setIsLoading(true);
    schoolRepository.getSchools(schoolCallback);
  }

  public void onSchoolClicked(School school) {
    navigateToDetailsLiveData.postValue(school);
  }

  private void setIsLoading(boolean loading) {
    if (loading) {
      showLoadingLiveData.postValue(null);
    } else {
      hideLoadingLiveData.postValue(null);
    }
  }

  // Live data
  public MutableLiveData<List<School>> getSchoolsLiveData() {
    return schoolsLiveData;
  }

  private void setSchoolsLiveData(List<School> schoolsLiveData) {
    setIsLoading(false);
    this.schoolsLiveData.postValue(schoolsLiveData);
  }

  public MutableLiveData<School> getNavigateToDetailsLiveData() {
    return navigateToDetailsLiveData;
  }

  public MutableLiveData<String> getShowErrorMessageLiveData() {
    return showErrorMessageLiveData;
  }

  public MutableLiveData<Void> getHideLoadingLiveData() {
    return hideLoadingLiveData;
  }

  public MutableLiveData<Void> getShowLoadingLiveData() {
    return showLoadingLiveData;
  }

  private class SchoolCallback implements LoadSchoolsCallback {

    @Override
    public void onSchoolsLoaded(List<School> schools) {
      setSchoolsLiveData(schools);
    }

    @Override
    public void onDataNotAvailable() {
      setIsLoading(false);
      showErrorMessageLiveData.postValue("Empty data");
    }

    @Override
    public void onError(String msg) {
      setIsLoading(false);
      showErrorMessageLiveData.postValue(msg);
    }
  }
}
