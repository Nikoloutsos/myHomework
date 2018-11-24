package com.tutorial.androiddreamer.myhomework.Model;

import android.app.Application;
import android.arch.paging.DataSource;
import android.os.AsyncTask;

public class ArchivedNoteRepository {
    private ArchivedNoteDAO archivedNoteDAO;


    private DataSource.Factory<Integer, ArchivedNote> allArchivedNotesOrderedByTime;
    private DataSource.Factory<Integer, ArchivedNote> allArchivedNotesOrderedByImportance;

    public ArchivedNoteRepository(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        archivedNoteDAO = appDatabase.getArchivedNoteDAO();
        allArchivedNotesOrderedByTime = archivedNoteDAO.getAllArchivedNotesOrderedByTime();
        allArchivedNotesOrderedByImportance = archivedNoteDAO.getAllArchivedNotesOrderedByPriority();
    }



    //CRUD methods
    public void addArchivedNote(ArchivedNote archivedNote){ new AddArchivedNoteAsyncTask(archivedNoteDAO).execute(archivedNote);}
    public void deleteArchivedNote(ArchivedNote archivedNote){ new DeleteArchivedNoteAsyncTask(archivedNoteDAO).execute(archivedNote);}
    public void deleteAllArchivedNotes(ArchivedNote archivedNote){ new DeleteAllArchivedNoteAsyncTask(archivedNoteDAO).execute();}

    //AsyncTasks. We need to keep the UI thread away from the heavy work!
    private static class AddArchivedNoteAsyncTask extends AsyncTask<ArchivedNote, Void, Void>{
        private ArchivedNoteDAO archivedNoteDAO;
        public AddArchivedNoteAsyncTask(ArchivedNoteDAO archivedNoteDAO){
            this.archivedNoteDAO = archivedNoteDAO;
        }
        @Override
        protected Void doInBackground(ArchivedNote... archivedNotes) {
            archivedNoteDAO.addArchivedNote(archivedNotes[0]);
            return null;
        }
    }
    private static class DeleteArchivedNoteAsyncTask extends AsyncTask<ArchivedNote, Void, Void>{
        private ArchivedNoteDAO archivedNoteDAO;
        public DeleteArchivedNoteAsyncTask(ArchivedNoteDAO archivedNoteDAO){
            this.archivedNoteDAO = archivedNoteDAO;
        }
        @Override
        protected Void doInBackground(ArchivedNote... archivedNotes) {
            archivedNoteDAO.deleteArchivedNote(archivedNotes[0]);
            return null;
        }
    }
    private static class DeleteAllArchivedNoteAsyncTask extends AsyncTask<ArchivedNote, Void, Void>{
        private ArchivedNoteDAO archivedNoteDAO;
        public DeleteAllArchivedNoteAsyncTask(ArchivedNoteDAO archivedNoteDAO){
            this.archivedNoteDAO = archivedNoteDAO;
        }
        @Override
        protected Void doInBackground(ArchivedNote... archivedNotes) {
            archivedNoteDAO.deleteAllArchivedNotes();
            return null;
        }
    }

    //Getters



    public DataSource.Factory<Integer, ArchivedNote> getAllOrderedArchivedNotes(int order) {
        if(order == 0){
            return allArchivedNotesOrderedByTime;
        }else{
            return allArchivedNotesOrderedByImportance;
        }
    }


}
