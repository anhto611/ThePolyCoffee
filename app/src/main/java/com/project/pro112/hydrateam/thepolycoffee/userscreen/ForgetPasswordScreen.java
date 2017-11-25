package com.project.pro112.hydrateam.thepolycoffee.userscreen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.project.pro112.hydrateam.thepolycoffee.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class ForgetPasswordScreen extends AppCompatActivity {
    TextView txtForgetPassword;
    EditText edtEmailForgetPassword;
    Button btnResetPassword;
    FirebaseAuth mAuth;
    ACProgressPie progressPie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_screen);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        mAuth = FirebaseAuth.getInstance();

        //Đổi Font:
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Lobster-Regular.ttf");
        txtForgetPassword.setTypeface(face);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hien thi progressPie
                progressPie = new ACProgressPie.Builder(ForgetPasswordScreen.this)
                        .ringColor(Color.WHITE)
                        .pieColor(Color.WHITE)
                        .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                        .build();
                progressPie.show();

                //Reset Password:
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        //Kiểm Tra Null Và Tồn Tại Email:
        String email = edtEmailForgetPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email can not is empty", Toast.LENGTH_SHORT).show();
            progressPie.dismiss();
        } else {
            //Gửi Mail Reset Password Cho Email:
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgetPasswordScreen.this, "We have sent you instructions to reset your password", Toast.LENGTH_SHORT).show();
                                progressPie.dismiss();
                                edtEmailForgetPassword.setText("");
                                edtEmailForgetPassword.requestFocus();
                                startActivity(new Intent(ForgetPasswordScreen.this, LoginScreen.class));
                            } else {
                                Toast.makeText(ForgetPasswordScreen.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                progressPie.dismiss();
                            }
                        }
                    });
        }
    }

    private void initView() {
        txtForgetPassword = (TextView) findViewById(R.id.textViewForgetPassword);
        edtEmailForgetPassword = (EditText) findViewById(R.id.edtEmailResetPassword);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword);
    }
}
