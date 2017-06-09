package com.dainv.hiragana;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.dainv.hiragana.model.JPChar;
import com.dainv.hiragana.view.ChartFragment;

public class HiraganaActivity extends AppCompatActivity {

    private ImageView btnSwitchChart;
    private ImageView btnWriting;
    private ImageView btnExercise;
    private ImageView btnPlaySound;

    private TabLayout tabChartType;
    private ViewPager pagerChart;
    private PagerAdapter pagerAdapter;

    private static boolean is_playing = false;
    private static int current_chart = JPChar.HIRAGANA_CHART;

    private Thread player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiragana);

        btnSwitchChart = (ImageView)findViewById(R.id.btnSwitch);
        btnPlaySound = (ImageView)findViewById(R.id.btnPlayAll);
        btnExercise = (ImageView)findViewById(R.id.btnHiraExer);
        btnWriting = (ImageView)findViewById(R.id.btnPencil);

        final Context context = this;
        Intent intent = getIntent();
        int chart_type = intent.getIntExtra("CHART_TYPE", JPChar.HIRAGANA_CHART);
        if ((chart_type == JPChar.HIRAGANA_CHART) || (chart_type == JPChar.KATAKANA_CHART))
            current_chart = chart_type;

        /* tablayout implement */
        tabChartType = (TabLayout)findViewById(R.id.tabChartType);
        tabChartType.addTab(tabChartType.newTab().setText(R.string.basic_header));
        tabChartType.addTab(tabChartType.newTab().setText(R.string.dakuten_header));
        tabChartType.addTab(tabChartType.newTab().setText(R.string.combo_header));

        pagerChart = (ViewPager)findViewById(R.id.pagerChart);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerChart.setAdapter(pagerAdapter);
        pagerChart.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabChartType));

        tabChartType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateChartView();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                /* Do nothing */
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                /* Do nothing */
            }
        });
        /* end tablayout */

        btnSwitchChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_chart == JPChar.HIRAGANA_CHART)
                    current_chart = JPChar.KATAKANA_CHART;
                else
                    current_chart = JPChar.HIRAGANA_CHART;
                updateChartView();
                pagerAdapter.notifyDataSetChanged();
            }
        });

        btnPlaySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_playing == false) {
                    is_playing = true;
                    playAllSounds();
                    btnPlaySound.setImageResource(R.drawable.stop);
                } else {
                    stopPlaying();
                }
            }
        });

        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();

                Intent intent = new Intent(context, ExcerciseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("QUESTION_TYPE", JPChar.QTYPE_READ_HIRA);
                context.startActivity(intent);
            }
        });

        btnWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();

                Intent intent = new Intent(context, CharActivity.class);
                intent.putExtra("CHAR_TYPE", current_chart);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopPlaying();
    }

    private void playAllSounds() {
        player = new Thread(new Runnable() {
            int current_table = tabChartType.getSelectedTabPosition();
            String sound_chars[] = JPChar.basic_chars;
            Context context = getApplicationContext();

            /* show play icon after finishing play all sounds */
            Runnable showPlayButton = new Runnable() {
                @Override
                public void run() {
                    stopPlaying();
                }
            };

            @Override
            public void run() {
                if (current_table == JPChar.TABLE_DAKUTEN) {
                    sound_chars = JPChar.dakuten_chars;
                } else if (current_table == JPChar.TABLE_COMBO) {
                    sound_chars = JPChar.combo_chars;
                }

                for (int i = 0; i < sound_chars.length; i++) {
                    if (sound_chars[i] == "")
                        continue;

                    /* stop playing all sounds simply by setting is_playing var to false */
                    if (is_playing == false)
                        return;

                    JPChar.playSound(sound_chars[i], context);
                    /* waiting a while for a sound is finished playing */
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                /* set stop status after finishing play all sounds */
                runOnUiThread(showPlayButton);
            }
        });
        player.start();
    }

    private void stopPlaying() {
        is_playing = false;
        btnPlaySound.setImageResource(R.drawable.play);
    }

    private void updateChartView() {
        int tab_index = tabChartType.getSelectedTabPosition();
        pagerChart.setCurrentItem(tab_index);
    }

    private final static int NUMBER_OF_TABS = 3;
    private class PagerAdapter extends FragmentStatePagerAdapter {
        private int numTabs = NUMBER_OF_TABS;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ChartFragment fragment = ChartFragment.newInstance(current_chart, position);
            return fragment;
        }

        @Override
        public int getCount() {
            return numTabs;
        }

        /**
         * This function helps force to switch chart table from hiragana to katakana
         * and vice versa
         */
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
