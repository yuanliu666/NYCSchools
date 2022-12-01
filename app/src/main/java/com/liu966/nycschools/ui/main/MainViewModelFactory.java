package com.liu966.nycschools.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.liu966.nycschools.data.repository.SchoolRepository;

class MainViewModelFactory implements ViewModelProvider.Factory {

  private final SchoolRepository schoolRepository;

  public MainViewModelFactory(SchoolRepository schoolRepository) {
    this.schoolRepository = schoolRepository;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(MainViewModel.class)) {
      return (T) new MainViewModel(schoolRepository);
    }

    throw new IllegalArgumentException("Unknown ViewModel class");
  }
}
