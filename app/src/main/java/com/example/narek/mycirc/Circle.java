package com.example.narek.mycirc;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Narek on 3/23/16.
 */
public class Circle  {

    private float centerX;
    private float centerY;
    private int color;
    private float radius;

    public Circle(float centerX, float centerY, float radius, int color) {
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
        this.color = color;
    }

    public int getColor() {

        return color;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public boolean containsPoint(float x, float y) {
        float deltaX = centerX - x;
        float deltaY = centerY - y;
        return radius >= Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }


}

