package com.example.jupiter.paintboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PaintBoard extends View {
    Paint  paint;
    Bitmap mBitmap;
    Canvas mCanvas;

    int oldX;
    int oldY;

    public PaintBoard(Context context) {
        super(context);

        init(context);
    }

    public PaintBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.BLACK);

    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setLineWidth(float lineWidth) {
        paint.setStrokeWidth(lineWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.WHITE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int curX = (int) event.getX();
        int curY = (int) event.getY();

        if (action == MotionEvent.ACTION_DOWN) {
            oldX = curX;
            oldY = curY;
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (oldX > 0 || oldY >0 ){
                mCanvas.drawLine(oldX, oldY, curX, curY, paint);
            }

            oldX = curX;
            oldY = curY;
        } else if (action == MotionEvent.ACTION_UP) {

        }

        invalidate();

        return true;
    }
}
