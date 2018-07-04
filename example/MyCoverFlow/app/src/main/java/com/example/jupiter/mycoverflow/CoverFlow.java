package com.example.jupiter.mycoverflow;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class CoverFlow extends Gallery {
    Camera camera = new Camera();

    public static int maxRotationAngle = 55;
    public static int maxZoom = -60;
    private int centerPoint;

    public CoverFlow(Context context) {
        super(context);

        init(context);
    }

    public CoverFlow(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        setStaticTransformationsEnabled(true);

    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        int childCenter =  child.getLeft() + child.getWidth() / 2;
        int childWidth = child.getWidth();

        int rotationAngle = 0;
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);

        if (childCenter == centerPoint) {
            transformImageBitmap((ImageView) child, t, 0);
        } else {
            rotationAngle = (int) ((((float)(centerPoint - childCenter)/childWidth)) * maxRotationAngle);
            if (Math.abs(rotationAngle) > maxRotationAngle) {
                rotationAngle = (rotationAngle < 0)?-maxRotationAngle:maxRotationAngle;
            }
            transformImageBitmap((ImageView) child, t, rotationAngle);
        }

        return true;
    }

    private void transformImageBitmap(ImageView child, Transformation t, int rotationAngle) {
        camera.save();

        Matrix matrix = t.getMatrix();
        int imageHeight = child.getLayoutParams().height;
        int imageWidth = child.getLayoutParams().width;
        int rotation = Math.abs(rotationAngle);

        camera.translate(0.0f, 0.0f, 100.0f);

        if (rotation < maxRotationAngle) {
            float zoomAmount = (float) (maxZoom + (rotation * 1.5));
            camera.translate(0.0f, 0.0f, zoomAmount);
        }

        camera.rotateY(rotationAngle);
        camera.getMatrix(matrix);

        matrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
        matrix.postTranslate((imageWidth / 2), (imageHeight / 2));

        camera.restore();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerPoint = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
        super.onSizeChanged(w, h, oldw, oldh);


    }
}
