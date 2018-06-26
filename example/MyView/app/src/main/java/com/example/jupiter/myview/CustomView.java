package com.example.jupiter.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class CustomView extends View {
    Paint paint;
    int deviceWidth;
    int deviceHeight;
    ShapeDrawable drawable;

    public CustomView(Context context) {
        super(context);

        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.RED);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = (Display) manager.getDefaultDisplay();
        deviceWidth = display.getWidth();
        deviceHeight = display.getHeight();

        drawable = new ShapeDrawable();
        RectShape rect = new RectShape();
        rect.resize(deviceWidth, deviceHeight/2.0f);
        drawable.setShape(rect);
        drawable.setBounds(0, 0, deviceWidth, deviceHeight);

        LinearGradient gradient=  new LinearGradient(0, 0, 0, deviceHeight, Color.BLUE, Color.YELLOW, Shader.TileMode.CLAMP);
        Paint curPaint = drawable.getPaint();
        curPaint.setShader(gradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawRect(100,100,200,200, paint);
        /*paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4.0f);
        paint.setColor(Color.GREEN);
        canvas.drawRect(10,10,100,100, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(128,0,0,255);
        canvas.drawRect(120,10,210,100, paint);

        DashPathEffect dashEffect = new DashPathEffect(new float[]{5,5},1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5.0f);
        paint.setPathEffect(dashEffect);
        paint.setColor(Color.GREEN);
        canvas.drawRect(240,10,330,100, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40.0f);
        canvas.drawText("Hello.", 20, 320, paint);*/

        drawable.draw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            Toast.makeText(getContext(), "Down", Toast.LENGTH_LONG).show();
        }

        return true;
    }
}
