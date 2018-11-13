package com.lithe.service.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.volleySample.R;


/**
 * Created by lithe on 1/24/2018.
 */

public class IconView extends View {

    private PorterDuffColorFilter porterDuffColorFilter;
    private int iconColor = -1;

    public IconView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IconView, 0, 0);

        try {
            iconColor = typedArray.getColor(R.styleable.IconView_iconViewColor, -1);
            porterDuffColorFilter = new PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN);
        } finally {
            typedArray.recycle();
        }

    }

    public void setIconColor(int iconColor) {
        this.iconColor = iconColor;
        porterDuffColorFilter = new PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN);
        invalidate();
    }

    public int getIconColor() {
        return iconColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawableBackground = getBackground();
        if (drawableBackground != null && iconColor != -1) {
            drawableBackground.setColorFilter(porterDuffColorFilter);
            setBackground(drawableBackground);
        }
    }
}
