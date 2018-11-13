package com.lithe.service.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.volleySample.R;

import java.util.ArrayList;
import java.util.List;


public abstract class BottomSheetCheck {

    public BottomSheetCheck(Context context, List<String> list) {
        final Animation animIn = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
        final Animation animOut = AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom);
        animIn.setStartOffset(100);

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.bottom_sheet_check_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                BottomSheetCheck.this.onDismiss();
            }
        });

        final RelativeLayout parentLayout = (RelativeLayout) dialog.findViewById(R.id.dialog_parent);

        parentLayout.startAnimation(animIn);

        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                parentLayout.startAnimation(animOut);
            }
        });

        RelativeLayout layout = (RelativeLayout) dialog.findViewById(R.id.bottom_sheet_fix_bottom_layout);
        ListView listView = (ListView) dialog.findViewById(R.id.bottom_sheet_fix_list);
        Button buttonCancel = (Button) dialog.findViewById(R.id.bottom_sheet_fix_button_cancel);
        Button buttonOk = (Button) dialog.findViewById(R.id.bottom_sheet_fix_button_ok);
        TextView textNoListItems = (TextView) dialog.findViewById(R.id.bottom_sheet_text_no_list_items);

        layout.setVisibility(View.VISIBLE);

        final BottomSheetItemCheckAdepter bottomSheetItemCheckAdepter = new BottomSheetItemCheckAdepter(context, list);

        listView.setAdapter(bottomSheetItemCheckAdepter);

        if (list.size() == 0) {
            textNoListItems.setVisibility(View.VISIBLE);
        }

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelButtonClick();
                animOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                parentLayout.startAnimation(animOut);
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOkButtonClick(bottomSheetItemCheckAdepter.getCheckedItemList());
                animOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                parentLayout.startAnimation(animOut);
            }
        });

        dialog.show();
    }

    public abstract void onCancelButtonClick();

    public abstract void onOkButtonClick(List<Integer> checkedListItems);

    public abstract void onDismiss();


    public class BottomSheetItemCheckAdepter extends BaseAdapter {

        private Context context;
        private List<String> listItem;
        private List<Integer> checkedListItems;
        private List<Boolean> listCheckPosition;

        public BottomSheetItemCheckAdepter(final Context context, final List<String> listItem) {
            this.context = context;
            this.listItem = listItem;
            checkedListItems = new ArrayList<>();
            listCheckPosition = new ArrayList<>();
            for (int i = 0; i < listItem.size(); i++) {
                listCheckPosition.add(false);
            }
        }

        @Override
        public int getCount() {
            return listItem.size();
        }

        @Override
        public Object getItem(final int position) {
            return listItem.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_check_list_cell, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.checkTitle = (CheckBox) convertView.findViewById(R.id.list_cell_title);


                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.checkTitle.setText(listItem.get(position));

            viewHolder.checkTitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Integer currentPosition = position;

                    if (isChecked) {
                        listCheckPosition.set(position, true);
                        checkedListItems.add(currentPosition);
                    } else {
                        listCheckPosition.set(position, false);
                        checkedListItems.remove(currentPosition);
                    }
                }
            });

            viewHolder.checkTitle.setChecked(listCheckPosition.get(position));

            return convertView;
        }

        public List<Integer> getCheckedItemList() {
            return checkedListItems;
        }

        private class ViewHolder {
            CheckBox checkTitle;
        }

    }

}
