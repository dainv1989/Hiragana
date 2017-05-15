package com.dainv.hiragana.view;

/**
 * Created by Dai Nguyen on 5/14/2017.
 */

public class AlphabetItem {
    private String strJpchar;
    private String strRomaji;

    public AlphabetItem(String jpchar, String romaji) {
        this.strJpchar = jpchar;
        this.strRomaji = romaji;
    }

    public String getJpchar() {
        return this.strJpchar;
    }

    public String getRomaji() {
        return this.strRomaji;
    }
}
