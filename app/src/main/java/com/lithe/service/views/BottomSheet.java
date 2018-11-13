package com.lithe.service.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.volleySample.R;
import com.lithe.service.models.IdValue;

import java.util.List;

/**
 * Created by Krunal on 1/25/2017.
 */

public abstract class BottomSheet {

    public BottomSheet(Context context, List<IdValue> list) {
        final Animation animIn = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
        final Animation animOut = AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom);
        animIn.setStartOffset(100);

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.bottom_sheet_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                BottomSheet.this.onDismiss();
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
        Button buttonCancel = (Button) dialog.findViewById(R.id.bottom_sheet_fix_button);
        TextView textNoListItems = (TextView) dialog.findViewById(R.id.bottom_sheet_text_no_list_items);

        layout.setVisibility(View.VISIBLE);

        listView.setAdapter(new BottomSheetItemAdepter(context, list));

        if (list.size() == 0) {
            textNoListItems.setVisibility(View.VISIBLE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemSelected(position);
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

        dialog.show();
    }

    public abstract void onListItemSelected(int selectedPosition);

    public abstract void onCancelButtonClick();

    public abstract void onDismiss();

    public class BottomSheetItemAdepter extends BaseAdapter {

        private Context context;
        private List<IdValue> itemList;

        public BottomSheetItemAdepter(final Context context, final List<IdValue> itemList) {
            this.context = context;
            this.itemList = itemList;
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(final int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_list_cell, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.list_cell_title);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            IdValue idValue = itemList.get(position);
            viewHolder.txtTitle.setText(idValue.getValue());
            return convertView;
        }

        private class ViewHolder {
            TextView txtTitle;
        }
    }
}
