package SystemWindow;
import DatabaseControl.Database;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class SystemWindow extends JFrame {
    Database DB=null;
    private DefaultTableModel TableModel=null;
    private JTable ScreenTable= null;
    private JPanel panel =new JPanel();
    public SystemWindow(Database _DB)
    {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800,500);
        this.setLocationRelativeTo(null);

        TableModel = new  DefaultTableModel(new String[]{"姓名", "性别", "工作组","评级","教育经历","入职时间","工资","员工ID"}, 10);
        ScreenTable = new JTable(TableModel);

        JScrollPane test = new JScrollPane(ScreenTable);
        this.add(test,BorderLayout.EAST);
        this.add(panel,BorderLayout.WEST);
        this.DB =_DB;
        UpdateTable();
    }

    private void UpdateTable()
    {
        try {
            ResultSet _RS = DB.DBstmt.executeQuery("select * from worktable;");
            while(_RS.next())
            {
                Object[] Temp = {_RS.getString(1),_RS.getString(2),_RS.getString(3),_RS.getString(4),_RS.getString(5),_RS.getString(6),_RS.getString(7),_RS.getString(8),_RS.getString(9)};
                TableModel.addRow(Temp);
            }
        }catch (Exception Err)
        {
            Err.printStackTrace();
        }
    }
}
