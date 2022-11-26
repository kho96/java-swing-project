package ver01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PictureDiaryMain extends JFrame implements ActionListener {
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
//	JTextArea txtArea = new JTextArea();
	
	// 임시 컴포넌트 설정 -> 텍스트에어리어 (변경시에 페인트 컴포넌트로 변경예정)
	JPanel paintPnl = new JPanel(new BorderLayout());
	MyLabel paintLabel = new MyLabel();

	// 날짜 입력 다이얼로그 생성
	CalDialog dialog = new CalDialog(PictureDiaryMain.this, "날짜선택", true);
	
	// dao
	PaintDao dao = PaintDao.getInstance();
	
	// 그림을 저장할 벡터 선언
	Vector<PaintVo> vec = new Vector<>();
	
	// id 값
	private String id;
	
	public PictureDiaryMain(String id) {
		this.id = id;
		System.out.println(id);
		setTitle("그림일기 메인화면");
		setSize(500, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setNorth();
		setCenter();
		setListener();
		setVisible(true);
	}
	
	// PictureDiaryMain의 North패널 
	private void setNorth() {
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.ORANGE);
		JLabel lbl = new JLabel("My PictureDiary");
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
		year = String.valueOf(now.get(Calendar.YEAR));  
		month = String.valueOf(now.get(Calendar.MONTH)+1);
		date = String.valueOf(now.get(Calendar.DATE));  
		str = year +"년 " + month + "월 " + date + "일";
		String dateYY = String.valueOf(now.get(Calendar.YEAR) - 2000);
		String dateMM;
		if ((now.get(Calendar.MONTH)+1) < 10) {
			dateMM = "0" + month;
		} else {
			dateMM = month;
		}
		String dateDD;
		if (now.get(Calendar.DATE) < 10) {
			dateDD = "0" + date;
		} else {
			dateDD = date;
		}
		String dateYYMMDD = dateYY+"/"+dateMM+"/"+dateDD;
		pnlDate.add(btnL);
		lblDate.setText(str);
		pnlDate.add(lblDate);
		pnlDate.add(btnR);
//		showDiary(str);
		pnlB.add(pnlDate, BorderLayout.NORTH);
//		pnlB.add(new JScrollPane(txtArea));
//		txtArea.setEditable(false);
		paintPnl.setBackground(Color.WHITE);
		paintPnl.add(paintLabel);
		pnlB.add(paintPnl);
		con.add(pnlB);
		vec = dao.sendData(id, dateYYMMDD);
		System.out.println(dateYYMMDD);
	}
	
	
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
				
				int intYear = Integer.parseInt(year);
				int intMonth = Integer.parseInt(month)-1;
				int intDate = Integer.parseInt(date);
				now.set(intYear, intMonth, intDate);
				
				String dateYY = String.valueOf(now.get(Calendar.YEAR) - 2000);
				String dateMM;
				if ((now.get(Calendar.MONTH)+1) < 10) {
					dateMM = "0" + month;
				} else {
					dateMM = month;
				}
				String dateDD;
				if (now.get(Calendar.DATE) < 10) {
					dateDD = "0" + date;
				} else {
					dateDD = date;
				}
				String dateYYMMDD = dateYY+"/"+dateMM+"/"+dateDD;
				
				vec = dao.sendData(id, dateYYMMDD);
				repaint();
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

	
	// 페인트 컴포넌트를 위한 JLabel class 생성
	class MyLabel extends JLabel {
		
		public MyLabel() {}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// 그림일기가 존재하지 않는 경우
			if (vec.size() == 0) {
				g.setFont(new Font("맑은 고딕", Font.BOLD, 20));
				g.drawString("저장된 그림일기가 없습니다.", 20, 20);
				return;
			}
			// 좌표값 선언
			int startX, startY, movedX, movedY;
			String userShape, u_id;
			for (PaintVo v: vec) {
				String strColor = v.getUserColor();
				String str1 = strColor.substring(15, strColor.length()-1);
				int count1 = str1.indexOf(",");
				int count2 = str1.lastIndexOf(",");
				String strR = str1.substring(2, count1);
				String strG = str1.substring(count1+3, count2);
				String strB = str1.substring(count2+3);
				int colorR = Integer.parseInt(strR);
				int colorG = Integer.parseInt(strG);
				int colorB = Integer.parseInt(strB);
				Color userColor1 = new Color(colorR, colorG, colorB);
				g.setColor(userColor1);
				userShape = v.getUserShape();
				startX = v.getStartX();
				startY = v.getStartY();
				movedX = v.getMovedX();
				movedY = v.getMovedY();
				switch(userShape) {
				case "직선" :
					g.drawLine(v.getStartX(), v.getStartY(), v.getMovedX(), v.getMovedY());
					break;
				case "곡선" :                                                                                  
					g.fillOval(v.getStartX(), v.getStartY(), 3, 3);                                                
					break;                                                                                   
				case "사각형" :                                                                                 
					if (v.getMovedX() > v.getStartX() && v.getMovedY() < v.getStartY()) {                                
						g.drawRect(v.getStartX(), v.getMovedY(), v.getMovedX() - v.getStartX(), v.getStartY() - v.getMovedY());
					} else if (v.getMovedX() >= v.getStartX() && v.getMovedY() >= v.getStartY()) {                       
						g.drawRect(v.getStartX(), v.getStartY(), v.getMovedX()-v.getStartX(), v.getMovedY()-v.getStartY());    
					} else if (v.getMovedX() < v.getStartX() && v.getMovedY() >= v.getStartY()) {                        
						g.drawRect(v.getMovedX(), v.getStartY(), v.getStartX()-v.getMovedX(), v.getMovedY()-v.getStartY());    
					} else if (v.getMovedX() < v.getStartX() && v.getMovedY() < v.getStartY()) {                         
						g.drawRect(v.getMovedX(), v.getMovedY(), v.getStartX() - v.getMovedX(), v.getStartY() - v.getMovedY());
					}                                                                                        
					break;                                                                                   
				case "사각형(색)" :                                                                              
					if (v.getMovedX() > v.getStartX() && v.getMovedY() < v.getStartY()) {                                
						g.fillRect(v.getStartX(), v.getMovedY(), v.getMovedX() - v.getStartX(), v.getStartY() - v.getMovedY());
					} else if (v.getMovedX() >= v.getStartX() && v.getMovedY() >= v.getStartY()) {                       
						g.fillRect(v.getStartX(), v.getStartY(), v.getMovedX()-v.getStartX(), v.getMovedY()-v.getStartY());    
					} else if (v.getMovedX() < v.getStartX() && v.getMovedY() >= v.getStartY()) {                        
						g.fillRect(v.getMovedX(), v.getStartY(), v.getStartX()-v.getMovedX(), v.getMovedY()-v.getStartY());    
					} else if (v.getMovedX() < v.getStartX() && v.getMovedY() < v.getStartY()) {                         
						g.fillRect(v.getMovedX(), v.getMovedY(), v.getStartX() - v.getMovedX(), v.getStartY() - v.getMovedY());
					}                                                                                        
					break;                                                                                   
				case "원" :                                                                                   
					if (v.getMovedX() > v.getStartX() && v.getMovedY() < v.getStartY()) {                                
						g.drawOval(v.getStartX(), v.getMovedY(), v.getMovedX() - v.getStartX(), v.getStartY() - v.getMovedY());
					} else if (v.getMovedX() >= v.getStartX() && v.getMovedY() >= v.getStartY()) {                       
						g.drawOval(v.getStartX(), v.getStartY(), v.getMovedX()-v.getStartX(), v.getMovedY()-v.getStartY());    
					} else if (v.getMovedX() < v.getStartX() && v.getMovedY() >= v.getStartY()) {                        
						g.drawOval(v.getMovedX(), v.getStartY(), v.getStartX()-v.getMovedX(), v.getMovedY()-v.getStartY());    
					} else if (v.getMovedX() < v.getStartX() && v.getMovedY() < v.getStartY()) {                         
						g.drawOval(v.getMovedX(), v.getMovedY(), v.getStartX() - v.getMovedX(), v.getStartY() - v.getMovedY());
					}                                                                                        
					break;                                                                                   
				case "원(색)" :                                                                                
					if (v.getMovedX() > v.getStartX() && v.getMovedY() < v.getStartY()) {                                
						g.fillOval(v.getStartX(), v.getMovedY(), v.getMovedX() - v.getStartX(), v.getStartY() - v.getMovedY());
					} else if (v.getMovedX() >= v.getStartX() && v.getMovedY() >= v.getStartY()) {                       
						g.fillOval(v.getStartX(), v.getStartY(), v.getMovedX()-v.getStartX(), v.getMovedY()-v.getStartY());    
					} else if (v.getMovedX() < v.getStartX() && v.getMovedY() >= v.getStartY()) {                        
						g.fillOval(v.getMovedX(), v.getStartY(), v.getStartX()-v.getMovedX(), v.getMovedY()-v.getStartY());    
					} else if (v.getMovedX() < v.getStartX() && v.getMovedY() < v.getStartY()) {                         
						g.fillOval(v.getMovedX(), v.getMovedY(), v.getStartX() - v.getMovedX(), v.getStartY() - v.getMovedY());
					}                                                                                        
					break;               
				}//switch
				
			}//for
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == btnWrite) {
			dispose();
			new PictureDiaryWriteFrame(id);
		} else if (obj == btnMenu) {
			dispose();
			new MenuFrame(id);
		} else if (obj == btnL) {
			int intYear = Integer.parseInt(year);
			int intMonth = Integer.parseInt(month)-1;
			int intDate = Integer.parseInt(date);
			now.set(intYear, intMonth, intDate);
			now.add(Calendar.DATE, -1);
			year = String.valueOf(now.get(Calendar.YEAR));     
			month = String.valueOf(now.get(Calendar.MONTH)+1); 
			date = String.valueOf(now.get(Calendar.DATE));
			str = year +"년 " + month + "월 " + date + "일";      
			lblDate.setText(str); 
			String dateYY = String.valueOf(now.get(Calendar.YEAR) - 2000);
			String dateMM;
			if ((now.get(Calendar.MONTH)+1) < 10) {
				dateMM = "0" + month;
			} else {
				dateMM = month;
			}
			String dateDD;
			if (now.get(Calendar.DATE) < 10) {
				dateDD = "0" + date;
			} else {
				dateDD = date;
			}
			String dateYYMMDD = dateYY+"/"+dateMM+"/"+dateDD;
			vec = dao.sendData(id, dateYYMMDD);
			repaint();
		} else if (obj == btnR) {
			int intYear = Integer.parseInt(year);
			int intMonth = Integer.parseInt(month)-1;
			int intDate = Integer.parseInt(date);
			now.set(intYear, intMonth, intDate);
			now.add(Calendar.DATE, +1);
			year = String.valueOf(now.get(Calendar.YEAR));     
			month = String.valueOf(now.get(Calendar.MONTH)+1); 
			date = String.valueOf(now.get(Calendar.DATE));     
			str = year +"년 " + month + "월 " + date + "일";      
			lblDate.setText(str);
			String dateYY = String.valueOf(now.get(Calendar.YEAR) - 2000);
			String dateMM;
			if ((now.get(Calendar.MONTH)+1) < 10) {
				dateMM = "0" + month;
			} else {
				dateMM = month;
			}
			String dateDD;
			if (now.get(Calendar.DATE) < 10) {
				dateDD = "0" + date;
			} else {
				dateDD = date;
			}
			String dateYYMMDD = dateYY+"/"+dateMM+"/"+dateDD;
			vec = dao.sendData(id, dateYYMMDD);
			repaint();
		} // if ~ else if
		
	}// actionPerformed

}
