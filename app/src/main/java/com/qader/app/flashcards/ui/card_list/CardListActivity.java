package com.qader.app.flashcards.ui.card_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qader.app.flashcards.R;
import com.qader.app.flashcards.database.entity.CardEntity;
import com.qader.app.flashcards.databinding.ActivityCardListBinding;
import com.qader.app.flashcards.ui.practice_list.PracticeListActivity;
import com.qader.app.flashcards.utils.DialogUtils;
import com.qader.app.flashcards.viewmodel.CardListViewModel;

import java.util.Date;

public class CardListActivity extends AppCompatActivity implements CardListListener{

//    private static final String TAG = CardListActivity.class.getSimpleName();
    private CardListViewModel mCardListViewModel;
    private int mCardParentId;

    private ActivityCardListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =  DataBindingUtil.setContentView(this, R.layout.activity_card_list);

        getWindow().setBackgroundDrawable(null);

        binding.setLifecycleOwner(this);

        binding.setCardListViewModel(mCardListViewModel);

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCardParentId = getIntent().getIntExtra("SUBJECT_EXTRA_ID", -1);

        // Adapter
        final CardListAdapter adapter = new CardListAdapter();

        // RecyclerView

        RecyclerView recyclerView = binding.includedLayout.cardListRecyclerView;

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Preparing our factory
        CardListViewModel.Factory factory = new CardListViewModel.Factory(getApplication(), mCardParentId);
        // Getting our viewModel
        mCardListViewModel = ViewModelProviders.of(this, factory).get(CardListViewModel.class);
        // Setting data to the adapter
        mCardListViewModel.getCardsByParentId().observe(this, cardEntities -> adapter.setCardEntities(cardEntities));

        // Add card
        binding.addCardFab.setOnClickListener(v -> DialogUtils.addCardQuestionAnswer(CardListActivity.this,CardListActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_start_practice) {
            Intent intent = new Intent(CardListActivity.this, PracticeListActivity.class);
            intent.putExtra("SUBJECT_EXTRA_ID", mCardParentId);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

//    public void addQuestionAnswerDialog(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        LinearLayout addQuestionAnswerLayout = new LinearLayout(this);
//        addQuestionAnswerLayout.setOrientation(LinearLayout.VERTICAL);
//
//        // Add a TextView here for the "Title" label, as noted in the comments
//        final EditText inputQuestion = new EditText(this);
//        inputQuestion.setHint("Question");
//        inputQuestion.setInputType(InputType.TYPE_CLASS_TEXT);
//
//        addQuestionAnswerLayout.addView(inputQuestion); // Notice this is an add method
//
//        // Add another TextView here for the "Description" label
//        final EditText inputAnswer = new EditText(this);
//        inputAnswer.setHint("Answer");
//        inputAnswer.setInputType(InputType.TYPE_CLASS_TEXT);
//        addQuestionAnswerLayout.addView(inputAnswer); // Another add method
//
//        builder
//            .setTitle("Add Subject Name")
//            .setView(addQuestionAnswerLayout)
//            .setPositiveButton("OK", (dialog, which) -> {
//                if (inputQuestion.getText().toString().trim().length() > 0 && inputAnswer.getText().toString().trim().length() > 0)
//                    mCardListViewModel.insert(new CardEntity(inputQuestion.getText().toString().trim(), inputAnswer.getText().toString().trim(), mCardParentId, new Date()));
//                else
//                    Toast.makeText(CardListActivity.this, "Please Add Question & Answer", Toast.LENGTH_SHORT).show();
//            })
//            .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
//            .show();
//    }

    @Override
    public void addCard(String question, String answer) {
        mCardListViewModel.insert(new CardEntity(question, answer, mCardParentId, new Date()));

    }
}
