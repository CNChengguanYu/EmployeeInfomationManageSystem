/**
 *          Code By 2022-12-8
 *                   主类实现
 *          AIIT-Sophomore-HomeWork
 */

import LoginWindow.*;
import java.sql.*;
import DatabaseControl.Database;
public class Main
{
    private static Database db = new Database();
 public static Connection connDB;
    public static void main(String[] args)
    {
        LoginWindow login = new LoginWindow();
        login.GetDB(db);
        login.setVisible(true);
    }
}