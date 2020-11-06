package com.example.mycaone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE = 12;
    private ImageView imageViewadd;
    private EditText inputImageName;
    private TextView textviewprogress;
    private ProgressBar progressBar;
    private Button btnupload,showimage;

    Uri imageuri;
    boolean imageisadded=false;
    DatabaseReference Dataref;
    StorageReference Storageref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewadd=findViewById(R.id.imageView);
        inputImageName=findViewById(R.id.editText);
        textviewprogress=findViewById(R.id.textView);
        progressBar=findViewById(R.id.progressBar);
        btnupload=findViewById(R.id.button);
        showimage=findViewById(R.id.button2);

        textviewprogress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        Dataref= FirebaseDatabase.getInstance().getReference().child("images");
        Storageref= FirebaseStorage.getInstance().getReference().child("images1");

        imageViewadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              final String imageName =  inputImageName.getText().toString();
              if(imageisadded!=false && imageName!=null)
              {
                  uploadimage(imageName);
              }
            }
        });

        showimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Home_Activity.class);
                startActivity(intent);
            }
        });
    }


    private void uploadimage(final String imageName) {
        textviewprogress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

       final String key=Dataref.push().getKey();
        Storageref.child(key+".jpg").putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Storageref.child(key+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap=new HashMap();
                        hashMap.put("imagess",imageName);
                        hashMap.put("ImageUrl",uri.toString());
                        Dataref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"Image Uploaded",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
           double progress=(taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
           progressBar.setProgress((int) progress);
           textviewprogress.setText(progress+"%");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_IMAGE && data!=null)
        {
         imageuri=data.getData();
         imageisadded=true;
         imageViewadd.setImageURI(imageuri);
        }
    }
}