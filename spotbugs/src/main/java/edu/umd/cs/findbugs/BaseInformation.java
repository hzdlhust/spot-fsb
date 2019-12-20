package edu.umd.cs.findbugs;

import com.itextpdf.text.Annotation;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

import java.io.*;
import java.util.*;


public class BaseInformation {
    private  String dir;
    public static int priorityHigh=0;
    public static int priorityNormal=0;
    public static int priorityLow=0;
    public static Set<PriorityBug> priorityBugs=new HashSet<>();
    public boolean temp=false;
    public static int classFiles;
    public AllBugTypes allBugTypes=new AllBugTypes();
    public BaseInformation(){
    }


    public void setClassFiles(int classFile){
        classFiles=classFile;
    }
    public void PriorityInfo(BugInstance bugInstance){
        if(bugInstance.getPriority()==1) priorityHigh++;
        else if(bugInstance.getPriority()==2) priorityNormal++;
        else if(bugInstance.getPriority()==3) priorityLow++;
    }

    public void BugInfo(BugInstance bugInstance,int id){

        String bugTypes=bugInstance.getType();
        ClassAnnotation classAnnotation=bugInstance.getPrimaryClass();
        String className=classAnnotation.getSimpleClassName();

        if(allBugTypes.getBugType_Name().size()==0){
            allBugTypes.setBugType_Name();}
        String description=allBugTypes.bugType_Name.get(bugTypes);
        if(description==null){
            description="nothing";
        }
        int priority=bugInstance.getPriority();
        String s=null;
        if(priority==1) s="高危漏洞";
        else if(priority==2) s="中危漏洞";
        else s="低危漏洞";
        PriorityBug priorityBug=new PriorityBug(description);
        boolean flag=true;

        File directory = new File("");
        String  categoryName= null;
        try {
            categoryName = directory.getCanonicalPath()+"\\"+AnalyseCommand.fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!description.equals("nothing")) {
            for (PriorityBug priorityBug1 : priorityBugs) {
                //如果存进去了
                if (priorityBug1.getBugName().equals(description) && priority <= 3) {
                    HashMap<Integer, Integer> p = priorityBug1.getPrioritys();
                    Set<BugLineAndImage> bugLineAndImages=priorityBug1.getBugLineAndImage();
                    int num = p.get(priority);
                    num++;
                    p.put(priority, num);
                    priorityBugs.remove(priorityBug1);
                    priorityBug1.setPrioritys(p);
                    BugLineAndImage bugLineAndImage=new BugLineAndImage();
                    bugLineAndImage.setBugLine(bugInstance.getPrimaryClass().getClassName()+","+bugInstance.getPrimarySourceLineAnnotation().getStartLine()+"行,"+s);
                    bugLineAndImage.setClassName(className+"_"+bugTypes+"_"+bugInstance.getPrimarySourceLineAnnotation().getStartLine()+".png");
                    bugLineAndImages.add(bugLineAndImage);
                    priorityBug1.setBugLineAndImage(bugLineAndImages);
                    priorityBugs.add(priorityBug1);
                    flag = false;
                    break;
                }
            }
            if (flag && priority <= 3) {
                HashMap<Integer, Integer> p = priorityBug.getPrioritys();
                p.put(priority, 1);
                priorityBug.setPrioritys(p);
                //String detail=bugInstance.getBugPattern().getDetailText();//detail信息如何获取
                String detail=bugInstance.getBugPattern().getDetailPlainText();
                priorityBug.setDetailText(detail);

                BugLineAndImage bugLineAndImage=new BugLineAndImage();
                bugLineAndImage.setBugLine(bugInstance.getPrimaryClass().getClassName()+".java: "+bugInstance.getPrimarySourceLineAnnotation().getStartLine()+"行,"+s);
                bugLineAndImage.setClassName(className+"_"+bugTypes+"_"+bugInstance.getPrimarySourceLineAnnotation().getStartLine()+".png");
                Set<BugLineAndImage> bugLineAndImages=new HashSet<>();
                bugLineAndImages.add(bugLineAndImage);
                priorityBug.setBugLineAndImage(bugLineAndImages);

                priorityBugs.add(priorityBug);
            }

        }

        String deleteFileName=categoryName+"\\"+className+"_"+bugTypes+"_"+bugInstance.getPrimarySourceLineAnnotation().getStartLine();
        File file=new File(deleteFileName+".dot");
        if(file.isFile()){
            file.delete();
        }

    }

    public String setSimpleInfo(BugInstance bugInstance){
        ClassAnnotation classAnnotation=bugInstance.getPrimaryClass();
        MethodAnnotation methodAnnotation=bugInstance.getPrimaryMethod();
        //StringBuilder method=new StringBuilder();
        List<? extends BugAnnotation> annotationList=bugInstance.getAnnotations();
        StringBuilder str=new StringBuilder("In method "+ methodAnnotation.getFullMethod(classAnnotation)+"\n");
        for(BugAnnotation annotation:annotationList){
            if(annotation instanceof SourceLineAnnotation){
                SourceLineAnnotation sourceLineAnnotation=(SourceLineAnnotation) annotation;
                str.append(new StringBuilder(sourceLineAnnotation.toString())+"\n");
            }
            if(annotation instanceof StringAnnotation){
                StringAnnotation stringAnnotation=(StringAnnotation) annotation;
                str.append(new StringBuilder(stringAnnotation.toString())+"\n");
            }
        }
        return str+"";

    }

}
