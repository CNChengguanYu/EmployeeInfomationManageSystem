package SystemWindow;
import DatabaseControl.Database;
import com.sun.source.tree.ReturnTree;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Objects;

public class SystemWindow extends JFrame {
    Database DB=null;
    private DefaultTableModel TableModel=null;
    private JTable ScreenTable= null;
    private JPanel panel =new JPanel();

    private JScrollPane DataPanel = null;

    private JButton Add = new JButton("添加员工");

    private JButton Del = new JButton("删除员工");

    private JButton Upd = new JButton("更新数据");

    private JComboBox<String> Word=null,order=null;

    private JButton Ord = new JButton("排序");

    private HashMap box_2_colname = new HashMap();

    private JLabel Text = new JLabel(" ");
    public SystemWindow(Database _DB)
    {
        this.setTitle("员工管理");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,500);
        this.setLocationRelativeTo(null);

        TableModel = new  DefaultTableModel(new String[]{"姓名", "性别", "工作组","职位","评级","教育经历","入职时间","工资","员工ID"}, 0);
        ScreenTable = new JTable(TableModel);

        DataPanel = new JScrollPane(ScreenTable);
        this.add(DataPanel ,BorderLayout.EAST);

        Upd.addActionListener(new UpdData());
        Add.addActionListener(new AddData());
        Del.addActionListener(new DelData());
        InitOrdBox();
        panel.setLayout(new GridLayout(5,4,5,5));
        panel.add(Add);
        panel.add(Del);
        panel.add(Upd);

