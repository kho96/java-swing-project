package ver01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class RegisterDialog extends JDialog implements ActionListener{
	
	// 입력창
	JTextField tfName = new JTextField(10);
	JTextField tfId = new JTextField(10);
	JPasswordField pfPw = new JPasswordField(10);
	JPasswordField pfCPw = new JPasswordField(10);
	
	// JButton
	JButton btnId = new JButton("중복 확인");
	JButton btnRegister = new JButton("가입");
	JButton btnExit = new JButton("취소");
	
	// 사용할 변수 선언 및 초기화
	boolean useableId = false;
	String ableId;
	String pw = "";
	String cPw = "";
	
	// UserDao
	UserDao dao = UserDao.getInstance();
	
	public RegisterDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(300, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setNorth();
		setForm();
		setListener();
		setVisible(true);
	}
	
	// 리스너 달기
	private void setListener() {
		btnExit.addActionListener(this);
		btnId.addActionListener(this);
		btnRegister.addActionListener(this);
	}

	// 회원가입 글자 입력 - North
	private void setNorth() {
		JPanel pnl= new JPanel();
		JLabel lbl = new JLabel("회원 가입");
		lbl.setFont(new Font("휴면 편지체", Font.BOLD, 35));
		lbl.setForeground(Color.MAGENTA);
		pnl.add(lbl);
		add(pnl, BorderLayout.NORTH);
	}
	
	// 회원가입 양식 - Center
	private void setForm() {
		// JLabel
		JLabel lblName = new JLabel("이름");
		JLabel lblId = new JLabel("ID");
		JLabel lblPw = new JLabel("PW");
		JLabel lblCPw = new JLabel("PW 확인");
		
		// Center 패널 생성
		JPanel pnlCenter = new JPanel(new GridLayout(5,1));
		// 각 항목 패널 생성하기
		JPanel pnlName = new JPanel(); 
		JPanel pnlId = new JPanel();
		JPanel pnlPw = new JPanel();
		JPanel pnlCPw = new JPanel();
		JPanel pnlbtn = new JPanel();
		
		//각 패널에 컴포넌트 달기
		pnlName.add(lblName);
		pnlName.add(tfName);
		
		pnlId.add(lblId);
		pnlId.add(tfId);
		pnlId.add(btnId);
		
		pnlPw.add(lblPw);
		pnlPw.add(pfPw);
		
		pnlCPw.add(lblCPw);
		pnlCPw.add(pfCPw);
		
		pnlbtn.add(btnRegister);
		pnlbtn.add(btnExit);
		
		// 센터 패널에 각 패널 달기
		pnlCenter.add(pnlName);
		pnlCenter.add(pnlId);
		pnlCenter.add(pnlPw);
		pnlCenter.add(pnlCPw);
		pnlCenter.add(pnlbtn);
		
		// 다이얼로그에 센터패널 달기
		add(pnlCenter);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnExit) {
			dispose();
		} else if (obj == btnRegister) {
			String userInputName = tfName.getText();
			String userInputId = tfId.getText();
			
			// 비밀번호, 비밀번호 확인에 입력한 값 String으로 받아내기
			String userInputPw = new String(pfPw.getPassword());
			String userInputCPw = new String(pfCPw.getPassword());
			
			// 항목입력을 안한 경우
			if(userInputName.trim().equals("") || userInputId.trim().equals("") ||
					userInputPw.trim().equals("") || userInputCPw.trim().equals("")) {
				JOptionPane.showMessageDialog(this, "모든 항목을 입력해주세요.", "오류 메세지", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// 비밀번호, 비밀번호확인 두가지 정보가 일치하지 않는 경우
			if(!userInputPw.equals(userInputCPw)) {
				JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "오류 메세지", JOptionPane.ERROR_MESSAGE);
				userInputPw = "";
				userInputCPw = "";
				return;
			}
			
			// 중복확인을 하지 않았거나, 허용가능한 아이디로 중복확인을 하고, 다시 아이디를 입력해서(불가능한 아이디) 가입을 누른 경우
			if (!useableId || !userInputId.equals(ableId)) {
				JOptionPane.showMessageDialog(this, "중복 확인을 해주세요.", "오류 메세지", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// 정상적으로 처리된 경우 DB에 입력값 저장
			boolean result = dao.insertUser(userInputName, userInputId, userInputPw);
			if (result) { // DB에 insert 성공
				JOptionPane.showMessageDialog(this, "정상적으로 가입이 완료되었습니다.", "가입 완료", JOptionPane.PLAIN_MESSAGE);
				dispose();
			} else { // DB insert 실패
				JOptionPane.showMessageDialog(this, "가입실패", "가입 오류", JOptionPane.ERROR_MESSAGE);
			}
			
		} else if (obj == btnId) { // 중복확인 버튼을 눌렀을 때
			String userInputId = tfId.getText();
			
			// 아이디 중복 확인 
			boolean result = dao.checkId(userInputId);
			if (result) {
				JOptionPane.showMessageDialog(this, "사용가능한 아이디 입니다.", "중복 확인", JOptionPane.PLAIN_MESSAGE);
				useableId = true; 
				ableId = userInputId;
				return;
			} 
			JOptionPane.showMessageDialog(this, "사용할 수 없는 아이디 입니다.", "중복 확인", JOptionPane.ERROR_MESSAGE);
			tfId.setText("");
			useableId = false;
			
		}
		
	}
}
