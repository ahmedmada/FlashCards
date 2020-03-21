package com.qader.app.flashcards.ui.practice_list;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.qader.app.flashcards.R;
import com.qader.app.flashcards.database.entity.CardEntity;
import com.qader.app.flashcards.databinding.ActivityPracticeListBinding;
import com.qader.app.flashcards.viewmodel.CardListViewModel;

import java.util.ArrayList;

public class PracticeListActivity extends AppCompatActivity {

//    private static final String TAG = PracticeListActivity.class.getSimpleName();
    private CardListViewModel mCardListViewModel;
    private int mCardParentId;
    private int mCurrentPosition;
    private ArrayList<CardEntity> mCurrentCardEntities;
    private ArrayList<CardEntity> mAllCardEntities;
    private ActivityPracticeListBinding binding;
    private PracticeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_practice_list);

        getWindow().setBackgroundDrawable(null);

        binding.setLifecycleOwner(this);

        binding.setCardListViewModel(mCardListViewModel);

        setSupportActionBar(binding.toolbar);

        mCardParentId = getIntent().getIntExtra("SUBJECT_EXTRA_ID", -1);

        // Adapter
        adapter = new PracticeListAdapter();

        // RecyclerView
        final RecyclerView recyclerView = binding.includedLayout.practiceListRecyclerView;

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setHasFixedSize(true);

        // Preparing our factory
        CardListViewModel.Factory factory = new CardListViewModel.Factory(getApplication(), mCardParentId);
        // getting our viewModel
        mCardListViewModel = ViewModelProviders.of(this, factory).get(CardListViewModel.class);
        // Setting data to the adapter
        mCardListViewModel.getCardsByParentId().observe(this, cardEntities -> {
            mCurrentCardEntities = new ArrayList<>();
            mCurrentCardEntities.addAll(cardEntities);
            mAllCardEntities = new ArrayList<>();
            mAllCardEntities.addAll(cardEntities);
//                mAllCardEntities = mCurrentCardEntities;

            adapter.setCardEntities(cardEntities);
        });


        checkAnswer();


        binding.includedLayout.nextArrow.setOnClickListener(v -> {
            mCurrentPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            recyclerView.smoothScrollToPosition(mCurrentPosition + 1);
        });

        binding.includedLayout.backArrow.setOnClickListener(v -> {
            mCurrentPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if (mCurrentPosition != 0) {
                recyclerView.smoothScrollToPosition(mCurrentPosition - 1);
            } else {
                Toast.makeText(PracticeListActivity.this, "You reach start", Toast.LENGTH_SHORT).show();
            }
        });


        binding.includedLayout.closeImageView.setOnClickListener(v -> onBackPressed());

        binding.includedLayout.correctImageView.setOnClickListener(v -> {
            if (mCurrentCardEntities.size() > 0){
                mCurrentPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                mCurrentCardEntities.remove(mCurrentPosition);
                adapter.setCardEntities(mCurrentCardEntities);
            }
//            if (mCurrentCardEntities.size() == 0) binding.includedLayout.correctImageView.setVisibility(View.GONE);
//                adapter.notifyDataSetChanged();
        });

        binding.includedLayout.againImageView.setOnClickListener(v -> {
            if (mAllCardEntities.size() > 0){
                adapter.setCardEntities(mAllCardEntities);
                mCurrentCardEntities = mAllCardEntities;
            }
        });
    }

    private void checkAnswer(){
        // Navigation Button
        adapter.setClickListener(new PracticeListAdapter.onItemClickListener() {
            @Override
            public void ItemClicked(final View v, int position) {
                //CardEntity card = adapter.getItem(position);
                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(v, "scaleX", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(v, "scaleX", 0f, 1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        oa2.start();
                        TextView front = v.findViewById(R.id.card_front_textView);
                        TextView back = v.findViewById(R.id.card_back_textView);
                        if (front.getVisibility() == View.VISIBLE) {
                            front.setVisibility(View.GONE);
                            back.setVisibility(View.VISIBLE);
                            v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        } else {
                            front.setVisibility(View.VISIBLE);
                            back.setVisibility(View.GONE);
                            v.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        }
                    }
                });
                oa1.start();
            }
        });
    }

}
