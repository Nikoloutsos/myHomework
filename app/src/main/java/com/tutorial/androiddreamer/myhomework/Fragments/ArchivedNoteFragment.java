package com.tutorial.androiddreamer.myhomework.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorial.androiddreamer.myhomework.Activities.AddNoteActivity;
import com.tutorial.androiddreamer.myhomework.Activities.BaseDisplayNoteActivity;
import com.tutorial.androiddreamer.myhomework.Adapters.PagedListArchiveNoteAdapter;
import com.tutorial.androiddreamer.myhomework.Model.ArchivedNote;
import com.tutorial.androiddreamer.myhomework.R;
import com.tutorial.androiddreamer.myhomework.ViewModels.ArchivedNoteFragmentViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tutorial.androiddreamer.myhomework.Activities.MainActivity.EXTRA_ID;
import static com.tutorial.androiddreamer.myhomework.Activities.MainActivity.EXTRA_IMPORTANCE;
import static com.tutorial.androiddreamer.myhomework.Activities.MainActivity.EXTRA_MODE;
import static com.tutorial.androiddreamer.myhomework.Activities.MainActivity.EXTRA_NOTE;
import static com.tutorial.androiddreamer.myhomework.Activities.MainActivity.EXTRA_SUBJECT;
import static com.tutorial.androiddreamer.myhomework.Activities.MainActivity.EXTRA_TIME;


public class ArchivedNoteFragment extends Fragment {
    private ArchivedNoteFragmentViewModel viewModel;
    private PagedListArchiveNoteAdapter adapter;
    @BindView(R.id.rv_fragment_archived_note) RecyclerView recyclerView;
    @BindView(R.id.cl_empty_list_fragment_arch_note) ConstraintLayout emptyView;
    @BindView(R.id.imageView_empty_list) ImageView emptyListImage;
    @BindView(R.id.tc_activity_main_its_lonely) TextView  tvEmptyListImage;

    public ArchivedNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_archived_note, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ArchivedNoteFragmentViewModel.class);
        setUIThemeForElements();

        adapter = new PagedListArchiveNoteAdapter(new PagedListArchiveNoteAdapter.LongClickListener() {
            @Override
            public void onLongClick(ArchivedNote archivedNote) {
                viewModel.getArchivedNoteRepository().deleteArchivedNote(archivedNote);
            }
        },
                new PagedListArchiveNoteAdapter.SimpleClickListener() {
                    @Override
                    public void onSimpleClickListener(ArchivedNote note) {
                        Intent intent = new Intent(ArchivedNoteFragment.this.getContext(), BaseDisplayNoteActivity.class);
                        intent.putExtra(EXTRA_MODE, AddNoteActivity.MODE_EDIT_NOTE)
                                .putExtra(EXTRA_SUBJECT, note.getSubject())
                                .putExtra(EXTRA_NOTE, note.getDescription())
                                .putExtra(EXTRA_ID, note.getId())
                                .putExtra(EXTRA_IMPORTANCE, note.getPriority())
                                .putExtra(EXTRA_TIME, note.getTime());
                        startActivity(intent);
                    }
                });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        viewModel.getAllArchivedNotes().observe(this, new Observer<PagedList<ArchivedNote>>() {
            @Override
            public void onChanged(@Nullable PagedList<ArchivedNote> archivedNotes) {
                if(archivedNotes != null) adapter.submitList(archivedNotes);
                if(archivedNotes.isEmpty()){
                    emptyView.setVisibility(View.VISIBLE);
                }else{
                    emptyView.setVisibility(View.GONE);
                }
            }
        });
    }

    // TODO: 12/4/2018
    private void setUIThemeForElements(){
        if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 0){
        }else if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 1){
            emptyListImage.setImageResource(R.drawable.ic_whitebox);
            tvEmptyListImage.setTextColor(Color.WHITE);
        }
    }
}
