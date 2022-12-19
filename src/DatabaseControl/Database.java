package DatabaseControl;

import java.sql.*;

public class Database {
    public Connection DBconn = null;

    public Statement DBstmt =null;

    public ResultSet DBrs =null;

    public Database() {
        try {
            //连接JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException Error) {
            Error.printStackTrace();
            System.out.println("连接JDBC失败");
        }
    }

    public final boolean ConnectDB(String _URL, String _UserName, String _Password)
    {
        try {
            DBconn=DriverManager.getConnection(_URL,_UserName,_Password);
            DBstmt=DBconn.createStatement();
            DBrs=DBstmt.getResultSet();
            return true;
        }catch (Exception Error)
        {
            return false;
        }
    }

    public final boolean ReConnectDB(String _URL, String _UserName, String _Password)
    {
        try {
            DBrs=null;
            DBstmt=null;
            DBconn=null;

            DBconn=DriverManager.getConnection(_URL,_UserName,_Password);
            DBstmt=DBconn.createStatement();
            DBrs=DBstmt.getResultSet();

            return true;
        }catch (Exception Error)
        {
            Error.printStackTrace();
            return false;
        }
    }

    public final Connection getConnect() {
        return this.DBconn;
    }
}
