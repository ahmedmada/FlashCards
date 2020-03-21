package com.qader.app.flashcards.database.dao;


import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.qader.app.flashcards.database.entity.CardEntity;

@Dao
public interface CardDao {

    // Using Paging
    @Query("SELECT * FROM cards ")
    DataSource.Factory<Integer, CardEntity> getAllCard();

    // Using Paging
    @Query("SELECT * FROM cards WHERE card_parent_id = :id")
    DataSource.Factory<Integer, CardEntity> getCardsByParentId(int id);

    @Query("SELECT * FROM cards WHERE card_id = :id")
    CardEntity getCardById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCard(CardEntity cardEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCard(CardEntity cardEntity);

    @Delete
    void deleteCard(CardEntity cardEntity);

    @Query("DELETE FROM cards")
    void deleteAll();

}
