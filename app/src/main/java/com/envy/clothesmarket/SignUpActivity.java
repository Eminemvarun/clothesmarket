package com.envy.clothesmarket;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {


    EditText password_ET, username_ET, email_ET,price;
    Button signupBTN;

    //Authentication
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser firebaseUser;

    //Firebase Connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        signupBTN = findViewById(R.id.acc_signUp_btn);
        password_ET = findViewById(R.id.password_create);
        username_ET = findViewById(R.id.username_create_ET);
        email_ET = findViewById(R.id.email_create);

        //Firebase Authentication

        firebaseAuth =  FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(username_ET.getText().toString())&& !TextUtils.isEmpty(email_ET.getText().toString())
                    && !TextUtils.isEmpty(password_ET.getText().toString())){
                    String email = email_ET.getText().toString().trim();
                    String username = username_ET.getText().toString().trim();
                    String password = password_ET.getText().toString().trim();

                    createUserAccount(email,username,password);
                }
                else{
                    Toast.makeText(SignUpActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void createUserAccount(String email,String username,String password) {

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)) {
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(SignUpActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this,"Unable to create this account",Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}