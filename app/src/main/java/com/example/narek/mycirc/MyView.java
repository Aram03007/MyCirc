package com.example.narek.mycirc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Narek on 3/23/16.
 */
public class MyView  extends View {
    private static final int RADIUS = 60;

    private SparseArray<PointF> mActivePointers;
    List<Circle> circles = new ArrayList<>();
    private Paint mPaint;
    private int[] colors = { Color.BLUE, Color.GREEN, Color.MAGENTA,
            Color.BLACK, Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY,
            Color.LTGRAY, Color.YELLOW };

    private Paint textPaint;


    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mActivePointers = new SparseArray<PointF>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // set painter color to a color you like
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(20);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("", "list size " + circles.size());
        // get pointer index from the event object
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);


        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                // We have a new pointer. Lets add it to the list of pointers
                for (int i = 0; i < circles.size(); i++) {
                    if (circles.get(i).containsPoint(event.getX(pointerIndex), event.getY(pointerIndex))) {
                        circles.remove(i);
                        invalidate();
                        return false;
                    }
                }
                Random random = new Random();
                int tmp = random.nextInt(10);

                Circle circle = new Circle(event.getX(), event.getY(), RADIUS,colors[tmp]);

                    circles.add(pointerId, circle);
                    PointF f = new PointF();
                    f.x = event.getX(pointerIndex);
                    f.y = event.getY(pointerIndex);
                    mActivePointers.put(pointerId, f);




                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    Circle circle = circles.get(event.getPointerId(i));
                    if (circle != null) {
                        circle.setCenterX(event.getX(i));
                        circle.setCenterY(event.getY(i));
                    }
                }
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    PointF point = mActivePointers.get(event.getPointerId(i));
                    if (point != null) {
                        point.x = event.getX(i);
                        point.y = event.getY(i);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                mActivePointers.clear();

            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
//                mActivePointers.remove(pointerId);
//                break;
            }
        }

        invalidate();


        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // draw all pointers
        for (int size = circles.size(), i = 0; i < size; i++) {
            Circle circle = circles.get(i);
            if (circle != null) {
                mPaint.setColor(circle.getColor());
                canvas.drawCircle(circle.getCenterX(), circle.getCenterY(), RADIUS, mPaint);
            }
        }
        canvas.drawText("Total pointers: " + mActivePointers.size(), 10, 40, textPaint);

    }


}
