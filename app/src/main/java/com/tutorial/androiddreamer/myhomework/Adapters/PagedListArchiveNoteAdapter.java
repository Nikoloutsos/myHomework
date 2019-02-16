package com.tutorial.androiddreamer.myhomework.Adapters;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorial.androiddreamer.myhomework.Helpers.ColorChooser;
import com.tutorial.androiddreamer.myhomework.Helpers.DateUtil;
import com.tutorial.androiddreamer.myhomework.Model.ArchivedNote;
import com.tutorial.androiddreamer.myhomework.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagedListArchiveNoteAdapter extends PagedListAdapter<ArchivedNote, PagedListArchiveNoteAdapter.ArchivedNoteViewHolder > {
    LongClickListener listener;
    SimpleClickListener listener2;
    public PagedListArchiveNoteAdapter(LongClickListener listener, SimpleClickListener listener2) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.listener2 = listener2;
    }

    private static final DiffUtil.ItemCallback<ArchivedNote> DIFF_CALLBACK = new DiffUtil.ItemCallback<ArchivedNote>() {
        @Override
        public boolean areItemsTheSame(ArchivedNote oldItem, ArchivedNote newItem) {
            return oldItem.getId() == newItem.getId();
        }
        @Override
        public boolean areContentsTheSame(ArchivedNote oldItem, ArchivedNote newItem) {
            return oldItem.getSubject().equals(newItem.getSubject()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getTime() == newItem.getTime();
        }
    };

    class ArchivedNoteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        @BindView(R.id.tv_note_item_time) TextView tvNoteItemTime;
        @BindView(R.id.tv_note_item_description) TextView tvNoteItemDescription;
        @BindView(R.id.tv_note_item_subject) TextView tvNoteItemSubject;
        @BindView(R.id.tv_note_item_importance) TextView tvCircularImportance;
        public ArchivedNoteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onSimpleClickListener(getItem(getAdapterPosition()));
                }
            });
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select The Action");
            MenuItem complete =  menu.add(0, v.getId(), 0, "Delete");//groupId, itemId, order, title
            complete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getTitle().toString().equalsIgnoreCase("Delete")) listener.onLongClick(getItem(getAdapterPosition()));
            return true;
        }
    }

    @NonNull
    @Override
    public ArchivedNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new ArchivedNoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArchivedNoteViewHolder holder, int position) {
        ArchivedNote currentNote = getItem(position);
        holder.tvNoteItemSubject.setText(currentNote.getSubject());
        holder.tvNoteItemDescription.setText(currentNote.getDescription());
        holder.tvNoteItemTime.setText(DateUtil.convertUnixTimeStampInReadableForm(currentNote.getTime()));
        holder.tvCircularImportance.getBackground().setColorFilter(Color.parseColor(ColorChooser.chooseColor(currentNote.getPriority())),
                PorterDuff.Mode.SRC_OVER);
        holder.tvCircularImportance.setText("" + currentNote.getPriority());

    }

    public interface LongClickListener{
        void onLongClick(ArchivedNote archivedNote);
    }

    public interface SimpleClickListener{
        void onSimpleClickListener(ArchivedNote archivedNote);
    }


}
