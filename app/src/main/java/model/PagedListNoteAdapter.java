package model;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorial.androiddreamer.myhomework.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagedListNoteAdapter extends PagedListAdapter<Note, PagedListNoteAdapter.NoteViewHolder> {
    private OnItemClickListener listener;


    public PagedListNoteAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
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
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull PagedListNoteAdapter.NoteViewHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.tvNoteItemSubject.setText(currentNote.getSubject());
        holder.tvNoteItemDescription.setText(currentNote.getDescription());
        holder.tvNoteItemTime.setText(DateUtil.convertUnixTimeStampInReadableForm(currentNote.getTime()));
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_note_item_time)
        TextView tvNoteItemTime;
        @BindView(R.id.tv_note_item_description)
        TextView tvNoteItemDescription;
        @BindView(R.id.tv_note_item_subject)
        TextView tvNoteItemSubject;

        public NoteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getItem(getAdapterPosition()));
                }
            });
        }
    }
    public interface OnItemClickListener{
        public void onClick(Note note);
    }



}

