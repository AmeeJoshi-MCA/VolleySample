package com.lithe.service.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class CustomLinearLayoutManager extends LinearLayoutManager {

    private boolean canVerticallyScrollable = true;
    private boolean canHorizontallyScrollable = true;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public void setCanVerticallyScrollable(boolean canVerticallyScrollable) {
        this.canVerticallyScrollable = canVerticallyScrollable;
    }

    public void setCanHorizontallyScrollable(boolean canHorizontallyScrollable) {
        this.canHorizontallyScrollable = canHorizontallyScrollable;
    }

    @Override
    public boolean canScrollVertically() {
        return canVerticallyScrollable && super.canScrollVertically();
    }

    @Override
    public boolean canScrollHorizontally() {
        return canHorizontallyScrollable && super.canScrollHorizontally();
    }
}
