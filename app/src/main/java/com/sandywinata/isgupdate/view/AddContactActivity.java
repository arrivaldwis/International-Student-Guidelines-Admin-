package com.sandywinata.isgupdate.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sandywinata.isgupdate.R;
import com.sandywinata.isgupdate.config.Constants;
import com.sandywinata.isgupdate.model.ContactModel;
import com.sandywinata.isgupdate.model.POIModel;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddContactActivity extends AppCompatActivity {

    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.imgPerson)
    ImageView imgPerson;
    @BindView(R.id.tvName)
    EditText etName;
    @BindView(R.id.tvPhone)
    EditText etPhone;
    @BindView(R.id.tvMobile)
    EditText etMobile;
    @BindView(R.id.tvOccupation)
    EditText etOccupation;
    @BindView(R.id.tvOffice)
    EditText etOffice;
    @BindView(R.id.tvEmail)
    EditText etEmail;
    private StorageReference refPhotoProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.imgPerson)
    public void imgPOI() {
        ImagePicker.create(this)
                .returnMode(ReturnMode.ALL) //all artinya setiap aksi yang dipilih dri galeri atau kamera akan langsung di kirimkan ke activity result
                .folderMode(true) // jika true maka ditampilkan daftar folder
                .toolbarFolderTitle("Folder") // judul folder yang dipilih
                .toolbarImageTitle("Tap to select") // judul pemilihan gambar
                .toolbarArrowColor(Color.WHITE) // tombol back pada toolbar
                .single() // single mode
                .limit(1) // limit/batasan gambar yang bisa dipilih
                .showCamera(true) // tampilkan kamera atau tidak
                .imageDirectory("Camera") // mengatur default directory yang dibuka
                .enableLog(false) // disabling log
                .start(); // jalankan imagepicker
    }

    Uri photoUrl;

    @OnClick(R.id.btnAdd)
    public void add() {
        addPOI();
    }

    private void addPOI() {
        if(etName.getText().toString().isEmpty()) {
            etName.setError("Required");
            return;
        }
        if(etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Required");
            return;
        }
        if(etMobile.getText().toString().isEmpty()) {
            etMobile.setError("Required");
            return;
        }
        if(etOccupation.getText().toString().isEmpty()) {
            etOccupation.setError("Required");
            return;
        }
        if(etOffice.getText().toString().isEmpty()) {
            etOffice.setError("Required");
            return;
        }
        if(etPhone.getText().toString().isEmpty()) {
            etPhone.setError("Required");
            return;
        }

        final String name =  etName.getText().toString();
        final String occupation =  etOccupation.getText().toString();
        final String phone =  etPhone.getText().toString();
        final String mobile =  etMobile.getText().toString();
        final String email =  etEmail.getText().toString();
        final String office =  etOffice.getText().toString();

        if(isPicChange) {

            //penggunaan firebase storage untuk store gambar yang di pilih
            //penamaan folder dan nama gambar yang diupload
            refPhotoProfile = Constants.storageRef.child("contact/" + System.currentTimeMillis() + ".jpg");
            StorageReference photoImagesRef = Constants.storageRef.child("contact/" + System.currentTimeMillis() + ".jpg");

            //atur nama dan lokasi sesuai dengan photoImagesRef diatas
            refPhotoProfile.getName().equals(photoImagesRef.getName());
            refPhotoProfile.getPath().equals(photoImagesRef.getPath());

            //mengambil gambar dari gambar pada imageview diubah ke dalam byte[] lalu upload ke firebase storage
            imgPerson.setDrawingCacheEnabled(true);
            imgPerson.buildDrawingCache();
            Bitmap bitmap = imgPerson.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] data = baos.toByteArray();

            //proses upload
            UploadTask uploadTask = refPhotoProfile.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Jika upload berhasil jalankan method didalam
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    photoUrl = taskSnapshot.getDownloadUrl();
                    Constants.refContact.push().setValue(new ContactModel(name, occupation,
                            office, email, phone, mobile, photoUrl.toString()));
                    Toast.makeText(AddContactActivity.this, "Contact has successfully added!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "Please choose the image first!", Toast.LENGTH_SHORT).show();
        }
    }

    boolean isPicChange = false;

    //method untuk handler saat image picker gambar telah dipilih
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //jika image picker membawa sebuah data berupa foto maka
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            //ambil data foto yang dipilih
            Image image = ImagePicker.getFirstImageOrNull(data);
            //ambil path/lokasi dari foto/gambar yang dipilih
            File imgFile = new File(image.getPath());
            if (imgFile.exists()) {
                //convert path ke bitmap dan tampilkan pada imageview
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgPerson.setImageBitmap(myBitmap);

                //set variabel change true karena gambar telah diupdate
                isPicChange = true;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
