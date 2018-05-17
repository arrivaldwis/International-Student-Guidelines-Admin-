package com.sandywinata.isgupdate.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.sandywinata.isgupdate.R;
import com.sandywinata.isgupdate.config.Constants;
import com.sandywinata.isgupdate.model.UserModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.cvAcademicCal)
    CardView cvAcademicCal;
    @BindView(R.id.cvAccomodation)
    CardView cvAccomodation;
    @BindView(R.id.cvChat)
    CardView cvChat;
    @BindView(R.id.cvContact)
    CardView cvContact;
    @BindView(R.id.cvAddUser)
    CardView cvAddUser;
    @BindView(R.id.cvPointofInterest)
    CardView cvPointofInterest;
    @BindView(R.id.tvName)
    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadName();
    }

    private void loadName() {
        UserModel user = Constants.getLoginUser(this);
        tvName.setText("Welcome, "+user.getName()+"!");
    }

    @OnClick(R.id.cvAcademicCal)
    public void academic() {
        Intent intent = new Intent(MainActivity.this, AcademicCal.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvAccomodation)
    public void accommodation() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.cvContact)
    public void contact() {
        Intent intent = new Intent(MainActivity.this, Contacts.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvAddUser)
    public void adduser() {
        Intent intent = new Intent(MainActivity.this, AddUser.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvPointofInterest)
    public void poi() {
        Intent intent = new Intent(MainActivity.this, PointOfInterest.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvChat)
    public void chat() {
        Intent intent = new Intent(MainActivity.this, ChatList.class);
        startActivity(intent);
    }
}
