package ver01;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CalDialog extends JDialog implements ActionListener {
	// 버튼 생성
	JButton btnOk = new JButton("확인");
	JButton btnExit = new JButton("취소");
	
	// now 현재 설정
	GregorianCalendar now = new GregorianCalendar();
	
	// 날짜 값 변수 선언
	String year, month, date;
	
	public CalDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500, 130);
		setLocationRelativeTo(null);
		year = String.valueOf(now.get(Calendar.YEAR));  
		month = String.valueOf(now.get(Calendar.MONTH)+1);
		date = String.valueOf(now.get(Calendar.DATE));  
		setComboBox();
		setButton();
	}
	
	// 버튼 달기
	private void setButton() {
		JPanel pnl = new JPanel();
		pnl.add(btnOk);
		pnl.add(btnExit);
		btnOk.addActionListener(this);
		btnExit.addActionListener(this);
		add(pnl, BorderLayout.SOUTH);
	}

	// 콤보박스 생성(3개-년,월,일)
	private void setComboBox() {
		JPanel pnl = new JPanel();
		
		// 콤보박스 생성(년,월,일), 현재 날짜로 select
		String[] arrYears = {"2022", "2021", "2020", "2019", "2018"};
		JComboBox<String> years = new JComboBox<>(arrYears);
		for (int i = 0; i < arrYears.length; i++) {
			if (year.equals(arrYears[i])) {
				years.setSelectedIndex(i);
			}
		}
		
		String[] arrMonths = {"1", "2", "3", "4", "5", "6", "7",
								"8", "9", "10", "11", "12"};
		JComboBox<String> months = new JComboBox<>(arrMonths);
		for (int i = 0; i < arrMonths.length; i++) {
			if (month.equals(arrMonths[i])) {
				months.setSelectedIndex(i);
			}
		}
		
		String[] arrDates = new String[31];
		for (int i = 0; i < arrDates.length; i++) {
			arrDates[i] = String.valueOf(i+1);
		}
		JComboBox<String> dates = new JComboBox<>(arrDates);
		for (int i = 0; i < arrDates.length; i++) {
			if (date.equals(arrDates[i])) {
				dates.setSelectedIndex(i);
			}
		}
		
		// 리스너 달기(선택한 값을 날짜 값으로 얻기)
		years.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				if(state == ItemEvent.SELECTED) {
					year = (String)e.getItem();
				}
			}
		});
		months.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				if(state == ItemEvent.SELECTED) {
					month = (String)e.getItem();
				}
				
			}
		});
		dates.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				if(state == ItemEvent.SELECTED) {
					date = (String)e.getItem();
				}
				
			}
		});
		
		// 콤보박스, JLabel 달기
		pnl.add(years);
		pnl.add(new JLabel("년"));
		pnl.add(months);
		pnl.add(new JLabel("월"));
		pnl.add(dates);
		pnl.add(new JLabel("일"));
		add(pnl);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnExit) {
			dispose();
		} else if (obj == btnOk) {
			int intYear = Integer.parseInt(year);
			int intMonth = Integer.parseInt(month)-1;
			int intDate = Integer.parseInt(date);
			now.set(intYear, intMonth, intDate);
			String selectedDate = String.valueOf(now.get(Calendar.DATE));
			if(!selectedDate.equals(date)) {
				JOptionPane.showMessageDialog(this, "존재하지 않는 날짜입니다.", "에러메세지", JOptionPane.ERROR_MESSAGE);
				return;
			}
			dispose();
		}
	}


	public String getYear() {
		return year;
	}

	public String getDate() {
		return date;
	}

	public String getMonth() {
		return month;
	}

	
	
	
}
