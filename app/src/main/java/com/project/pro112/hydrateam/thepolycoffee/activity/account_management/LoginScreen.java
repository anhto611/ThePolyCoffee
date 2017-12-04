package com.project.pro112.hydrateam.thepolycoffee.activity.account_management;

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

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.pro112.hydrateam.thepolycoffee.R;
import com.project.pro112.hydrateam.thepolycoffee.activity.main.MainHome;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class LoginScreen extends AppCompatActivity {

    private final static int RC_SIGN_IN = 9001;
    String LINK_AVT_DEFAULT_MALE = "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/User%20Avatar%20Default%2Fmale.png?alt=media&token=f2233ca0-2a04-4aa7-b373-6d0995dc2b8c";
    String LINK_AVT_DEFAULT_FEMALE = "https://firebasestorage.googleapis.com/v0/b/the-poly-coffe.appspot.com/o/User%20Avatar%20Default%2Ffemale.png?alt=media&token=b61f8e96-b44c-4b8b-8ea7-cc5f4a298641";

    TextView txtLogoApp;
    EditText edtEmailLogin, edtPasswordLogin;
    TextView txtforgetPassword;
    Button btnLogin, btnSignUp;
    ACProgressPie progressPie;
    String TAG = "MainActivity";

    //Đối tượng Nhận Thông Tin Facebook Của Người Dùng:
    String id, first_name, name, email, gender, birthday;

    boolean doubleBackToExitPressedOnce = false;


    private SignInButton btnGG;
    private GoogleApiClient mGoogleApiClient;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseUsers;
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

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d(TAG, connectionResult.toString());
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btnGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        //ForgetPassword:
        txtforgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetPassword();
            }
        });
    }

    private void signIn() {
        //Hien thi progressPie
        progressPie = new ACProgressPie.Builder(LoginScreen.this)
                .ringColor(Color.WHITE)
                .pieColor(Color.WHITE)
                .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                .build();
        progressPie.show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //updateUI(user);
                            progressPie.dismiss();

                            //Them Thông Tin Đăng Kí:
                            addInfoForGoogle();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                            progressPie.dismiss();
                        }

                        // ...
                    }
                });
    }

    private void addInfoForGoogle() {
        // Sign in success, update UI with the signed-in user's information
        startActivity(new Intent(LoginScreen.this, SignUpGoogle.class));
        finish();
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

    //Anh Xa View:
    private void initView() {
        //Khởi tạo Firebase:
        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUsers.keepSynced(true);

        //Check User:
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Checking User:
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Intent moveToHome = new Intent(LoginScreen.this, MainHome.class);
                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(moveToHome);
                }
            }
        };

        //FindViewByID:
        txtforgetPassword = (TextView) findViewById(R.id.forgetPassword);
        //BUTTON GOOGLE:
        btnGG = (SignInButton) findViewById(R.id.btnGoogle);
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
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
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

        //moveToHome:
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //moveToHome:
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

}
