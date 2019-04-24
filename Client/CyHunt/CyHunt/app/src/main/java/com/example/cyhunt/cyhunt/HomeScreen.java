package com.example.cyhunt.cyhunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cyhunt.cyhunt.ApiAuthenticationClient.ApiResultHandler;

import java.util.List;


public class HomeScreen extends AppCompatActivity implements ApiResultHandler {

    private Button button_login_login;
    private EditText editText_login_username;
    private EditText editText_login_password;

    private final ApiAuthenticationClient connector = new ApiAuthenticationClient((ApiResultHandler)this);
    private static HomeScreen parent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        parent = this;

        button_login_login = (Button) findViewById(R.id.loginButton);
        button_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    editText_login_username = (EditText) findViewById(R.id.userName);
                    editText_login_password = (EditText) findViewById(R.id.passwordBox);
                    connector.LoginUser(editText_login_username.getText().toString(), editText_login_password.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void openMainActivity() {
        Intent intent = new Intent(  this, ObjectiveScreen.class);
        startActivity(intent);
    }


    @Override
    public void handleResult(final UserResponse response) {
        if (response != null) {
            parent.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_LONG).show();
                    if (response.isSuccess()) {
                        openMainActivity();
                    }

                    if (response.getMessage().contains("does not exist")) {
                        try {
                            connector.AddUser(editText_login_username.getText().toString(), editText_login_password.getText().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void handleObjectiveListResult(final List<Objective> objectives) {
    }
}
