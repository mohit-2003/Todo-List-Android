package com.tgm.todolist.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tgm.todolist.room.entity.Notes

@Dao
interface NotesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notes: Notes)

    @Update
    suspend fun update(notes: Notes)

    @Delete
    suspend fun delete(notes: Notes)

    @Query("SELECT * FROM Notes")
    fun getAllNotes(): LiveData<List<Notes>>
}