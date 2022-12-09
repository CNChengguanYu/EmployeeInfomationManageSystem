package LoginWindow;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
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

	private JTextField account_Textline, password_Textline, port_Textline;
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
		//调用父类方法，构造窗口名
		super("Login");



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


		//创建面板
		panel = new JPanel();
		panel.setBackground(Color.ORANGE);  //测试用
		//设置布局方式(自由布局)
		panel.setLayout(null);
		//把面板添加到窗口
		this.add(panel);

		//创建标签(登录提示)
        login_Text_Label = new JLabel("Login now");
        login_Text_Label.setBounds(250,250,200,30);
        panel.add(login_Text_Label);

		//创建登录输入栏
		account_Textline = new JTextField("用户名");
		account_Textline.setBounds(275, 225, 200, 30);
		panel.add(account_Textline);

		//创建密码输入栏
		password_Textline = new JTextField("密码");
		password_Textline.setBounds(275, 275, 200, 30);
		panel.add(password_Textline);

		//创建登录按钮
		login_Button = new JButton("登录");
		login_Button.setBounds(275, 315, 200, 30);
			//定义按钮事件
			login_Button.addActionListener(new LoginButtonClicked());
		panel.add(login_Button);

		//创建关闭按钮
		close_Button = new JButton();
		close_Button.setBounds(480,5,15,15);
			//定义按钮事件
			close_Button.addActionListener(new CloseButtonClicked());
		panel.add(close_Button);

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
			System.out.println("clicked");
		}
	}

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
