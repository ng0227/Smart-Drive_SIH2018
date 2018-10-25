package com.techhive.smartdrive.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techhive.smartdrive.R;
import com.techhive.smartdrive.Utilities.Constants;
import com.techhive.smartdrive.Utilities.User;
import com.techhive.smartdrive.Utilities.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private static EditText fullName, emailId, mobileNumber,
            password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private static Animation shakeAnimation;
    private LinearLayout layout;
    private FirebaseAuth auth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        initViews();
        setListeners();
    }
    private void initViews() {
        fullName = (EditText)findViewById(R.id.fullName);
        emailId = (EditText)findViewById(R.id.userEmailId);
        mobileNumber = (EditText)findViewById(R.id.mobileNumber);
//        location = (EditText)findViewById(R.id.location);
        password = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText)findViewById(R.id.confirmPassword);
        signUpButton = (Button)findViewById(R.id.signUpBtn);
        login = (TextView)findViewById(R.id.already_user);
        terms_conditions = (CheckBox)findViewById(R.id.terms_conditions);
        layout=(LinearLayout)findViewById(R.id.signup_linearlayout);
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.shake);
        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
                checkValidation();
                break;

            case R.id.already_user:

                // Replace login fragment
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                break;
        }
    }

    private void checkValidation() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
//        String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Constants.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0) {

            showtoast("All fields are required.");
layout.startAnimation(shakeAnimation);
            // Check if email id valid or not
        }
        else if(getMobileNumber.length()<10||getMobileNumber.length()>10)
        {
            showtoast("Your Mobile Number is Invalid");
            mobileNumber.startAnimation(shakeAnimation);
        }else if (!m.find()) {
            showtoast("Your Email Id is Invalid.");
            emailId.startAnimation(shakeAnimation);
            // Check if both password should be equal
        }else if (!getConfirmPassword.equals(getPassword)) {
            showtoast("Both password doesn't match.");
            password.startAnimation(shakeAnimation);
            confirmPassword.startAnimation(shakeAnimation);
            // Make sure user should check Terms and Conditions checkbox
        } else if (!terms_conditions.isChecked()) {
            showtoast("Please select Terms and Conditions.");
            terms_conditions.startAnimation(shakeAnimation);
            // Else do signup or do your stuff
        }

        else{
            progressDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);
            progressDialog.setMessage("Please wait ...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            signup(getEmailId,getFullName,getMobileNumber,getPassword);
        }

    }
    ProgressDialog progressDialog;
    void signup(final String email1, final String name, final String number, String password)
    {
        final String e=email1;
        final String p=password;
        auth.createUserWithEmailAndPassword(email1, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (!task.isSuccessful())
                        {
                            progressDialog.dismiss();
                           showtoast("Unable to sign up. Try Later");
                        }
                        else
                        {
                            User user=new User(name,"null",e,number,p);
                            String id= Utils.encodeEmail(e);
                            databaseReference.child(id).setValue(user);
                            showtoast("User Created Succesfully");
                            progressDialog.dismiss();
                            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                        }
                    }
                });


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
