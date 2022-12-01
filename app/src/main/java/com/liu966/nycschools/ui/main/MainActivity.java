package com.liu966.nycschools.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import com.liu966.nycschools.data.DataManager;
import com.liu966.nycschools.data.domain.School;
import com.liu966.nycschools.data.repository.SchoolRepository;
import com.liu966.nycschools.databinding.ActivityMainBinding;
import com.liu966.nycschools.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements
    SchoolAdapter.SchoolClickListener {

  private SchoolAdapter adapter;


  @NonNull
  @Override
  protected MainViewModel createViewModel() {
    SchoolRepository schoolRepository = DataManager.getInstance().getSchoolRepository();
    MainViewModelFactory factory = new MainViewModelFactory(schoolRepository);
    return new ViewModelProvider(this, factory).get(MainViewModel.class);
  }

  @NonNull
  @Override
  protected ActivityMainBinding createViewBinding(LayoutInflater layoutInflater) {
    return ActivityMainBinding.inflate(layoutInflater);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    adapter = new SchoolAdapter(this);
    binding.recyclerView.setAdapter(adapter);

    observeViewModel();
    viewModel.loadSchools();
  }

  private void observeViewModel() {
    viewModel.getShowLoadingLiveData().observe(this,
        aVoid -> binding.progressBar.setVisibility(View.VISIBLE));

    viewModel.getHideLoadingLiveData().observe(this,
        aVoid -> binding.progressBar.setVisibility(View.GONE));

    viewModel.getSchoolsLiveData().observe(this, schools -> adapter.setItems(schools));

    viewModel.getNavigateToDetailsLiveData().observe(this, school -> {
      // Will show details in next step
      Toast.makeText(getApplicationContext(), school.getSchoolName(), Toast.LENGTH_SHORT).show();
    });

    viewModel.getShowErrorMessageLiveData().observe(this,
        message -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
  }

  @Override
  public void onSchoolClicked(School school) {
    viewModel.onSchoolClicked(school);
  }
}