        Ord.addActionListener(new OrderData());
        JPanel panel2 = new JPanel();
        panel2.add(Word);
        panel2.add(order);
        panel2.add(Ord);
        panel.add(panel2);
        panel.add(Text);
        this.add(panel,BorderLayout.WEST);
        this.DB =_DB;
        UpdateTable();
    }

    private void InitOrdBox()
    {
        String[] word = {"组别","评级","入职时间","工资","编号"};
        box_2_colname.put("组别","workteam");
        box_2_colname.put("评级","evaluation");
        box_2_colname.put("入职时间","Entry_date");
        box_2_colname.put("工资","salary");
        box_2_colname.put("编号","id");
        Word = new JComboBox<String>(word);
        order = new JComboBox<String>(new String[]{"升序","降序"});
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

    private  void UpdateTable(String SQL)
    {
        TableModel.setRowCount(0);
        try {
            ResultSet _RS = DB.DBstmt.executeQuery(SQL);
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

    private class OrderData implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String Value = (String) box_2_colname.get(Word.getSelectedItem());
            String Mode ;
            if(order.getSelectedItem() == "升序")
            {
                Mode = "DESC";
            }else
            {
                Mode = "ASC";
            }
            String SQL="select * from worktable order by "+Value+" "+Mode+";";
            UpdateTable(SQL);
        }
    }
    private class AddData implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new AddWindow();
        }
    }

    private class DelData implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            int index = ScreenTable.getSelectedRow();
            if(index < 0)
            {
                Text.setText("请选择一行");
                return;
            }
            String Info= ScreenTable.getValueAt(index,2) +
                    (String)ScreenTable.getValueAt(index,3)+":"+
                    ScreenTable.getValueAt(index,0) +",编号:";
            String Id=(String)ScreenTable.getValueAt(index,8);
            new DelWindow(Info,Id);
            Text.setText("");
        }
    }

    private class UpdData implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int index = ScreenTable.getSelectedRow();
            if(index<0)
            {
                Text.setText("请选择一行");
                return;
            }
            new UpdateWindow(index);
            Text.setText("");
        }
    }

    class AddWindow extends JFrame
    {
        private JButton AddBTN =new JButton("添加");
        private JTextField NameLine = new JTextField("",10);

        private JTextField WorkTeamLine = new JTextField("",10);

        private JTextField  PostLine = new JTextField("",10);

        private  JComboBox<String> EvaluationBox = new JComboBox<String>();

        private  JComboBox<String> Educational_backgroundBox = new  JComboBox<String>();

        private  JComboBox<String> Date_yy,Date_mm,Date_dd;

        private JTextField SalaryLine = new JTextField("",10);

        private JTextField IDLine = new JTextField("",10);
        private  JComboBox<String> SexBox =new JComboBox();

        private JLabel text = new JLabel(" ");
        AddWindow()
        {
            this.setSize(200,400);
            this.setLayout(  new FlowLayout(FlowLayout.LEADING,15,10));
            AddBTN.addActionListener(new AddBTNAction());
            this.add(new JLabel("姓名"));
            this.add(NameLine);
            this.add(new JLabel("性别"));
            InitSexBox();
            this.add(SexBox);
            this.add(new JLabel("工作组"));
            this.add(WorkTeamLine);
            this.add(new JLabel("职位"));
            this.add(PostLine);
            this.add(new JLabel("评级"));
            InitEvaluation();
            this.add(EvaluationBox);
            this.add(new JLabel("学历"));
            InitEducational_background();
            this.add(Educational_backgroundBox);
            this.add(new JLabel("入职日期                   "));
            InitDate();
            this.add(new JLabel("工资"));
            this.add(SalaryLine);
            this.add(new JLabel("编号"));
            this.add(IDLine);
            this.add(AddBTN);
            this.add(text);
            this.setVisible(true);
        }
        private void InitSexBox()
        {
            SexBox.addItem("男                      ");
            SexBox.addItem("女                      ");
        }

        private void InitEvaluation()
        {
            EvaluationBox.addItem("A                  ");
            EvaluationBox.addItem("B                  ");
            EvaluationBox.addItem("C                  ");
            EvaluationBox.addItem("D                  ");
        }

        private void InitEducational_background()
        {
            Educational_backgroundBox.addItem("小学             ");
            Educational_backgroundBox.addItem("初中             ");
            Educational_backgroundBox.addItem("高中             ");
            Educational_backgroundBox.addItem("大学             ");
            Educational_backgroundBox.addItem("研究生           ");
            Educational_backgroundBox.addItem("博士             ");
        }

        private void InitDate()
        {
            Date_yy = new JComboBox<String> ();
            for(int year =2022 ;year>1950;--year)
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
    class UpdateWindow extends JFrame
    {
        private JButton AddBTN =new JButton("添加");
        private JTextField NameLine = null;

        private JTextField WorkTeamLine = null;

        private JTextField  PostLine = null;

        private  JComboBox<String> EvaluationBox = new JComboBox<String>();

        private  JComboBox<String> Educational_backgroundBox = new  JComboBox<String>();

        private  JComboBox<String> Date_yy,Date_mm,Date_dd;

        private JTextField SalaryLine = null;

        private  JComboBox<String> SexBox =new JComboBox();

        int Index = 0;

        private JLabel text = new JLabel(" ");
        UpdateWindow(int index)
        {
            UpdateInfo(index);
            this.setSize(200,400);
            this.setLayout(  new FlowLayout(FlowLayout.LEADING,15,10));
            AddBTN.addActionListener(new AddBTNAction());
            this.add(new JLabel("姓名"));
            this.add(NameLine);
            this.add(new JLabel("性别"));
            InitSexBox();
            this.add(SexBox);
            this.add(new JLabel("工作组"));
            this.add(WorkTeamLine);
            this.add(new JLabel("职位"));
            this.add(PostLine);
            this.add(new JLabel("评级"));
            InitEvaluation();
            this.add(EvaluationBox);
            this.add(new JLabel("学历"));
            InitEducational_background();
            this.add(Educational_backgroundBox);
            this.add(new JLabel("入职日期                   "));
            InitDate();
            this.add(new JLabel("工资"));
            this.add(SalaryLine);
            this.add(new JLabel("编号"));

            this.add(AddBTN);
            this.add(text);
            this.setVisible(true);
        }

        void UpdateInfo(int index)
        {
            Index=index;
            NameLine =  new JTextField((String)ScreenTable.getValueAt(index,0),10);
            WorkTeamLine = new JTextField((String)ScreenTable.getValueAt(index,2),10);
            PostLine = new JTextField((String)ScreenTable.getValueAt(index,3),10);
            SalaryLine = new JTextField((String)ScreenTable.getValueAt(index,7),10);
        }
        private void InitSexBox()
        {
            SexBox.addItem("男                      ");
            SexBox.addItem("女                      ");
        }

        private void InitEvaluation()
        {
            EvaluationBox.addItem("A                  ");
            EvaluationBox.addItem("B                  ");
            EvaluationBox.addItem("C                  ");
            EvaluationBox.addItem("D                  ");
        }

        private void InitEducational_background()
        {
            Educational_backgroundBox.addItem("小学             ");
            Educational_backgroundBox.addItem("初中             ");
            Educational_backgroundBox.addItem("高中             ");
            Educational_backgroundBox.addItem("大学             ");
            Educational_backgroundBox.addItem("研究生           ");
            Educational_backgroundBox.addItem("博士             ");
        }

        private void InitDate()
        {
            Date_yy = new JComboBox<String> ();
            for(int year =2022 ;year>1950;--year)
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

                String _SQL = "UPDATE worktable\n" +
                        "set \n" +
                        "name = " +"'"+NameLine.getText()+"',\n"+
                        "sex =  " +"'"+SexBox.getSelectedItem()+"',\n"+
                        "workteam =" +"'"+WorkTeamLine.getText()+"',\n"+
                        "post = " +"'"+PostLine.getText()+"',\n"+
                        "evaluation = " +"'"+EvaluationBox.getSelectedItem()+"',\n"+
                        "educational_background = " +"'"+Educational_backgroundBox.getSelectedItem()+"',\n"+
                        "Entry_date = '"+Date_yy.getSelectedItem()+"-"+Date_mm.getSelectedItem()+"-"+Date_dd.getSelectedItem()+"',\n"+
                        "salary = "+SalaryLine.getText()+"\n"+
                        "where id ="+(String)ScreenTable.getValueAt(Index,8)+";";
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
    class DelWindow extends JFrame
    {
        JPanel panel  = new JPanel();
        JButton yes =new JButton("确定");
        JButton no = new JButton("取消");

        String ID =null;
        DelWindow(String Info,String Id)
        {
            ID=Id;
            this.setSize(300,130);
            this.setVisible(true);
            yes.addActionListener(new Yes_btn());
            no.addActionListener(new No_btn());
            panel.add(new JLabel("您确定要删除:"));
            panel.add(new JLabel(Info+Id+"吗？"));
            panel.add(yes);
            panel.add(no);
            this.add(panel);
        }
        void close()
        {
            this.dispose();
        }
        public  class Yes_btn implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String _SQL ="delete from worktable where id = "+ID+";";
                    DB.DBstmt.execute(_SQL);
                }catch (Exception Err)
                {
                    Err.printStackTrace();
                }
                UpdateTable();
                close();
            }
        }

        public class No_btn implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                close();
            }
        }
    }
}



