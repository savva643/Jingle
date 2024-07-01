package com.example.jingle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import com.google.android.exoplayer2.ui.DefaultTimeBar;

public class RoundedCornerTimeBar extends DefaultTimeBar {
    private Path roundedPath;

    public RoundedCornerTimeBar(Context context) {
        super(context);
        init();
    }

    public RoundedCornerTimeBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundedCornerTimeBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        roundedPath = new Path();
    }

    @Override
    public void onDraw(Canvas canvas) {
        // Call the superclass's onDraw method
        super.onDraw(canvas);

        // Draw rounded corners
        int radius = 20; // Adjust the radius as needed
        roundedPath.reset();
        roundedPath.addRoundRect(0, 0, getWidth(), getHeight(), radius, radius, Path.Direction.CW);

        canvas.clipPath(roundedPath);
    }
}