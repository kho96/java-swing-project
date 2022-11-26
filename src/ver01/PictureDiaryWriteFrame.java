package ver01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PictureDiaryWriteFrame extends JFrame implements ActionListener {
	Container con = getContentPane();
	
	// JButton
	JButton btnMyDiary = new JButton("My Diary");
	JButton btnSave = new JButton("저장");
	JButton btnExit = new JButton("취소");
		
	// now 현재 설정
	GregorianCalendar now = new GregorianCalendar();
	
	// 임시 컴포넌트 설정 -> 텍스트에어리어 (변경시에 페인트 컴포넌트로 변경예정)
	JPanel paintPnl = new JPanel(new BorderLayout());
	MyLabel paintLabel = new MyLabel();
	
	// 좌표값 선언
	int startX, startY, movedX, movedY;
	
	// 콤보박스 생성
	String[] strsShape = {"직선", "곡선", "사각형", "사각형(색)", "원", "원(색)"};
	JComboBox<String> comboShape = new JComboBox<>(strsShape);
	
//	Color[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.MAGENTA,
//							Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW};
//	JComboBox<Color> comboColor = new JComboBox<>(colors);
	
	// 색상 선택
	JButton btnColor = new JButton("색상");
	JColorChooser colorChooser = new JColorChooser();
	JButton btnChoose = new JButton("선택");
	
	// 콤보박스 선택값 변수 선언, 할당
	String userShape = "직선";
	Color userColor = Color.BLACK;
	
	// 그림을 저장할 벡터 선언
	Vector<PaintVo> vecPic = new Vector<>();
	
	// dao 생성
	PaintDao dao = PaintDao.getInstance();
	
	// 저장된 데이터를 가져올 벡터 생성
	Vector<PaintVo> vecFirst = new Vector<>();
	
	// id 
	private String id;
	
	// 날짜 값
	private String year, month, date;
	
	public PictureDiaryWriteFrame(String id) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
	// PictureDiaryWriteFrame의 North패널
	private void setNorth() {
		JPanel pnlNorth = new JPanel(new BorderLayout());
		
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.ORANGE);
		JLabel lbl = new JLabel("그림일기 쓰기");
		lbl.setFont(new Font("휴면 편지체", Font.BOLD, 30));
		pnl.add(btnMyDiary);
		pnl.add(lbl);
		pnlNorth.add(pnl);
		
		JPanel pnlDate = new JPanel();
		year = String.valueOf(now.get(Calendar.YEAR));
		month = String.valueOf(now.get(Calendar.MONTH)+1);
		date = String.valueOf(now.get(Calendar.DATE));
		JLabel lbl1 = new JLabel(year +"년 ");
		JLabel lbl2 = new JLabel(month +"월 ");
		JLabel lbl3 = new JLabel(date +"일");
		pnlDate.add(lbl1);
		pnlDate.add(lbl2);
		pnlDate.add(lbl3);
		pnlDate.add(comboShape);
		pnlDate.add(btnColor);
		pnlNorth.add(pnlDate, BorderLayout.SOUTH);
		
		con.add(pnlNorth, BorderLayout.NORTH);
	}

	// Center패널
	private void setCenter() {
		
		paintPnl.setBackground(Color.WHITE);
		
		paintPnl.add(paintLabel);
		con.add(paintPnl);	
	}

	// PictureDiaryWriteFrame의 South패널
	private void setSouth() {
		JPanel pnl = new JPanel();
		pnl.add(btnSave);
		pnl.add(btnExit);
		con.add(pnl, BorderLayout.SOUTH);
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
		vecFirst = dao.sendData(id, dateYYMMDD);
	}

	// 리스너
	private void setListener() {
		btnExit.addActionListener(this);
		btnSave.addActionListener(this);
		btnMyDiary.addActionListener(this);
		btnColor.addActionListener(this);
		
		
		paintLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				startX = e.getX();
				startY = e.getY();
				System.out.println("클릭");
				if(userShape.equals("곡선")) {
					PaintVo myPic = new PaintVo(id, userShape, String.valueOf(userColor), startX, startY, movedX, movedY);
					vecPic.add(myPic);
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("뗌");
				PaintVo myPic = new PaintVo(id, userShape, String.valueOf(userColor), startX, startY, movedX, movedY);
				vecPic.add(myPic);
				// 정보 초기화, 계속 repaint되는 버그 수정
				startX = 0;
				startY = 0;
				movedX = 0;
				movedY = 0;
			}

		});
		
		paintLabel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				movedX = e.getX();
				movedY = e.getY();
				if(userShape.equals("곡선")) {
					startX = e.getX();
					startY = e.getY();
					PaintVo myPic = new PaintVo(id, userShape, String.valueOf(userColor), startX, startY, movedX, movedY);
					vecPic.add(myPic);
				}
				repaint();
			}
		});
		
		comboShape.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				if (state == ItemEvent.SELECTED) {
					Object obj = e.getItem();
					userShape = (String)obj;
					return;
//					System.out.println(userShape);
				}
				
			}
		});
