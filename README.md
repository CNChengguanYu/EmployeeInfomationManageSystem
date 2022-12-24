> 本项目已经开源至GitHub
> >构建于
> >JDK-17 
> >
> >Mysql 8.0.31
> >
> >JDBC 8.0.31
> >Java Swing
> >
> >环境:
> >
> >> Windows 10 
> >
> >**大二上Java作业**          

该程序使用Java编写，用于对数据库实现增删改查的图形化操作。

# 使用方法

`在启动程序之前，请自行安装配置Mysql，本程序基于Mysql8.0.31开发，其他版本未测试过`

找到程序的Jar包，双击运行

<img src=".\DocImg\SoftWave.png" alt="SoftWave" style="zoom:50%;" />

如果运行成功，将进入数据库登录界面

<img src=".\DocImg\Login_default.png" alt="Login_default" style="zoom:30%;" />



<img src=".\DocImg\Login_design.png" alt="Login_design" style="zoom:50%;" />

​											*此为登录窗口的设计图，由于时间问题未能实现*

在输入栏输入正确的用户名、数据库地址、端口、密码等，如果输入错误导致不能链接数据库，则会报错：									<img src=".\DocImg\Login_Err.png" alt="Login_default" style="zoom: 33%;" />

如果是第一次运行该系统，则会自动创建数据库`project_database`，并且创建表`worktable`

如果数据库已经存在，则会尝试链接到数据库，并且查询是否存在表`worktable`，如果不存在，则创建，存在则不做任何操作![Table_Struct](.\DocImg\Table_Struct.png)

​																		*表的结构*

登录、初始化成功后，将会进入系统界面。<img src=".\DocImg\Sys_default.png" alt="Sys_default" style="zoom: 33%;" />

## 添加员工

如果表中已有数据，则会显示在左侧表格中

我们可以尝试添加员工。点击`添加员工`按钮

<img src=".\DocImg\Sys_ADD_NULL.png" alt="Sys_ADD_NULL" style="zoom:33%;" />

可以通过该窗口输入数据，输入完毕后该数据会被添加到数据库

在将数据添加到数据库之前，会进行数据合法性的判断，防止程序出错，如：工资栏填的不是数字，系统会提示错误<img src=".\DocImg\Sys_ADD_ERR1.png" alt="Sys_ADD_ERR1" style="zoom:33%;" />

如果员工编号重复，系统也会提示错误<img src=".\DocImg\Sys_ADD_Err2.png" alt="Sys_ADD_Err2" style="zoom:33%;" />

其他如姓名长度、工作名称长度等问题不予演示

如果数据合法，则会将添加数据到数据库，并且刷新表

<img src=".\DocImg\Sys_ADD_OK.png" alt="Sys_ADD_OK" style="zoom:33%;" />

## 删除员工

我们可以尝试删除员工。我们需要先`选择一行`,然后点击`删除员工`按钮

如果您没有选择行，则会报错

<img src=".\DocImg\Sys_NoselectERR.png" alt="Sys_NoselectERR" style="zoom:33%;" />

选中后，弹出新窗口确认信息

<img src=".\DocImg\Sys_DEL_.png" alt="Sys_DEL_" style="zoom:33%;" />

点击确定后删除

<img src=".\DocImg\Sys_DEL_OK.png" alt="Sys_DEL_OK" style="zoom:33%;" />



## 修改数据

我们可以尝试删除员工。我们需要先`选择一行`,然后点击`更新数据`按钮

如果我们没有选择行，同样会报错

<img src=".\DocImg\SYS_UPD_.png" alt="SYS_UPD_" style="zoom:33%;" />

为了防止误操作，该功能不能修改ID。我们尝试将评级改为D，

<img src=".\DocImg\Sys_UPD_ok.png" alt="Sys_UPD_ok" style="zoom:33%;" />

修改成功

## 排序数据

我们可以根据一些字段，升序\降序排序数据

<img src=".\DocImg\Sys_ord_cbox_word.png" alt="Sys_ord_cbox_word" style="zoom:33%;" />

<img src=".\DocImg\Sys_ORD_E_UP.png" alt="Sys_ORD_E_UP" style="zoom:33%;" />

评级降序排名

<img src=".\DocImg\Sys_ORD_S_D.png" alt="Sys_ORD_S_D" style="zoom:33%;" />

工资升序排名



----



功能介绍完毕,由于时间紧迫，个人能力、经验不足，该程序功能简陋、UI混乱、效率低、容错性差。仅供交作业使用。
