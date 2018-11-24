package com.tutorial.androiddreamer.myhomework.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Archived_note_table")
public class ArchivedNote {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private long time;

    private String description;

    private String subject;

    private int priority;

    public ArchivedNote(long time, String description, String subject, int priority) {
        this.time = time;
        this.description = description;
        this.subject = subject;
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

    public String getSubject() {
        return subject;
    }
}
