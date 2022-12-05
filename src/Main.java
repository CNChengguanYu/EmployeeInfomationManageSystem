import javax.swing.*;
public class Main
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("测试窗口名");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.add(panel);
        panel.setLayout(null);
        int x=100 , y =100;
        for(short i =0;i<9;++i)
        {
            JButton TempButton =new JButton(String.valueOf(i));
            TempButton.setBounds(i*x,y,80,60);
            if(i%3==0){y+=100;}
            System.out.println("x:y"+i*x+y);
            panel.add(TempButton);
        }
//        JButton button = new JButton("123");
//        button.setBounds(10,80,100,100);
//        panel.add(button);
        frame.setVisible(true);
    }
}