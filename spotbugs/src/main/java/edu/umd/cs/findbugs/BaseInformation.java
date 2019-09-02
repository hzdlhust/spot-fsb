package edu.umd.cs.findbugs;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class BaseInformation {
    private  String dir;
    private static int priorityHigh=0;
    private static int priorityNormal=0;
    private static int priorityLow=0;
    private static Set<PriorityBug> priorityBugs=new HashSet<>();
    public boolean temp=false;
    public BaseInformation(){
        File file = new File("");
        try{
         //   String filePath="H:\\FirstProject\\IdeaProject\\spot-fsb\\spotbugs";
            String filePath=System.getProperty("user.dir");

            dir=filePath+"\\repoters";
            File oFile=new File(dir);
            if(!oFile.exists()){
                oFile.mkdir();
                temp=true;
            }
            File fos=new File(dir+"\\baseInformation.txt");
            if(!fos.exists()){
                fos.createNewFile();
                temp=true;
            }
          //  BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(dir+"\\baseInformation.txt"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    /*源代码统计*/
     public void saveSrcInfo(int classFiles){
         try {
             BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(dir+"\\baseInformation.txt"));
             String writesout="源代码文件数="+classFiles+"\n\n";
             out.write(writesout.getBytes());
             out.flush();
             out.close();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     public void PriorityInfo(BugInstance bugInstance){
        if(bugInstance.getPriority()==1) priorityHigh++;
        else if(bugInstance.getPriority()==2) priorityNormal++;
        else if(bugInstance.getPriority()==3) priorityLow++;
     }
     /*等级统计*/
     public void savePriorityInfo(){
         String content="等级:高,数量:"+priorityHigh+"\n"+"等级:中,数量:"+priorityNormal+"\n"+"等级:低,数量:"+priorityLow+"\n\n";
         BufferedWriter out = null;
         try {
             out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir+"\\baseInformation.txt", true)));
             out.write(content);
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
     }
     /*分类统计*/
     public void BugInfo(BugInstance bugInstance){
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
     }
     /*存储文件*/
     public void saveBugInfo(){
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
     }
}
