package edu.umd.cs.findbugs;
import com.itextpdf.text.Image;

import javax.swing.*;


public class BugLineAndImage {
    private String bugLine;
    private Image images;
    public BugLineAndImage(){

    }
    public void setBugLine(String bugLine){
        this.bugLine=bugLine;
    }
    public void setImages(Image images){
        this.images=images;
    }
    public String getBugLine(){
        return bugLine;
    }
    public Image getImages(){
        return images;
    }
}
