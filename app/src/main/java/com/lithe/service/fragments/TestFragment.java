package com.lithe.service.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.volleySample.R;
import com.lithe.service.application.AppControl;

import com.lithe.service.views.BottomSheet;
import com.lithe.service.views.FrameLoaderDialog;

public class TestFragment extends Fragment {

    private View rootView;

    private FrameLoaderDialog frameLoaderDialog;
    private TabLayout tabLayout;
    private RecyclerView recyclerViewItemList;
    private TextView textButtonAccept, textViewTotalSummary, textNoRecord;
    private String totalSummaryOfSubscription, totalSummaryOfOnlineOrder, totalSummaryOfPickupReturn;
    private AppControl appControl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_pickup_order, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setupObjects();
        setupUpdateUI();
        setupComponents();
        performOperations();
    }

    private synchronized void setupObjects() {


        recyclerViewItemList = (RecyclerView) findViewById(R.id.fragment_pickup_order_recycler_view_item_total);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewItemList.setLayoutManager(mLayoutManager);
        recyclerViewItemList.setItemAnimator(new DefaultItemAnimator());

        textButtonAccept = (TextView) findViewById(R.id.activity_today_textview_accept);
        textViewTotalSummary = (TextView) findViewById(R.id.textview_item_summery_total);
        textNoRecord = (TextView) findViewById(R.id.text_no_records);

        tabLayout = (TabLayout) findViewById(R.id.fragment_pickup_order_tab_layout);
    }

    private synchronized void setupUpdateUI() {

        //new blank list for menus

        /*String stockOnHandList = AppControl.getPreferenceHelper().getStartDayResponse();
        if (stockOnHandList != null) {
            stockOnHandListResponse = new Gson().fromJson(stockOnHandList, StockOnHandListResponse.class);

            listSubscriptionOrder = stockOnHandListResponse.getData();
            *//*listOnlineOrder = startDayResponse.getData().getOnlineOrderList();
            listPickupReturnOrder = startDayResponse.getData().getPickupReturnOrderList();

            totalSummaryOfSubscription = startDayResponse.getData().getTotalSummaryOfSubscriptionOrder();
            totalSummaryOfOnlineOrder = startDayResponse.getData().getTotalSummaryOfOnlineOrder();
            totalSummaryOfPickupReturn = startDayResponse.getData().getTotalSummaryOfPickupReturnOrder();*//*
        }*/
    }

    private synchronized void setupComponents() {
        textButtonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppControl.getPreferenceHelper().setIsAccepted(true);

            }
        });
    }

    private synchronized void performOperations() {
    }


    private void setupSubscriptionOrderList() {


            /*recyclerViewTotalItemList.setAdapter(new PickupOrderTotalQtyListAdapter(homeActivity, listItemTotalQty) {
                @Override
                public void onCellClick(int position, IdValue idValue) {

                }
            });*/


        new BottomSheet(getActivity(), null) {
            @Override
            public void onListItemSelected(int selectedPosition) {
               // final String selRemark = tempList.get(selectedPosition).getValue();
            }

            @Override
            public void onCancelButtonClick() {

            }

            @Override
            public void onDismiss() {

            }
        };

    }

    private View findViewById(int resourceID) {
        return rootView.findViewById(resourceID);
    }
}