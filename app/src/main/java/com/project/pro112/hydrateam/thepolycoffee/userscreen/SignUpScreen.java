package com.project.pro112.hydrateam.thepolycoffee.userscreen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.pro112.hydrateam.thepolycoffee.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class SignUpScreen extends AppCompatActivity {
    EditText editEmailSignUp, editPasswordSignUp, edtConfirmPasswordSignUp;
    Button btnSignUp;
    TextView textViewRegister;
    ACProgressPie progressPie;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        mAuth = FirebaseAuth.getInstance();
        initView();

        //Đổi Font:
        Typeface face = Typeface.createFromAsset(getAssets(),
                "Lobster-Regular.ttf");
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
    }

    private void DangKy() {
        final String email = editEmailSignUp.getText().toString().trim();
        final String password = editPasswordSignUp.getText().toString().trim();
        final String confirmPassword = edtConfirmPasswordSignUp.getText().toString();

        //Kiem Tra Null Truong Email
        if (TextUtils.isEmpty(email)) {
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

        } else {
            //Code Dang Ky User:
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                progressPie.dismiss();

                                //Chuyen Du Lieu Vua Dang Ky Sang LoginScreen:
                                Intent putEmailAndPass = new Intent(SignUpScreen.this, LoginScreen.class);
                                putEmailAndPass.putExtra("email", email);
                                putEmailAndPass.putExtra("password", password);
                                startActivity(putEmailAndPass);

                                Toast.makeText(SignUpScreen.this, "Registration Successfully", Toast.LENGTH_SHORT).show();

                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                progressPie.dismiss();
                                Toast.makeText(SignUpScreen.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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

    private void initView() {
        edtConfirmPasswordSignUp = (EditText) findViewById(R.id.edtConfirmPasswordSignUp);
        textViewRegister = (TextView) findViewById(R.id.textViewRegister);
        editEmailSignUp = (EditText) findViewById(R.id.edtEmailSignUp);
        editPasswordSignUp = (EditText) findViewById(R.id.edtPasswordSignUp);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
    }
}
