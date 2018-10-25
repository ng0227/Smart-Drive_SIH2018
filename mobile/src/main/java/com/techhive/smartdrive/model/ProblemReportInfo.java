package com.techhive.smartdrive.model;

/**
 * Created by naman on 30/03/18.
 */

public class ProblemReportInfo {

    String pid, email, highway_Name, description, image_url;
    String date;
    String longitude, latitude;
    int status;
    String problem_Level, problem_Category;

    public ProblemReportInfo() {
    }

    public ProblemReportInfo(String pid, String email, String highway_Name,
                             String description, String image_url, String date,
                             String longitude, String latitude, int status,
                             String problem_Level, String problem_Category) {
        this.pid = pid;
        this.email = email;
        this.highway_Name = highway_Name;
        this.description = description;
        this.image_url = image_url;
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
        this.problem_Level = problem_Level;
        this.problem_Category = problem_Category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHighway_Name() {
        return highway_Name;
    }

    public void setHighway_Name(String highway_Name) {
        this.highway_Name = highway_Name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProblem_Level() {
        return problem_Level;
    }

    public void setProblem_Level(String problem_Level) {
        this.problem_Level = problem_Level;
    }

    public String getProblem_Category() {
        return problem_Category;
    }

    public void setProblem_Category(String problem_Category) {
        this.problem_Category = problem_Category;
    }
}