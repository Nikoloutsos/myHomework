package com.tutorial.androiddreamer.myhomework.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.paging.DataSource;

import com.tutorial.androiddreamer.myhomework.Model.Note;


@Dao
public interface NoteDAO {
    @Insert
    public void addNote(Note note);

    @Update
    public void updateNote(Note note);

    @Delete
    public void deleteNote(Note note);

    @Query("DELETE FROM note_table")
    public void deleteAllNotes();

    @Query("SELECT * from note_table ORDER BY time DESC")
    public DataSource.Factory<Integer, Note> getAllNotesOrderedByTime();

    @Query("SELECT * from note_table ORDER BY priority DESC")
    public DataSource.Factory<Integer, Note> getAllNotesOrderedByPriority();

}
