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

public class LoginActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_login);

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

    public void checkLogin(View view) {
        EditText editText1 = (EditText)findViewById(R.id.editText);
        EditText editText2 = (EditText)findViewById(R.id.editText2);

        String username = editText1.getText().toString().trim();
        String password = editText2.getText().toString().trim();

        if(TextUtils.isEmpty(username)) {
            editText1.setError("Empty username field!");
        }
        if(TextUtils.isEmpty(password)) {
            editText2.setError("Empty password field!");
        }
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
//            if (username.equals("1234") && password.equals("1234")) {
//                Toast.makeText(getApplicationContext(), "Successful Authentication!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), ImagePickActivity.class);
//                startActivity(intent);
//            } else {
//                Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
//            }

            // [START sign_in_with_email]
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
                            }

                            // [START_EXCLUDE]
                            else {

                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(getApplicationContext(), "Successful Authentication!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ImagePickActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
// [END sign_in_with_email]
        }
    }
}
