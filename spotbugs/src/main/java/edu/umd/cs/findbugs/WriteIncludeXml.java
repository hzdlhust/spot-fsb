package edu.umd.cs.findbugs;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


public class WriteIncludeXml {
    private String dir;
   // public HashMap<String,String> bugType_Name=new HashMap<>();
    public  ArrayList<String> list = new ArrayList<String>();
    public WriteIncludeXml(){
    }

    public void setList() {
       list.add("SERVLET_PARAMETER");
       list.add("SERVLET_QUERY_STRING");
       list.add("CUSTOM_INJECTION");
       list.add("SQL_INJECTION");
       list.add("SQL_INJECTION_TURBINE");
       list.add("SQL_INJECTION_HIBERNATE");
       list.add("SQL_INJECTION_JDO");
       list.add("SQL_INJECTION_JPA");
       list.add("SQL_INJECTION_SPRING_JDBC");
       list.add("SQL_INJECTION_JDBC");
       list.add("SCALA_SQL_INJECTION_SLICK");
       list.add("SCALA_SQL_INJECTION_ANORM");

       list.add("COMMAND_INJECTION");
       list.add("SCALA_COMMAND_INJECTION");

        list.add("JSP_SPRING_EVAL");
        list.add("SCRIPT_ENGINE_INJECTION");
        list.add("SPEL_INJECTION");
        list.add("EL_INJECTION");
        list.add("SEAM_LOG_INJECTION");

        list.add("XSS_REQUEST_WRAPPER");
        list.add("JSP_JSTL_OUT");
        list.add("XSS_JSP_PRINT");
        list.add("XSS_SERVLET");
        list.add("SCALA_XSS_TWIRL");
        list.add("SCALA_XSS_MVC_API");
        list.add("SERVLET_PARAMETER");

        list.add("UNVALIDATED_REDIRECT");
        list.add("PLAY_UNVALIDATED_REDIRECT");
        list.add("SPRING_UNVALIDATED_REDIRECT");

        list.add("JSP_XSLT");
        list.add("MALICIOUS_XSLT");
        list.add("SERVLET_PARAMETER");
        list.add("SERVLET_QUERY_STRING");
        list.add("PATH_TRAVERSAL_IN");
        list.add("PATH_TRAVERSAL_OUT");
        list.add("SCALA_PATH_TRAVERSAL_IN");
        list.add("FILE_UPLOAD_FILENAME");

        list.add("PERMISSIVE_CORS");
        list.add("SPRING_CSRF_PROTECTION_DISABLED");
        list.add("SPRING_CSRF_UNRESTRICTED_REQUEST_MAPPING");

        list.add("HTTP_RESPONSE_SPLITTING");
        list.add("Unrestricted_FileUpload");
        list.add("RESOURCE_INJECTION");

        list.add("LDAP_ENTRY_POISONING");
        list.add("LDAP_INJECTION");

        list.add("XXE_XMLSTREAMREADER");
        list.add("XXE_XPATH");
        list.add("XXE_SAXPARSER");
        list.add("XXE_XMLREADER");
        list.add("XXE_DOCUMENT");
        list.add("XXE_DTD_TRANSFORM_FACTORY");
        list.add("XXE_XSLT_TRANSFORM_FACTORY");

        list.add("JSP_INCLUDE");
        list.add("JSON_HIJACKING");
        list.add("OGNL_INJECTION");
        list.add("XPATH_INJECTION");

        list.add("STRUTS_FILE_DISCLOSURE");
        list.add("SPRING_FILE_DISCLOSURE");
        list.add("REQUESTDISPATCHER_FILE_DISCLOSURE");

        list.add("WEAK_MESSAGE_DIGEST_MD5");
        list.add("WEAK_MESSAGE_DIGEST_SHA1");
        list.add("CUSTOM_MESSAGE_DIGEST");

        list.add("LDAP_ANONYMOUS");
        list.add("HARD_CODE_PASSWORD");
        list.add("PRIVACY_VIOLATION");

        list.add("INSECURE_COOKIE");
        list.add("HTTPONLY_COOKIE");
        list.add("COOKIE_PERSISTENT");
        list.add("SERVLET_SESSION_ID");
        list.add("COOKIE_USAGE");

        list.add("INSECURE_SMTP_SSL");
        list.add("COOKIE_USAGE");
        list.add("DEFAULT_HTTP_CLIENT");
        list.add("SSL_CONTEXT");

        list.add("Seesion");
        list.add("RACE_CONDITION");

        list.add("TEMPLATE_INJECTION_VELOCITY");
        list.add("TEMPLATE_INJECTION_FREEMARKER");

        list.add("STRUTS_FORM_VALIDATION");
        list.add("TRUST_BOUNDARY_VIOLATION");
        list.add("BEAN_PROPERTY_INJECTION");
        list.add("JAXWS_ENDPOINT");
        list.add("JAXRS_ENDPOINT");
        list.add("TAPESTRY_ENDPOINT");
        list.add("WICKET_ENDPOINT");
        list.add("XML_DECODER");

        list.add("HTTP_PARAMETER_POLLUTION");
        list.add("FORMAT_STRING_MANIPULATION");
        list.add("SMTP_HEADER_INJECTION");

        list.add("URL_REWRITING");
        list.add("WEAK_TRUST_MANAGER");
        list.add("WEAK_HOSTNAME_VERIFIER");

        list.add("XML_DECODER");
        list.add("OBJECT_DESERIALIZATION");
        list.add("JACKSON_UNSAFE_DESERIALIZATION");
        list.add("DESERIALIZATION_GADGET");

        list.add("SCALA_PLAY_SSRF");
        list.add("URLCONNECTION_SSRF_FD");

        list.add("HAZELCAST_SYMMETRIC_ENCRYPTION");
        list.add("NULL_CIPHER");
        list.add("UNENCRYPTED_SOCKET");
        list.add("UNENCRYPTED_SERVER_SOCKET");
        list.add("DES_USAGE");
        list.add("TDES_USAGE");
        list.add("RSA_NO_PADDING");
        list.add("HARD_CODE_KEY");
        list.add("BLOWFISH_KEY_SIZE");
        list.add("RSA_KEY_SIZE");
        list.add("STATIC_IV");
        list.add("ECB_MODE");
        list.add("PADDING_ORACLE");
        list.add("CIPHER_INTEGRITY");
        list.add("ESAPI_ENCRYPTOR");

        list.add("SCALA_SENSITIVE_DATA_EXPOSURE");
        list.add("INFORMATION_EXPOSURE_THROUGH_AN_ERROR_MESSAGE");
        list.add("UNENCRYPTED_SOCKET");
        list.add("UNENCRYPTED_SERVER_SOCKET");
        list.add("UNSAFE_HASH_EQUALS");

        list.add("PREDICTABLE_RANDOM");
        list.add("PREDICTABLE_RANDOM_SCALA");

        list.add("SERVLET_CONTENT_TYPE");
        list.add("SERVLET_SERVER_NAME");
        list.add("SERVLET_HEADER");
        list.add("SERVLET_HEADER_REFERER");
        list.add("SERVLET_HEADER_USER_AGENT");

        list.add("WEAK_FILENAMEUTILS");

        list.add("HTTP_RESPONSE_SPLITTING");
        list.add("CRLF_INJECTION_LOGS");

        list.add("EXTERNAL_CONFIG_CONTROL");
        list.add("BAD_HEXA_CONVERSION");
        list.add("AWS_QUERY_INJECTION");

    }

