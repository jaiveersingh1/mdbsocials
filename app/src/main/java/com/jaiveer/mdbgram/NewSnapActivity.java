package com.jaiveer.mdbgram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class NewSnapActivity extends AppCompatActivity {

    private StorageReference mStorageRef;

    private static final int PICK_IMAGE = 1;
    ImageView imageView;
    EditText captionText;
    Button uploadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_snap);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.push();
        String key = ref.getKey();

        imageView = findViewById(R.id.imageView);
        captionText = findViewById(R.id.captionEdit);
        uploadBtn = findViewById(R.id.uploadBtn);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PICK_IMAGE);
                }
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            imageView.setImageBitmap(bitmap);
            captionText.setVisibility(View.VISIBLE);
            uploadBtn.setVisibility(View.VISIBLE);
        }
    }
}
