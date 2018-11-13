package com.lithe.service.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.volleySample.R;
import com.lithe.service.application.AppControl;
import java.util.List;

import ru.rambler.libs.swipe_layout.SwipeLayout;

public abstract class CustOrderListAdapter extends RecyclerView.Adapter<CustOrderListAdapter.ViewHolder> {

    private AppControl appControl;

    private Context context;

    private List<String> listDeliveryBoyOrder;

    public CustOrderListAdapter(Context context, List<String> listDeliveryBoyOrder) {

        appControl = new AppControl(context);
        this.context = context;
        this.listDeliveryBoyOrder = listDeliveryBoyOrder;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_list_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


       // holder.textViewCustomerAddress.setText(deliveryBoyOrderData.getDeliveryAddress());

        /*switch (customerData.getOrderStatus()) {
            case Constants.KEY_NEW_ORDER_STATUS:
                holder.frameLayoutOrderConform.setVisibility(View.GONE);
                holder.swipeLayout.setSwipeEnabled(true);
                break;
            case Constants.KEY_CONFORM_ORDER_STATUS:
                holder.layoutParent.setEnabled(false);
                holder.viewConformStatusImage.setBackground(appControl.getDrawableByID(R.drawable.ic_order_conform));
                holder.frameLayoutOrderConform.setVisibility(View.VISIBLE);
                holder.swipeLayout.setSwipeEnabled(false);
                break;
            case Constants.KEY_CANCEL_ORDER_STATUS:
                holder.viewConformStatusImage.setBackground(appControl.getDrawableByID(R.drawable.ic_close_red));
                holder.frameLayoutOrderConform.setVisibility(View.VISIBLE);
                holder.swipeLayout.setSwipeEnabled(false);
                break;
        }*/

        holder.swipeLayout.setOnSwipeListener(new SwipeLayout.OnSwipeListener() {
            @Override
            public void onBeginSwipe(SwipeLayout swipeLayout, boolean moveToRight) {

            }

            @Override
            public void onSwipeClampReached(SwipeLayout swipeLayout, boolean moveToRight) {

            }

            @Override
            public void onLeftStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {

            }

            @Override
            public void onRightStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {

            }
        });

        holder.layoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCellClick(position);
            }
        });

       /* holder.frameLayoutPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int itemCount = Integer.parseInt(deliveryBoyOrderData.getItemQty());
                itemCount++;
                deliveryBoyOrderData.setItemQty(String.valueOf(deliveryBoyOrderData.getItemQty()));

                holder.textViewItemQtyCount.setText("" + itemCount);

            }
        });

        holder.frameLayoutMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int itemCount = Integer.parseInt(deliveryBoyOrderData.getItemQty());
                itemCount--;

                if (itemCount != 0) {
                    itemCount--;
                    deliveryBoyOrderData.set(String.valueOf(itemCount));
                    holder.textViewItemQtyCount.setText("" + itemCount);
                }
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return listDeliveryBoyOrder.size();
    }

    abstract public void onCellClick(int position);

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private View viewConformStatusImage;
        private TextView textViewCustName, textViewCustomerContactNo, textViewOrderId,
                textViewPaymentType, textViewCustomerAddress;

        private CardView layoutParent;
        private FrameLayout frameLayoutOrderConform;
        private FrameLayout frameActionCall, frameActionCancelOrder, frameActionConformOrder;
        private SwipeLayout swipeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            layoutParent = (CardView) itemView.findViewById(R.id.today_listing_cell_layout_parent);

            frameActionCall = (FrameLayout) itemView.findViewById(R.id.listing_cell_right_frame_action_call);
            frameActionCancelOrder = (FrameLayout) itemView.findViewById(R.id.listing_cell_right_frame_action_cancel);
            frameActionConformOrder = (FrameLayout) itemView.findViewById(R.id.listing_cell_right_frame_action_conform);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.today_listing_cell_layout_swipe);

            textViewCustName = (TextView) itemView.findViewById(R.id.today_listing_cell_text_customer_name);
            textViewCustomerContactNo = (TextView) itemView.findViewById(R.id.today_listing_cell_text_customer_contact_number);
            textViewOrderId = (TextView) itemView.findViewById(R.id.today_listing_cell_text_order_id);
            textViewPaymentType = (TextView) itemView.findViewById(R.id.today_listing_cell_text_payment_type);
            textViewCustomerAddress = (TextView) itemView.findViewById(R.id.today_listing_cell_text_customer_address);

        }

        public View getItemView() {
            return itemView;
        }
    }
}