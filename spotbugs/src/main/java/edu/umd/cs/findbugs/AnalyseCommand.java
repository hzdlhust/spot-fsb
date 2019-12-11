package edu.umd.cs.findbugs;

import edu.umd.cs.findbugs.detect.TestingGround;

/*处理报告位置以及漏洞的分类*/
public class AnalyseCommand {
    public static String bugreporterLocation;
    public static String bugTypes;//以0000111代替，0表示需要分析的漏洞  1表示不需要分析的漏洞 无表示all
    public static boolean isBugreporterLocation=false;
    public static boolean isSelectBugTypes=false;
    public AnalyseCommand(){ }
    public AnalyseCommand(String[] commandLine) {
        int argsCount = commandLine.length;
        for(int i=0;i<argsCount;i++){
            String a=commandLine[i];
            if(a.equals("-bugTypes")){
                bugTypes=commandLine[i+1];
                isSelectBugTypes=true;
                i++;
            }
            else if(a.equals("-location")){
                bugreporterLocation=commandLine[i+1];
                isBugreporterLocation=true;
                i++;
            }
        }
        if(!isSelectBugTypes){
            bugTypes="all";
        }
      //  AllBugTypes allBugTypes=new AllBugTypes();
         //   allBugTypes.setBugName();
    //    allBugTypes.setIsBugType();

        if(!isBugreporterLocation){
            bugreporterLocation=null;
        }
    }
}


