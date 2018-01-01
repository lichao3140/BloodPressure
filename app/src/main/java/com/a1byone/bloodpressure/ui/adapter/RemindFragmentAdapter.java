package com.a1byone.bloodpressure.ui.adapter;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a1byone.bloodpressure.R;
import com.a1byone.bloodpressure.bean.RemindModel;
import com.a1byone.bloodpressure.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-12-20.
 */

public class RemindFragmentAdapter extends RecyclerView.Adapter {
    private static final String TAG = "RemindFragmentAdapter";

    public static final int ITEM_TYPE_RECYCLER_WIDTH = 1000;
    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    public static final int ITEM_TYPE_ACTION_WIDTH_NO_SPRING = 1002;
    private List<RemindModel> remindModels;
    private Context mContext;
    private ItemTouchHelper mItemTouchHelperExtension;

    public RemindFragmentAdapter (Context context) {
        remindModels = new ArrayList<>();
        mContext = context;
    }

    public void setDates(List<RemindModel> dates) {
        remindModels.clear();
        remindModels.addAll(dates);
    }

    public void updateData(List<RemindModel> dates) {
        setDates(dates);
        notifyDataSetChanged();
    }

    public void setItemTouchHelperExtension(ItemTouchHelper itemTouchHelperExtension) {
        mItemTouchHelperExtension = itemTouchHelperExtension;
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.item_fragment_remind_list, parent, false);
        if (viewType == ITEM_TYPE_ACTION_WIDTH)
            return new ItemSwipeWithActionWidthViewHolder(view);
        if (viewType == ITEM_TYPE_RECYCLER_WIDTH) {
            view = getLayoutInflater().inflate(R.layout.list_item_with_single_delete, parent, false);
            return new ItemViewHolderWithRecyclerWidth(view);
        }
        return new ItemSwipeWithActionWidthNoSpringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ItemBaseViewHolder baseViewHolder = (ItemBaseViewHolder) holder;
        baseViewHolder.bind(remindModels.get(position));
        baseViewHolder.mViewContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showShort(mContext, "tem Content click: #" + holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return remindModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (remindModels.get(position).position == 1) {
            return ITEM_TYPE_ACTION_WIDTH_NO_SPRING;
        }
        if (remindModels.get(position).position == 2) {
            return ITEM_TYPE_RECYCLER_WIDTH;
        }
        return ITEM_TYPE_ACTION_WIDTH;
    }

    private void doDelete(int adapterPosition) {
        remindModels.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public void move(int from, int to) {
        RemindModel prev = remindModels.remove(from);
        remindModels.add(to > from ? to - 1 : to, prev);
        notifyItemMoved(from, to);
    }

    class ItemSwipeWithActionWidthViewHolder extends ItemBaseViewHolder{

        View mActionViewDelete;
        View mActionViewRefresh;

        public ItemSwipeWithActionWidthViewHolder(View itemView) {
            super(itemView);
            mActionViewDelete = itemView.findViewById(R.id.view_list_repo_action_delete);
            mActionViewRefresh = itemView.findViewById(R.id.view_list_repo_action_update);
        }
    }

    class ItemBaseViewHolder extends RecyclerView.ViewHolder {
        TextView mTextTitle;
        TextView mTextIndex;
        View mViewContent;
        View mActionContainer;

        public ItemBaseViewHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_list_main_title);
            mTextIndex = (TextView) itemView.findViewById(R.id.text_list_main_index);
            mViewContent = itemView.findViewById(R.id.view_list_main_content);
            mActionContainer = itemView.findViewById(R.id.view_list_repo_action_container);
        }

        public void bind(RemindModel remindModel) {
            mTextTitle.setText(remindModel.time);
            mTextIndex.setText("#" + remindModel.position);
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        mItemTouchHelperExtension.startDrag(ItemBaseViewHolder.this);
                    }
                    return true;
                }
            });
        }
    }

    class ItemViewHolderWithRecyclerWidth extends ItemBaseViewHolder {
        View mActionViewDelete;

        public ItemViewHolderWithRecyclerWidth(View itemView) {
            super(itemView);
            mActionViewDelete = itemView.findViewById(R.id.view_list_repo_action_delete);
        }
    }

    class ItemSwipeWithActionWidthNoSpringViewHolder extends ItemSwipeWithActionWidthViewHolder {

        public ItemSwipeWithActionWidthNoSpringViewHolder(View itemView) {
            super(itemView);
        }

    }
}
