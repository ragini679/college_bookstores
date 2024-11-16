package com.example.college_bookstore;

import java.util.ArrayList;
import java.util.Arrays;

public class branch_data {
    public static ArrayList<String> getbranchlist(){
        ArrayList<String> branchlist=new ArrayList<>(Arrays.asList(
                "Computer Science and Engineering",
                "Information Technology",
                "Electronics and Communication Engineering",
                "Electrical Engineering",
                "Mechanical Engineering",
                "Civil Engineering",
                "Chemical Engineering",
                "Aerospace Engineering",
                "Biotechnology",
                "Automobile Engineering",
                "Production Engineering",
                "Instrumentation Engineering",
                "Structural Engineering",
                "Environmental Engineering",
                "Textile Engineering",
                "Mining Engineering",
                "Robotics Engineering",
                "Agricultural Engineering",
                "Software Engineering",
                "Data Science"
        ));
        return branchlist;
    }
}
