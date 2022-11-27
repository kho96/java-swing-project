package ver01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.sql.RowSetListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener{
	Container con = getContentPane();
	
	// JButton
	JButton btnMenu = new JButton("MENU");
	JButton btnWrite = new JButton("글쓰기");
	JButton btnL = new JButton("←");
	JButton btnR = new JButton("→");
	
	// now 현재 설정
	GregorianCalendar now = new GregorianCalendar();
	
	// 연도, 달, 일 그리고 연도+달+일 을 담을 변수 선언
	String year, month, date, str; 
	
	// 보여주고 있는 날을 표시할 JLabel, 일기를 보여줄 JTextArea 생성
	JLabel lblDate = new JLabel();
	JTextArea txtArea = new JTextArea();
	
	// 날짜 입력 다이얼로그 생성
	CalDialog dialog = new CalDialog(MainFrame.this, "날짜선택", true);
	
	// id
	private String id;
	
	public MainFrame(String id) {
		this.id = id;
		setTitle("메인화면");
		setSize(500, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setNorth();
		setCenter();
		setListener();
		setVisible(true);
	}	
	
	// 리스너
	private void setListener() {
		// 버튼 리스너 추가
		btnWrite.addActionListener(this);
		btnMenu.addActionListener(this);
		btnL.addActionListener(this);
		btnR.addActionListener(this);
		
		// JLabel에 리스너 추가
		lblDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dialog.setVisible(true);
				year = dialog.getYear();
				month = dialog.getMonth();
				date = dialog.getDate();
				str = year +"년 " + month + "월 " + date + "일"; 
				lblDate.setText(str);
				showDiary(str);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDate.setForeground(Color.BLUE);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				lblDate.setForeground(Color.BLACK);
			}
			
		});
	}

	// MainFrame의 North패널 
	private void setNorth() {
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.ORANGE);
		JLabel lbl = new JLabel("My Diary");
		lbl.setFont(new Font("휴면 편지체", Font.BOLD, 30));
		pnl.add(btnMenu);
		pnl.add(lbl);
		pnl.add(btnWrite);
		con.add(pnl, BorderLayout.NORTH);
	}
	
	
	// Center패널
	private void setCenter() {
		JPanel pnlB = new JPanel(new BorderLayout());
		JPanel pnlDate = new JPanel();
		pnlDate.add(btnL);
		pnlDate.add(lblDate);
		pnlDate.add(btnR);
		setCalendar(now);
		pnlB.add(pnlDate, BorderLayout.NORTH);
		pnlB.add(new JScrollPane(txtArea));
		txtArea.setEditable(false);
		con.add(pnlB);
	}
	
	
	// 보여지는 날짜 세팅
	private void setCalendar(GregorianCalendar now) {
		year = String.valueOf(now.get(Calendar.YEAR));  
		month = String.valueOf(now.get(Calendar.MONTH)+1);
		date = String.valueOf(now.get(Calendar.DATE));  
		str = year +"년 " + month + "월 " + date + "일";   
		lblDate.setText(str);
		showDiary(str);
	}

	// 날짜 이동
	private GregorianCalendar changeNow() {
		int intYear = Integer.parseInt(year);
		int intMonth = Integer.parseInt(month)-1;
		int intDate = Integer.parseInt(date);
		now.set(intYear, intMonth, intDate);
		return now;
	}
	
	// 일기 보여주기
	private void showDiary(String str) {
		String fileName = str.replaceAll(" ", ""); // fileName으로 정의될 문자열에 모든 공백 제거                                        
		try {                                
			FileReader reader = new FileReader("C:\\myDiary\\"+fileName+".txt");             
			String str1 = "";                                                                 
			while (true) {                                                                   
				int i = reader.read();                                                       
				if (i==-1) {                                                                 
					break;                                                                   
				}                                                                            
				str1 += String.valueOf((char)i);                                              
			}                                                                                
			txtArea.setText(str1);                                                            
			reader.close();                                                                  
		} catch (Exception e1) {                                                             
			txtArea.setText("일기가 존재하지 않습니다."); // 읽어올 파일이 없는 경우                                             
		}       
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == btnWrite) { // 글쓰기
			dispose();
			new WriteFrame(id);
		} else if (obj == btnMenu) { // 메뉴
			dispose();
			new MenuFrame(id);
		} else if (obj == btnL) { // 왼쪽
			now = changeNow();
			now.add(Calendar.DATE, -1);
			setCalendar(now);
		} else if (obj == btnR) { // 오른쪽
			now = changeNow();
			now.add(Calendar.DATE, +1);
			setCalendar(now);
		} // if ~ else if
		
	}
}
