package com.jitesh.filtr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
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
            if (username.equals("1234") && password.equals("1234")) {
                Toast.makeText(getApplicationContext(), "Successful Authentication!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ImagePickActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
