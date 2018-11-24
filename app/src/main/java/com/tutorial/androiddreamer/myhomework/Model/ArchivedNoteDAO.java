package com.tutorial.androiddreamer.myhomework.Model;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface ArchivedNoteDAO {

    @Insert
    public void addArchivedNote(ArchivedNote archivedNote);

    @Delete
    public void deleteArchivedNote(ArchivedNote archivedNote);

    @Query("DELETE FROM archived_note_table")
    public void deleteAllArchivedNotes();

    @Query("SELECT * from archived_note_table ORDER BY time DESC")
    public DataSource.Factory<Integer, ArchivedNote> getAllArchivedNotesOrderedByTime();

    @Query("SELECT * from archived_note_table ORDER BY priority DESC")
    public DataSource.Factory<Integer, ArchivedNote> getAllArchivedNotesOrderedByPriority();
}
