package com.techhive.smartdrive.Problems;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.techhive.smartdrive.R;
import com.techhive.smartdrive.Utilities.SharedPrefManager;
import com.techhive.smartdrive.Utilities.Utils;

import java.util.Date;

public class ReportProblemActivity extends AppCompatActivity {

    TextView tvemail,tvpcat,tvplevel;
    //EditText tvemail,tvpcat,tvplevel;

    EditText ethnumber,etlong,etlat,etdescription;
    RadioButton rbauto,rbset;
    Button btimage,btsubmit,btlastloc;
    LinearLayout cordlayout;
    boolean iscordlayoutvisible=false;
    String lati,longi,email,category="",level="";
    public SharedPrefManager sharedPrefManager;
    Context mContext = this;
    int Image_Request_Code=7,REQUEST_CAMERA=10;
    Uri FilePathUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_report_problem);
        setContentView(R.layout.report_layout);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        Bundle extra=getIntent().getExtras();
        lati=extra.getString("latitude");
        longi=extra.getString("longitude");
        sharedPrefManager = new SharedPrefManager(mContext);
        email=sharedPrefManager.getUserEmail();
        initviews();
    }
    private void initviews()
    {

        tvemail=findViewById(R.id.report_problem_email_textview);
        tvpcat=findViewById(R.id.report_problem_problem_category);
        tvplevel=findViewById(R.id.report_problem_problem_level);

        ethnumber=(EditText)findViewById(R.id.report_problem_highway_number);
        etlong=(EditText)findViewById(R.id.report_problem_logitude_edittext);
        etlat=(EditText)findViewById(R.id.report_problem_latitude_edittext);
        etdescription=(EditText)findViewById(R.id.report_problem_description_edittext);
        rbauto=(RadioButton)findViewById(R.id.report_problem_auto_location_radio);
        rbset=(RadioButton)findViewById(R.id.report_problem_set_location_radio);
        btimage=(Button)findViewById(R.id.report_problem_image_upload_button);
        btsubmit=(Button)findViewById(R.id.report_problem_submit_button);
        cordlayout=(LinearLayout)findViewById(R.id.report_problem_cordinates_layout);
        btlastloc=(Button)findViewById(R.id.report_problem_last_location_button);
        setbuttonfunctions();
    }
    private void setbuttonfunctions()
    {
    /*    rbauto.setChecked(true);
        rbauto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iscordlayoutvisible)
                {
                    cordlayout.setVisibility(View.GONE);
                    iscordlayoutvisible=false;
                    etlat.setText(lati);
                    etlong.setText(longi);
                }
            }
        });
        rbset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!iscordlayoutvisible)
                {
                    cordlayout.setVisibility(View.VISIBLE);
                    iscordlayoutvisible=true;
                }
            }
        });*/

  //      etlat.setText(lati);
 //       etlong.setText(longi);
        tvemail.setText(email);
        tvpcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategoryDialog();

            }
        });
        tvplevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLevelDialog();

            }
        });
