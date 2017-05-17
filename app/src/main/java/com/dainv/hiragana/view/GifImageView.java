package com.dainv.hiragana.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;

/**
 * Created by Hong-Quyen on 5/17/2017.
 * This class was copied from below reference:
 * http://www.mavengang.com/2016/05/02/gif-animation-android/
 */

public class GifImageView extends View {
    private InputStream inputStream;
    private Movie movie;
    private int height, width;
    private long start;
    private Context context;

    private static final int DURATION = 1000;

    public GifImageView(Context context) {
        super(context);
        this.context = context;
    }

    public GifImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GifImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        if (attrs.getAttributeName(1).equals("background")) {
            int id = Integer.parseInt(attrs.getAttributeValue(1).substring(1));
            setGifImageResource(id);
        }
    }

    private void init() {
        setFocusable(true);
        movie = Movie.decodeStream(inputStream);
        width = movie.width();
        height = movie.height();

        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long now = SystemClock.uptimeMillis();
        if (start == 0) {
            start = now;
        }

        if (movie != null) {
            int duration = movie.duration();
            if (duration == 0) {
                duration = DURATION;
            }

            int relTime = (int)((now - start) % duration);
            movie.setTime(relTime);
            movie.draw(canvas, 0, 0);
            invalidate();
        }
    }

    public void setGifImageResource(int id) {
        inputStream = context.getResources().openRawResource(id);
        init();
    }
}
