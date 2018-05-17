package com.sandywinata.isgupdate.config;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sandywinata.isgupdate.model.UserModel;

public class Constants extends Application {
    //firebase database
    public final static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public final static DatabaseReference refUser = database.getReference("user");
    public final static DatabaseReference refAcademicCal = database.getReference("academic");
    public final static DatabaseReference refContact = database.getReference("contact");
    public final static DatabaseReference refStudent = database.getReference("student");
    public final static DatabaseReference refPOI = database.getReference("poi");

    //firebase auth
    public final static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static FirebaseUser currentUser;

    //firebase storage
    public static FirebaseStorage storage = FirebaseStorage.getInstance();
    public static StorageReference storageRef = storage.getReference();

    public static UserModel getLoginUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("userSession", MODE_PRIVATE);
        String name = prefs.getString("nama", ""); //mengambil sharedpreferences nama
        String role = prefs.getString("role", ""); //mengambil sharedpreferences profile_pic
        String email = prefs.getString("email", ""); //mengambil sharedpreferences email
        return new UserModel(name, email, role);
    }
}
