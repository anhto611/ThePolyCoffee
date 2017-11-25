package com.project.pro112.hydrateam.thepolycoffee.userscreen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.activity.main.MainHome;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class LoginScreen extends AppCompatActivity {
    TextView txtLogoApp;
    EditText edtEmailLogin, edtPasswordLogin;
    TextView txtforgetPassword;
    Button btnLogin, btnSignUp;
    ACProgressPie progressPie;

    String TAG = "MainActivity";
    //Đối tượng Nhận Thông Tin Facebook Của Người Dùng:
    Object_Infomation_Facebook object_infomation_facebook;
    String id, first_name, name, email, gender, birthday;
    boolean doubleBackToExitPressedOnce = false;

    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private LoginButton loginButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.screen_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Ánh Xạ Các View:
        initView();

        //Đổi font của logoApp:
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Lobster-Regular.ttf");
        txtLogoApp.setTypeface(face);

        //Chuyen sang Dang Ky User:
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, SignUpScreen.class));
            }
        });

        //Login bang Email and Password
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithEmailAndPassword();
            }
        });

        //Login bằng Facebook
        loginWithFace();

        //ForgetPassword:
        txtforgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetPassword();
            }
        });
    }

    //ForgetPassword:
    private void forgetPassword() {
        startActivity(new Intent(LoginScreen.this, ForgetPasswordScreen.class));
    }

    //Login bang Email and Password
    private void loginWithEmailAndPassword() {
        String emailLogin = edtEmailLogin.getText().toString().trim();
        String passwordLogin = edtPasswordLogin.getText().toString().trim();

        if (TextUtils.isEmpty(emailLogin) || TextUtils.isEmpty(passwordLogin)) {
            Toast.makeText(this, "Email and password can not be empty", Toast.LENGTH_LONG).show();
        } else {
            //Hien thi progressPie
            progressPie = new ACProgressPie.Builder(LoginScreen.this)
                    .ringColor(Color.WHITE)
                    .pieColor(Color.WHITE)
                    .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                    .build();
            progressPie.show();

            //Code Dang Nhap
            mAuth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                progressPie.dismiss();
                                startActivity(new Intent(LoginScreen.this, MainHome.class));
                                Toast.makeText(LoginScreen.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                progressPie.dismiss();
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginScreen.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }
    }

    //Login bằng Facebook
    private void loginWithFace() {
        callbackManager = CallbackManager.Factory.create();

        //Xin quyền lấy profile Facebook:
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Tra Ve Du Lieu Luu Tren Firebase:
                accessToken = loginResult.getAccessToken();
                handleFacebookAccessToken(accessToken);

                //Ham Chua Gia Tri Profile User:
                resultUserFace();

                //Chuyen Sang Home:
                startActivity(new Intent(LoginScreen.this, MainHome.class));
                Toast.makeText(LoginScreen.this, "Login With Facebook Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }

    //Ham Chua Gia Tri Profile User:
    private void resultUserFace() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON", response.getJSONObject().toString());
                try {
                    id = object.getString("id");
                    first_name = object.getString("first_name");
                    name = object.getString("name");
                    email = object.getString("email");
                    gender = object.getString("gender");
                    birthday = object.getString("birthday");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Gán thông tin facebook vào Object:
                object_infomation_facebook = new Object_Infomation_Facebook();
                object_infomation_facebook.setId(id);
                object_infomation_facebook.setFirst_name(first_name);
                object_infomation_facebook.setName(name);
                object_infomation_facebook.setEmail(email);
                object_infomation_facebook.setGender(gender);
                object_infomation_facebook.setBirthday(birthday);
            }
        });
        //Lấy thông tin từ Sever:
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,name,email,gender,birthday");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        Log.d(TAG, "handleFacebookAccessToken:" + accessToken);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Result của Facebook.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Khi Login Lan Tiep Theo Se LogOut Face:
        LoginManager.getInstance().logOut();

        //Nhan Email and Password tu SignUpScreen:
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        edtEmailLogin.setText(email);
        edtPasswordLogin.setText(password);

        //Remove moveToHome:
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    //Anh Xa View:
    private void initView() {
        //Khởi tạo Firebase:
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Checking User:
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Intent moveToHome = new Intent(LoginScreen.this, MainHome.class);
                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(moveToHome);
                }
            }
        };
        mAuth.addAuthStateListener(mAuthStateListener);

        //Khởi tạo đối tượng:
        txtforgetPassword = (TextView) findViewById(R.id.forgetPassword);

        //FindViewByID:
        loginButton = (LoginButton) findViewById(R.id.btnLoginFacebook);
        btnSignUp = (Button) findViewById(R.id.btnSignUpInLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtEmailLogin = (EditText) findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = (EditText) findViewById(R.id.edtPasswordLogin);
        txtLogoApp = (TextView) findViewById(R.id.logoApp);

    }

    //Nhấn 2 lần Back để thoát App
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
