package edu.umd.cs.findbugs;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*创建.dot文件用来生成image流程图*/
public class CreateDotFile {
    HashMap<String,Integer> map=new HashMap<>();
    private String categoryName;
    public CreateDotFile(){
        File directory = new File("");
        String  directoryName= null;
        try {
            directoryName = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        categoryName=directoryName+"\\"+AnalyseCommand.fileName;
        File dirCategory=new File(categoryName);
        if(!dirCategory.exists()){
            dirCategory.mkdir();
        }
    }

    public void changeDotToImage(BugInstance bugInstance){
        ClassAnnotation classAnnotation=bugInstance.getPrimaryClass();
        String className=classAnnotation.getSimpleClassName();

        String type=bugInstance.getType();
        String fileName=className+"_"+type+"_"+bugInstance.getPrimarySourceLineAnnotation().getStartLine();

        String cmd="dot -Tpng "+categoryName+"\\"+fileName+".dot "+"-o "+categoryName+"\\"+fileName+".png";
        try {
            Process p=Runtime.getRuntime().exec(cmd);
            try {
                p.waitFor();
                p.destroy();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setMap(){
        map.put("SQL注入",0);
        map.put("命令注入",1);
        map.put("代码注入",2);
        map.put("XSS",3);
        map.put("未验证的重定向和转发",4);
        map.put("路径操作",5);
        map.put("跨站请求伪造",6);
        map.put("HTTP响应截断",7);
        map.put("上传任意文件",8);
        map.put("资源注入",9);
        map.put("LDAP注入",10);
        map.put("XML注入",11);
        map.put("危险文件包含",12);
        map.put("JSON注入(劫持)",13);
        map.put("OGNL注入",14);
        map.put("Xpath注入",15);
        map.put("文件泄露",16);
        map.put("不安全的加密存储",17);
        map.put("访问控制",18);
        map.put("密码管理",19);
        map.put("私有隐私侵犯",20);
        map.put("Cookie安全",21);
        map.put("不安全的传输",22);
        map.put("会话固定",23);
        map.put("竞争条件",24);
        map.put("模板注入",25);
        map.put("输入验证",26);
        map.put("HTTP参数污染",27);
        map.put("格式化字符串",28);
        map.put("SMTP注入",29);
        map.put("认证问题",30);
        map.put("反序列化",31);
        map.put("服务器请求伪造",32);
        map.put("加密问题",33);
        map.put("信息泄露",34);
        map.put("随机数问题",35);
        map.put("安全策略",36);
        map.put("空字节检验",37);
        map.put("资源消耗",38);
        map.put("CRLF注入",39);
        map.put("系统或配置设置的外部控制",40);
        map.put("类型转化错误",41);
        map.put("AWS注入",42);
        map.put("数据库密码安全",43);
    }
    public void saveFile(BugInstance bugInstance){
        ClassAnnotation classAnnotation=bugInstance.getPrimaryClass();
        String className=classAnnotation.getSimpleClassName();
        //只生成需要生成的.dot
        setMap();
        String type=bugInstance.getType();
        String bugTypes=AnalyseCommand.bugTypes;
        AllBugTypes allBugTypes=new AllBugTypes();
        allBugTypes.setBugType_Name();
        String bugName=allBugTypes.bugType_Name.get(type);

        if(AnalyseCommand.isSelectBugTypes&&!bugTypes.equals("all")){
            int temp=map.get(bugName);
            if(bugTypes.charAt(temp)!='1'){
                return;
            }
        }
        String fileName=className+"_"+type+"_"+bugInstance.getPrimarySourceLineAnnotation().getStartLine();
        File oFile=new File(categoryName+"\\"+fileName+".dot");
        if( !oFile.exists() )
        {
            try {
                oFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter out = null;

        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(categoryName+"\\"+fileName+".dot", true)));
            out.write("graph G {\n");

            // ClassAnnotation classAnnotation=bugInstance.getPrimaryClass();
            //  String simpleClassName=classAnnotation.getSimpleClassName();
            MethodAnnotation methodAnnotation=bugInstance.getPrimaryMethod();

            String methodName=null;
            try{
                methodName=methodAnnotation.getMethodName();}
            catch (NullPointerException e){
                //  e.printStackTrace();
            }
            String method=classAnnotation.getSimpleClassName()+"."+methodName+"   Line:"+ bugInstance.getPrimarySourceLineAnnotation().getStartLine();
            out.write("node0 [fontsize=\"20\",shape=\"rectangle\",style = \"filled\", color = \"black\", fillcolor = \"lemonchiffon\","+"label = \""+method+"\"];");
            out.write("\n");
            String methodInfo=methodAnnotation.getFullMethod(classAnnotation);
            out.write("node1[fontsize=\"20\",shape=\"Mrecord\","+"label = \"In method "+methodInfo+"\"];");
            out.write("\n");
            out.write("node0 -- node1[style = \"dotted\", penwidth=\"7\"];");
            out.write("\n");
            List<? extends BugAnnotation> annotationList=bugInstance.getAnnotations();
            SourceLineAnnotation sourceLineAnnotation=null;
            StringAnnotation stringAnnotation=null;
            int i=2;
            for (BugAnnotation annotation : annotationList) {
                String str=null;
                if(annotation instanceof SourceLineAnnotation){
                    sourceLineAnnotation=(SourceLineAnnotation) annotation;
                    str=sourceLineAnnotation.toString();
                    out.write("node"+i+"[fontsize=\"20\",shape=\"Mrecord\",label = \""+str+"\"];");
                    out.write("\n");
                    if(i==2){
                        out.write("node1 -- node2;");
                        out.write("\n");
                    }
                    else{
                        out.write("node"+(i-1)+" -- "+"node"+i+"[style = \"dotted\", penwidth=\"7\"];");
                        out.write("\n");
                    }
                    i++;
                }
            }
            i--;
            int j=0;
            for (BugAnnotation annotation : annotationList) {
                if(annotation instanceof StringAnnotation){
                    stringAnnotation=(StringAnnotation) annotation;
                    String s=stringAnnotation.toString();
                    if(stringAnnotation.getDescription().equals("Sink method")){
                        out.write("nodes"+j+"[fontsize=\"20\",shape=\"rectangle\",label = \""+s+"\",style = \"filled\", fillcolor = \"brown1\""+"];");
                        out.write("\n");}
                    else{
                        out.write("nodes"+j+"[fontsize=\"20\",shape=\"Mrecord\",label = \""+s+"\"];");
                        out.write("\n");
                    }
                    if(j==0){
                        out.write("node"+i+"-- nodes0;");
                        out.write("\n");
                    }
                    else{
                        out.write("nodes"+(j-1)+" -- "+"nodes"+j+"[style = \"dotted\", penwidth=\"7\"];");
                    }
                    j++;
                }
            }
            out.write("}\n");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        changeDotToImage(bugInstance);
    }
}
