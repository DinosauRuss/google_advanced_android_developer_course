package com.example.rek.animationapp;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class PulseAnimationView extends View {

    // Object to draw
    private float mRadius;
    private final Paint mPaint = new Paint();
    private static final int COLOR_ADJUSTER = 3;

    // Location of touch event
    private float mX;
    private float mY;

    // Animation vars
    private static final int ANIMATION_DURATION = 2000;
    private static final int ANIMATION_DELAY = 1000;
    private AnimatorSet mAnimatorSet = new AnimatorSet();


    /**
     * Constructor for creating view from code
     * @param context   Context where view lives
     */
    public PulseAnimationView(Context context) {
        super(context);
    }

    /**
     * Constructor for creating view from xml
     * @param context   Context where view lives
     * @param attrs     XML attributes
     */
    public PulseAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            // Center point of circle
            mX = event.getX();
            mY = event.getY();

            // Cancel any prevoius running animations
            if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
                mAnimatorSet.cancel();
            }
            mAnimatorSet.start();
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Animator to create circle with increasing radius
        // from 0 to width of the view
        ObjectAnimator growAnimator = ObjectAnimator.ofFloat(this, "radius",
                0, getWidth()/2);
        growAnimator.setDuration(ANIMATION_DURATION);
        growAnimator.setInterpolator(new LinearInterpolator());

        // Animator to create circle with decreasing radius
        // from width of the view to 0
        ObjectAnimator shrinkAnimator = ObjectAnimator.ofFloat(this, "radius",
                getWidth()/2, 0);
        shrinkAnimator.setDuration(ANIMATION_DURATION);
        shrinkAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        shrinkAnimator.setStartDelay(ANIMATION_DELAY);

        // Repeating animator object
        ObjectAnimator repeatAnimator = ObjectAnimator.ofFloat(this, "radius",
                0, getWidth()/2);
        repeatAnimator.setDuration(ANIMATION_DURATION);
        repeatAnimator.setStartDelay(ANIMATION_DELAY);
        repeatAnimator.setRepeatCount(1);
        repeatAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Play several animations sequentially
        mAnimatorSet.play(growAnimator).before(shrinkAnimator);
        mAnimatorSet.play(repeatAnimator).after(shrinkAnimator);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mX, mY, mRadius, mPaint);
    }

    /**
     * Required setter for animation property
     * @param radius    This views radius property
     */
    public void setRadius(float radius) {
        this.mRadius = radius;
        mPaint.setColor(Color.RED + (int) radius / COLOR_ADJUSTER);

        invalidate();
    }
}