//		comboColor.addItemListener(new ItemListener() {
//			
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				int state = e.getStateChange();
//				if (state == ItemEvent.SELECTED) {
//					Object obj = e.getItem();
//					userColor = (Color)obj;
//					return;
////					System.out.println(userColor);
//				}
//				
//			}
//		});
	}
	
	// 페인트 컴포넌트를 위한 JLabel class 생성
	class MyLabel extends JLabel {
		BufferedImage bi;
		public MyLabel() {
			
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (vecFirst != null) {
				for (PaintVo v: vecFirst) {
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
					String firstShape = v.getUserShape();
					System.out.println(firstShape);
					switch(firstShape) {
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
			}//if
			// 벡터에 저장된 그림 출력
			for (PaintVo pic : vecPic) {
				String strColor = pic.getUserColor();
				System.out.println(strColor);//strColor
				String str1 = strColor.substring(15, strColor.length()-1);
				System.out.println(str1);// rgb
				int count1 = str1.indexOf(",");
				int count2 = str1.lastIndexOf(",");
				System.out.println(count1);//첫번쨰, 인덱스
				System.out.println(count2);// 두번쨰, 인덱스
				String strR = str1.substring(2, count1);
				String strG = str1.substring(count1+3, count2);
				String strB = str1.substring(count2+3);
				System.out.println(strR);// r 값(string)
				System.out.println(strG);// g 값(string)
				System.out.println(strB);// b 값(string)
				int colorR = Integer.parseInt(strR);
				int colorG = Integer.parseInt(strG);
				int colorB = Integer.parseInt(strB);
				Color userColor1 = new Color(colorR, colorG, colorB);
				g.setColor(userColor1);
				switch(pic.getUserShape()) {
				case "직선" :
					g.drawLine(pic.getStartX(), pic.getStartY(), pic.getMovedX(), pic.getMovedY());
					break;
				case "곡선" :                                                                                  
					g.fillOval(pic.getStartX(), pic.getStartY(), 3, 3);                                                
					break;                                                                                   
				case "사각형" :                                                                                 
					if (pic.getMovedX() > pic.getStartX() && pic.getMovedY() < pic.getStartY()) {                                
						g.drawRect(pic.getStartX(), pic.getMovedY(), pic.getMovedX() - pic.getStartX(), pic.getStartY() - pic.getMovedY());
					} else if (pic.getMovedX() >= pic.getStartX() && pic.getMovedY() >= pic.getStartY()) {                       
						g.drawRect(pic.getStartX(), pic.getStartY(), pic.getMovedX()-pic.getStartX(), pic.getMovedY()-pic.getStartY());    
					} else if (pic.getMovedX() < pic.getStartX() && pic.getMovedY() >= pic.getStartY()) {                        
						g.drawRect(pic.getMovedX(), pic.getStartY(), pic.getStartX()-pic.getMovedX(), pic.getMovedY()-pic.getStartY());    
					} else if (pic.getMovedX() < pic.getStartX() && pic.getMovedY() < pic.getStartY()) {                         
						g.drawRect(pic.getMovedX(), pic.getMovedY(), pic.getStartX() - pic.getMovedX(), pic.getStartY() - pic.getMovedY());
					}                                                                                        
					break;                                                                                   
				case "사각형(색)" :                                                                              
					if (pic.getMovedX() > pic.getStartX() && pic.getMovedY() < pic.getStartY()) {                                
						g.fillRect(pic.getStartX(), pic.getMovedY(), pic.getMovedX() - pic.getStartX(), pic.getStartY() - pic.getMovedY());
					} else if (pic.getMovedX() >= pic.getStartX() && pic.getMovedY() >= pic.getStartY()) {                       
						g.fillRect(pic.getStartX(), pic.getStartY(), pic.getMovedX()-pic.getStartX(), pic.getMovedY()-pic.getStartY());    
					} else if (pic.getMovedX() < pic.getStartX() && pic.getMovedY() >= pic.getStartY()) {                        
						g.fillRect(pic.getMovedX(), pic.getStartY(), pic.getStartX()-pic.getMovedX(), pic.getMovedY()-pic.getStartY());    
					} else if (pic.getMovedX() < pic.getStartX() && pic.getMovedY() < pic.getStartY()) {                         
						g.fillRect(pic.getMovedX(), pic.getMovedY(), pic.getStartX() - pic.getMovedX(), pic.getStartY() - pic.getMovedY());
					}                                                                                        
					break;                                                                                   
				case "원" :                                                                                   
					if (pic.getMovedX() > pic.getStartX() && pic.getMovedY() < pic.getStartY()) {                                
						g.drawOval(pic.getStartX(), pic.getMovedY(), pic.getMovedX() - pic.getStartX(), pic.getStartY() - pic.getMovedY());
					} else if (pic.getMovedX() >= pic.getStartX() && pic.getMovedY() >= pic.getStartY()) {                       
						g.drawOval(pic.getStartX(), pic.getStartY(), pic.getMovedX()-pic.getStartX(), pic.getMovedY()-pic.getStartY());    
					} else if (pic.getMovedX() < pic.getStartX() && pic.getMovedY() >= pic.getStartY()) {                        
						g.drawOval(pic.getMovedX(), pic.getStartY(), pic.getStartX()-pic.getMovedX(), pic.getMovedY()-pic.getStartY());    
					} else if (pic.getMovedX() < pic.getStartX() && pic.getMovedY() < pic.getStartY()) {                         
						g.drawOval(pic.getMovedX(), pic.getMovedY(), pic.getStartX() - pic.getMovedX(), pic.getStartY() - pic.getMovedY());
					}                                                                                        
					break;                                                                                   
				case "원(색)" :                                                                                
					if (pic.getMovedX() > pic.getStartX() && pic.getMovedY() < pic.getStartY()) {                                
						g.fillOval(pic.getStartX(), pic.getMovedY(), pic.getMovedX() - pic.getStartX(), pic.getStartY() - pic.getMovedY());
					} else if (pic.getMovedX() >= pic.getStartX() && pic.getMovedY() >= pic.getStartY()) {                       
						g.fillOval(pic.getStartX(), pic.getStartY(), pic.getMovedX()-pic.getStartX(), pic.getMovedY()-pic.getStartY());    
					} else if (pic.getMovedX() < pic.getStartX() && pic.getMovedY() >= pic.getStartY()) {                        
						g.fillOval(pic.getMovedX(), pic.getStartY(), pic.getStartX()-pic.getMovedX(), pic.getMovedY()-pic.getStartY());    
					} else if (pic.getMovedX() < pic.getStartX() && pic.getMovedY() < pic.getStartY()) {                         
						g.fillOval(pic.getMovedX(), pic.getMovedY(), pic.getStartX() - pic.getMovedX(), pic.getStartY() - pic.getMovedY());
					}                                                                                        
					break;                                                                                   
				}
//				g.setColor(pic.userColor);
//				System.out.println(userColor);
//				System.out.println(userColor.toString());
//				switch (pic.userShape) {
//					case "직선" :
//						g.drawLine(pic.startX, pic.startY, pic.movedX, pic.movedY);
//						break;
//					case "곡선" :
//						g.fillOval(pic.startX, pic.startY, 3, 3);
//						break;
//					case "사각형" :
//						if (pic.movedX > pic.startX && pic.movedY < pic.startY) {                        
//							g.drawRect(pic.startX, pic.movedY, pic.movedX - pic.startX, pic.startY - pic.movedY);
//						} else if (pic.movedX >= pic.startX && pic.movedY >= pic.startY) {               
//							g.drawRect(pic.startX, pic.startY, pic.movedX-pic.startX, pic.movedY-pic.startY);    
//						} else if (pic.movedX < pic.startX && pic.movedY >= pic.startY) {                
//							g.drawRect(pic.movedX, pic.startY, pic.startX-pic.movedX, pic.movedY-pic.startY);    
//						} else if (pic.movedX < pic.startX && pic.movedY < pic.startY) {                 
//							g.drawRect(pic.movedX, pic.movedY, pic.startX - pic.movedX, pic.startY - pic.movedY);
//						}                                                                
//						break;
//					case "사각형(색)" :
//						if (pic.movedX > pic.startX && pic.movedY < pic.startY) {                        
//							g.fillRect(pic.startX, pic.movedY, pic.movedX - pic.startX, pic.startY - pic.movedY);
//						} else if (pic.movedX >= pic.startX && pic.movedY >= pic.startY) {               
//							g.fillRect(pic.startX, pic.startY, pic.movedX-pic.startX, pic.movedY-pic.startY);    
//						} else if (pic.movedX < pic.startX && pic.movedY >= pic.startY) {                
//							g.fillRect(pic.movedX, pic.startY, pic.startX-pic.movedX, pic.movedY-pic.startY);    
//						} else if (pic.movedX < pic.startX && pic.movedY < pic.startY) {                 
//							g.fillRect(pic.movedX, pic.movedY, pic.startX - pic.movedX, pic.startY - pic.movedY);
//						}                                                                 
//						break;
//					case "원" :
//						if (pic.movedX > pic.startX && pic.movedY < pic.startY) {                        
//							g.drawOval(pic.startX, pic.movedY, pic.movedX - pic.startX, pic.startY - pic.movedY);
//						} else if (pic.movedX >= pic.startX && pic.movedY >= pic.startY) {               
//							g.drawOval(pic.startX, pic.startY, pic.movedX-pic.startX, pic.movedY-pic.startY);    
//						} else if (pic.movedX < pic.startX && pic.movedY >= pic.startY) {                
//							g.drawOval(pic.movedX, pic.startY, pic.startX-pic.movedX, pic.movedY-pic.startY);    
//						} else if (pic.movedX < pic.startX && pic.movedY < pic.startY) {                 
//							g.drawOval(pic.movedX, pic.movedY, pic.startX - pic.movedX, pic.startY - pic.movedY);
//						}                                                                
//						break;
//					case "원(색)" :
//						if (pic.movedX > pic.startX && pic.movedY < pic.startY) {                        
//							g.fillOval(pic.startX, pic.movedY, pic.movedX - pic.startX, pic.startY - pic.movedY);
//						} else if (pic.movedX >= pic.startX && pic.movedY >= pic.startY) {               
//							g.fillOval(pic.startX, pic.startY, pic.movedX-pic.startX, pic.movedY-pic.startY);    
//						} else if (pic.movedX < pic.startX && pic.movedY >= pic.startY) {                
//							g.fillOval(pic.movedX, pic.startY, pic.startX-pic.movedX, pic.movedY-pic.startY);    
//						} else if (pic.movedX < pic.startX && pic.movedY < pic.startY) {                 
//							g.fillOval(pic.movedX, pic.movedY, pic.startX - pic.movedX, pic.startY - pic.movedY);
//						}                                                                 
//						break;
//				}
			}// 벡터 for
			
			g.setColor(userColor);
			switch (userShape) {
				case "직선" :
					g.drawLine(startX, startY, movedX, movedY);
					break;
				case "곡선" :
					g.fillOval(startX, startY, 3, 3);
					break;
				case "사각형" :
					if (movedX > startX && movedY < startY) {                        
						g.drawRect(startX, movedY, movedX - startX, startY - movedY);
					} else if (movedX >= startX && movedY >= startY) {               
						g.drawRect(startX, startY, movedX-startX, movedY-startY);    
					} else if (movedX < startX && movedY >= startY) {                
						g.drawRect(movedX, startY, startX-movedX, movedY-startY);    
					} else if (movedX < startX && movedY < startY) {                 
						g.drawRect(movedX, movedY, startX - movedX, startY - movedY);
					}                                                                
					break;
				case "사각형(색)" :
					if (movedX > startX && movedY < startY) {                         
						g.fillRect(startX, movedY, movedX - startX, startY - movedY); 
					} else if (movedX >= startX && movedY >= startY) {                
						g.fillRect(startX, startY, movedX-startX, movedY-startY);     
					} else if (movedX < startX && movedY >= startY) {                 
						g.fillRect(movedX, startY, startX-movedX, movedY-startY);     
					} else if (movedX < startX && movedY < startY) {                  
						g.fillRect(movedX, movedY, startX - movedX, startY - movedY); 
					}                                                                 
					break;
				case "원" :
					if (movedX > startX && movedY < startY) {                         
						g.drawOval(startX, movedY, movedX - startX, startY - movedY); 
					} else if (movedX >= startX && movedY >= startY) {                
						g.drawOval(startX, startY, movedX-startX, movedY-startY);     
					} else if (movedX < startX && movedY >= startY) {                 
						g.drawOval(movedX, startY, startX-movedX, movedY-startY);     
					} else if (movedX < startX && movedY < startY) {                  
						g.drawOval(movedX, movedY, startX - movedX, startY - movedY); 
					}                                                                 
					break;
				case "원(색)" :
					if (movedX > startX && movedY < startY) {                         
						g.fillOval(startX, movedY, movedX - startX, startY - movedY); 
					} else if (movedX >= startX && movedY >= startY) {                
						g.fillOval(startX, startY, movedX-startX, movedY-startY);     
					} else if (movedX < startX && movedY >= startY) {                 
						g.fillOval(movedX, startY, startX-movedX, movedY-startY);     
					} else if (movedX < startX && movedY < startY) {                  
						g.fillOval(movedX, movedY, startX - movedX, startY - movedY); 
					}                                                                 
					break;
			}//switch
		}// paintComponent
		
	
	}
	
	// 색선택 다이얼로그
	class ColorDialog extends JDialog {
		
		public ColorDialog() {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setTitle("색상 선택기");
			setSize(650, 400);
			setLocationRelativeTo(null);
			setModal(true);
			add(colorChooser);
			add(btnChoose, BorderLayout.SOUTH);
			btnChoose.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					userColor = colorChooser.getColor();
					System.out.println(userColor);
					dispose();
				}
			});
			setVisible(true);
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnExit) {
			dispose(); 
		} else if (obj == btnMyDiary) {
			dispose();
			new PictureDiaryMain(id);
		} else if (obj == btnSave) {
			boolean result = dao.savePicture(vecPic);
			if (result) {
				JOptionPane.showMessageDialog(con, "저장 완료", "저장 완료", JOptionPane.PLAIN_MESSAGE);
				dispose();
				new PictureDiaryMain(id);
			} else {
				JOptionPane.showMessageDialog(this, "일기를 저장하는데 오류가 생겼습니다.", "오류발생", JOptionPane.ERROR_MESSAGE);
			}
		} else if (obj == btnColor) {
			ColorDialog dialog = new ColorDialog();
		} 
	}

}
