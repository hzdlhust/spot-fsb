package edu.umd.cs.findbugs;


import java.util.HashMap;

public class AllBugTypes {

    public  HashMap<String,String> bugType_Name=new HashMap<>();
    public static HashMap<String,Integer> bugName=new HashMap<>();
    public static boolean[] isBugType=new boolean[132];
    private static int bugTypesNum=132;



    public AllBugTypes(){

    }
    private void setIsBugTypeTrue(int begin,int end){
        for(int i=begin;i<=end;i++){
            isBugType[i]=true;
        }
    }
    public void setIsBugType(){
        for(int i=0;i<bugTypesNum;i++){
            isBugType[i]=false;
        }
        String str=AnalyseCommand.bugTypes;
        if(str.equals("all")){
            setIsBugTypeTrue(0,bugTypesNum-1);
            return;
        }
        if(str.charAt(0)=='1'){
           setIsBugTypeTrue(0,11);
        }
        if(str.charAt(1)=='1'){
            setIsBugTypeTrue(12,13);
        }
        if(str.charAt(2)=='1'){
            setIsBugTypeTrue(14,18);
        }
        if(str.charAt(3)=='1'){
            setIsBugTypeTrue(19,25);
        }
        if(str.charAt(4)=='1'){
            setIsBugTypeTrue(26,28);
        }
        if(str.charAt(5)=='1'){
            setIsBugTypeTrue(29,36);
        }
        if(str.charAt(6)=='1'){
            setIsBugTypeTrue(37,39);
        }
        if(str.charAt(7)=='1'){
            setIsBugTypeTrue(40,40);
        }
        if(str.charAt(8)=='1'){
            setIsBugTypeTrue(41,41);
        }
        if(str.charAt(9)=='1'){
            setIsBugTypeTrue(42,42);
        }
        if(str.charAt(10)=='1'){
            setIsBugTypeTrue(43,44);
        }
        if(str.charAt(11)=='1'){
            setIsBugTypeTrue(45,51);
        }
        if(str.charAt(12)=='1'){
            setIsBugTypeTrue(52,52);
        }
        if(str.charAt(13)=='1'){
            setIsBugTypeTrue(53,53);
        }
        if(str.charAt(14)=='1'){
            setIsBugTypeTrue(54,54);
        }
        if(str.charAt(15)=='1'){
            setIsBugTypeTrue(55,55);
        }
        if(str.charAt(16)=='1'){
            setIsBugTypeTrue(56,58);
        }
        if(str.charAt(17)=='1'){
            setIsBugTypeTrue(59,61);
        }
        if(str.charAt(18)=='1'){
            setIsBugTypeTrue(62,62);
        }
        if(str.charAt(19)=='1'){
            setIsBugTypeTrue(63,63);
        }
        if(str.charAt(20)=='1'){
            setIsBugTypeTrue(64,64);
        }
        if(str.charAt(21)=='1'){
            setIsBugTypeTrue(65,69);
        }
        if(str.charAt(22)=='1'){
            setIsBugTypeTrue(70,73);
        }
        if(str.charAt(23)=='1'){
            setIsBugTypeTrue(74,74);
        }
        if(str.charAt(24)=='1'){
            setIsBugTypeTrue(75,75);
        }
        if(str.charAt(25)=='1'){
            setIsBugTypeTrue(76,77);
        }
        if (str.charAt(26)=='1'){
            setIsBugTypeTrue(78,85);
        }
        if(str.charAt(27)=='1'){
            setIsBugTypeTrue(86,86);
        }
        if(str.charAt(28)=='1'){
            setIsBugTypeTrue(87,87);
        }
        if(str.charAt(29)=='1'){
            setIsBugTypeTrue(88,88);
        }
        if(str.charAt(30)=='1'){
            setIsBugTypeTrue(89,91);
        }
        if(str.charAt(31)=='1'){
            setIsBugTypeTrue(92,95);
        }
        if(str.charAt(32)=='1'){
            setIsBugTypeTrue(96,97);
        }
        if(str.charAt(33)=='1'){
            setIsBugTypeTrue(98,112);
        }
        if(str.charAt(34)=='1'){
            setIsBugTypeTrue(113,117);
        }
        if(str.charAt(35)=='1'){
            setIsBugTypeTrue(118,119);
        }
        if(str.charAt(36)=='1'){
            setIsBugTypeTrue(120,124);
        }
        if(str.charAt(37)=='1'){
            setIsBugTypeTrue(125,125);
        }
        if(str.charAt(38)=='1'){
            setIsBugTypeTrue(126,126);
        }
        if(str.charAt(39)=='1'){
            setIsBugTypeTrue(127,128);
        }
        if(str.charAt(40)=='1'){
            setIsBugTypeTrue(129,129);
        }
        if(str.charAt(41)=='1'){
            setIsBugTypeTrue(130,130);
        }
        if(str.charAt(42)=='1'){
            setIsBugTypeTrue(131,131);
        }
    }
    public void setBugName(){
        bugName.put("SQL注入",1);
        bugName.put("命令注入",2);
        bugName.put("代码注入",3);
        bugName.put("跨站脚本攻击（XSS）",4);
        bugName.put("未验证的重定向和转发",5);
        bugName.put("路径操作",6);
        bugName.put("跨站请求伪造",7);
        bugName.put("HTTP响应截断",8);
        bugName.put("上传任意文件",9);
        bugName.put("资源注入",10);

        bugName.put("LDAP注入",11);
        bugName.put("XML注入",12);
        bugName.put("危险文件包含",13);
        bugName.put("JSON注入（劫持）",14);
        bugName.put("OGNL注入",15);
        bugName.put("Xpath注入",16);
        bugName.put("文件泄露",17);
        bugName.put("不安全的加密存储",18);
        bugName.put("访问控制",19);
        bugName.put("密码管理",20);

        bugName.put("私有信息侵犯",21);
        bugName.put("Cookie安全",22);
        bugName.put("不安全的传输",23);
        bugName.put("会话固定",24);
        bugName.put("竞争条件",25);
        bugName.put("模板注入",26);
        bugName.put("输入验证",27);
        bugName.put("HTTP参数污染",28);
        bugName.put("格式化字符串",29);
        bugName.put("SMTP注入",30);

        bugName.put("认证问题",31);
        bugName.put("反序列化",32);
        bugName.put("服务器端请求伪造",33);
        bugName.put("加密问题",34);
        bugName.put("信息泄露",35);
        bugName.put("随机数",36);
        bugName.put("安全决策",37);
        bugName.put("空字节检验",38);
        bugName.put("资源消耗",39);
        bugName.put("CRLF注入",40);

        bugName.put("系统或配置设置的外部控制",41);
        bugName.put("类型转化错误",42);
        bugName.put("AWS注入",43);

    }

