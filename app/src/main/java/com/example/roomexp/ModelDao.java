package com.example.roomexp;

import static androidx.room.OnConflictStrategy.ABORT;
import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface ModelDao {
    @Query("SELECT * FROM contacts order by first_name asc")
    List<Model> getSupportAll();


    @Insert
    long insert(Model model);

    @Update
    int update(Model model);

    @Query("DELETE FROM contacts WHERE uid =:id")
    void deleteByid(int id);











}
