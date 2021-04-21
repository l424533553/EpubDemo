package com.xuanyuan.epubdemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xuanyuan.epublib.entity.EpubBean;

import java.util.List;

public class MyAdatper extends RecyclerView.Adapter<MyAdatper.ViewHolder> {

    private final LayoutInflater mInflater;
    private List<EpubBean> list;
    private Context context;

    public MyAdatper(List<EpubBean> list, Context context) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
        this.context = context;
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
            //通过href获取
            String href = list.get(position).href;

            Intent intent = new Intent(context, ChapterDetailActivity.class);
            intent.putExtra("href", list.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}