package edu.umd.cs.findbugs;

import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.InputStream;
import java.util.Properties;

public class BugDataBase {
//    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://localhost:3306/bugknowledge?serverTimezone=UTC&useSSL=false&characterEncoding=utf8&autoReconnect=true";
//    static final String DB_USER = "root";
//    static final String DB_PASSWORD = "MySQL0330";
    private Connection conn = null;
    public static void main(String[] args) throws Exception{
        //BugDataBase bdb = new BugDataBase();
        //List res = bdb.SearchBugInstance();
//        List res = bdb.SearchBugInstanceByCategory(1);
        //List res = bdb.SearchBugInstanceByCategory("Correctness (CORRECTNESS)");
        //System.out.println(bdb.AddBugCategoryIntoBugCategory("test","jjjjjjjjjj"));
        //bdb.UpdateBugCategoryDescription(15,"new description");
        //bdb.AddBugInstanceIntoBugInformation("test",0,1,"have a test");
        //bdb.UpdateBugInstanceDescription(570,"newnewnewnewnew");
//        bdb.DeleteBugInstance(570);
//        bdb.DeleteBugInstance(571);
//        bdb.DeleteBugCategory(13);
//        bdb.DeleteBugCategory(14);
//        bdb.DeleteBugCategory(15);
        /*if(bdb.DeleteBugCategory(13))
            System.out.println("okkkkkk");
        else
            System.out.println("over");

         */
    }
    public BugDataBase() throws Exception{
        init();
        //System.out.println("over!");
    }
    private void init() throws Exception{
        InputStream is= BugDataBase.class.getClassLoader().getResourceAsStream("BugKnowledge/db.properties");
        Properties pro = new Properties();
        pro.load(is);
        String JDBC_DRIVER = pro.getProperty("driver");
        String DB_URL = pro.getProperty("url");
        String DB_USER = pro.getProperty("user");
        String DB_PASSWORD = pro.getProperty("password");
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println(conn);
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //List中元素为Map类型
    private static List ConvertList(ResultSet rs) throws SQLException{
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData(); //获取键名
        int ColumnCount = md.getColumnCount(); //获取行的数量
        while (rs.next()){
            Map rowData = new HashMap(); //声明Map
            for(int i=1;i <= ColumnCount; i++){
                rowData.put(md.getColumnName(i), rs.getObject(i)); //获取键名和值
            }
            list.add(rowData);
        }
        return list;
    }

    /***************查询**************/
    //查询，返回所有漏洞对象
    public List SearchBugInstance() throws Exception{
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM BUG_INFORMATION";
        ResultSet res = stmt.executeQuery(sql);
        return ConvertList(res);
    }
    //查询,通过漏洞名查询
    public List SearchBugInstanceByName(String name) throws Exception{
        String sql;
        sql = "SELECT * FROM BUG_INFORMATION WHERE NAME = ?";
        PreparedStatement pstmt =conn.prepareStatement(sql);
        pstmt.setString(1,name);
        ResultSet res = pstmt.executeQuery();
        return ConvertList(res);
    }
    //查询,通过漏洞cwe号查询
    public List SearchBugInstanceByCwe(int cwe) throws Exception{
        String sql = "SELECT * FROM BUG_INFORMATION WHERE CWE = ?";
        PreparedStatement pstmt =conn.prepareStatement(sql);
        pstmt.setInt(1,cwe);
        ResultSet res = pstmt.executeQuery();
        return ConvertList(res);
    }
    //查询，通过漏洞分类id查询
    public List SearchBugInstanceByCategory(int category_id) throws Exception{
        String sql = "SELECT * FROM BUG_INFORMATION WHERE CATEGORY_ID = ?";
        PreparedStatement pstmt =conn.prepareStatement(sql);
        pstmt.setInt(1,category_id);
        ResultSet res = pstmt.executeQuery();
        return ConvertList(res);
    }
    //查询，通过漏洞分类名查询
    public List SearchBugInstanceByCategory(String category) throws Exception{
        String sql = "SELECT * " +
                "FROM BUG_INFORMATION " +
                "WHERE CATEGORY_ID IN (SELECT CATEGORY_ID " +
                "FROM BUG_CATEGORY " +
                "WHERE CATEGORY_NAME = ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,category);
        ResultSet res = pstmt.executeQuery();
        return ConvertList(res);
    }
    //查询，返回所有漏洞分类
    public List SearchBugCategory() throws Exception{
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM BUG_CATEGORY";
        ResultSet res = stmt.executeQuery(sql);
        return ConvertList(res);
    }
    //查询，通过分类名查询漏洞分类
    public List SearchBugCategoryByName(String category) throws Exception{
        String sql = "SELECT * FROM BUG_CATEGORY WHERE CATEGORY_NAME = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,category);
        ResultSet res = pstmt.executeQuery();
        return ConvertList(res);
    }
    //查询，通过分类id查询漏洞分类
    public List SearchBugCategoryByName(int categoryId) throws Exception{
        String sql = "SELECT * FROM BUG_CATEGORY WHERE CATEGORY_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,categoryId);
        ResultSet res = pstmt.executeQuery();
        return ConvertList(res);
    }
    /***************查询**************/

    /***************新增**************/
    //新增漏洞信息表，返回是否增加成功
    public boolean AddBugInstanceIntoBugInformation(String Name, int cwe, int categoryId, String description) throws Exception{
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dt = formatter.format(date);
        String sql = "INSERT INTO BUG_INFORMATION (ID, NAME, CWE, CATEGORY_ID, DESCRIPTION, CREATE_TIME) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 0);
            pstmt.setString(2, Name);
            pstmt.setInt(3, cwe);
            pstmt.setInt(4, categoryId);
            pstmt.setString(5, description);
            pstmt.setString(6, dt);
            return pstmt.executeUpdate()>0;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    //新增漏洞分类表，返回是否增加成功
    public boolean AddBugCategoryIntoBugCategory(String Name, String description) throws Exception{
        String sql = "INSERT INTO BUG_CATEGORY (CATEGORY_ID, CATEGORY_NAME, DESCRIPTION) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 0);
            pstmt.setString(2, Name);
            pstmt.setString(3, description);
            return pstmt.executeUpdate()>0;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    /***************新增**************/


    /***************删除**************/
    //删除
    public boolean DeleteBugInstance(int id) throws Exception{
        String sql = "DELETE FROM BUG_INFORMATION WHERE ID = ?";
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate()>0;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean DeleteBugCategory(int id){
        String sql = "DELETE FROM BUG_CATEGORY WHERE CATEGORY_ID = ?";
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate()>0;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    /***************删除**************/

    /***************编辑**************/
    //编辑,修改漏洞名称
    public boolean UpdateBugInstanceName(int id, String name) throws Exception{
        String sql = "UPDATE BUG_INFORMATION SET NAME = ?, CREATE_TIME = ? WHERE ID = ?";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dt = formatter.format(date);
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);
            pstmt.setString(2,dt);
            pstmt.setInt(3, id);
            return pstmt.executeUpdate()>0;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //修改漏洞cwe号
    public boolean UpdateBugInstanceCwe(int id, int cwe) throws Exception{
        String sql = "UPDATE BUG_INFORMATION SET CWE = ?, CREATE_TIME = ? WHERE ID = ?";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dt = formatter.format(date);
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,cwe);
            pstmt.setString(2,dt);
            pstmt.setInt(3, id);
            return pstmt.executeUpdate()>0;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //修改漏洞分类id
    public boolean UpdateBugInstanceCategory(int id, int categoryId) throws Exception{
        String sql = "UPDATE BUG_INFORMATION SET CATEGORY_ID = ?, CREATE_TIME = ? WHERE ID = ?";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dt = formatter.format(date);
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,categoryId);
            pstmt.setString(2,dt);
            pstmt.setInt(3, id);
            return pstmt.executeUpdate()>0;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //修改漏洞描述
    public boolean UpdateBugInstanceDescription(int id, String description) throws Exception{
        String sql = "UPDATE BUG_INFORMATION SET DESCRIPTION = ?, CREATE_TIME = ? WHERE ID = ?";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dt = formatter.format(date);
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,description);
            pstmt.setString(2,dt);
            pstmt.setInt(3, id);
            return pstmt.executeUpdate()>0;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //修改漏洞分类名字
    public boolean UpdateBugCategoryDescription(int id, String description) throws Exception{
        String sql = "UPDATE BUG_CATEGORY SET DESCRIPTION = ? WHERE CATEGORY_ID = ?";
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        String dt = formatter.format(date);
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,description);
//            pstmt.setString(2,dt);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate()>0;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //修改漏洞分类名字
    public boolean UpdateBugCategoryName(int id, String name) throws Exception{
        String sql = "UPDATE BUG_CATEGORY SET DESCRIPTION = ? WHERE CATEGORY_ID = ?";
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        String dt = formatter.format(date);
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);
//            pstmt.setString(2,dt);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate()>0;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /***************编辑**************/
}
