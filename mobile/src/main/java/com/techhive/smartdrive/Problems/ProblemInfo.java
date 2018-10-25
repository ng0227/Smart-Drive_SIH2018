package com.techhive.smartdrive.Problems;

/**
 * Created by Sunain on 3/27/2018.
 */

public class ProblemInfo {
    String pid,email,longitude,latitude,highway_Name,problem_Level,problem_Category,description,image_url;
    String date;
    int status;
    public ProblemInfo(){}
    public ProblemInfo(String date, String pid, String email, String d1, String d2, String tempImageName, String tempProblevel, String tempCategory, String tempDesc, String s)
    {
        this.date=date;
        this.pid=pid;
        longitude=d1;
        latitude=d2;
        problem_Level=tempProblevel;
        problem_Category=tempCategory;
        description=tempDesc;
        highway_Name=tempImageName;
        image_url=s;
        this.email=email;
        status=0;
    }
    public String getDate(){return date;}
    public String getPid(){return pid;}
    public String getlongitude(){return longitude;}
    public String getlatitude(){return latitude;}
    public String getHighway_Name(){return highway_Name;}
    public String getProblem_Level(){return problem_Level;}
    public String getProblem_Category(){return problem_Category;}
    public String getDescription(){return description;}
    public String getImage_url(){return image_url;}
    public String getEmail(){return email;}
    public int getStatus(){return status;}
}
