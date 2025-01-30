package com.goodtechsystem.mypwd.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goodtechsystem.mypwd.R;
import com.goodtechsystem.mypwd.vo.PwdVO;

import java.util.ArrayList;

public class PwdListItemAdapter extends RecyclerView.Adapter<PwdListItemAdapter.ViewHolder> {

    private ArrayList<PwdVO> dataSource;
    private Context context;

    public PwdListItemAdapter(Context context, ArrayList<PwdVO> dataSource) {
        this.dataSource = dataSource;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pwdlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PwdVO vo = dataSource.get(position);
        holder.tbx_site.setText(vo.getSite());
        holder.tbx_id.setText(vo.getId());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra("oid", vo.getOid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataSource != null ? dataSource.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView tbx_site;
        protected TextView tbx_id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tbx_site = itemView.findViewById(R.id.pli_site);
            this.tbx_id = itemView.findViewById(R.id.pli_id);
        }
    }
}
