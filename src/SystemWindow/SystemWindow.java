package SystemWindow;
import DatabaseControl.Database;
import com.sun.source.tree.ReturnTree;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Objects;

public class SystemWindow extends JFrame {
    Database DB=null;
    private DefaultTableModel TableModel=null;
    private JTable ScreenTable= null;
    private JPanel panel =new JPanel();

    private JScrollPane DataPanel = null;

    private JButton Add = new JButton("添加员工");
    public SystemWindow(Database _DB)
    {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,500);
        this.setLocationRelativeTo(null);

        TableModel = new  DefaultTableModel(new String[]{"姓名", "性别", "工作组","职位","评级","教育经历","入职时间","工资","员工ID"}, 0);
        ScreenTable = new JTable(TableModel);

        DataPanel = new JScrollPane(ScreenTable);
        this.add(DataPanel ,BorderLayout.EAST);

        Add.addActionListener(new AddData());

        panel.add(Add);
        this.add(panel,BorderLayout.WEST);
        this.DB =_DB;
        UpdateTable();
    }

    private  void UpdateTable()
    {
        TableModel.setRowCount(0);
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


    private class AddData implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new AddWindow();
        }
    }

    public  class Update implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
           UpdateTable();
           System.out.println("1221212");
        }
    }

    class AddWindow extends JFrame
    {
        private JButton AddBTN =new JButton("添加");
        private JTextField NameLine = new JTextField("name",14);

        private JTextField WorkTeamLine = new JTextField("Workteam",14);

        private JTextField  PostLine = new JTextField("职位",14);

        private  JComboBox<String> EvaluationBox = new JComboBox<String>();

        private  JComboBox<String> Educational_backgroundBox = new  JComboBox<String>();

        private  JComboBox<String> Date_yy,Date_mm,Date_dd;

        private JTextField SalaryLine = new JTextField("薪资",14);

        private JTextField IDLine = new JTextField("ID",14);
        private  JComboBox<String> SexBox =new JComboBox();

        private JLabel text = new JLabel(" ");
        AddWindow()
        {
            this.setSize(200,400);
            this.setLayout(  new FlowLayout(FlowLayout.LEADING,15,10));
            AddBTN.addActionListener(new AddBTNAction());
            this.add(NameLine);
            InitSexBox();
            this.add(SexBox);
            this.add(WorkTeamLine);
            this.add(PostLine);
            InitEvaluation();
            this.add(EvaluationBox);
            InitEducational_background();
            this.add(Educational_backgroundBox);
            InitDate();
            this.add(SalaryLine);
            this.add(IDLine);
            this.add(AddBTN);
            this.add(text);
            this.setVisible(true);
        }
        private void InitSexBox()
        {
            SexBox.addItem("男                                ");
            SexBox.addItem("女                                ");
        }

        private void InitEvaluation()
        {
            EvaluationBox.addItem("A+                                ");
            EvaluationBox.addItem("A                                  ");
            EvaluationBox.addItem("B                                  ");
            EvaluationBox.addItem("C                                  ");
            EvaluationBox.addItem("D                                  ");
        }

        private void InitEducational_background()
        {
            Educational_backgroundBox.addItem("小学                            ");
            Educational_backgroundBox.addItem("初中                            ");
            Educational_backgroundBox.addItem("高中                            ");
            Educational_backgroundBox.addItem("大学                            ");
            Educational_backgroundBox.addItem("研究生                         ");
            Educational_backgroundBox.addItem("博士                            ");
        }

        private void InitDate()
        {
            Date_yy = new JComboBox<String> ();
            for(int year =1950 ;year<2022;year++)
            {
                Date_yy.addItem(String.valueOf(year));
            }
            Date_mm = new JComboBox<String> ();
            for(int m =1 ;m<13;m++)
            {
                Date_mm.addItem(String.valueOf(m));
            }
            Date_dd = new JComboBox<String> ();
            for(int d=1 ;d<31;d++)
            {
                Date_dd.addItem(String.valueOf(d));
            }
            this.add(Date_yy);
            this.add(Date_mm);
            this.add(Date_dd);
        }

        public  class AddBTNAction implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                if(NameLine.getText().length()>10)
                {
                    text.setText("名字长度过长");
                    return;
                }
                if(WorkTeamLine.getText().length()>20)
                {
                    text.setText("工作组长度过长");
                    return;
                }
                if(PostLine.getText().length()>10)
                {
                    text.setText("职位长度过长");
                    return;
                }
                try {
                    if (SalaryLine.getText().length() > 10 || Integer.parseInt(SalaryLine.getText()) >= 2147483647) {
                        text.setText("薪资超过限制");
                        return;
                    }
                }catch (Exception Err)
                {
                    text.setText("薪资格式错误");
                    return;
                }

                try
                {
                    String ID = IDLine.getText();
                    Integer.parseInt(ID);
                    //尝试转换

                    ResultSet _RS =DB.DBstmt.executeQuery("select id from worktable");
                    while(_RS.next())
                    {
                        if(Objects.equals(_RS.getString("id"), ID))
                        {
                            text.setText("ID重复");
                            return ;
                        }
                    }
                }catch (Exception Err)
                {
                    Err.printStackTrace();
                    text.setText("ID输入格式错误");
                }
                String _SQL = "insert into worktable value\n" +
                        "(\n" +
                        "'"+NameLine.getText()+"',\n" +
                        "'"+SexBox.getSelectedItem()+"',\n" +
                        "'"+WorkTeamLine.getText()+"',\n" +
                        "'"+PostLine.getText()+"',\n" +
                        "'"+EvaluationBox.getSelectedItem()+"',\n" +
                        "'"+Educational_backgroundBox.getSelectedItem()+"',\n" +
                        "'"+Date_yy.getSelectedItem()+"-"+Date_mm.getSelectedItem()+"-"+Date_dd.getSelectedItem()+"',\n" +
                        ""+SalaryLine.getText()+",\n" +
                        ""+IDLine.getText()+"\n" +
                        ")";
                System.out.println(_SQL);
                try {
                    DB.DBstmt.execute(_SQL);
                }catch (Exception Err)
                {
                    Err.printStackTrace();
                }
                UpdateTable();
            }
        }
    }
}



