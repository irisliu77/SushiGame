package sushigame.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PlateViewWidget extends JPanel implements ActionListener{

	private JLabel color;
	private JLabel type;
	private JLabel chef;
	private JLabel age;
	private JButton info;
	private JFrame frame;
	
	public PlateViewWidget(){
		
		setLayout(new BorderLayout());
		
		//创建一个info button，点击将弹出pop up window 显示具体信息
		info = new JButton("info");
		info.setActionCommand("get the info");
		info.addActionListener(this);
		add(info);
		
		//创建一个框架，并给他一个panel，用于显示信息
		frame = new JFrame();
		JPanel plateview = new JPanel();
		plateview.setLayout(new BorderLayout());
		frame.add(plateview);
		
		color = new JLabel("");
		plateview.add(color);
		type = new JLabel("");
		plateview.add(type);
		chef = new JLabel("");
		plateview.add(chef);
		age = new JLabel("");
		plateview.add(age);
		
	}
	
	public JLabel getColorL() {
		return color;
	}
	
	public JLabel getTypeL() {
		return type;
	}
	
	public JLabel getChefL() {
		return chef;
	}
	
	public JLabel getAgeL() {
		return age;
	}
	
	public JButton getInfo() {
		return info;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "get the info") {
			JOptionPane.showMessageDialog(frame, "Color: " + color.getText() + "\n" + "Type: " + type.getText()
			+"\n" + "Chef: " + chef.getText() + "\n"+ "Age: " + age.getText());
		}
		
	}

}
