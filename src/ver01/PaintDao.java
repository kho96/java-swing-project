package ver01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class PaintDao {
	private static PaintDao instance;
	
	private PaintDao() { }
	
	public static PaintDao getInstance() {
		if (instance == null) {
			instance = new PaintDao();
		}
		return instance;
	}
	
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String ID = "diary";
	private final String PW = "1234";
	
	private Connection getConnection() {	
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, ID, PW);
			return conn;
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return null;
	}
	
	private void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		if (rs != null) try { rs.close(); } catch(Exception e) {}
		if (pstmt != null) try { pstmt.close(); } catch(Exception e) {}
		if (conn != null) try { conn.close(); } catch(Exception e) {}
	}
	
	// 그림 저장
	public boolean savePicture(Vector<PaintVo> vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		if (vo != null) {
			for (PaintVo p : vo) {	
				try {
					conn = getConnection();
					String sql = "insert into tbl_paint(startX, startY, movedX, movedY, userColor, userShape, u_id)"
							+ "   values(?,?,?,?,?,?,?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, p.getStartX());
					pstmt.setInt(2, p.getStartY());
					pstmt.setInt(3, p.getMovedX());
					pstmt.setInt(4, p.getMovedY());
					pstmt.setString(5, p.getUserColor());
					pstmt.setString(6, p.getUserShape());
					pstmt.setString(7, p.getU_id());
					int count = pstmt.executeUpdate();
					if (count > 0) {
						continue;
					}
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					closeAll(conn, pstmt, null);
				}
			}
			return true;
		}// if
		return false;
	}
	
	// 그림데이터 보내기
	public Vector<PaintVo> sendData(String id, String date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select * from tbl_paint"
					+ "	  where TO_DATE(paint_date, 'YY/MM/DD') = ? and u_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			Vector<PaintVo> vec = new Vector<>();
			while(rs.next()) {
				int startX = rs.getInt("startX");
				int startY = rs.getInt("startY");
				int movedX = rs.getInt("movedX");
				int movedY = rs.getInt("movedY");
				String u_Id = rs.getString("u_id");
				String userShape = rs.getString("userShape");
				String userColor = rs.getString("userColor");
				PaintVo vo = new PaintVo(u_Id, userShape, userColor, startX, startY, movedX, movedY);
				vec.add(vo);
			}
			return vec;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return null;
	}
}
