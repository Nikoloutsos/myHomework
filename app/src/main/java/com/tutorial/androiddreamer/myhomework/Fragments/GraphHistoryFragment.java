package com.tutorial.androiddreamer.myhomework.Fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.tutorial.androiddreamer.myhomework.R;
import com.tutorial.androiddreamer.myhomework.ViewModels.GraphHistoryFragmentViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphHistoryFragment extends Fragment {
    @BindView(R.id.viewKonfetti) KonfettiView KonfettiView;
    @BindView(R.id.pie_chart) PieChart pieChart;
    private GraphHistoryFragmentViewModel viewModel;

    public GraphHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_graph_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(GraphHistoryFragmentViewModel.class);
        super.onActivityCreated(savedInstanceState);
        setUIThemeForElements();
        fillPieChartWithData();

        Log.d("aaaa", "onActivityCreated -->" + viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getTotalNotesDeleted());

    }



    private void setUIThemeForElements(){
        //TODO use the right colors!
        Legend legend;
        switch (viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme()){
            case 0:
                legend = pieChart.getLegend();
                legend.setForm(Legend.LegendForm.CIRCLE);
                legend.setTextColor(Color.BLACK);
                break;
            case 1:
                legend = pieChart.getLegend();
                legend.setForm(Legend.LegendForm.CIRCLE);
                legend.setTextColor(Color.WHITE);
                break;
        }
    }

    private void fillPieChartWithData(){

        Description desc = new Description();
        desc.setText("");
        pieChart.setDescription(desc);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(7);
        pieChart.setTransparentCircleRadius(10);


        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        yEntrys.add(new PieEntry(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getTotalNotesAdded(),
                "ADDED"));
        yEntrys.add(new PieEntry(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getTotalNotesDeleted(),
                "DELETED"));
        yEntrys.add(new PieEntry(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getTotalNotesEdited(),
                "EDITED"));
        yEntrys.add(new PieEntry(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getTotalNotesArchived(),
                "ARCHIVED"));
        yEntrys.add(new PieEntry(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getTotalNotesShared(),
                "SHARED"));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.parseColor("#FF8C00"));
        colors.add(Color.DKGRAY);
        colors.add(Color.CYAN);

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(4);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "" + ((int) value);
            }
        });


        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setEntryLabelColor(Color.WHITE); //COLOR OF PIE LABELS
        pieChart.invalidate();
    }

}
