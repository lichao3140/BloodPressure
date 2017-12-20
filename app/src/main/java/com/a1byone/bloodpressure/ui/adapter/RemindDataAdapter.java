package com.a1byone.bloodpressure.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.a1byone.bloodpressure.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提醒重复日期
 * Created by Administrator on 2017-12-20.
 */

public class RemindDataAdapter extends RecyclerView.Adapter<RemindDataAdapter.MyViewHolder>
        implements View.OnClickListener, View.OnLongClickListener{

    private List<String> list;//数据源
    private Context context;
    private boolean isShowBox = false;  //是否显示单选框,默认false
    private Map<Integer, Boolean> map = new HashMap<>();  // 存储勾选框状态的map集合
    private RecyclerViewOnItemClickListener onItemClickListener;  //接口实例

    public RemindDataAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        initMap();
    }

    //初始化map集合,默认为全选中
    private void initMap() {
        for (int i = 0; i < list.size(); i++) {
            map.put(i, true);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_remind_data,parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(root);
        //为Item设置点击事件
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(list.get(position));
        //长按显示/隐藏
        if (isShowBox) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.add_remind_data_anim);
        //设置checkBox显示的动画
        if (isShowBox)
            holder.checkBox.startAnimation(animation);
        holder.root.setTag(position);  //设置Tag
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                map.put(position, isChecked); //用map集合保存
            }
        });
        // 设置CheckBox的状态
        if (map.get(position) == null) {
            map.put(position, false);
        }
        holder.checkBox.setChecked(map.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            onItemClickListener.onItemClickListener(view, (Integer) view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        //不管显示隐藏，清空状态
        initMap();
        return onItemClickListener != null && onItemClickListener.onItemLongClickListener(view, (Integer) view.getTag());
    }

    //视图管理
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private CheckBox checkBox;
        private View root;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.root = itemView;
            title = (TextView) root.findViewById(R.id.tv_item_data);
            checkBox = (CheckBox) root.findViewById(R.id.cb_item_data);
        }
    }

    //设置点击事件
    public void setRecyclerViewOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //设置是否显示CheckBox
    public void setShowBox() {
        //取反
        isShowBox = !isShowBox;
    }

    //点击item选中CheckBox
    public void setSelectItem(int position) {
        //对当前状态取反
        if (map.get(position)) {
            map.put(position, false);
            Log.e("lichao", "点" + position + "显示");
        } else {
            map.put(position, true);
            Log.e("lichao", "点" + position + "隐藏");
        }
        notifyItemChanged(position);
    }

    //返回集合给Activity
    public Map<Integer, Boolean> getMap() {
        return map;
    }

    //接口回调设置点击事件
    public interface RecyclerViewOnItemClickListener {
        //点击事件
        void onItemClickListener(View view, int position);

        //长按事件
        boolean onItemLongClickListener(View view, int position);
    }
}