    public  void setDir() {
        File directory = new File("");//参数为空
        try {
            String courseFile = directory.getCanonicalPath() ;
            System.out.print(courseFile);
            this.dir=courseFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createXML(){
        writeXML(dir+"\\include.xml");
       // writeXML(dir);
    }
    public void writeXML(String dir){
       Document document=null;
        OutputStream outputStream=null;
        XMLWriter xmlWriter=null;
        try{
            document= DocumentHelper.createDocument();
            Element rootElem=document.addElement("FindBugsFilter","https://github.com/spotbugs/filter/3.0.0");
           /*rootElem.addNamespace("xmlns","https://github.com/spotbugs/filter/3.0.0");
            rootElem.addAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
            rootElem.addAttribute("xsi:schemaLocation","https://github.com/spotbugs/filter/3.0.0 https://raw.githubusercontent.com/spotbugs/spotbugs/3.1.0/spotbugs/etc/findbugsfilter.xsd");
            document.add(rootElem);*/
            rootElem.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
            rootElem.addAttribute("xsi:schemaLocation","https://github.com/spotbugs/filter/3.0.0 https://raw.githubusercontent.com/spotbugs/spotbugs/3.1.0/spotbugs/etc/findbugsfilter.xsd");
            String str=AnalyseCommand.bugTypes;
            setList();
            int i=0;
            int[] bugNum=new int[]{12,2,5,7,3,
                                   8,3,1,1,1,
                                    2,7,1,1,1,
                                    1,3,3,1,1,
                                    1,5,4,1,1,
                                    2,8,1,1,1,
                                    3,4,2,15,5,
                                    2,5,1,0,2,
                                    1,1,1};
            for(int index=0;index<43;index++) {
                if (str.charAt(index) == '1') {
                    int j=0;
                    while (j < bugNum[index]) {
                        Element match = rootElem.addElement("Match");
                        Element type=match.addElement("Bug");
                        type.addAttribute("pattern",list.get(i++));
                        j++;
                    }
                }
            }
            OutputFormat outputFormat=OutputFormat.createPrettyPrint();
            outputFormat.setEncoding("UTF-8");
            outputStream=new FileOutputStream(dir);
            xmlWriter=new XMLWriter(outputStream,outputFormat);
            xmlWriter.write(document);
        }catch (Exception e){
            System.out.print(e);
        }
        finally {
            try {
                xmlWriter.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



}
