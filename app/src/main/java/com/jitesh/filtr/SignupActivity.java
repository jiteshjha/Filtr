package com.jitesh.filtr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);
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
            if (email.equals("1234@mail.com") && username.equals("1234") && password.equals("1234")) {
                Toast.makeText(getApplicationContext(), "Successful Registration!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ImagePickActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