  /*  public HashMap<String,Integer> getBugName(){
        return bugName;
    }*/

    public HashMap<String,String> getBugType_Name(){
        return bugType_Name;
    }
  //  public HashMap<String,String>
    public void setBugType_Name(){
        bugType_Name.put("SERVLET_PARAMETER","SQL注入");
        bugType_Name.put("SERVLET_QUERY_STRING","SQL注入");
        bugType_Name.put("CUSTOM_INJECTION","SQL注入");
        bugType_Name.put("SQL_INJECTION","SQL注入");
        bugType_Name.put("SQL_INJECTION_TURBINE","SQL注入");
        bugType_Name.put("SQL_INJECTION_HIBERNATE","SQL注入");
        bugType_Name.put("SQL_INJECTION_JDO","SQL注入");
        bugType_Name.put("SQL_INJECTION_JPA","SQL注入");
        bugType_Name.put("SQL_INJECTION_SPRING_JDBC","SQL注入");
        bugType_Name.put("SQL_INJECTION_JDBC","SQL注入");
        bugType_Name.put("SCALA_SQL_INJECTION_SLICK","SQL注入");
        bugType_Name.put("SCALA_SQL_INJECTION_ANORM","SQL注入");

        bugType_Name.put("COMMAND_INJECTION","命令注入");
        bugType_Name.put("SCALA_COMMAND_INJECTION","命令注入");

        bugType_Name.put("JSP_SPRING_EVAL","代码注入");
        bugType_Name.put("SCRIPT_ENGINE_INJECTION","代码注入");
        bugType_Name.put("SPEL_INJECTION","代码注入");
        bugType_Name.put("EL_INJECTION","代码注入");
        bugType_Name.put("SEAM_LOG_INJECTION","代码注入");

        bugType_Name.put("XSS_REQUEST_WRAPPER","XSS");
        bugType_Name.put("JSP_JSTL_OUT","XSS");
        bugType_Name.put("XSS_JSP_PRINT","XSS");
        bugType_Name.put("XSS_SERVLET","XSS");
        bugType_Name.put("SCALA_XSS_TWIRL","XSS");
        bugType_Name.put("SCALA_XSS_MVC_API","XSS");
        bugType_Name.put("SERVLET_PARAMETER","XSS");

        bugType_Name.put("UNVALIDATED_REDIRECT","未验证的重定向和转发");
        bugType_Name.put("PLAY_UNVALIDATED_REDIRECT","未验证的重定向和转发");
        bugType_Name.put("SPRING_UNVALIDATED_REDIRECT","未验证的重定向和转发");

        bugType_Name.put("JSP_XSLT","路径操作");
        bugType_Name.put("MALICIOUS_XSLT","路径操作");
        bugType_Name.put("SERVLET_PARAMETER","路径操作");
        bugType_Name.put("SERVLET_QUERY_STRING","路径操作");
        bugType_Name.put("PATH_TRAVERSAL_IN","路径操作");
        bugType_Name.put("PATH_TRAVERSAL_OUT","路径操作");
        bugType_Name.put("SCALA_PATH_TRAVERSAL_IN","路径操作");
        bugType_Name.put("FILE_UPLOAD_FILENAME","路径操作");

        bugType_Name.put("PERMISSIVE_CORS","跨站请求伪造");
        bugType_Name.put("SPRING_CSRF_PROTECTION_DISABLED","跨站请求伪造");
        bugType_Name.put("SPRING_CSRF_UNRESTRICTED_REQUEST_MAPPING","跨站请求伪造");

        bugType_Name.put("HTTP_RESPONSE_SPLITTING","HTTP响应截断");
        bugType_Name.put("Unrestricted_FileUpload","上传任意文件");
        bugType_Name.put("RESOURCE_INJECTION","资源注入");
        bugType_Name.put("LDAP_ENTRY_POISONING","LDAP注入");
        bugType_Name.put("LDAP_INJECTION","LDAP注入");

        bugType_Name.put("XXE_XMLSTREAMREADER","XML注入");
        bugType_Name.put("XXE_XPATH","XML注入");
        bugType_Name.put("XXE_SAXPARSER","XML注入");
        bugType_Name.put("XXE_XMLREADER","XML注入");
        bugType_Name.put("XXE_DOCUMENT","XML注入");
        bugType_Name.put("XXE_DTD_TRANSFORM_FACTORY","XML注入");
        bugType_Name.put("XXE_XSLT_TRANSFORM_FACTORY","XML注入");

        bugType_Name.put("JSP_INCLUDE","危险文件包含");
        bugType_Name.put("JSON_HIJACKING","JSON注入(劫持)");
        bugType_Name.put("OGNL_INJECTION","OGNL注入");
        bugType_Name.put("XPATH_INJECTION","Xpath注入");

        bugType_Name.put("STRUTS_FILE_DISCLOSURE","文件泄露");
        bugType_Name.put("SPRING_FILE_DISCLOSURE","文件泄露");
        bugType_Name.put("REQUESTDISPATCHER_FILE_DISCLOSURE","文件泄露");

        bugType_Name.put("WEAK_MESSAGE_DIGEST_MD5","不安全的加密存储");
        bugType_Name.put("WEAK_MESSAGE_DIGEST_SHA1","不安全的加密存储");
        bugType_Name.put("CUSTOM_MESSAGE_DIGEST","不安全的加密存储");

        bugType_Name.put("LDAP_ANONYMOUS","访问控制");
        bugType_Name.put("HARD_CODE_PASSWORD","密码管理");
        bugType_Name.put("PRIVACY_VIOLATION","私有隐私侵犯");
        bugType_Name.put("INSECURE_COOKIE","Cookie安全");
        bugType_Name.put("HTTPONLY_COOKIE","Cookie安全");
        bugType_Name.put("COOKIE_PERSISTENT","Cookie安全");
        bugType_Name.put("SERVLET_SESSION_ID","Cookie安全");
        bugType_Name.put("COOKIE_USAGE","Cookie安全");

        bugType_Name.put("INSECURE_SMTP_SSL","不安全的传输");
        bugType_Name.put("DEFAULT_HTTP_CLIENT","不安全的传输");
        bugType_Name.put("SSL_CONTEXT","不安全的传输");

        bugType_Name.put("Seesion","会话固定");
        bugType_Name.put("RACE_CONDITION","竞争条件");
        bugType_Name.put("TEMPLATE_INJECTION_VELOCITY","模板注入");
        bugType_Name.put("TEMPLATE_INJECTION_FREEMARKER","模板注入");

        bugType_Name.put("STRUTS_FORM_VALIDATION","输入验证");
        bugType_Name.put("TRUST_BOUNDARY_VIOLATION","输入验证");
        bugType_Name.put("BEAN_PROPERTY_INJECTION","输入验证");
        bugType_Name.put("JAXWS_ENDPOINT","输入验证");
        bugType_Name.put("JAXRS_ENDPOINT","输入验证");
        bugType_Name.put("TAPESTRY_ENDPOINT","输入验证");
        bugType_Name.put("WICKET_ENDPOINT","输入验证");
        bugType_Name.put("XML_DECODER","输入验证");
        bugType_Name.put("HTTP_PARAMETER_POLLUTION","HTTP参数污染");
        bugType_Name.put("FORMAT_STRING_MANIPULATION","格式化字符串");
        bugType_Name.put("SMTP_HEADER_INJECTION","SMTP注入");

        bugType_Name.put("URL_REWRITING","认证问题");
        bugType_Name.put("WEAK_TRUST_MANAGER","认证问题");
        bugType_Name.put("WEAK_HOSTNAME_VERIFIER","认证问题");

        bugType_Name.put("XML_DECODER","反序列化");
        bugType_Name.put("OBJECT_DESERIALIZATION","反序列化");
        bugType_Name.put("JACKSON_UNSAFE_DESERIALIZATION","反序列化");
        bugType_Name.put("DESERIALIZATION_GADGET","反序列化");

        bugType_Name.put("SCALA_PLAY_SSRF","服务器请求伪造");
        bugType_Name.put("URLCONNECTION_SSRF_FD","服务器请求伪造");

        bugType_Name.put("HAZELCAST_SYMMETRIC_ENCRYPTION","加密问题");
        bugType_Name.put("NULL_CIPHER","加密问题");
        bugType_Name.put("UNENCRYPTED_SOCKET","加密问题");
        bugType_Name.put("UNENCRYPTED_SERVER_SOCKET","加密问题");
        bugType_Name.put("DES_USAGE","加密问题");
        bugType_Name.put("TDES_USAGE","加密问题");
        bugType_Name.put("HARD_CODE_KEY","加密问题");
        bugType_Name.put("BLOWFISH_KEY_SIZE","加密问题");
        bugType_Name.put("RSA_KEY_SIZE","加密问题");
        bugType_Name.put("STATIC_IV","加密问题");
        bugType_Name.put("ECB_MODE","加密问题");
        bugType_Name.put("PADDING_ORACLE","加密问题");
        bugType_Name.put("CIPHER_INTEGRITY","加密问题");
        bugType_Name.put("ESAPI_ENCRYPTOR","加密问题");

        bugType_Name.put("SCALA_SENSITIVE_DATA_EXPOSURE","信息泄露");
        bugType_Name.put("INFORMATION_EXPOSURE_THROUGH_AN_ERROR_MESSAGE","信息泄露");
        bugType_Name.put("UNENCRYPTED_SOCKET","信息泄露");
        bugType_Name.put("UNENCRYPTED_SERVER_SOCKET","信息泄露");
        bugType_Name.put("UNSAFE_HASH_EQUALS","信息泄露");

        bugType_Name.put("PREDICTABLE_RANDOM","随机数问题");
        bugType_Name.put("PREDICTABLE_RANDOM_SCALA","随机数问题");

        bugType_Name.put("SERVLET_CONTENT_TYPE","安全策略");
        bugType_Name.put("SERVLET_SERVER_NAME","安全策略");
        bugType_Name.put("SERVLET_HEADER","安全策略");
        bugType_Name.put("SERVLET_HEADER_REFERER","安全策略");
        bugType_Name.put("SERVLET_HEADER_USER_AGENT","安全策略");

        bugType_Name.put("WEAK_FILENAMEUTILS","空字节检验");
        bugType_Name.put("HTTP_RESPONSE_SPLITTING","CRLF注入");
        bugType_Name.put("CRLF_INJECTION_LOGS","CRLF注入");

        bugType_Name.put(" EXTERNAL_CONFIG_CONTROL","系统或配置设置的外部控制");
        bugType_Name.put("BAD_HEXA_CONVERSION","类型转化错误");
        bugType_Name.put("AWS_QUERY_INJECTION","AWS注入");
    }
}
