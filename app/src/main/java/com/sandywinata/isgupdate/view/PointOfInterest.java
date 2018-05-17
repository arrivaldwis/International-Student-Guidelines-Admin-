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
import com.sandywinata.isgupdate.adapter.POIAdapter;
import com.sandywinata.isgupdate.config.Constants;
import com.sandywinata.isgupdate.model.ContactModel;
import com.sandywinata.isgupdate.model.POIModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PointOfInterest extends AppCompatActivity {

    @BindView(R.id.rvData)
    RecyclerView rvData;
    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;

    private ArrayList<POIModel> poiList;
    private POIAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_of_interest);
        ButterKnife.bind(this);
        setUI();
        loadPOI();
    }

    @OnClick(R.id.btnAdd)
    public void add() {
        Intent intent = new Intent(PointOfInterest.this, AddPOIActivity.class);
        startActivity(intent);
    }

    private void setUI() {
        poiList = new ArrayList<>();
        LinearLayoutManager llManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(llManager);
        mAdapter = new POIAdapter(this, poiList);
        rvData.setAdapter(mAdapter);
    }

    private void loadPOI() {
        Constants.refPOI.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                poiList.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    POIModel model = ds.getValue(POIModel.class);
                    poiList.add(model);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
