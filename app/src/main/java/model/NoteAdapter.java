package model;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
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

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {
    private final String TAG = "NoteAdapter";
    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(Note oldItem, Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Note oldItem, Note newItem) {
            return oldItem.getSubject().equals(newItem.getSubject()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getTime() == newItem.getTime();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.tvNoteItemSubject.setText(currentNote.getSubject());
        holder.tvNoteItemDescription.setText(currentNote.getDescription());
        holder.tvNoteItemTime.setText(DateUtil.convertUnixTimeStampInReadableForm(currentNote.getTime()));

    }

    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView tvNoteItemTime, tvNoteItemDescription, tvNoteItemSubject;

        public NoteHolder(View itemView) {
            super(itemView);
            tvNoteItemTime = itemView.findViewById(R.id.tv_note_item_time);
            tvNoteItemDescription = itemView.findViewById(R.id.tv_note_item_description);
            tvNoteItemSubject = itemView.findViewById(R.id.tv_note_item_subject);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getItem(getAdapterPosition()));
                }
            });
        }
    }






    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        public void onClick(Note note);
    }



}
