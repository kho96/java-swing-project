package ver01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.RowSetListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame implements ActionListener {
	Container con = getContentPane();
	
	// 입력창
	JTextField tfId = new JTextField(10);
	JPasswordField pfPw	= new JPasswordField(10);
	
	// 버튼
	JButton btnLogin = new JButton("로그인");
	JButton btnRegister = new JButton("회원가입");
	
	// UserDao
	UserDao dao = UserDao.getInstance();
	
	
	
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("로그인");
		setSize(400, 250);
		setLocationRelativeTo(null); // 화면 중앙 배치
		setResizable(false);
		setName();
		setUi();
		setListener();
		setVisible(true);
	}

	// 리스너달기
	private void setListener() {
		tfId.addActionListener(this);
		pfPw.addActionListener(this);
		btnLogin.addActionListener(this);
		btnRegister.addActionListener(this);
	}

	// MyDiary 제목을 North에 붙이기
	private void setName() {
		JPanel pnl= new JPanel();
		JLabel lbl = new JLabel("My Diary");
		lbl.setFont(new Font("휴면 편지체", Font.BOLD, 50));
		lbl.setForeground(Color.MAGENTA);
		pnl.add(lbl);
		con.add(pnl, BorderLayout.NORTH);
		
	}

	// Center 구성하기
	private void setUi() {
		// Center패널 생성-그리드 레이아웃
		JPanel pnl = new JPanel(new GridLayout(3, 1));
		
		// 각 패널 생성
		JPanel pnlId = new JPanel();
		JPanel pnlPw = new JPanel();
		JPanel pnlBtn = new JPanel();
		
		// 각 패널에 컴포넌트 추가
		pnlId.add(new JLabel("ID  "));
		pnlId.add(tfId);
		pnlPw.add(new JLabel("PW"));
		pnlPw.add(pfPw);
		pnlBtn.add(btnLogin);
		pnlBtn.add(btnRegister);
		
		// Center패널에 각 패널 추가
		pnl.add(pnlId);
		pnl.add(pnlPw);
		pnl.add(pnlBtn);
		
		// 프레임에 Center패널 붙이기
		con.add(pnl);
	}

	
	
	public static void main(String[] args) {
		new LoginFrame();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == btnRegister) {
			RegisterDialog dialog = new RegisterDialog(LoginFrame.this, "회원가입", true);
		} else {
			String id = tfId.getText();
			// getPassword -> char[] -> String으로 변환
			String pw = new String(pfPw.getPassword());
			// 아이디와 비밀번호 입력을 안했을 경우
			if (id.trim().equals("")) {
				JOptionPane.showMessageDialog(con, "아이디를 입력하세요.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (pw.trim().equals("")) {
				JOptionPane.showMessageDialog(con, "비밀번호를 입력하세요.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
				return;
			}        
			// 아이디, 비밀번호 확인
			boolean result = dao.checkUser(id, pw);
			if(result) { // 로그인 성공
				System.out.println(id);
				new MenuFrame(id);
				dispose();      
			} else { // 로그인 실패
				JOptionPane.showMessageDialog(con, "아이디 또는 비밀번호를 확인하세요.", "로그인 오류", JOptionPane.ERROR_MESSAGE);         
				return;                                                                                                 
			}
			
		} // else
		
	}

}
