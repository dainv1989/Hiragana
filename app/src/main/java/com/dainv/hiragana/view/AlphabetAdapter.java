package com.dainv.hiragana.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Hong-Quyen on 5/14/2017.
 */

public class AlphabetAdapter extends ArrayAdapter<AlphabetItem> {
    private List<AlphabetItem> items;
    private Context context;

    public AlphabetAdapter(Context context, int resource, List<AlphabetItem> items) {
        super(context, resource, items);
        this.items = items;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlphabetCellView view = new AlphabetCellView(this.context);
        view.setItem(this.items.get(position));
        return view;
    }
}
