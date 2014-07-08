package com.jgrindall.logo.comms.test;

import com.jgrindall.logo.comms.*;
import java.util.Vector;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args){
        new Main();
    }
    public Main(){
        Vector<String> tests = new Vector<String>();

        // correct strings
        tests.add("fd:100;");
        tests.add("fd:78.9875;");
        tests.add("fd:78.9875;rt:65;");
        tests.add("fd:78.9875;rt:65.0;fd:-19.8;");
        tests.add("rt:78.9875;rt:65.0;fd:-19.8;end:0;");
        tests.add("end:0;");
        tests.add("rt:78.9875;rt:65.0;fd:-19.8;rt:-100.0;end:0;");


        // incorrect strings
        tests.add("fd:100");
        tests.add("fd 78.9875;");
        tests.add("fd:;rt:65;");
        tests.add("");
        tests.add("end:0;;");
        tests.add("end");
        tests.add("rt:78.9875;rt:65.0;fd:-19.8;rt:-100.0end:0;");

        for(int i=0;i<=tests.size()-1;i++){
            System.out.println("test "+i);
            String test = tests.elementAt(i);
            try{
                ArrayList<ALogoCommandObject> arrayList = LogoCommandUtils.stringToObject(test);
                System.out.println(arrayList);
                String check = "";
                for(int j=0;j<=arrayList.size()-1;j++){
                    check+=LogoCommandUtils.toString(arrayList.get(j));
                }
                System.out.println("TEST:  "+test);
                System.out.println("CHECK: "+check);
                System.out.println("---------------------------");
            }
            catch(Exception e){
                System.out.println("Exception "+e.getMessage());
            }
        }
    }
}