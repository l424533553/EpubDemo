package com.xuanyuan.epubdemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.xuanyuan.epubbook.entity.ChapterBean;

import java.util.List;

public class MyAdatper extends RecyclerView.Adapter<MyAdatper.ViewHolder> {

    private final LayoutInflater mInflater;
    private List<ChapterBean> list;
    private Context context;

    private OnItemClick onClickListener;

    public void setOnClickListener(OnItemClick onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MyAdatper(List<ChapterBean> list, Context context) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    
    public void setList(@Nullable List<ChapterBean> list) {
        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.title);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(list.get(position).tilte);
        holder.mTextView.setOnClickListener(v -> {
            if (onClickListener != null) {
                //通过href获取
                String href = list.get(position).href;
                Intent intent = new Intent(context, ChapterDetailActivity.class);
                intent.putExtra("href", href);
                onClickListener.onItemClick(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}