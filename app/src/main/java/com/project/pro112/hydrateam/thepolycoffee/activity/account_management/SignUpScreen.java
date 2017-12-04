package com.project.pro112.hydrateam.thepolycoffee.activity.account_management;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.models.Object_UserProfile;
import com.project.pro112.hydrateam.thepolycoffee.models.UserRank;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class SignUpScreen extends AppCompatActivity {
    EditText editEmailSignUp, editPasswordSignUp, edtConfirmPasswordSignUp, edtFullName, edtGender;
    Button btnSignUp;
    TextView textViewRegister;
    ACProgressPie progressPie;
    private FirebaseAuth mAuth;

    String LINK_AVT_DEFAULT_MALE = "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/User%20Avatar%20Default%2Fmale.png?alt=media&token=f2233ca0-2a04-4aa7-b373-6d0995dc2b8c";
    String LINK_AVT_DEFAULT_FEMALE = "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/User%20Avatar%20Default%2Ffemale.png?alt=media&token=b61f8e96-b44c-4b8b-8ea7-cc5f4a298641";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();
        initView();

        //Đổi Font:
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Lobster-Regular.ttf");
        textViewRegister.setTypeface(face);

        //Sự Kiện Đăng Ký:
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hien thi progressPie
                progressPie = new ACProgressPie.Builder(SignUpScreen.this)
                        .ringColor(Color.WHITE)
                        .pieColor(Color.WHITE)
                        .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                        .build();
                progressPie.show();

                //Su Kien Dang Ky
                DangKy();
            }
        });

        //Su Kien Nhan Nut Gender:
        edtGender.setFocusable(false);
        edtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGender();
            }
        });
    }

    private void DangKy() {
        final String fullname = edtFullName.getText().toString().trim();
        final String email = editEmailSignUp.getText().toString().trim();
        final String password = editPasswordSignUp.getText().toString().trim();
        final String confirmPassword = edtConfirmPasswordSignUp.getText().toString();
        final String gender = edtGender.getText().toString().trim();

        //Kiem Tra Null Truong FullName
        if (TextUtils.isEmpty(fullname)) {
            Toast.makeText(this, "Name can not be empty", Toast.LENGTH_LONG).show();
            editEmailSignUp.requestFocus();
            progressPie.dismiss();

            //Kiem Tra Null Truong Email
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email can not be empty", Toast.LENGTH_LONG).show();
            editEmailSignUp.requestFocus();
            progressPie.dismiss();

            //Kiểm Tra Null Truong Pass:
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password can not be empty", Toast.LENGTH_LONG).show();
            editPasswordSignUp.requestFocus();
            progressPie.dismiss();

            //Kiểm Tra Null Truong Confirm Your Password:
        } else if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Confirm Your Password can not be empty", Toast.LENGTH_LONG).show();
            edtConfirmPasswordSignUp.requestFocus();
            progressPie.dismiss();

            //Kiểm Tra Equals Truong Confirm Your Password And Truong Password:
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password incorrect", Toast.LENGTH_SHORT).show();
            edtConfirmPasswordSignUp.requestFocus();
            progressPie.dismiss();

            //Kiem Tra Null Truong gender
        } else if (TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "Gender can not be empty", Toast.LENGTH_LONG).show();
            progressPie.dismiss();
        } else {
            //Code Dang Ky User:
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                progressPie.dismiss();

                                //Rank Users:
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_id = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                                DatabaseReference rank_user_id = FirebaseDatabase.getInstance().getReference().child("UserRank").child(user_id);

                                Object_UserProfile object_userProfile;
                                UserRank userRank;

                                if (gender.equals("Male")) {
                                    object_userProfile = new Object_UserProfile(fullname, email, gender, "", "", LINK_AVT_DEFAULT_MALE);
                                    current_user_id.setValue(object_userProfile);
                                } else {
                                    object_userProfile = new Object_UserProfile(fullname, email, gender, "", "", LINK_AVT_DEFAULT_FEMALE);
                                    current_user_id.setValue(object_userProfile);
                                }
                                userRank = new UserRank(0, 0, "New member");
                                rank_user_id.setValue(userRank);
                                //

                                //Chuyen Du Lieu Vua Dang Ky Sang LoginScreen:
                                Intent putEmailAndPass = new Intent(SignUpScreen.this, LoginScreen.class);
                                putEmailAndPass.putExtra("email", email);
                                putEmailAndPass.putExtra("password", password);
                                FirebaseAuth.getInstance().signOut();
                                startActivity(putEmailAndPass);

                                Toast.makeText(SignUpScreen.this, "Registration Successfully", Toast.LENGTH_SHORT).show();

                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpScreen.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressPie.dismiss();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        // do something on back.
        super.onBackPressed();
        startActivity(new Intent(this, LoginScreen.class));
    }

    private void initView() {
        edtConfirmPasswordSignUp = (EditText) findViewById(R.id.edtConfirmPasswordSignUp);
        textViewRegister = (TextView) findViewById(R.id.textViewRegister);
        editEmailSignUp = (EditText) findViewById(R.id.edtEmailSignUp);
        editPasswordSignUp = (EditText) findViewById(R.id.edtPasswordSignUp);
        edtFullName = (EditText) findViewById(R.id.edtFullName);
        edtGender = (EditText) findViewById(R.id.edtGender);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
    }

    //Set Gender:
    private void setGender() {
        PopupMenu popupMenu = new PopupMenu(SignUpScreen.this, edtGender);
        getMenuInflater().inflate(R.menu.gender, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.male: {
                        edtGender.setText("Male");
                        break;
                    }
                    case R.id.female: {
                        edtGender.setText("Female");
                        break;
                    }
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
