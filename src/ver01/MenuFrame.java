package ver01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuFrame extends JFrame implements ActionListener {
	Container con = getContentPane();
	JButton btnMyDiary = new JButton("My Diary");
	JButton btnPaintDiary = new JButton("그림일기(준비중)");
	JButton btnFriendDiary = new JButton("교환일기(준비중)");
	
	// id값
	private String id;
	
	public MenuFrame(String id) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.id = id;
		setTitle("메뉴");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setNorth();
		setCenter();
		setListener(); 
		setVisible(true);
	}

	// North 패널
	private void setNorth() {
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.ORANGE);
		JLabel lbl = new JLabel("Menu");
		lbl.setFont(new Font("휴면 편지체", Font.BOLD, 30));
		pnl.add(lbl);
		con.add(pnl, BorderLayout.NORTH);
		
	}
	
	// Center 패널
	private void setCenter() {
		JPanel pnl = new JPanel(new GridLayout(3, 1));
		pnl.add(btnMyDiary);
		pnl.add(btnPaintDiary);
		pnl.add(btnFriendDiary);
		btnFriendDiary.setEnabled(false);
		con.add(pnl);
	}
	
	// 리스너 추가
	private void setListener() {
		btnMyDiary.addActionListener(this);
		btnPaintDiary.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == btnMyDiary) {
			dispose();
			new MainFrame(id);
		} else if (obj == btnPaintDiary) {
			dispose();
			System.out.println(id);
			new PictureDiaryMain(id);
		}
		
	}
}
