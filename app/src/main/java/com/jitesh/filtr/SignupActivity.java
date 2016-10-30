package com.jitesh.filtr;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignupActivity extends AppCompatActivity {

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        // [END auth_state_listener]


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void checkLogin(View view) {
        EditText editText1 = (EditText)findViewById(R.id.editText3Signup);
        EditText editText2 = (EditText)findViewById(R.id.editTextSignup);
        EditText editText3 = (EditText)findViewById(R.id.editText2Signup);

        String email = editText1.getText().toString().trim();
        String username = editText2.getText().toString().trim();
        String password = editText3.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            editText1.setError("Empty Email field!");
        }
        if(TextUtils.isEmpty(username)) {
            editText2.setError("Empty Username field!");
        }
        if(TextUtils.isEmpty(password)) {
            editText3.setError("Empty Password field!");
        }

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            /*if (email.equals("1234@mail.com") && username.equals("1234") && password.equals("1234")) {
                Toast.makeText(getApplicationContext(), "Successful Registration!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ImagePickActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_SHORT).show();
            }*/

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Successful Registration!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ImagePickActivity.class);
                                startActivity(intent);
                            }

                            // ...
                        }
                    });

        }
    }
}
