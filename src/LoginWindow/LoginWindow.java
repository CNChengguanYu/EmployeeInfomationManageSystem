package LoginWindow;
import javax.swing.*;
public class LoginWindow extends JFrame
{
    public LoginWindow(String name)
    {
        super(name);
        this.setSize(600,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel label1 = new JLabel("testlabel1");
        label1.setBounds(10,10,40,20);
        JLabel label2 = new JLabel("testlabel2");
        label2.setBounds(60,10,40,20);
        JButton button = new JButton("testbutton");
        button.setBounds(35,40,30,20);
        panel.add(label1);
        panel.add(label2);
        panel.add(button);
        this.add(panel);
    }
}
