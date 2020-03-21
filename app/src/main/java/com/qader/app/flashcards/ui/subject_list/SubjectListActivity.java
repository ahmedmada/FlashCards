package com.qader.app.flashcards.ui.subject_list;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qader.app.flashcards.R;
import com.qader.app.flashcards.database.entity.SubjectEntity;
import com.qader.app.flashcards.databinding.ActivitySubjectListBinding;
import com.qader.app.flashcards.ui.card_list.CardListActivity;
import com.qader.app.flashcards.utils.DialogUtils;
import com.qader.app.flashcards.viewmodel.SubjectListViewModel;

import java.util.Date;
import java.util.Objects;

public class SubjectListActivity extends AppCompatActivity implements SubjectListListener{

    private SubjectListViewModel mSubjectListViewModel;
    private SubjectListAdapter adapter;
    private ActivitySubjectListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        binding =  DataBindingUtil.setContentView(this, R.layout.activity_subject_list);

        getWindow().setBackgroundDrawable(null);

        binding.setLifecycleOwner(this);

        binding.setSubjectListViewModel(mSubjectListViewModel);

        setSupportActionBar(binding.toolbar);

        // Adapter
        adapter = new SubjectListAdapter();

        // RecyclerView
        binding.subjectListRecyclerView.setAdapter(adapter);
        binding.subjectListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Getting our viewModel
        mSubjectListViewModel = ViewModelProviders.of(this).get(SubjectListViewModel.class);
        // Observe data and setting it to the adapter
        mSubjectListViewModel.getAllSubjects().observe(this, adapter::setSubjectEntities);

        // Add subject
        binding.addSubjectButton.setOnClickListener(v -> DialogUtils.addSubjectNameDialog(SubjectListActivity.this,SubjectListActivity.this));

        // Open subject when click on it
        adapter.setClickListener((v, position) -> {
            final SubjectEntity subject = adapter.getItem(position);
            Intent intent = new Intent(SubjectListActivity.this, CardListActivity.class);
            intent.putExtra("SUBJECT_EXTRA_ID", Objects.requireNonNull(subject).getId());
            startActivity(intent);
        });

        initSwipeToDelete();

    }

    private void initSwipeToDelete() {
        // Delete subject by swabbing item left and right
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                final SubjectEntity subject = adapter.getItem(position);
                mSubjectListViewModel.delete(subject);
                Toast.makeText(SubjectListActivity.this, "Subject: " + Objects.requireNonNull(subject).getTitle() + " deleted.", Toast.LENGTH_SHORT).show();
            }
        });
        itemTouchHelper.attachToRecyclerView(binding.subjectListRecyclerView);

    }

    @Override
    public void addSubject(String subjectName) {
        mSubjectListViewModel.insert(new SubjectEntity(subjectName, new Date(), 1));
    }
}
