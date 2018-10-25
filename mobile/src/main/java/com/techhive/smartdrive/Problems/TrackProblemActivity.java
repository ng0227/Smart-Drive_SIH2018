package com.techhive.smartdrive.Problems;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techhive.smartdrive.Adapter.TrackProblemAdapter;
import com.techhive.smartdrive.R;
import com.techhive.smartdrive.model.ProblemReportInfo;

import java.util.ArrayList;
import java.util.Collections;

public class TrackProblemActivity extends AppCompatActivity {

    DatabaseReference databaseReference,databaseReference1;
    ArrayList<ProblemReportInfo> clientProblems = new ArrayList<>();
    ArrayList<ProblemReportInfo> processProblems=new ArrayList<>();
    ArrayList<ProblemReportInfo> FinalProblemList=new ArrayList<>();
    private TrackProblemAdapter problemAdapter;
    private RecyclerView recyclerView;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_problem);
        mAuth=FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.recycler_view);
        setUpAdapter();

        databaseReference = FirebaseDatabase.getInstance().getReference("Client_Problems");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                clientProblems.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ProblemReportInfo imageUploadInfo = postSnapshot.getValue(ProblemReportInfo.class);
                    if(imageUploadInfo.getEmail().equals(mAuth.getCurrentUser().getEmail()))
                    {clientProblems.add(imageUploadInfo);}
                }
                Collections.reverse(clientProblems);
                joinbothlists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //progressDialog.dismiss();
            }
        });

        databaseReference1 = FirebaseDatabase.getInstance().getReference("Process_Problems");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                processProblems.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ProblemReportInfo imageUploadInfo = postSnapshot.getValue(ProblemReportInfo.class);
                    if(imageUploadInfo.getEmail().equals(mAuth.getCurrentUser().getEmail()))
                    {processProblems.add(imageUploadInfo);}

                }
                Collections.reverse(processProblems);
                joinbothlists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //progressDialog.dismiss();
            }
        });
    }

    private void joinbothlists()
    {
        FinalProblemList.clear();
        FinalProblemList.addAll(processProblems);
        FinalProblemList.addAll(clientProblems);
        problemAdapter.setProblems(FinalProblemList);
        problemAdapter.notifyDataSetChanged();
    }
    private void setUpAdapter() {
        problemAdapter = new TrackProblemAdapter(this, new ArrayList<ProblemReportInfo>());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(problemAdapter);
    }
}

