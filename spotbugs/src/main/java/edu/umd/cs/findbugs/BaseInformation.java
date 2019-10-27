package edu.umd.cs.findbugs;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


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

    public void BugInfo(BugInstance bugInstance){
        BugPattern bugPattern=bugInstance.getBugPattern();
        String bugTypes=bugInstance.getType();

        if(allBugTypes.getBugType_Name().size()==0){
        allBugTypes.setBugType_Name();}
        String description=allBugTypes.bugType_Name.get(bugTypes);
        if(description==null){
            description="nothing";
        }
        int priority=bugInstance.getPriority();
        PriorityBug priorityBug=new PriorityBug(description);
        boolean flag=true;
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
                    bugLineAndImage.setBugLine(bugInstance.getPrimaryClass().getClassName()+","+bugInstance.getPrimarySourceLineAnnotation().getStartLine());
                    String picturePath=AnalyseCommand.bugreporterLocation+"\\images\\"+bugTypes+".jpg";
                    try {
                        Image image = Image.getInstance(picturePath);
                        bugLineAndImage.setImages(image);
                        bugLineAndImages.add(bugLineAndImage);
                        priorityBug1.setBugLineAndImage(bugLineAndImages);

                    } catch (BadElementException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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

                /**/
                BugLineAndImage bugLineAndImage=new BugLineAndImage();
                bugLineAndImage.setBugLine(bugInstance.getPrimaryClass().getClassName()+".java: "+bugInstance.getPrimarySourceLineAnnotation().getStartLine()+"行");
                String picturePath=AnalyseCommand.bugreporterLocation+"\\images\\"+bugTypes+".jpg";
                Image image = null;
                try {
                    image = Image.getInstance(picturePath);
                } catch (BadElementException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bugLineAndImage.setImages(image);
                Set<BugLineAndImage> bugLineAndImages=new HashSet<>();
                bugLineAndImages.add(bugLineAndImage);
                priorityBug.setBugLineAndImage(bugLineAndImages);

                priorityBugs.add(priorityBug);
            }

        }
    }
     /*分类统计*/
   /*  public void BugInfo(BugInstance bugInstance){
          BugPattern bugPattern=bugInstance.getBugPattern();
          BugCode bugCode=bugPattern.getBugCode();
          String description=bugCode.getDescription();
          int priority=bugInstance.getPriority();
          PriorityBug priorityBug=new PriorityBug(description);
          boolean flag=true;
          for(PriorityBug priorityBug1:priorityBugs){
              if(priorityBug1.getDescription().equals(description)&&priority<=3){
                  HashMap<Integer,Integer> p=priorityBug1.getPrioritys();
                  int num=p.get(priority);
                  num++;
                  p.put(priority,num);
                  priorityBugs.remove(priorityBug1);
                  priorityBug1.setPrioritys(p);
                  priorityBugs.add(priorityBug1);
                  flag=false;
                  break;
              }
          }
          if(flag&&priority<=3){
              HashMap<Integer,Integer> p=priorityBug.getPrioritys();
              p.put(priority,1);
              priorityBug.setPrioritys(p);
              priorityBugs.add(priorityBug);
          }
     }*/
     /*存储文件*/
  /*   public void saveBugInfo(){
         BufferedWriter out = null;
         try {
             out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir+"\\baseInformation.txt", true)));
             for(PriorityBug priorityBug:priorityBugs){
                 String content="漏洞名称:"+priorityBug.getDescription()+
                         ",高:"+priorityBug.getPrioritys().get(1)+
                         ",中:"+priorityBug.getPrioritys().get(2)+
                         ",低:"+priorityBug.getPrioritys().get(3)+"\n";
                         out.write(content);
             }
             out.write("\n");
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             try {
                 if(out != null){
                     out.close();
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }*/
     /*存储漏洞信息*/
  /*   public void saveDetailText(BugInstance bugInstance,int id){
         String s=Integer.toString(id);
         String fileName="";
         if(s.length()<5) {
             for(int i=0;i<5-s.length();i++){
                 fileName+="0";
             }
             fileName+=s;
         }
         else if(s.length()>5){
             System.out.print("too many bugs");
         }
         else fileName=s;

         String filePath=System.getProperty("user.dir");
         String dir=filePath+"\\repoters";
         File oFile=new File(dir);
         if(!oFile.exists()){
             oFile.mkdir();
         }
         dir=dir+"\\detailedInformation";
         File file=new File(dir);
         if(!file.exists()){
            file.mkdir();
         }
         dir=dir+"\\"+fileName+".txt";
         File f=new File(dir);
         if(!f.exists()){
             try {
                 f.createNewFile();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         BugPattern bugPattern=bugInstance.getBugPattern();
         BugCode bugCode=bugPattern.getBugCode();
         String detailPlainText="Bug description:"+bugCode.getDescription()+"\n\n"+bugPattern.getDetailPlainText()+"\n\n"+"Bug kind and pattern:"
                 +bugPattern.getCategory()+"-"+bugPattern.getType();
         BufferedOutputStream out= null;
         try {
             out = new BufferedOutputStream(new FileOutputStream(dir));
             out.write(detailPlainText.getBytes());
             out.flush();
             out.close();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
}*/
}
