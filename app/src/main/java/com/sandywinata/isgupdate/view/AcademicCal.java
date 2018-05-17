package com.sandywinata.isgupdate.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sandywinata.isgupdate.R;
import com.sandywinata.isgupdate.config.Constants;
import com.sandywinata.isgupdate.model.AcademicModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AcademicCal extends AppCompatActivity {

    @BindView(R.id.imgTranscript)
    ImageView imgTranscript;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;

    private StorageReference refPhotoProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_cal);
        ButterKnife.bind(this);
        loadAcademic();
    }

    private void loadAcademic() {
        Constants.refAcademicCal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AcademicModel model = dataSnapshot.getValue(AcademicModel.class);

                Picasso.get()
                        .load(model.imgUrl)
                        .into(imgTranscript);

                tvName.setText(model.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.imgTranscript)
    public void changePic() {
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

    @OnClick(R.id.btnUpdate)
    public void update() {
        if(tvName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Harap lengkapi data", Toast.LENGTH_SHORT).show(); //tampilkan pesan
            return;
        }

        //pengecekan jika gambar di update dari handle saat gambar dipilih oleh user
        if(isPicChange) {

            //penggunaan firebase storage untuk store gambar yang di pilih
            //penamaan folder dan nama gambar yang diupload
            refPhotoProfile = Constants.storageRef.child("academic/" + System.currentTimeMillis() + ".jpg");
            StorageReference photoImagesRef = Constants.storageRef.child("academic/" + System.currentTimeMillis() + ".jpg");

            //atur nama dan lokasi sesuai dengan photoImagesRef diatas
            refPhotoProfile.getName().equals(photoImagesRef.getName());
            refPhotoProfile.getPath().equals(photoImagesRef.getPath());

            //mengambil gambar dari gambar pada imageview diubah ke dalam byte[] lalu upload ke firebase storage
            imgTranscript.setDrawingCacheEnabled(true);
            imgTranscript.buildDrawingCache();
            Bitmap bitmap = imgTranscript.getDrawingCache();
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
                    prosesUpdate();
                }
            });
        } else {
            prosesUpdate();
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
                imgTranscript.setImageBitmap(myBitmap);

                //set variabel change true karena gambar telah diupdate
                isPicChange = true;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void prosesUpdate() {

        //mengambil data dari edittext masing2 profil
        final String name = tvName.getText().toString();

        //get dari tabel/collection user
        Constants.refAcademicCal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //mengambil data email
                AcademicModel emails = dataSnapshot.getValue(AcademicModel.class);

                Constants.refAcademicCal.child("name").setValue(name);
                if (isPicChange)
                    Constants.refAcademicCal.child("imgUrl").setValue(photoUrl.toString());

                Toast.makeText(AcademicCal.this, "Update berhasil!", Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
                //showProgress(false);
            }
        });
    }
}
