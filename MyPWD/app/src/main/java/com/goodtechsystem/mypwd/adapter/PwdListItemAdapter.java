package com.goodtechsystem.mypwd.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.goodtechsystem.mypwd.activity.PwdDetailActivity;
import com.goodtechsystem.mypwd.R;
import com.goodtechsystem.mypwd.bo.PwdBO;
import com.goodtechsystem.mypwd.util.EncDecUtil;
import com.goodtechsystem.mypwd.vo.PwdConst;
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

        String id = vo.getId();
        id = new EncDecUtil().decryptString(id);

        holder.tbx_id.setText(id);
        holder.tbx_purpose.setText(vo.getPurpose());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PwdDetailActivity.class);
                intent.putExtra("oid", vo.getOid());
                intent.putExtra("type", PwdConst.TYPE_MODIFY);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteDialog(position, vo.getOid());
                return true;
            }
        });
    }

    protected void showDeleteDialog(int position, String oid){
        new AlertDialog.Builder(context)
                .setTitle(R.string.warn_pwd_delete_title)
                .setMessage(R.string.warn_pwd_delete_confirm)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    dataSource.remove(position);
                    this.notifyItemRemoved(position);

                    PwdBO bo = new PwdBO(context);
                    bo.deletePwd(oid);

                    Toast.makeText(context, context.getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(context.getString(R.string.cancel), null)
                .show();
    }

    @Override
    public int getItemCount() {
        return (dataSource != null ? dataSource.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView tbx_site;
        protected TextView tbx_id;
        protected TextView tbx_purpose;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tbx_site = itemView.findViewById(R.id.pli_site);
            this.tbx_id = itemView.findViewById(R.id.pli_id);
            this.tbx_purpose = itemView.findViewById(R.id.pli_purpose);
        }
    }
}
