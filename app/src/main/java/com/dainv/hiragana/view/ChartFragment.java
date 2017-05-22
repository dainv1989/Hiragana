package com.dainv.hiragana.view;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.dainv.hiragana.R;
import com.dainv.hiragana.model.AlphabetAdapter;
import com.dainv.hiragana.model.AlphabetItem;
import com.dainv.hiragana.model.JPChar;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {
    private AlphabetAdapter adapter;
    private GridView gvChart;

    private List<AlphabetItem> lstChart;
    private static int chartType = JPChar.HIRAGANA_CHART;
    private static int table = JPChar.TABLE_BASIC;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        JPChar.initAlphabetChart();

        gvChart = (GridView)view.findViewById(R.id.gridChart);
        gvChart.setNumColumns(5);

        setChart(chartType, table);
        if (table == JPChar.TABLE_COMBO)
            gvChart.setNumColumns(3);

        adapter = new AlphabetAdapter(view.getContext(), gvChart.getId(), lstChart);
        gvChart.setAdapter(adapter);

        return view;
    }

    public void setChart(int chartType, int table) {
        if (chartType == JPChar.HIRAGANA_CHART) {
            switch (table) {
                case JPChar.TABLE_BASIC:
                    lstChart = JPChar.lstHiraBasic;
                    break;
                case JPChar.TABLE_COMBO:
                    lstChart = JPChar.lstHiraCombo;
                    break;
                case JPChar.TABLE_DAKUTEN:
                    lstChart = JPChar.lstHiraDakuten;
                    break;
                default:
                    lstChart = null;
                    break;
            }
        } else if (chartType == JPChar.KATAKANA_CHART) {
            switch (table) {
                case JPChar.TABLE_BASIC:
                    lstChart = JPChar.lstKataBasic;
                    break;
                case JPChar.TABLE_COMBO:
                    lstChart = JPChar.lstKataCombo;
                    break;
                case JPChar.TABLE_DAKUTEN:
                    lstChart = JPChar.lstKataDakuten;
                    break;
                default:
                    lstChart = null;
                    break;
            }
        }

        /* store chart and table type information */
        if (lstChart != null) {
            this.chartType = chartType;
            this.table = table;
        }
    }
}
