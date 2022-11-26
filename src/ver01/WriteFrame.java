package ver01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class WriteFrame extends JFrame implements ActionListener{
	Container con = getContentPane();
	
	// JButton
	JButton btnMyDiary = new JButton("My Diary");
	JButton btnSave = new JButton("저장");
	JButton btnExit = new JButton("취소");
	
	// now 현재 설정
	GregorianCalendar now = new GregorianCalendar();
	
	// 일기를 출력하고 입력가능한 JTextArea 생성
	JTextArea txtArea = new JTextArea();
	
	// id 값
	private String id;
	
	public WriteFrame(String id) {
		this.id = id;
		setTitle("글쓰기");
		setSize(500, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setNorth();
		setCenter();
		setSouth();
		setListener();
		setVisible(true);
		
		
	}

	// 리스너
	private void setListener() {
		btnExit.addActionListener(this);
		btnSave.addActionListener(this);
		btnMyDiary.addActionListener(this);
		
	}
	
	// WriteFrame의 North패널
	private void setNorth() {
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.ORANGE);
		JLabel lbl = new JLabel("글쓰기");
		lbl.setFont(new Font("휴면 편지체", Font.BOLD, 30));
		pnl.add(btnMyDiary);
		pnl.add(lbl);
		con.add(pnl, BorderLayout.NORTH);
	}

	
	// Center패널
	private void setCenter() {
		JPanel pnl = new JPanel(new BorderLayout());
		JPanel pnlDate = new JPanel();
		String year = String.valueOf(now.get(Calendar.YEAR));
		String month = String.valueOf(now.get(Calendar.MONTH)+1);
		String date = String.valueOf(now.get(Calendar.DATE));
		JLabel lbl1 = new JLabel(year +"년 ");
		JLabel lbl2 = new JLabel(month +"월 ");
		JLabel lbl3 = new JLabel(date +"일");
		pnlDate.add(lbl1);
		pnlDate.add(lbl2);
		pnlDate.add(lbl3);
		pnl.add(pnlDate, BorderLayout.NORTH);
		
		try {
			String strDate = year +"년 " + month + "월 " + date + "일";
			String fileName = strDate.replaceAll(" ", "");
//			FileReader reader = new FileReader("G:\\workspace\\diary\\"+fileName+".txt");
			FileReader reader = new FileReader("C:\\myDiary\\"+fileName+".txt");
			//new InputStreamReader(new FileInputStream(getName()), "utf-8");
			String str = "";
			while (true) {
				int i = reader.read();
				if (i==-1) {
					break;
				}
				str += String.valueOf((char)i);
			}
			txtArea.setText(str);
			reader.close();
		} catch (Exception e) {
			txtArea.setText("");
		}
		pnl.add(new JScrollPane(txtArea));
		con.add(pnl);
	}

	// WriteFrame의 South패널
	private void setSouth() {
		JPanel pnl = new JPanel();
		pnl.add(btnSave);
		pnl.add(btnExit);
		con.add(pnl, BorderLayout.SOUTH);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == btnExit || obj == btnMyDiary) {
			dispose();
			new MainFrame(id);
		} else if (obj == btnSave) {
			try {
				String year = String.valueOf(now.get(Calendar.YEAR));
				String month = String.valueOf(now.get(Calendar.MONTH)+1);
				String date = String.valueOf(now.get(Calendar.DATE));
				String strDate = year +"년 " + month + "월 " + date + "일";
				String fileName = strDate.replaceAll(" ", "");
//				FileWriter writer = new FileWriter(
//						"G:\\workspace\\diary\\"+fileName+".txt");
				
				// 폴더확인
				File file = new File("C:\\myDiary");
				if (!file.exists()) {
//					System.out.println("폴더없음");
					file.mkdir();
				}
				FileWriter writer = new FileWriter("C:\\myDiary\\"+fileName+".txt");
				BufferedWriter br = new BufferedWriter(writer);
				br.write(txtArea.getText());
				br.close();
				writer.close();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "일기를 저장하는데 오류가 생겼습니다.", "오류발생", JOptionPane.ERROR_MESSAGE);
			}
			
			JOptionPane.showMessageDialog(con, "저장 완료", "저장 완료", JOptionPane.PLAIN_MESSAGE);
			dispose();
			new MainFrame(id);
		} 
		
	}
}
