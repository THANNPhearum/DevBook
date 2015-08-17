package com.dmi.devbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmi.devbook.R;
import com.dmi.devbook.model.Dev;
import com.dmi.devbook.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by thannphearum on 8/17/15.
 */
public class DevAdapter extends RecyclerView.Adapter<DevAdapter.ViewHolder> {

    private List<Dev> mDevs = null;
    private Context mContext = null;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView viewDevName;
        public TextView viewDevPosition;
        public ImageView viewDevPhoto;

        public ViewHolder(View v) {
            super(v);
            viewDevName = (TextView) v.findViewById(R.id.view_deventry_name);
            viewDevPosition = (TextView) v.findViewById(R.id.view_deventry_position);
            viewDevPhoto = (ImageView) v.findViewById(R.id.view_deventry_photo);
        }
    }

    public DevAdapter(List<Dev> devs, Context context) {
        mDevs = devs;
        mContext = context;
    }

    @Override
    public DevAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_deventry, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.viewDevName.setText(mDataset[position]);
        holder.viewDevName.setText(mDevs.get(position).getName());
        holder.viewDevPosition.setText(mDevs.get(position).getPosition());
        Picasso.with(mContext).load(mDevs.get(position).getPhoto()).transform(new CircleTransform()).into(holder.viewDevPhoto);
    }

    @Override
    public int getItemCount() {
        return mDevs.size();
    }

}
