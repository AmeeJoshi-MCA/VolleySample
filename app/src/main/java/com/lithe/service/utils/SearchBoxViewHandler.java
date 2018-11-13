package com.lithe.service.utils;

import android.app.Activity;
import android.transition.TransitionManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.volleySample.R;
import com.lithe.service.application.AppControl;
import com.lithe.service.customviews.IconView;

/**
 * SearchBoxViewHandler class is separate module for search box view handling
 */
public class SearchBoxViewHandler {

    private final int searchTextDelay = 3000; // delay in ms

    private Activity activity;
    private FrameLayout frameSearchBox;

    private FrameLayout frameBottom;

    private EditText editTextSearch;
    private IconView iconViewCancel;

    private AppControl appControl;

    private EditTextDelayedTextWatcher editTextDelayedTextWatcher;

    /**
     * You need to create object for this class and call this constructor by passing some required parameters below
     *
     * @param activity       for basic context
     * @param frameSearchBox this contains Search Box view which is included in required layout or fragment
     */
    public SearchBoxViewHandler(Activity activity, FrameLayout frameSearchBox) {
        this.activity = activity;
        this.frameSearchBox = frameSearchBox;

        appControl = new AppControl(activity);

        frameBottom = (FrameLayout) frameSearchBox.findViewById(R.id.layout_search_box_frame_bottom);
        editTextSearch = (EditText) frameSearchBox.findViewById(R.id.layout_search_box_edittext_search);
        iconViewCancel = (IconView) frameSearchBox.findViewById(R.id.layout_search_box_icon_view_cancel);

        iconViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSearchBox();
            }
        });

        frameBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSearchBox();
            }
        });

        editTextDelayedTextWatcher = new EditTextDelayedTextWatcher(editTextSearch, searchTextDelay);
        editTextSearch.addTextChangedListener(editTextDelayedTextWatcher);

        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideSearchBox();
                }
                return true;
            }
        });
    }

    public void showSearchBox() {
        //requesting focus on input and open keyboard also
        editTextSearch.requestFocus();
        AppControl.showKeyboardOnInput(editTextSearch, activity);
        //show search box layout with transition
        TransitionManager.beginDelayedTransition(frameSearchBox);
        if (frameSearchBox.getVisibility() != View.VISIBLE) {
            frameSearchBox.setVisibility(View.VISIBLE);
        }
    }

    public void hideSearchBox() {
        //cancel focus on input and close keyboard also
        editTextSearch.clearFocus();
        AppControl.hideKeyboard(activity);
        //hide search box layout with transition
        TransitionManager.beginDelayedTransition(frameSearchBox);
        if (frameSearchBox.getVisibility() == View.VISIBLE) {
            frameSearchBox.setVisibility(View.GONE);
        }
    }

    public void clearBox(){
        editTextSearch.setText("");
    }

    public String getSearchValue() {
        return editTextSearch.getText().toString().trim();
    }

    /**
     * @return boolean is search layout is visible or not
     */
    public boolean isSearchBoxVisible() {
        return frameSearchBox.getVisibility() == View.VISIBLE;
    }

    /**
     * set listener to receive text change events on search input
     *
     * @param delayedTextChangeListener this will set listener to search input
     */
    public void setDelayedTextChangeListener(EditTextDelayedTextWatcher.DelayedTextChangeListener delayedTextChangeListener) {
        editTextDelayedTextWatcher.setDelayedTextChangeListener(delayedTextChangeListener);
    }

}
