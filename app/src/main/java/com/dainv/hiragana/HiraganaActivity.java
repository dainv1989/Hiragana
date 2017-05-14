package com.dainv.hiragana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.dainv.hiragana.view.AlphabetAdapter;
import com.dainv.hiragana.view.AlphabetItem;

import java.util.ArrayList;
import java.util.List;

public class HiraganaActivity extends AppCompatActivity {

    private static String basic_mask[] = {
            "a", "i", "u", "e", "o",
            "ka", "ki", "ku", "ke", "ko",
            "ya", "", "yu", "", "yo",
            "ra", "ri", "ru", "re", "ro",
            "wa", "", "", "", "wo",
            "n"
    };

    private GridView gvBasicChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiragana);

        gvBasicChart = (GridView)findViewById(R.id.gridChart);
        List<AlphabetItem> lstChart = initAlphabetChart();
        AlphabetAdapter adapter = new AlphabetAdapter(this, gvBasicChart.getId(), lstChart);
        gvBasicChart.setAdapter(adapter);
    }

    private List<AlphabetItem> initAlphabetChart() {
        List<AlphabetItem> lstItem = new ArrayList<>();

        for (int i = 0; i < basic_mask.length; i++) {
            lstItem.add(new AlphabetItem(basic_mask[i], basic_mask[i]));
        }
        return lstItem;
    }
}
