package com.liu966.nycschools.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.liu966.nycschools.R;
import com.liu966.nycschools.data.DataManager;
import com.liu966.nycschools.data.domain.School;
import com.liu966.nycschools.data.model.SATQueryResult;
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
      viewModel.loadSATScores(school.getDbn());
    });

    viewModel.getShowErrorMessageLiveData().observe(this,
        message -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());

    viewModel.getSatScoreLiveData().observe(this, this::displaySATScores);
  }

  @Override
  public void onSchoolClicked(School school) {
    viewModel.onSchoolClicked(school);
  }

  private void displaySATScores(SATQueryResult result) {
    new MaterialAlertDialogBuilder(this)
        .setTitle(R.string.sat_dialog_title)
        .setMessage(getString(R.string.sat_dialog_content, result.getSchool_name(),
            result.getSat_critical_reading_avg_score(),
            result.getSat_math_avg_score(),
            result.getSat_writing_avg_score()))
        .setNegativeButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss())
        .show();
  }
}