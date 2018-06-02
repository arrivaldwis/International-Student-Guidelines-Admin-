package com.sandywinata.isgupdate.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sandywinata.isgupdate.R;
import com.sandywinata.isgupdate.config.Constants;
import com.sandywinata.isgupdate.model.ContactModel;
import com.sandywinata.isgupdate.view.AddContactActivity;
import com.sandywinata.isgupdate.view.Contacts;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Leonardo on 4/21/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    //deklarasi context dari activity dan arraylist yang didapatkan
    Context context;
    List<ContactModel> commentList;
//    RoundedBitmapDrawable rounded;

    //konstruktor utk inisialisasi value pada context dan list dari activity
    public ContactAdapter(Context context, List<ContactModel> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    //view holder untuk deklarasi dan inisialisasi view pada layout comment
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView person_name;
        TextView occupation;
        TextView roomnumber;
        TextView mobile;
        TextView phone;
        TextView email;
        ImageView person_photo;
        ImageView imgEdit;
        ImageView imgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            person_name = itemView.findViewById(R.id.person_name);
            occupation = itemView.findViewById(R.id.occupation);
            roomnumber = itemView.findViewById(R.id.roomnumber);
            mobile = itemView.findViewById(R.id.mobile);
            phone = itemView.findViewById(R.id.phone);
            email = itemView.findViewById(R.id.email);
            person_photo = itemView.findViewById(R.id.person_photo);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
        }
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating/set layout comment ke adapter
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ContactModel comment = commentList.get(position);
        holder.person_name.setText(comment.getName());
        holder.occupation.setText(comment.getJabatan());
        holder.roomnumber.setText(comment.getOffice());
        holder.mobile.setText(comment.getMobile());
        holder.phone.setText(comment.getPhone());
        holder.email.setText(comment.getEmail());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData(comment);
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AddContactActivity.class);
                i.putExtra("mode", "edit");
                i.putExtra("model", comment);
                context.startActivity(i);
            }
        });
        Picasso.get().load(comment.getImgUrl()).into(holder.person_photo);
    }

    private void deleteData(final ContactModel contact) {
        Constants.refContact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    ContactModel model = ds.getValue(ContactModel.class);
                    if(model.getEmail().equals(contact.getEmail())) {
                        Constants.refContact.child(ds.getKey()).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

}