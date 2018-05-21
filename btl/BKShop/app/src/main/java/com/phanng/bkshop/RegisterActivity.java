package com.phanng.bkshop;

import org.apache.commons.math3.util.Pair;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.phanng.bkshop.model.User;
import com.phanng.bkshop.restutil.RestServiceClient;

public class RegisterActivity extends AppCompatActivity {
    private View registerFormView;
    private View registerProgressView;

    TextView tv_email;
    TextView tv_password;
    TextView tv_fullname;
    TextView tv_dateofbirth;
    TextView tv_phonenumber;
    TextView tv_address;

    private Button registerButton;

    RegisterTask registerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        tv_email = (TextView) findViewById(R.id.email_register);
        tv_password = (TextView) findViewById(R.id.password_register);
        tv_fullname = (TextView) findViewById(R.id.fullname_register);
        tv_dateofbirth = (TextView) findViewById(R.id.date_of_birth_register);
        tv_address = (TextView) findViewById(R.id.address_register);
        tv_phonenumber = (TextView) findViewById(R.id.phone_number_register);

        registerFormView = findViewById(R.id.register_form);
        registerProgressView = findViewById(R.id.register_progress);

    }

    private void attemptRegister() {
        if (!validateAllForm()) {
            // TODO display error
            return ;
        }
        User newUser = new User (
                tv_email.getText().toString(),
                tv_password.getText().toString(),
                tv_fullname.getText().toString(),
                tv_phonenumber.getText().toString(),
                tv_address.getText().toString(),
                tv_dateofbirth.getText().toString()
        );
        showProgress(true);
        registerTask = new RegisterTask();
        registerTask.execute(newUser);


    }

    private boolean validateAllForm() {
        boolean ret = true;
        // Check email
        if (TextUtils.isEmpty(tv_email.getText().toString())){
            ret = false;
            tv_email.setError("Cannot leave blank");
        }
        else if (!isEmailValid(tv_email.getText().toString())) {
            ret = false;
            tv_email.setError("Invalid email");
        }
        // Check password
        if (TextUtils.isEmpty(tv_password.getText().toString())) {
            ret = false;
            tv_password.setError("Cannot leave blank");
        }
        // Check fullname
        if (TextUtils.isEmpty(tv_fullname.getText().toString())) {
            ret = false;
            tv_fullname.setError("Cannot leave blank");
        }
        // Check phonenumber
        if (TextUtils.isEmpty(tv_phonenumber.getText().toString())) {
            ret = false;
            tv_phonenumber.setError("Cannot leave blank");
        }
        //Check address
        if (TextUtils.isEmpty(tv_address.getText().toString())) {
            ret = false;
            tv_address.setError("Cannot leave blank");
        }

        if (TextUtils.isEmpty(tv_dateofbirth.getText().toString())) {
            ret = false;
            tv_dateofbirth.setError("Cannot leave blank");
        }

        return ret;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            registerFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                   registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            registerProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            registerProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registerProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            registerProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public class RegisterTask extends AsyncTask<User, Void, Boolean> {


        RegisterTask() { }

        @Override
        protected Boolean doInBackground(User... params) {
            // TODO: attempt authentication against a network service.
            RestServiceClient client = RestServiceClient.getInstance();
            Pair res = client.register(params[0]);
            String status = (String)res.getFirst();
            String message = (String) res.getSecond();
            if (status.equals("SUCCESS")) {
                return true;
            }


            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            registerTask = null;
            showProgress(false);

            if (success) {
                // TODO after successful registering
                Toast.makeText(getApplicationContext(),"Register successful !",Toast.LENGTH_LONG)
                        .show();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }
                finish();
            } else {
                tv_email.setError("Email is already used");
                tv_email.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            registerTask = null;
            showProgress(false);
        }
    }
}
