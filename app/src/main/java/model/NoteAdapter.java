package model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorial.androiddreamer.myhomework.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private final String TAG = "NOTEADAPTER";
    private List<Note> notes = new ArrayList<>();

    public NoteAdapter(){}


    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.tvNoteItemSubject.setText(currentNote.getSubject());
        holder.tvNoteItemDescription.setText(currentNote.getDescription());
        holder.tvNoteItemTime.setText(convertUnixTimeStampInReadableForm(currentNote.getTime()));

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "list size is : " + notes.size());
        return notes.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView tvNoteItemTime, tvNoteItemDescription, tvNoteItemSubject;

        public NoteHolder(View itemView) {
            super(itemView);
            tvNoteItemTime = itemView.findViewById(R.id.tv_note_item_time);
            tvNoteItemDescription = itemView.findViewById(R.id.tv_note_item_description);
            tvNoteItemSubject = itemView.findViewById(R.id.tv_note_item_subject);
        }
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        Log.d(TAG, "list size when setnotes  is : " + this.notes.size());
        notifyDataSetChanged();

    }


    private String convertUnixTimeStampInReadableForm(long timestamp){
        Date date = new java.util.Date(timestamp);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(TimeZone.getDefault().toString()));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }



}
