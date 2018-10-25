package com.techhive.smartdrive.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techhive.smartdrive.R;
import com.techhive.smartdrive.Utilities.Constants;
import com.techhive.smartdrive.Utilities.SharedPrefManager;
import com.techhive.smartdrive.Utilities.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private FirebaseAuth auth;
    public SharedPrefManager sharedPrefManager;
    Context mContext = this;
    DatabaseReference dbref;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
//getSupportActionBar().setDisplayShowHomeEnabled(true);
//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//setTitle("Login");
//        getSupportActionBar().hide();
        initViews();
        setListeners();
    }

    private void initViews() {


        emailid = (EditText)findViewById(R.id.login_emailid);
        password = (EditText)findViewById(R.id.login_password);
        loginButton = (Button)findViewById(R.id.loginBtn);
        forgotPassword = (TextView)findViewById(R.id.forgot_password);
        signUp = (TextView)findViewById(R.id.createAccount);
        show_hide_password = (CheckBox)findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout)findViewById(R.id.login_linearlayout1);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.shake);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText("Hide Password");// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText("Show Password");// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;

            case R.id.forgot_password:

                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                // Replace forgot password fragment with animation

                break;
            case R.id.createAccount:

                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                // Replace signup frgament with animation

                break;
        }
    }

    private void checkValidation() {
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Constants.regEx);

        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            showtoast("Enter both credentials");
        }
        // Check if email id is valid or not
        else if (!m.find()) {
            //new CustomToast().Show_Toast(getApplicationContext(), view, "Your Email Id is Invalid.");
            emailid.startAnimation(shakeAnimation);
            showtoast("Your Email Id is Invalid.");
        } // Else do login and do your stuff
        else {
            progressDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);
            progressDialog.setMessage("Please wait ...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            login(getEmailId,getPassword);
            // Toast.makeText(getApplicationContext(), "Do Login.", Toast.LENGTH_SHORT).show();
        }
    }
    private void login(String email,String password)
    {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // there was an error
                            progressDialog.dismiss();
                            showtoast("Error: Check Email Id and Password");
                        } else {
                            savinguserdata();

                        }
                    }
                });
    }
    private  void savinguserdata()
    {
        //String name,phoneno,photourl;
        sharedPrefManager = new SharedPrefManager(mContext);
        if(auth.getCurrentUser()!=null)
        {
            dbref = FirebaseDatabase.getInstance().getReference("Users").child(Utils.encodeEmail(emailid.getText().toString()));
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try
                    {

//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        User user=postSnapshot.getValue(User.class);
//                        Log.e("User email",user.getEmail());
//                        Log.e("user name",user.getFullName());
//                    }
                    Log.e("email auth",auth.getCurrentUser().getEmail());
                    Log.e("email encoded",Utils.encodeEmail(Utils.encodeEmail(emailid.getText().toString())));
                    Log.e("email database",dataSnapshot.child("email").getValue().toString());
                    Log.e("name database",dataSnapshot.child("fullName").getValue().toString());
                    Log.e("phone database",dataSnapshot.child("phonenumber").getValue().toString());

                        if(dataSnapshot.child("email").getValue().toString().equals(auth.getCurrentUser().getEmail()))
                        {
                            String name=dataSnapshot.child("fullName").getValue().toString();
                            String phoneno=dataSnapshot.child("phonenumber").getValue().toString();
                            String photourl=dataSnapshot.child("photo").getValue().toString();
                            sharedPrefManager.saveIsLoggedIn(mContext, true);
                            sharedPrefManager.saveEmail(mContext, auth.getCurrentUser().getEmail().toString());
                            sharedPrefManager.saveName(mContext, name);
                            sharedPrefManager.savePhone(mContext, phoneno);
                            sharedPrefManager.savePhoto(mContext, photourl);
                            Intent intent = new Intent(LoginActivity.this, NavActivity.class);
                            progressDialog.dismiss();
                            startActivity(intent);
                            finish();
                        }
                    }catch (Exception e)
                    {
                        showtoast(e.toString());
                        Log.e("inside database try",e.toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
showtoast(databaseError.getMessage());
                    Log.e("outside database try",databaseError.toString());
                }
            });

        }

    }
    @Override
    protected void onDestroy() {
        if(progressDialog.isShowing())
        {progressDialog.dismiss();}
        super.onDestroy();
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
