package model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

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
    public LiveData<List<Note>> getAllNotesOrderedByTime();

    @Query("SELECT * from note_table ORDER BY priority DESC")
    public LiveData<List<Note>> getAllNotesOrderedByPriority();


}
