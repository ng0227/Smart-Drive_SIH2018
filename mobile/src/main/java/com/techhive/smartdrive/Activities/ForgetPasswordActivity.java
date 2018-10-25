package com.techhive.smartdrive.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.techhive.smartdrive.R;
import com.techhive.smartdrive.Utilities.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPasswordActivity extends AppCompatActivity implements
        View.OnClickListener{
    private static EditText emailId;
    private static TextView submit, back;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        auth = FirebaseAuth.getInstance();
        initViews();
        setListeners();
    }

    private void initViews() {
        emailId = (EditText)findViewById(R.id.registered_emailid);
        submit = (TextView)findViewById(R.id.forgot_button);
        back = (TextView)findViewById(R.id.backToLoginBtn);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            back.setTextColor(csl);
            submit.setTextColor(csl);

        } catch (Exception e) {
        }

    }

    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backToLoginBtn:

                // Replace Login Fragment on Back Presses
                startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));
                break;

            case R.id.forgot_button:

                // Call Submit button task
                submitButtonTask();
                break;

        }
    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();

        // Pattern for email id validation
        Pattern p = Pattern.compile(Constants.regEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);

        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0)

            showtoast("Please enter your Email Id.");

            // Check if email id is valid or not
        else if (!m.find())
            showtoast("Your Email Id is Invalid.");

            // Else submit email id and fetch passwod or do your stuff
        else
            {
                auth.sendPasswordResetEmail(getEmailId)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                   showtoast("We have sent you instructions to reset your password via email!");
                                } else
                                    {
                                        showtoast("Failed to send reset password email!");
                                    }
                            }
                        });
            }

    }
    public void showtoast(String s)
    {
        LayoutInflater li = getLayoutInflater();
        View layout = li.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.toast_root));
        TextView text = (TextView) layout.findViewById(R.id.toast_error);
        text.setText(s);

        Toast toast = new Toast(getApplicationContext());// Get Toast Context
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);// Set
        toast.setDuration(Toast.LENGTH_SHORT);// Set Duration
        toast.setView(layout); // Set Custom View over toast

        toast.show();// Finally show toast
    }
}
