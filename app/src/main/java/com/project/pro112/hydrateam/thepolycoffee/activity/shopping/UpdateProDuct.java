package com.project.pro112.hydrateam.thepolycoffee.activity.shopping;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.models.Food;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class UpdateProDuct extends AppCompatActivity implements View.OnClickListener{
    public Button update,btnCancel;
    public TextInputEditText pName,pPrice,pDes;
    public ImageView foodImg;
    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 2;
    Uri imageHoldUri = null;
    Intent intent;
    private String linkImg = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pro_duct);
        innitView();
        foodImg.setOnClickListener(this);
    }

    private void innitView() {
        intent = getIntent();
        update = (Button) findViewById(R.id.btnUpdate);
        pName = (TextInputEditText) findViewById(R.id.pName);
        pName.setHint(intent.getStringExtra("name"));
        pPrice = (TextInputEditText) findViewById(R.id.pPrice);
        pPrice.setHint(intent.getStringExtra("price"));
        pDes = (TextInputEditText) findViewById(R.id.pDes);
        pDes.setHint(intent.getStringExtra("des"));
        foodImg = findViewById(R.id.foodImg);
        Picasso.with(UpdateProDuct.this).load(intent.getStringExtra("image")).into(foodImg);
        linkImg = intent.getStringExtra("image");
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProduct();
            }
        });
    }

    private void updateProduct() {
        final String name;
        final String des;
        double price = 0;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef;
        name = pName.getText().toString();
        des = pDes.getText().toString();
        try {
            price = Double.parseDouble(pPrice.getText().toString());
        }catch (NumberFormatException e){
            Toast.makeText(this, "Price is a number!", Toast.LENGTH_SHORT).show();
        }
        if (name.equals("") || des.equals("")){
            Toast.makeText(this, "There is something empty!", Toast.LENGTH_SHORT).show();
        }else{
            myRef = database.getReference("Foods/" + intent.getStringExtra("typeN")).child(""+intent.getStringExtra("key"));
            if (linkImg.equals("")) {
                Toast.makeText(UpdateProDuct.this, "Please choose an image!", Toast.LENGTH_SHORT).show();
            } else {
                myRef.setValue(new Food(des + "", "" + linkImg, name + "", price + ""));
                Toast.makeText(UpdateProDuct.this, "Update product successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.foodImg: {
                profilePicSelection();
                break;
            }
        }
    }

    //Code click vao TextView Edit Photo:
    public void profilePicSelection() {
        //DISPLAY DIALOG TO CHOOSE CAMERA OR GALLERY
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProDuct.this);
        builder.setTitle("Add Photo!");

        //SET ITEMS AND THERE LISTENERS
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //Mo galary:
    private void galleryIntent() {
        //CHOOSE IMAGE FROM GALLERY
        Log.d("gola", "entered here");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    //Mo Camera chup hinh:
    private void cameraIntent() {
        //CHOOSE CAMERA
        Log.d("gola", "entered here");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    // ActivityResultIntent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //SAVE URI FROM GALLERY
        if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        } else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            //SAVE URI FROM CAMERA
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        //image crop library code
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageHoldUri = result.getUri();
                foodImg.setImageURI(imageHoldUri);

                //Submit Image:
                StorageReference mStorageRef;
                mStorageRef = FirebaseStorage.getInstance().getReference();
                String local = "";
                local = intent.getStringExtra("type");
                StorageReference mStorage = mStorageRef.child("" +local).child(imageHoldUri.getLastPathSegment());
                mStorage.putFile(imageHoldUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Lấy link Avatar Từ Storage:
                        linkImg = String.valueOf(taskSnapshot.getDownloadUrl());
                        //KHI THAY DOI AVATAR LAP TUC THAY DOI DAI DIEN:
//                        Picasso.with(Order.this).load(linkImg).into(foodImg);
                        Toast.makeText(UpdateProDuct.this, "Set Img Successfully", Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
