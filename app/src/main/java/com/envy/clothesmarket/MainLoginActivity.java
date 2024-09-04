package com.envy.clothesmarket;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainLoginActivity extends AppCompatActivity {

    Button registerBTN, loginBTN,googleBTN;

    EditText email_ET, password_ET;
    ProgressBar loginProgressBar;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    GoogleSignInClient signInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Firebase Authentication

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            Intent intent = new Intent(MainLoginActivity.this, ClothesListingActivity.class);
            startActivity(intent);
            finish();
        }

        //Register Functionality

        registerBTN =  findViewById(R.id.register);
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainLoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        //Login Functionality
        email_ET = findViewById(R.id.editText_email);
        password_ET = findViewById(R.id.editText_password);
        loginBTN = findViewById(R.id.login);
        loginProgressBar = findViewById(R.id.login_progressBar);
        loginBTN.setOnClickListener(view->{
            loginProgressBar.setVisibility(View.VISIBLE);
            loginUser(email_ET.getText().toString(),password_ET.getText().toString());
        });
        //Google Sign In

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail().build();
        signInClient = GoogleSignIn.getClient(this,gso);
        firebaseAuth = FirebaseAuth.getInstance();
        googleBTN = findViewById(R.id.login_with_Google);
        googleBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProgressBar.setVisibility(View.VISIBLE);
                Intent i = signInClient.getSignInIntent();
                activityResultLauncher.launch(i);
            }
        });



    }

    private void loginUser(String email, String password) {
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        firebaseUser = firebaseAuth.getCurrentUser();
                        Toast.makeText(MainLoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainLoginActivity.this, ClothesListingActivity.class);
                        startActivity(i);
                        finish();
                    }})
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loginProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainLoginActivity.this, "Check Credentials and Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    })
            ;
        }
        else{
            loginProgressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Enter both fields", Toast.LENGTH_SHORT).show();
        }
    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if(o.getResultCode() == RESULT_OK){
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(o.getData());
                try{
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
                    FirebaseAuth.getInstance().signInWithCredential(authCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            firebaseUser = firebaseAuth.getCurrentUser();
                            Toast.makeText(MainLoginActivity.this, "Welcome " +firebaseUser.getDisplayName(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainLoginActivity.this, ClothesListingActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loginProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainLoginActivity.this, "Sign In Failed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (ApiException e){
                    e.printStackTrace();
                    loginProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(MainLoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });
}