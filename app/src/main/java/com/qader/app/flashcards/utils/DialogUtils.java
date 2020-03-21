package com.qader.app.flashcards.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qader.app.flashcards.database.entity.CardEntity;
import com.qader.app.flashcards.ui.card_list.CardListActivity;
import com.qader.app.flashcards.ui.card_list.CardListListener;
import com.qader.app.flashcards.ui.subject_list.SubjectListListener;

import java.util.Date;

public class DialogUtils {
    public static void addSubjectNameDialog(Context context, SubjectListListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final EditText inputSubjectName = new EditText(context);
        inputSubjectName.setInputType(InputType.TYPE_CLASS_TEXT);

        builder
            .setTitle("Add Subject Name")
            .setView(inputSubjectName)
            .setPositiveButton("OK", (dialog, which) -> {
                if (inputSubjectName.getText().toString().trim().length() > 0)
                    listener.addSubject(inputSubjectName.getText().toString().trim());
                else
                    Toast.makeText(context, "Please Add Subject Name", Toast.LENGTH_SHORT).show();

            })
            .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
            .show();
    }


    public static void addCardQuestionAnswer(Context context, CardListListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LinearLayout addQuestionAnswerLayout = new LinearLayout(context);
        addQuestionAnswerLayout.setOrientation(LinearLayout.VERTICAL);

        // Add a TextView here for the "Title" label, as noted in the comments
        final EditText inputQuestion = new EditText(context);
        inputQuestion.setHint("Question");
        inputQuestion.setInputType(InputType.TYPE_CLASS_TEXT);

        addQuestionAnswerLayout.addView(inputQuestion); // Notice this is an add method

        // Add another TextView here for the "Description" label
        final EditText inputAnswer = new EditText(context);
        inputAnswer.setHint("Answer");
        inputAnswer.setInputType(InputType.TYPE_CLASS_TEXT);
        addQuestionAnswerLayout.addView(inputAnswer); // Another add method

        builder
            .setTitle("Add Subject Name")
            .setView(addQuestionAnswerLayout)
            .setPositiveButton("OK", (dialog, which) -> {
                if (inputQuestion.getText().toString().trim().length() > 0 && inputAnswer.getText().toString().trim().length() > 0)
                    listener.addCard(inputQuestion.getText().toString().trim(), inputAnswer.getText().toString().trim());
//                    mCardListViewModel.insert(new CardEntity(inputQuestion.getText().toString().trim(), inputAnswer.getText().toString().trim(), mCardParentId, new Date()));
                else
                    Toast.makeText(context, "Please Add Question & Answer", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
            .show();
    }
}
