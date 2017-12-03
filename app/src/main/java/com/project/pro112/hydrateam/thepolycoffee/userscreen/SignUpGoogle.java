package com.project.pro112.hydrateam.thepolycoffee.userscreen;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.pro112.hydrateam.thepolycoffee.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class SignUpGoogle extends AppCompatActivity {

    EditText edtFullNameGG, editTextContactNumberGG, edtGenderGG;
    Button btnSubmitGG;
    TextView textViewInfoGG;
    ACProgressPie progressPie;
    //FIREBASE FIELDS
    FirebaseDatabase mFirebaseDatabase;
    FirebaseAuth mAuth;
    //    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabaseReference;
    StorageReference mStorageRef;
    Object_UserProfile object_userProfile;

    String LINK_AVT_DEFAULT_MALE = "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/User%20Avatar%20Default%2Fmale.png?alt=media&token=f2233ca0-2a04-4aa7-b373-6d0995dc2b8c";
    String LINK_AVT_DEFAULT_FEMALE = "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/User%20Avatar%20Default%2Ffemale.png?alt=media&token=b61f8e96-b44c-4b8b-8ea7-cc5f4a298641";

    //UID, UID User, Link Avatar:
    String userID, userEmail, imgAvatar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_google);

        initView();
        //Đổi Font:
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Lobster-Regular.ttf");
        textViewInfoGG.setTypeface(face);

        //Set Gender:
        edtGenderGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGender();
            }
        });

        //Submit infoGG Database:
        btnSubmitGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitDatabase();
            }
        });
    }

    //Submit infoGG Database:
    private void submitDatabase() {
        String fullName = edtFullNameGG.getText().toString().trim();
        String contactNumber = editTextContactNumberGG.getText().toString().trim();
        String gender = edtGenderGG.getText().toString().trim();
        String email = userEmail;


        if (TextUtils.isEmpty(fullName)) {
            Toast.makeText(this, "Full Name can not be empty", Toast.LENGTH_SHORT).show();
            edtFullNameGG.requestFocus();
        } else if (TextUtils.isEmpty(contactNumber)) {
            Toast.makeText(this, "Contact Number can not be empty", Toast.LENGTH_SHORT).show();
            editTextContactNumberGG.requestFocus();
        } else if (gender.matches("")) {
            Toast.makeText(this, "Gender can not be empty", Toast.LENGTH_SHORT).show();
        } else {
            //KIEM TRA GIOI TINH DE SET IMG_AVATAR TUONG UNG:
            if (gender.equals("Male")) {
                imgAvatar = LINK_AVT_DEFAULT_MALE;
            } else if (gender.equals("Female")) {
                imgAvatar = LINK_AVT_DEFAULT_FEMALE;
            }

            //Hien thi progressPie
            progressPie = new ACProgressPie.Builder(SignUpGoogle.this)
                    .ringColor(Color.WHITE)
                    .pieColor(Color.WHITE)
                    .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                    .build();
            progressPie.show();

            object_userProfile = new Object_UserProfile(fullName, email, gender, "", contactNumber, imgAvatar);
            mDatabaseReference.child("Users").child(userID).setValue(object_userProfile);
            Toast.makeText(this, "Submit Successfully", Toast.LENGTH_SHORT).show();
            finish();

            progressPie.dismiss();
        }

        /*if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(contactNumber)) {
            Toast.makeText(this, "Thiếu Thông Tin", Toast.LENGTH_SHORT).show();
            progressPie.dismiss();
        } else if (gender.equals("Male")) {
            imgAvatar = LINK_AVT_DEFAULT_MALE;
        } else if (gender.equals("Female")) {
            imgAvatar = LINK_AVT_DEFAULT_FEMALE;
        }*/

    }

    //Set Gender:
    private void setGender() {
        PopupMenu popupMenu = new PopupMenu(SignUpGoogle.this, edtGenderGG);
        getMenuInflater().inflate(R.menu.gender, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.male: {
                        edtGenderGG.setText("Male");
                        break;
                    }
                    case R.id.female: {
                        edtGenderGG.setText("Female");
                        break;
                    }
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void initView() {
        //Khởi Tọa Object:
        object_userProfile = new Object_UserProfile();

        //FIREBASE INSTANCE:
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        FirebaseUser mUser = mAuth.getCurrentUser();
        userID = mUser.getUid();
        userEmail = mUser.getEmail();

        //FindViewByID:
        edtFullNameGG = (EditText) findViewById(R.id.edtFullNameGG);
        edtGenderGG = (EditText) findViewById(R.id.edtGenderGG);
        edtGenderGG.setFocusable(false);
        editTextContactNumberGG = (EditText) findViewById(R.id.editTextContactNumberGG);
        btnSubmitGG = (Button) findViewById(R.id.btnSubmitGG);
        textViewInfoGG = (TextView) findViewById(R.id.textViewInfoGG);
    }
}
