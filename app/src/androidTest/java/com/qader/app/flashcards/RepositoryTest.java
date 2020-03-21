package com.qader.app.flashcards;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.qader.app.flashcards.database.AppDatabase;
import com.qader.app.flashcards.database.dao.CardDao;
import com.qader.app.flashcards.database.dao.SubjectDao;
import com.qader.app.flashcards.database.entity.SubjectEntity;
import com.qader.app.flashcards.repository.SubjectRepository;
import com.qader.app.flashcards.ui.subject_list.SubjectListActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class RepositoryTest {

    SubjectRepository mSubjectRepository;

    private AppDatabase mAppDatabase;
    private SubjectDao mSubjectDao;
    private CardDao mCardDao;
    private Context mContext;

    @Rule
    public ActivityTestRule<SubjectListActivity> mActivityTestRule = new ActivityTestRule<>(SubjectListActivity.class);


    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();

        mAppDatabase = Room.inMemoryDatabaseBuilder(mContext, AppDatabase.class).build();
        mSubjectDao = mAppDatabase.subjectDao();
        mCardDao = mAppDatabase.cardDao();

        mSubjectRepository = SubjectRepository.getInstance(mActivityTestRule.getActivity().getApplication());
    }

    @Test
    public void testRepository() throws InterruptedException{
        mSubjectRepository.insert(new SubjectEntity("Math", new Date(), 1));

        final LiveData<SubjectEntity> retrieveSubject = mSubjectRepository.getSubjectById(1);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        retrieveSubject.observeForever(new Observer<SubjectEntity>() {
            @Override
            public void onChanged(@Nullable SubjectEntity subjectEntity) {
                assertThat(subjectEntity.getTitle(), is("Computer"));
                countDownLatch.countDown();
                retrieveSubject.removeObserver(this);
            }
        });
        countDownLatch.await(2, TimeUnit.SECONDS);
    }

}
