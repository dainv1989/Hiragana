package com.dainv.hiragana.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dainv.hiragana.R;

/**
 * Created by Hong-Quyen on 5/14/2017.
 */

public class AlphabetCellView extends LinearLayout {
    private TextView tvJpchar;
    private TextView tvRomaji;

    public AlphabetCellView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.alphabet_cell, this);

        tvJpchar = (TextView)findViewById(R.id.txtJpchar);
        tvRomaji = (TextView)findViewById(R.id.txtRomaji);
    }

    public void setItem(AlphabetItem item) {
        tvJpchar.setText(item.getJpchar());
        tvRomaji.setText(item.getRomaji());
    }
}
