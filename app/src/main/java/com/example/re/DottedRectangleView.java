package com.example.re;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class DottedRectangleView extends View {
    private Paint paint;
    private Path path;
    private float phase;
    private ValueAnimator animator;
    private static final float DASH_LENGTH = 20;  // Length of each dash
    private static final float GAP_LENGTH = 20;   // Space between dashes

    public DottedRectangleView(Context context) {
        super(context);
        init();
    }

    public DottedRectangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Configure paint properties
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFF000000);  // Black color
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setPathEffect(new DashPathEffect(new float[]{DASH_LENGTH, GAP_LENGTH}, 0));

        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Create rectangle path with 50px padding
        int padding = 50;
        path.reset();
        path.moveTo(padding, padding);
        path.lineTo(w - padding, padding);
        path.lineTo(w - padding, h - padding);
        path.lineTo(padding, h - padding);
        path.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    public void startAnimation() {
        if (animator != null && animator.isRunning()) {
            return;
        }

        // Animate between 0 and sum of dash + gap lengths
        animator = ValueAnimator.ofFloat(0, DASH_LENGTH + GAP_LENGTH);
        animator.setDuration(1500);  // Animation duration
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);

        animator.addUpdateListener(animation -> {
            phase = (float) animation.getAnimatedValue();
            paint.setPathEffect(new DashPathEffect(new float[]{DASH_LENGTH, GAP_LENGTH}, phase));
            invalidate();  // redraw the view
        });
        animator.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();  // Start animation when view is attached
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null) {
            animator.cancel();  // Stop animation when view is detached
        }
    }
}