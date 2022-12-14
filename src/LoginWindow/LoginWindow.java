package LoginWindow;

import DatabaseControl.Database;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Toolkit;

public class LoginWindow extends JFrame
{
	//---窗口元素---
	private int Width = 500 , Height = 500;
	//窗口的宽高

	private JPanel panel;
	//登录窗口的面板

	private JLabel login_Text_Label;
	//登录窗口的标签（用于存储显示"Login Now"）

	private JButton login_Button,close_Button;
	//登录按钮/关闭按钮

	private JTextField username_Textline, password_Textline, port_Textline,address_Textline;

	private Icon img_LoginButton = new ImageIcon("./SrcImg/img_login_button.png");
	private ImageIcon img_Login_Background = new ImageIcon("./SrcImg/img_login_panel.png");

	private ImageIcon img_Login_Photo = new ImageIcon("./SrcImg/img_login_photo.png");
	private JLabel label_Login_Background = new JLabel(img_Login_Background);
	private JLabel label_Login_Photo = new JLabel(img_Login_Photo);

	private String userName="root";
	private  String passWord="PASSWORD";
	private  String port="3306";
	private  String address="127.0.0.1";
	private String URL="jdbc:mysql://localhost:3306/world?useUnicode = true&characterEncoding=utf-8";

	private Connection connDB=null;

	private Database db	=	null;
	//输入窗口
	//---END---


	//---窗口功能元素---
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	//获取屏幕尺寸
	int screenWidth = (int) screensize.getWidth();
	//屏幕宽度

	int screenHeight = (int) screensize.getHeight();
	//屏幕高度

	boolean isDraging;
	//是否拖动(鼠标是否处于按下状态)

	int MouseX;
	int MouseY;
	//鼠标箭头的坐标
	//---END--

	/*
	 *----------------------------------------------------以下为函数实现---------------------------------------------------------
	 */

	//窗口的构造函数，用于初始化对象
	public LoginWindow()
	{
		super("login");
		InitFrame();
	}
	//初始化窗口
	private  void InitFrame()
	{
		//增加一个鼠标点击事件，用于确定拖动状态
		this.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{   //鼠标点击时
				isDraging = true;
				MouseX = e.getX();
				MouseY = e.getY();
				//更新状态为点击，记录初始坐标
			}

			public void mouseReleased(MouseEvent e)
			{   //鼠标松开时
				isDraging = false;
				//更新状态为释放
			}
		});

		//增加一个鼠标移动事件，用于更新窗口坐标
		this.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{	//如果鼠标是点击状态
				if (isDraging)
				{
					int left = getLocation().x;
					int top = getLocation().y;
					//获取窗口坐标
					setLocation(left + e.getX() - MouseX, top + e.getY() - MouseY);
					//计算 窗口坐标+(鼠标坐标-前坐标) <=> (窗口坐标-△x) ，后移动到该位置
				}
			}
		});


		//设置窗口长宽(px)
		this.setSize(Width, Height);
		//禁止窗口缩放
		this.setResizable(false);
		//关闭标题栏
		this.setUndecorated(true);
		//设置窗口初始位置 (为了中置窗口，坐标为 屏幕宽(高)度/2 - 窗口宽(高)度/2 )
		this.setLocation(screenWidth / 2 - Width/2 , screenHeight / 2 - Height/2);




		//获取面板
		panel = (JPanel)this.getContentPane();
		//设置背景透明(为了显示异形窗口)
		this.setBackground(new Color(0,0,0,0));
		//设置布局方式(自由布局)
		panel.setLayout(null);

		//创建标签(登录提示)
		login_Text_Label = new JLabel("Login now");
		login_Text_Label.setBounds(265,90,140,35);
		panel.add(login_Text_Label);

		//创建登录输入栏
		username_Textline = new JTextField("用户名");
		username_Textline.setBounds(265, 160, 220, 30);
		panel.add(username_Textline);

		//端口栏
		port_Textline = new JTextField("端口");
		port_Textline.setBounds(415,210,70,30);
		panel.add(port_Textline);

		//地址输入栏
		address_Textline = new JTextField("地址");
		address_Textline.setBounds(265,210,140,30);
		panel.add(address_Textline);

		//创建密码输入栏
		password_Textline = new JTextField("密码");
		password_Textline.setBounds(265, 260, 220, 30);
		panel.add(password_Textline);

		//创建登录按钮
		login_Button = new JButton(img_LoginButton);
		login_Button.setBorder(null);
		login_Button.setBounds(265, 310, 220, 30);
		//定义按钮事件
		login_Button.addActionListener(new LoginButtonClicked());
		panel.add(login_Button);

		//创建关闭按钮
		close_Button = new JButton();
		close_Button.setBounds(460,10,30,30);
		//定义按钮事件
		close_Button.addActionListener(new CloseButtonClicked());
		panel.add(close_Button);

		//设置窗口图片
		label_Login_Photo.setBounds(0,0,500,500);
		panel.add(label_Login_Photo);

		//设置背景图层
		label_Login_Background.setBounds(0,0,img_Login_Background.getIconWidth(),img_Login_Background.getIconHeight());
		this.getContentPane().add(label_Login_Background);
	}

	public void GetDB(Database _db)
	{
		db=_db;
	}
	public void WindowClose()
	{
		this.dispose();
	}




	//按钮点击后的行动类，通过实现接口来实现
	private class LoginButtonClicked implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				//获取输入的内容
				userName = username_Textline.getText();
				address = address_Textline.getText();
				port = port_Textline.getText();
				passWord = password_Textline.getText();
				//填写url
				URL = "jdbc:mysql://"+address+":"+port+"/world?useUnicode = true&characterEncoding=utf-8";
				//尝试连接数据库
				if(db.ConnectDB(URL,userName,passWord))
				{
					System.out.println("嘎嘎的！");
				}else
				{
					//由于不知道如何通过类来抛出异常，所以这里手动抛出
					throw new RuntimeException();
				}
			}
			catch (Exception Error)
			{
				System.out.println("粗事辣!");
				Error.printStackTrace();
			}

		}
	}
	//点击关闭按钮后的事件(重写接口)
	private class CloseButtonClicked implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//JFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			WindowClose();
			System.out.println("closed");
		}

	}
}
