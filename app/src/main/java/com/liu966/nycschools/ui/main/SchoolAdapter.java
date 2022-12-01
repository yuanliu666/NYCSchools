package com.liu966.nycschools.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.liu966.nycschools.data.domain.School;
import com.liu966.nycschools.databinding.ItemSchoolBinding;
import java.util.ArrayList;
import java.util.List;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder> {

  private final SchoolClickListener listener;
  private List<School> items;

  public SchoolAdapter(SchoolClickListener schoolClickListener) {
    this.listener = schoolClickListener;
    items = new ArrayList<>();
  }

  public void setItems(List<School> items) {
    this.items = items;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemSchoolBinding binding = ItemSchoolBinding.inflate(LayoutInflater.from(parent.getContext()),
        parent, false);
    return new SchoolViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(SchoolViewHolder holder, int position) {
    holder.bind(position);
  }

  private School getItem(int position) {
    return items.get(position);
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public interface SchoolClickListener {

    void onSchoolClicked(School school);
  }

  public class SchoolViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemSchoolBinding binding;

    SchoolViewHolder(ItemSchoolBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void bind(int position) {
      School school = getItem(position);

      setClickListener(school);
      setName(school.getSchoolName());
    }

    private void setClickListener(School school) {
      itemView.setTag(school);
      itemView.setOnClickListener(this);
    }

    private void setName(String name) {
      binding.name.setText(name);
    }

    @Override
    public void onClick(View view) {
      listener.onSchoolClicked((School) view.getTag());
    }
  }
}
