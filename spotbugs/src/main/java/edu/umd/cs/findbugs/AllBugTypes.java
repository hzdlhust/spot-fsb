package edu.umd.cs.findbugs;


import java.util.HashMap;

public class AllBugTypes {

    public  HashMap<String,String> bugType_Name=new HashMap<>();



    public AllBugTypes(){

    }

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
     //   bugType_Name.put("SERVLET_PARAMETER","XSS");

        bugType_Name.put("UNVALIDATED_REDIRECT","未验证的重定向和转发");
        bugType_Name.put("PLAY_UNVALIDATED_REDIRECT","未验证的重定向和转发");
        bugType_Name.put("SPRING_UNVALIDATED_REDIRECT","未验证的重定向和转发");

        bugType_Name.put("JSP_XSLT","路径操作");
        bugType_Name.put("MALICIOUS_XSLT","路径操作");
       // bugType_Name.put("SERVLET_PARAMETER","路径操作");
       // bugType_Name.put("SERVLET_QUERY_STRING","路径操作");
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
      //  bugType_Name.put("XML_DECODER","输入验证");
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
    //    bugType_Name.put("UNENCRYPTED_SOCKET","信息泄露");
      //  bugType_Name.put("UNENCRYPTED_SERVER_SOCKET","信息泄露");
        bugType_Name.put("UNSAFE_HASH_EQUALS","信息泄露");

        bugType_Name.put("PREDICTABLE_RANDOM","随机数问题");
        bugType_Name.put("PREDICTABLE_RANDOM_SCALA","随机数问题");

        bugType_Name.put("SERVLET_CONTENT_TYPE","安全策略");
        bugType_Name.put("SERVLET_SERVER_NAME","安全策略");
        bugType_Name.put("SERVLET_HEADER","安全策略");
        bugType_Name.put("SERVLET_HEADER_REFERER","安全策略");
        bugType_Name.put("SERVLET_HEADER_USER_AGENT","安全策略");

        bugType_Name.put("WEAK_FILENAMEUTILS","空字节检验");
     //   bugType_Name.put("HTTP_RESPONSE_SPLITTING","CRLF注入");
        bugType_Name.put("CRLF_INJECTION_LOGS","CRLF注入");

        bugType_Name.put(" EXTERNAL_CONFIG_CONTROL","系统或配置设置的外部控制");
        bugType_Name.put("BAD_HEXA_CONVERSION","类型转化错误");
        bugType_Name.put("AWS_QUERY_INJECTION","AWS注入");
        bugType_Name.put("DMI_CONSTANT_DB_PASSWORD","数据库密码安全");
        bugType_Name.put("DMI_EMPTY_DB_PASSWORD","数据库密码安全");

    }
}
