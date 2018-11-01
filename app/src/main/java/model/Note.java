package model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long time;

    private String description;

    private int priority;


    public Note(long time, String description, int priority) {
        this.time = time;
        this.description = description;
        this.priority = priority;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
