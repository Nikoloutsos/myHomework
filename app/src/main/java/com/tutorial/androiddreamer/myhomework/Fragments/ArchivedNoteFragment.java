package com.tutorial.androiddreamer.myhomework.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tutorial.androiddreamer.myhomework.Adapters.PagedListArchiveNoteAdapter;
import com.tutorial.androiddreamer.myhomework.Model.ArchivedNote;
import com.tutorial.androiddreamer.myhomework.R;
import com.tutorial.androiddreamer.myhomework.ViewModels.ArchivedNoteFragmentViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ArchivedNoteFragment extends Fragment {
    private ArchivedNoteFragmentViewModel viewModel;
    private PagedListArchiveNoteAdapter adapter;
    @BindView(R.id.rv_fragment_archived_note) RecyclerView recyclerView;

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
        adapter = new PagedListArchiveNoteAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        viewModel.getAllArchivedNotes().observe(this, new Observer<PagedList<ArchivedNote>>() {
            @Override
            public void onChanged(@Nullable PagedList<ArchivedNote> archivedNotes) {
                if(archivedNotes != null) adapter.submitList(archivedNotes);
            }
        });




    }
}
