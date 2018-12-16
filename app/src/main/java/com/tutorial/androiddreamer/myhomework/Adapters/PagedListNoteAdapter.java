package com.tutorial.androiddreamer.myhomework.Adapters;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorial.androiddreamer.myhomework.Helpers.ColorChooser;
import com.tutorial.androiddreamer.myhomework.Helpers.DateUtil;
import com.tutorial.androiddreamer.myhomework.Model.Note;
import com.tutorial.androiddreamer.myhomework.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagedListNoteAdapter extends PagedListAdapter<Note, PagedListNoteAdapter.NoteViewHolder> {
    private OnItemClickListener listener;
    private OnItemLongClickListener listener2;
    private Context context;


    public PagedListNoteAdapter(Context context, OnItemClickListener listener, OnItemLongClickListener listener2) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
        this.listener2 = listener2;
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
        holder.tvCircularImportance.getBackground().setColorFilter(Color.parseColor(ColorChooser.chooseColor(currentNote.getPriority())),
                PorterDuff.Mode.SRC_OVER);
        holder.tvCircularImportance.setText("" + currentNote.getPriority());
    }

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        @BindView(R.id.tv_note_item_time) TextView tvNoteItemTime;
        @BindView(R.id.tv_note_item_description) TextView tvNoteItemDescription;
        @BindView(R.id.tv_note_item_subject) TextView tvNoteItemSubject;
        @BindView(R.id.tv_note_item_importance) TextView tvCircularImportance;

        public NoteViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getItem(getAdapterPosition()));
                }
            });
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.setHeaderTitle("Select The Action");
           MenuItem complete =  menu.add(0, v.getId(), 0, context.getResources().getString(R.string.archive));//groupId, itemId, order, title
           MenuItem edit =  menu.add(0, v.getId(), 0, context.getResources().getString(R.string.edit));//groupId, itemId, order, title
           MenuItem delete =  menu.add(0, v.getId(), 0, context.getResources().getString(R.string.share));//groupId, itemId, order, title
           complete.setOnMenuItemClickListener(this);
           edit.setOnMenuItemClickListener(this);
           delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (menuItem.getTitle().toString().equalsIgnoreCase(context.getResources().getString(R.string.archive))){
                listener2.onLongClick(getItem(getAdapterPosition()), context.getResources().getString(R.string.archive));
            }else if (menuItem.getTitle().toString().equalsIgnoreCase(context.getResources().getString(R.string.edit))){
                listener2.onLongClick(getItem(getAdapterPosition()), context.getResources().getString(R.string.edit));
            }else if(menuItem.getTitle().toString().equalsIgnoreCase(context.getResources().getString(R.string.share))){
                listener2.onLongClick(getItem(getAdapterPosition()), context.getResources().getString(R.string.share));
            }
            return false;
        }
    }
    public interface OnItemClickListener{
        public void onClick(Note note);
    }

    public interface OnItemLongClickListener{
        public void onLongClick(Note note, String str);
    }

    public OnItemClickListener getListener() {
        return listener;
    }

    @Override
    public void submitList(PagedList<Note> pagedList) {
        super.submitList(pagedList);
    }
}

