package com.tutorial.androiddreamer.myhomework.Model;

import android.app.Application;
import android.arch.paging.DataSource;
import android.os.AsyncTask;

public class NoteRepository {

    private NoteDAO noteDAO;

    private DataSource.Factory<Integer, Note> allNotesOrderedByTime;
    private DataSource.Factory<Integer, Note> allNotesOrderedByImportance;

    public NoteRepository(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        noteDAO = appDatabase.getNoteDAO();
        allNotesOrderedByTime = noteDAO.getAllNotesOrderedByTime();
        allNotesOrderedByImportance = noteDAO.getAllNotesOrderedByPriority();
    }

    //CRUD methods
    public void insertNote(Note note){
        new InsertNoteAsyncTask(noteDAO).execute(note);
    }
    public void updateNote(Note note){
        new UpdateNoteAsyncTask(noteDAO).execute(note);
    }
    public void deleteNote(Note note){
        new DeleteNoteAsyncTask(noteDAO).execute(note);
    }
    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDAO).execute();
    }


    public DataSource.Factory<Integer, Note> getAllNotesOrderedByTime() {
        return allNotesOrderedByTime;
    }
    public DataSource.Factory<Integer, Note> getAllNotesOrderedByImportance() {
        return allNotesOrderedByImportance;
    }

    //AsyncTasks. We need to keep the UI thread away from the heavy work!
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDAO noteDAO;
        public InsertNoteAsyncTask(NoteDAO noteDao) {
            this.noteDAO = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.addNote(notes[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDAO noteDAO;
        public UpdateNoteAsyncTask(NoteDAO noteDao) {
            this.noteDAO = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.updateNote(notes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDAO noteDAO;
        public DeleteNoteAsyncTask(NoteDAO noteDao) {
            this.noteDAO = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.deleteNote(notes[0]);
            return null;
        }
    }
    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDAO noteDAO;
        public DeleteAllNotesAsyncTask(NoteDAO noteDao) {
            this.noteDAO = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.deleteAllNotes();
            return null;
        }
    }

}
