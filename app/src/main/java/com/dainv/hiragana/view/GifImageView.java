package com.dainv.hiragana.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Hong-Quyen on 5/17/2017.
 * This class was copied from below reference:
 * http://www.mavengang.com/2016/05/02/gif-animation-android/
 */

public class GifImageView extends View {

    private final static String TAG = "GifImageView";

    private InputStream inputStream = null;
    private Movie movie = null;
    private String asset;
    private Context context;

    private int height, width;
    private long start = 0;

    private Timer gifTimer;
    private GifTimer gifStop;

    private static final int DURATION = 3000;
    private static boolean is_stopped = false;

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
            setGifImageAsset(asset);
        }
    }

    private void init() {
        setFocusable(true);
        movie = Movie.decodeStream(inputStream);
        width = movie.width();
        height = movie.height();

        requestLayout();

        /* set timer to play gif once */
        gifStop = new GifTimer();
        gifTimer = new Timer();
        gifTimer.schedule(gifStop, movie.duration(), DURATION);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (is_stopped == true) {
            /* show gif image without animation */
            movie.draw(canvas, 0, 0);
            return;
        }

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

    public void setGifImageAsset(String assetPath) {
        AssetManager assetManager = context.getAssets();
        this.asset = assetPath;
        try {
            inputStream = assetManager.open(assetPath);
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "open asset failed");
            return;
        }
        init();
    }

    public void replay() {
        /* return if gif is animating */
        if (is_stopped == false)
            return;

        /* re-play gif animation */
        is_stopped = false;
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "close asset failed");
        }
        start = 0;
        gifTimer.cancel();
        gifStop.cancel();
        this.destroyDrawingCache();

        setGifImageAsset(asset);
        this.invalidate();
    }

    class GifTimer extends TimerTask {
        public void run() {
            /* force cleanup gif data */
            this.cancel();
            try {
                this.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            /* stop itself at 1st run */
            is_stopped = true;
        }
    }
}
