package edu.umd.cs.findbugs;

import java.util.*;

/**
 * 此类用于分类统计bug的等级 .
 */
public class PriorityBug {


    private HashMap<Integer,Integer> prioritys=new HashMap<>();
    private Set<BugLineAndImage> bugLineAndImage=new HashSet<>();

    private String bugName;
    private String detailText;
  /*  private String[] description=new String[]{"SQL注入","命令注入","代码注入",
                                                "跨站脚本攻击(XSS)", "未验证重定向与转发","路径操作",
                                                "跨站请求伪造","HTTP响应截断","上传任意文件",
                                                "资源注入","LDAP注入","XML注入","危险文件包含",
                                                "JSON注入(劫持)","OGNL注入","Xpath注入",
                                                "文件泄露","不安全的加密机制","访问控制",
                                                "密码管理","私有信息侵犯","Cookie安全",
                                                "不安全的传输","会话固定","竞态条件","模板注入",
                                                "输入验证","HTTP参数污染","格式化字符串",
                                                "SMTP注入","认证问题","反序列化",
                                                "服务器端请求伪造","加密问题","信息泄露",
                                                "随机数","安全决策","空字节检验","资源消耗",
                                                "CRLF注入","系统或配置设置的外部控制","类型转换错误",
                                                "AWS注入"};*/

    public PriorityBug(String name){
        this.bugName=name;
        prioritys.put(1,0);
        prioritys.put(2,0);
        prioritys.put(3,0);
    }
    public String getBugName(){
        return bugName;
    }
    public HashMap<Integer,Integer> getPrioritys(){
        return prioritys;
    }
    public void setPrioritys(HashMap<Integer,Integer> priority){
        this.prioritys=priority;
    }

    public void setDetailText(String detailTexts) {
        detailText = detailTexts;
    }
    public String getDetailText(){
        return detailText;
    }
    public Set<BugLineAndImage> getBugLineAndImage(){
        return bugLineAndImage;
    }

    public void setBugLineAndImage(Set<BugLineAndImage> bugLineAndImage) {
        this.bugLineAndImage = bugLineAndImage;
    }
}
