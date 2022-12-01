package com.liu966.nycschools;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.liu966.nycschools.data.DataManager;
import com.liu966.nycschools.data.domain.School;
import com.liu966.nycschools.data.repository.SchoolRepository.LoadSchoolsCallback;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    DataManager dm = DataManager.getInstance();
    dm.getSchoolRepository().getMovies(new LoadSchoolsCallback() {
      @Override
      public void onSchoolsLoaded(List<School> schools) {
        Toast.makeText(getApplicationContext(), "size: " + schools.size(), Toast.LENGTH_LONG)
            .show();
      }

      @Override
      public void onDataNotAvailable() {
        Toast.makeText(getApplicationContext(), "data not available", Toast.LENGTH_LONG).show();
      }

      @Override
      public void onError(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        TextView tv = findViewById(R.id.tv);
        tv.setText(msg);
      }
    });
  }
}