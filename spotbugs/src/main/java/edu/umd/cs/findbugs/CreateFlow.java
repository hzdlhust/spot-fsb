package edu.umd.cs.findbugs;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class CreateFlow extends JPanel{
    public BugInstance bugInstance;
    public int id;
    public Graphics tg = null;
    public BufferedImage bi;
    public CreateFlow(BugInstance bugInstance,int id){
        this.bugInstance=bugInstance;
        this.id=id;
    }
    public CreateFlow(){ this.setBackground(Color.WHITE);}
    public void init(){
        bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        tg = bi.getGraphics();
        paint(tg);
       saveFile();
    }
    public void paint(Graphics g) {
        super.paint(g);
        int width=700;
        int height=40;
        if(bugInstance== null){
            System.out.print("异常");
            return;
        }
        /*Class and method name*/
        ClassAnnotation classAnnotation=bugInstance.getPrimaryClass();
        String simpleClassName=classAnnotation.getSimpleClassName();
        MethodAnnotation methodAnnotation=bugInstance.getPrimaryMethod();
        String methodName=null;
        try{
            methodName=methodAnnotation.getMethodName();}
        catch (NullPointerException e){
          //  e.printStackTrace();
        }
        simpleClassName=simpleClassName+"."+methodName+"   Line："+ bugInstance.getPrimarySourceLineAnnotation().getStartLine();
        g.drawRect(10,10,width,height);
        g.drawString(simpleClassName,30,30);
        /*路径*/
        int baseX=10;
        int baseY=10;
        int x=baseX;
        int y=baseY;
        List<? extends BugAnnotation> annotationList=bugInstance.getAnnotations();
        SourceLineAnnotation sourceLineAnnotation=null;
        boolean temp=true;
        for (BugAnnotation annotation : annotationList) {
            if(annotation instanceof SourceLineAnnotation){
                if(!temp){
                    g.drawLine(210,y+40,210,y+70);
                }
                sourceLineAnnotation=(SourceLineAnnotation) annotation;
                g.drawRect(baseX,y+70,width,height);
                String str=sourceLineAnnotation.toString();
              /*  String str=sourceLineAnnotation.getSourceFile();
                int startLine=sourceLineAnnotation.getStartLine();
                str=str+"   Line："+startLine;*/
                g.drawString(str,baseX+20,y+70+20);
                y=y+70;
                if(temp){
                    Graphics2D g2d = (Graphics2D) g.create();
                    float[] dash = new float[] { 5, 5 };
                    BasicStroke bs1 = new BasicStroke(
                            1,                      // 画笔宽度/线宽
                            BasicStroke.CAP_SQUARE,
                            BasicStroke.JOIN_MITER,
                            10.0f,
                            dash,                   // 虚线模式数组
                            0.0f
                    );
                   // BasicStroke bs1 = new BasicStroke(5);       // 笔画的轮廓（画笔宽度/线宽为5px）
                    g2d.setStroke(bs1);
                    g2d.drawLine(210, baseY+40, 210, baseY+70);
                    g2d.dispose();
                }
                temp=false;
            }
        }
        for (BugAnnotation annotation : annotationList) {
            if(annotation instanceof StringAnnotation && ((StringAnnotation)annotation).getDescription().equals("Sink method")){
                StringAnnotation stringAnnotation=(StringAnnotation) annotation;
                g.drawRect(baseX,y+70,width,height);
                String str="Sink method："+stringAnnotation.getValue();
                g.drawString(str,baseX+20,y+70+20);
                g.drawLine(210,y+40,210,y+70);
            }
        }
    }

    public void saveFile(){
       // String directoryName="H:\\FirstProject\\IdeaProject\\spot-fsb\\spotbugs";
        String directoryName=System.getProperty("user.dir");
        directoryName=directoryName+"\\repoters";
        File file=new File(directoryName);
        if(!file.exists()){
                file.mkdir();
        }

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
    public void setWindows(BugInstance bugInstance,int id){
        JFrame jf=new JFrame();
        jf.setTitle("调用流程图");
        jf.setSize(900,600);
        CreateFlow save=new CreateFlow();
        save.setId(id);
        save.setBugInstance(bugInstance);
        jf.add(save);
        jf.setVisible(true);
        jf.setVisible(false);
        save.init();
    }

    public void setBugInstance(BugInstance bugInstance) {
        this.bugInstance = bugInstance;
    }
    public void setId(int id){
        this.id=id;
    }
}