/*
        btlastloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etlat.setText(sharedPrefManager.getLatitude());
                etlong.setText(sharedPrefManager.getLongitude());
                showtoast("Last Saved Location is loaded in textbox");
            }
        });
*/
        btimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDailog();
            }
        });
        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProblemUpload pu=new ProblemUpload(ReportProblemActivity.this,email,longi,lati,ethnumber.getText().toString(),level,category,etdescription.getText().toString(),FilePathUri);
                pu.UploadProblemImage();
            }
        });
    }
    private void showImageDailog()
    {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Problem Level");
        String[] pictureDialogItems = {
                "Camera",
                "Gallery",
                "Cancel"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                startcamerintent();
                                break;
                            case 1:
                                startgalleryintent();
                                break;
                            case 2:

                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    private void startcamerintent()
    {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private void startgalleryintent()
    {
        Intent intent = new Intent();
        // Setting intent type as image to select image from phone storage.
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();
        }
        if (requestCode == REQUEST_CAMERA  && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();
        }
        showtoast(""+FilePathUri);
        Log.e("url",""+FilePathUri);
        }
    private void showCategoryDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Problem Category");
        String[] pictureDialogItems = {
                "1. Potholes",
                "2. Accidents",
                "3. Road terrain",
                "4. Markings/Sign boards",
                "5. Helpline"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                category="1";
                                break;
                            case 1:
                                category="2";
                                break;
                            case 2:
                                category="3";
                                break;
                            case 3:
                                category="4";
                                break;
                            case 4:
                                category="5";
                                break;
                        }
                        tvpcat.setText(category);
                    }
                });
        pictureDialog.show();
    }
    private void showLevelDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Problem Level");
        String[] pictureDialogItems = {
                "1.Noticeable",
                "2.Possible threat",
                "3.Very Dangerous"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                level="1";
                                break;
                            case 1:
                                level="2";
                                break;
                            case 2:
                                level="3";
                                break;
                        }
                        tvplevel.setText(level);
                    }
                });
        pictureDialog.show();
    }

    public void showtoast(String s)
    {
        LayoutInflater li = getLayoutInflater();
        View layout = li.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.toast_root));
        TextView text = (TextView) layout.findViewById(R.id.toast_error);
        text.setText(s);
        Toast toast = new Toast(getApplicationContext());// Get Toast Context
        toast.setGravity(Gravity.BOTTOM| Gravity.FILL_HORIZONTAL, 0, 0);// Set
        toast.setDuration(Toast.LENGTH_LONG);// Set Duration
        toast.setView(layout); // Set Custom View over toast
        toast.show();// Finally show toast
    }

    public class ProblemUpload
    {
        String longitude,latitude,highway_Name,problem_Level,problem_Category,description,email;
        Uri image_url;
        public String Storage_Path = "Problem_Image_Uploads/";
        public String Database_Path = "Client_Problems";
        StorageReference storageReference;
        DatabaseReference databaseReference;
        Context c;
        ProgressDialog progressDialog;
        public ProblemUpload(){}
        public ProblemUpload(Activity c, String email, String d1, String d2, String highway_Name, String problem_Level, String problem_Category, String description, Uri s)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            storageReference = FirebaseStorage.getInstance().getReference();
            databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
            this.email=email;
            longitude=d1;
            latitude=d2;
            this.problem_Level=problem_Level;
            this.problem_Category=problem_Category;
            this.description=description;
            this.highway_Name=highway_Name;
            image_url=s;
            this.c=c;
            progressDialog = new ProgressDialog(c, R.style.MyAlertDialogStyle);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        public String GetFileExtension(Uri uri) {
            ContentResolver contentResolver = c.getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }
        public void UploadProblemImage() {
            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            if (image_url == null) {
                UploadProblem("");
                return;
            }
            Utils utils=new Utils(getApplicationContext());
            if(!utils.isNetworkAvailable())
            {
                UploadProblem("");
                return;
            }
            try{
                StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(image_url));
                storageReference2nd.putFile(image_url)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String TempImageName = taskSnapshot.getDownloadUrl().toString();
                            UploadProblem(TempImageName);
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            showtoast("There was some error in reporting. please try again.");
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setTitle("Image is Uploading...");
                        }
                    });
        }catch (Exception e){UploadProblem("");}
        }
        public void UploadProblem(String imageurl1)
        {
            @SuppressWarnings("VisibleForTests")
            String UploadId = databaseReference.push().getKey();
            ProblemInfo UploadInfo = new ProblemInfo(new Date().toLocaleString(),UploadId,email,longitude,latitude,highway_Name,problem_Level,problem_Category,description,imageurl1);
            databaseReference.child(UploadId).setValue(UploadInfo);
            progressDialog.dismiss();
//            Intent i=new Intent(ReportProblemActivity.this,ReportProblemActivity.class);
//            startActivity(i);
            showtoast("Problem is Successfully registered");
            finish();
        }

    }

}
