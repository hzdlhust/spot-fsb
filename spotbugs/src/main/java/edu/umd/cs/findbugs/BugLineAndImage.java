package edu.umd.cs.findbugs;
import com.itextpdf.text.Image;

import javax.swing.*;


public class BugLineAndImage {
    private String bugLine;
    private Image images;
    private String simpleInfo;
    public BugLineAndImage(){

    }
    public void setBugLine(String bugLine){
        this.bugLine=bugLine;
    }
    public void setImages(Image images){
        this.images=images;
    }
    public void setSimpleInfo(String simpleInfo){this.simpleInfo=simpleInfo;}
    public String getBugLine(){
        return bugLine;
    }
    public Image getImages(){
        return images;
    }
    public String getSimpleInfo(){return simpleInfo;}
}
