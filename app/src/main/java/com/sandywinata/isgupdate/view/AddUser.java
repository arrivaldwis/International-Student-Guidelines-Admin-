package com.sandywinata.isgupdate.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sandywinata.isgupdate.R;
import com.sandywinata.isgupdate.config.Constants;
import com.sandywinata.isgupdate.model.StudentModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddUser extends AppCompatActivity {

    @BindView(R.id.txtID)
    EditText txtID;
    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.btnAdd)
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);
    }
    
    @OnClick(R.id.btnAdd)
    public void add() {
        addUser();
    }

    private void addUser()  {
        if(txtID.getText().toString().isEmpty()) {
            txtID.setError("Required");
            return;
        }
        
        if(txtEmail.getText().toString().isEmpty()) {
            txtEmail.setError("Required");
            return;
        }
        
        if(txtName.getText().toString().isEmpty()) {
            txtName.setError("Required");
            return;
        }
        
        String sId =  txtID.getText().toString();
        String email =  txtEmail.getText().toString();
        String name =  txtName.getText().toString();
        
        Constants.refStudent.push().setValue(new StudentModel(sId, email, name));
        Toast.makeText(this, "Student has successfully added!", Toast.LENGTH_SHORT).show();
    }
}
