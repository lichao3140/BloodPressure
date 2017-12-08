package com.a1byone.bloodpressure.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a1byone.bloodpressure.R;

/**
 * Created by Administrator on 2017-12-08.
 */

public class MeasureRecyclerViewAdapter extends RecyclerView.Adapter<MeasureRecyclerViewAdapter.DevicesHolder> {
    private Context mCtx;

    public MeasureRecyclerViewAdapter(Context context) {
        this.mCtx = context;
    }

    @Override
    public DevicesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_devices, null);
        return new DevicesHolder(view);
    }

    @Override
    public void onBindViewHolder(DevicesHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class DevicesHolder extends RecyclerView.ViewHolder {

        public DevicesHolder(View itemView) {
            super(itemView);
        }
    }
}
