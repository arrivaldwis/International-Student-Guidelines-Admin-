package com.sandywinata.isgupdate.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sandywinata.isgupdate.R;
import com.sandywinata.isgupdate.config.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sandywinata.isgupdate.config.Constants.mAuth;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        loginProcess(email, password);
    }

    private void loginProcess(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            Constants.refUser.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                                        String role = (String) ds.child("role").getValue();
                                        String emails = (String) ds.child("email").getValue();
                                        String nama = (String) ds.child("name").getValue();

                                        if (email.equals(emails)) {
                                            SharedPreferences.Editor editor = getSharedPreferences("userSession", MODE_PRIVATE).edit();
                                            editor.putString("role", role);
                                            editor.putString("email", email);
                                            editor.putString("nama", nama);
                                            editor.apply();

                                            Log.d("", "signInWithEmail:success");
                                            FirebaseUser curUser = Constants.mAuth.getCurrentUser();
                                            Constants.currentUser = curUser;

                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w("", "Failed to read value.", databaseError.toException());
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
