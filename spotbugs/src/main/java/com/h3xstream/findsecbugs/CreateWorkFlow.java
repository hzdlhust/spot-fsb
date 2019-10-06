package com.h3xstream.findsecbugs;

import edu.umd.cs.findbugs.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CreateWorkFlow extends JFrame{
    private BugInstance bugInstance;
    private int id;
    public CreateWorkFlow(){}
    public void generateWorkFlow(BugInstance bugInstance,int id) throws IOException {
        setId(id);
        setBugInstance(bugInstance);
        JFrame jf=new JFrame();
        jf.setTitle("调用流程图");
        jf.setSize(900,500);
        AddWorkFlow addWorkFlow=new AddWorkFlow();
        jf.add(addWorkFlow);
        jf.setVisible(true);
        jf.setVisible(false);//窗口不显示
        addWorkFlow.init();

    }
    public void setBugInstance(BugInstance bugInstance){
        this.bugInstance=bugInstance;
    }

    public void setId(int id){
        this.id=id;
    }
    class AddWorkFlow extends JPanel{
        Graphics tg = null;
        BufferedImage bi;
        private List<? extends BugAnnotation> annotationList;
        private SourceLineAnnotation sourceLineAnnotation;
        private ClassAnnotation classAnnotation;
        private MethodAnnotation methodAnnotation;
        private BugPattern bugPattern;
        public AddWorkFlow(){ this.setBackground(Color.WHITE); }
        public void init() throws IOException {
            bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            tg = bi.getGraphics();
            paint(tg);
            saveFile();
        }

        //具体画图像
        public void paint(Graphics g) {
            if(bugInstance==null){
                System.out.print("异常");
                return;
            }
            annotationList=bugInstance.getAnnotations();
            super.paint(g);

            sourceLineAnnotation=bugInstance.getPrimarySourceLineAnnotation();
            classAnnotation=bugInstance.getPrimaryClass();
            methodAnnotation=bugInstance.getPrimaryMethod();
            StringAnnotation stringAnnotation=null;
            bugPattern=bugInstance.getBugPattern();

            for (BugAnnotation annotation : annotationList) {
                if(annotation instanceof StringAnnotation && ((StringAnnotation)annotation).getDescription().equals("Sink method")){
                    stringAnnotation=(StringAnnotation) annotation;
                }
            }
           // System.out.print("test");
            int width=400;
            int height=40;
            //类信息
            g.drawRect(10,10,width,height);

            String className="Class: "+classAnnotation.getClassName()+".java";
            g.drawString(className,20,30);
            //方法信息
            String methodName="Method: "+methodAnnotation.getFullMethod(classAnnotation);
            g.drawRect(10,80,width,height);
            g.drawString(methodName,20,100);
            //连接线段
            g.drawLine(110,50,110,80);
            //
            String sourceClassName=sourceLineAnnotation.getSourceFile()+" line:"+sourceLineAnnotation.getStartLine();
            g.drawRect(10,150,width,height);
            g.drawString(sourceClassName,20,170);
            g.drawLine(110,120,110,150);

            if(stringAnnotation!=null){
                g.drawRect(10,220,width+150,height);
                g.drawString("sink method:"+stringAnnotation.getValue(),20,240);
                g.drawLine(110,190,110,220);
            }

        }

        public void saveFile(){
           String category=bugPattern.getCategory();
            File directory=new File("");
            String currDirectoryName="";
            String directoryName="H:\\FirstProject\\IdeaProject\\spot-fsb\\spotbugs\\repoters";
            File file=new File(directoryName);
            if(!file.exists()){
              file.mkdir();
            }
          /*  try {
                currDirectoryName=directory.getCanonicalPath();

            } catch (IOException e) {
                e.printStackTrace();
            }
            if(currDirectoryName.indexOf("\\build\\libs")!=-1){
                directoryName=currDirectoryName.substring(0,currDirectoryName.length()-"\\build\\libs".length());
            }
            else{
                directoryName=currDirectoryName;
            }*/
   /*         String categoryName=directoryName+"\\images\\"+category;
            File dirCategory=new File(categoryName);
            if(!dirCategory.exists()){
                dirCategory.mkdir();
            }
            String descriptionName=categoryName+"\\"+bugPattern.getBugCode().getDescription();
            File dirDescription=new File(descriptionName);
            if(!dirDescription.exists()){
                dirDescription.mkdir();
            }
            String fileName=classAnnotation.getSourceFileName()+"_"+sourceLineAnnotation.getStartLine()+".jpg";
            File oFile=new File(descriptionName+"\\"+fileName);*/
            String categoryName=directoryName+"\\images";
            File dirCategory=new File(categoryName);
            if(!dirCategory.exists()){
                dirCategory.mkdir();
            }
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
            File oFile=new File(categoryName+"\\"+fileName+".jpg");
            if( !oFile.exists() )
            {
                try {
                    oFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                ImageIO.write(bi, "jpg", oFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

