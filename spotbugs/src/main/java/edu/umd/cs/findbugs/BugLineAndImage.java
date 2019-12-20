package edu.umd.cs.findbugs;
import com.itextpdf.text.Image;

import javax.swing.*;


public class BugLineAndImage {
    private String bugLine;

    private String simpleInfo;
    private String className;
    public BugLineAndImage(){

    }
    public void setBugLine(String bugLine){
        this.bugLine=bugLine;
    }
    public void setClassName(String className){this.className=className;}
    public String  getClassName(){return className;}
    public void setSimpleInfo(String simpleInfo){this.simpleInfo=simpleInfo;}
    public String getBugLine(){
        return bugLine;
    }
    public String getSimpleInfo(){return simpleInfo;}
}
