package com.sandywinata.isgupdate.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sandywinata.isgupdate.R;
import com.sandywinata.isgupdate.adapter.ContactAdapter;
import com.sandywinata.isgupdate.config.Constants;
import com.sandywinata.isgupdate.model.ContactModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Contacts extends AppCompatActivity {

    @BindView(R.id.rvData)
    RecyclerView rvData;
    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;

    private ArrayList<ContactModel> contactList;
    private ContactAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        setUI();
        loadContact();
    }

    @OnClick(R.id.btnAdd)
    public void add() {
        Intent intent = new Intent(Contacts.this, AddContactActivity.class);
        startActivity(intent);
    }

    private void setUI() {
        contactList = new ArrayList<>();
        LinearLayoutManager llManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(llManager);
        mAdapter = new ContactAdapter(this, contactList);
        rvData.setAdapter(mAdapter);
    }

    private void loadContact() {
        Constants.refContact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactList.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    ContactModel model = ds.getValue(ContactModel.class);
                    contactList.add(model);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
