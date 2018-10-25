package com.techhive.smartdrive.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techhive.smartdrive.R;
import com.techhive.smartdrive.model.ProblemReportInfo;

import java.util.ArrayList;

/**
 * Created by naman on 30/03/18.
 */


public class TrackProblemAdapter extends RecyclerView.Adapter<TrackProblemAdapter.TrackProblemViewHolder> {

    Context context;
    ArrayList<ProblemReportInfo> problems;

    public TrackProblemAdapter(Context context, ArrayList<ProblemReportInfo> problems) {
        this.context = context;
        this.problems = problems;
    }

    public void setProblems(ArrayList<ProblemReportInfo> problems){
        this.problems.clear();
        this.problems.addAll(problems);
        notifyDataSetChanged();
    }

    @Override
    public TrackProblemAdapter.TrackProblemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.track_problems_item, parent, false);
        return new TrackProblemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackProblemAdapter.TrackProblemViewHolder holder, int position) {
        ProblemReportInfo info=problems.get(position);

        holder.description.setText(info.getDescription());

        if(info.getDate()!=null){
            holder.dateTv.setText(info.getDate());
        }
        if (info.getStatus() == -1) {
            holder.problemStatus.setText("Resolve");
            holder.status.setBackgroundResource(R.drawable.online_circle_view);
        } else if(info.getStatus()==0){
            holder.problemStatus.setText("Pending");
            holder.status.setBackgroundResource(R.drawable.offline_circle_view);
        }
    }

    @Override
    public int getItemCount() {
        return problems.size();
    }

    public class TrackProblemViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        ImageView date;
        View status;
        TextView problemStatus;
        TextView dateTv;

        public TrackProblemViewHolder(View itemView) {
            super(itemView);

            description=itemView.findViewById(R.id.description);
            date=itemView.findViewById(R.id.iv_schedule);
            status=itemView.findViewById(R.id.v_problem_status);
            problemStatus=itemView.findViewById(R.id.tv_test_status);
            dateTv=itemView.findViewById(R.id.tv_time);
        }
    }
}
