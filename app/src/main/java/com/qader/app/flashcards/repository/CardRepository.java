package com.qader.app.flashcards.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.qader.app.flashcards.AppExecutors;
import com.qader.app.flashcards.database.AppDatabase;
import com.qader.app.flashcards.database.dao.CardDao;
import com.qader.app.flashcards.database.entity.CardEntity;

public class CardRepository {
    private static CardRepository sInstance;
    private final CardDao mCardDao;

    private CardRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mCardDao = db.cardDao();
    }

    public static CardRepository getInstance(Application application) {
        if (sInstance == null) {
            synchronized (CardRepository.class) {
                if (sInstance == null) {
                    sInstance = new CardRepository(application);
                }
            }
        }
        return sInstance;
    }


    public LiveData<PagedList<CardEntity>> getAllCardsById(int id) {
        // Configuration for our Paging
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setPrefetchDistance(10)
                .setPageSize(20)
                .build();

        LiveData<PagedList<CardEntity>> cardsById = (new LivePagedListBuilder(mCardDao.getCardsByParentId(id), pagedListConfig)).build();

        return cardsById;
    }

    public void insert(final CardEntity cardEntity) {
        AppExecutors.getInstance().diskIO().execute(() -> mCardDao.insertCard(cardEntity));
    }

    public void update(final CardEntity cardEntity) {
        AppExecutors.getInstance().diskIO().execute(() -> mCardDao.updateCard(cardEntity));
    }

    public void delete(final CardEntity cardEntity) {
        AppExecutors.getInstance().diskIO().execute(() -> mCardDao.deleteCard(cardEntity));
    }

    public void deleteAll() {
        AppExecutors.getInstance().diskIO().execute(() -> mCardDao.deleteAll());
    }

}
