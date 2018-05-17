package com.sandywinata.isgupdate.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandywinata.isgupdate.R;
import com.sandywinata.isgupdate.model.ContactModel;
import com.sandywinata.isgupdate.model.POIModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Leonardo on 4/21/2018.
 */

public class POIAdapter extends RecyclerView.Adapter<POIAdapter.ViewHolder> {

    //deklarasi context dari activity dan arraylist yang didapatkan
    Context context;
    List<POIModel> commentList;
//    RoundedBitmapDrawable rounded;

    //konstruktor utk inisialisasi value pada context dan list dari activity
    public POIAdapter(Context context, List<POIModel> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    //view holder untuk deklarasi dan inisialisasi view pada layout comment
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView ticket;
        TextView desc;
        ImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            ticket = itemView.findViewById(R.id.tvTicket);
            desc = itemView.findViewById(R.id.tvDesc);
            photo = itemView.findViewById(R.id.imgPoi);
        }
    }

    @NonNull
    @Override
    public POIAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating/set layout comment ke adapter
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_poi, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final POIModel comment = commentList.get(position);
        holder.name.setText(comment.getName());
        holder.ticket.setText("Ticket entrance:\n"+comment.getTicket());
        holder.desc.setText("Description:\n"+comment.getDesc());
        Picasso.get().load(comment.getImgUrl()).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

}