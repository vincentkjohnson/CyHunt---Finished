package com.example.cyhunt.cyhunt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity {

    private Button button_login_login;
    private EditText editText_login_username;
    private EditText editText_login_password;
    private String username;
    private String password;
    private String baseURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //baseURL = "http://cyhunt-env.m3djxb9pkp.us-east-2.elasticbeanstalk.com:8080/";
        baseURL = "http://vincentj@cs319-055.misc.iastate.edu/home/vincentj/rest.php";

        editText_login_username = (EditText) findViewById(R.id.userName);
        editText_login_password = (EditText) findViewById(R.id.passwordBox);

        button_login_login = (Button) findViewById(R.id.loginButton);
        button_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    username = editText_login_username.getText().toString();
                    password = editText_login_password.getText().toString();

                    ApiAuthenticationClient apiAuthenticationClient = new ApiAuthenticationClient(baseURL, username, password);
                    //apiAuthenticationClient.setURLResrouce("user/add");
                    AsyncTask<Void, Void, String> execute = new ExecuteOperation(apiAuthenticationClient);
                    execute.execute();
                } catch (Exception e) {

                }
            }
        });
    }

    public class ExecuteOperation extends AsyncTask<Void, Void, String> {
        private ApiAuthenticationClient apiAuthenticationClient;
        private String isValidCredentials;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteOperation(ApiAuthenticationClient apiAuthenticationClient) {
            this.apiAuthenticationClient = apiAuthenticationClient;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                isValidCredentials = apiAuthenticationClient.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Login Success
            if (isValidCredentials.equals("true")) {
                openMainActivity();
            }
            // Login Failure
            else {
                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), apiAuthenticationClient.getHeaderFields().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void openMainActivity() {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        bundle.putString("baseURL", baseURL);
        Intent intent = new Intent(  this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
