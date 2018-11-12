package model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {Note.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract NoteDAO getNoteDAO();

    public static synchronized AppDatabase getInstance(Context context){
        // Singleton
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_note_db ").fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
