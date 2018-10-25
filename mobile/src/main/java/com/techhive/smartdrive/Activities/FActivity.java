package com.techhive.smartdrive.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.techhive.smartdrive.R;

public class FActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f);
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(FActivity.this,NavActivity.class));
        }
        else
        {
            startActivity(new Intent(FActivity.this,LoginActivity.class));
        }
    }
}
