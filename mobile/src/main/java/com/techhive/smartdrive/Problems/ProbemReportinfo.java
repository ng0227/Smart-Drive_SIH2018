package com.techhive.smartdrive.Problems;

/**
 * Created by Sunain on 3/30/2018.
 */

public class ProbemReportinfo {
    String pid,email,highway_Name,description,image_url;
    String date;
    double longitude,latitude;
    int status,problem_Level,problem_Category;
    public ProbemReportinfo(){}
    public ProbemReportinfo(String date, String pid, String email,double d1, double d2, String tempImageName, int tempProblevel, int tempCategory, String tempDesc, String s)
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
    public void setPid(String pid){this.pid=pid;}
    public void setEmail(String email){this.email=email;}
    public void setLongitude(double longitude){this.longitude=longitude;}
    public void setLatitude(double latitude){this.latitude=latitude;}
    public void setHighway_Name(String highway_Name){this.highway_Name=highway_Name;}
    public void setProblem_Level(int problem_Level){this.problem_Level=problem_Level;}
    public void setProblem_Category(int problem_Category){this.problem_Category=problem_Category;}
    public void setDescription(String description){this.description=description;}
    public void setImage_url(String image_url){this.image_url=image_url;}
    public void setDate(String date){this.date=date;}
    public void setStatus(int status){this.status=status;}

    public String getDate(){return date;}
    public String getPid(){return pid;}
    public double getlongitude(){return longitude;}
    public double getlatitude(){return latitude;}
    public String getHighway_Name(){return highway_Name;}
    public int getProblem_Level(){return problem_Level;}
    public int getProblem_Category(){return problem_Category;}
    public String getDescription(){return description;}
    public String getImage_url(){return image_url;}
    public String getEmail(){return email;}
    public int getStatus(){return status;}
}
