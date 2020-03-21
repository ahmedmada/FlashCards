package com.qader.app.flashcards.database.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "subjects")
public class SubjectEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subject_id")
    private int mId;
    private String mTitle;
    private Date mDate;
    private Integer mColor;

    // Room Construction
    public SubjectEntity(@NonNull int id, String title, Date date, Integer color) {
        mId = id;
        mTitle = title;
        mDate = date;
        mColor = color;
    }

    // Our Construction
    @Ignore
    public SubjectEntity(String title, Date date, Integer color) {
        mTitle = title;
        mDate = date;
        mColor = color;
    }

    // Getter and Setter pattern required for Room.
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Integer getColor() {
        return mColor;
    }

    public void setColor(Integer color) {
        mColor = color;
    }
}
