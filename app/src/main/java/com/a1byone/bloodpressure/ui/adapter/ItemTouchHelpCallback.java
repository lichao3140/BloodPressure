package com.a1byone.bloodpressure.ui.adapter;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Administrator on 2017-10-31.
 */

public class ItemTouchHelpCallback extends ItemTouchHelper.Callback {

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP| ItemTouchHelper.DOWN;
        int swipeFlag = ItemTouchHelper.LEFT;

        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        RemindFragmentAdapter adapter = (RemindFragmentAdapter) recyclerView.getAdapter();
        adapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dY != 0 && dX == 0) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        RemindFragmentAdapter.ItemBaseViewHolder holder = (RemindFragmentAdapter.ItemBaseViewHolder) viewHolder;
        if (viewHolder instanceof RemindFragmentAdapter.ItemSwipeWithActionWidthNoSpringViewHolder) {
            if (dX < -holder.mActionContainer.getWidth()) {
                dX =- holder.mActionContainer.getWidth();
            }
            holder.mViewContent.setTranslationX(dX);
            return;
        }
        if (viewHolder instanceof RemindFragmentAdapter.ItemBaseViewHolder) {
            holder.mViewContent.setTranslationX(dX);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
}
