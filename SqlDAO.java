package ui_swing;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlDAO {

	private static SqlDAO instance;

	private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String ID = "USER02";
	private final String PW = "1234";

	public StudentVo myVariableClass; //����, ������ ������ Ŭ����
	
	
	public SqlDAO() {/*SINGLE TON*/}//������
	
	public static SqlDAO getInstance() {
		if(instance == null) {
			instance = new SqlDAO();
		}
		return instance;
	}//getInstacne
	
	//------------- connection, close------------------------//
	private Connection getConnection() {
		try {
			Class.forName(DRIVER); // ����Ŭ ����̹��� �޸𸮿� �ε�
			Connection conn = DriverManager.getConnection(URL, ID, PW); // �޸𸮿� �ε��� Ŭ������ �̿��ؼ� ����
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}//connection
	
	private void close(PreparedStatement pstmt, Connection conn) {
		if (pstmt != null) try { pstmt.close(); } catch (Exception e) { }
		if (conn != null)  try { conn.close();  } catch (Exception e) { }
	}//close
		
	private void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		if (rs != null)    try { rs.close();    } catch (Exception e) { }
		if (pstmt != null) try { pstmt.close(); } catch 
		
		(Exception e) { }
		if (conn != null)  try { conn.close();  } catch (Exception e) { }
	}//close
	//------------------- connection, close ---------------------------//

	//��ü������ �˻�
	public ArrayList<StudentVo> allSearch() {
		
		String sql = "select * from tbl_student order by sno";
		
		try {
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();			
			
			ArrayList<StudentVo> list = new ArrayList<>();
			
			System.out.println(list.toString());
			
			while(rs.next()) {
				int count = 0;
				System.out.println("count:"+count);
				String sno = rs.getString(++count);
				String sname = rs.getString(++count);
				int syear = rs.getInt(++count);
				String gender = rs.getString(++count);
				String major= rs.getString(++count);
				int score = rs.getInt(++count);
				
				StudentVo var = new StudentVo(sno, sname, syear, gender, major, score);
				System.out.println("var:"+var.toString());
				list.add(var);
			}
			
			close(pstmt, conn);
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//�κ� �˻�
	public ArrayList<StudentVo> partSearch(String sql) {
		
		try {
			//System.out.println("�κа˻�");
			
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();			
			
			ArrayList<StudentVo> list = new ArrayList<>();
			
			while(rs.next()) {
				int count = 0;
				
				String sno = rs.getString(++count);
				String sname = rs.getString(++count);
				int syear = rs.getInt(++count);
				String gender = rs.getString(++count);
				String major= rs.getString(++count);
				int score = rs.getInt(++count);
				
				StudentVo var = new StudentVo(sno, sname, syear, gender, major, score);
				System.out.println("var:"+var.toString());
				list.add(var);
			}
			
			close(pstmt, conn);
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
	//�й� �Է¸����� �˻��ؼ� �����͸� �����´�
	public StudentVo getModifyOfSno(String sql) {
		try {
			
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			String sno = null;
			String sname = null;
			int syear =0 ;
			String gender = null;
			String major = null;
			int score = 0;
			
			while(rs.next()) {
				sno = rs.getString("sno");
				//System.out.println(sno);
				sname = rs.getString("sname");
				syear = rs.getInt("syear");
				gender = rs.getString("gender");
				major = rs.getString("major");
				score = rs.getInt("score");
				}
		
			StudentVo var = new StudentVo(sno, sname, syear, gender, major, score);
			
			close(rs, pstmt, conn);
			return var;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}//getModifyOfSno
	
	//������ �Է�
	public int inputData(StudentVo varClass) {
		
		String sql = "insert into tbl_student values(?,?,?,?,?,?)";
		try {
		
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			int count=0;
			pstmt.setString(++count, varClass.getSno());
			pstmt.setString(++count, varClass.getSname());
			pstmt.setInt(++count, varClass.getSyear());
			pstmt.setString(++count, varClass.getGender());
			pstmt.setString(++count, varClass.getMajor());
			pstmt.setInt(++count, varClass.getScore());
			
			int resultCount = pstmt.executeUpdate();
			
			close(pstmt, conn);
			return resultCount;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}//inputData
	
	//������ ����
	public int modifyData(StudentVo varClass) {
		String sql = "update tbl_student set sname=?, syear=?, gender=?, major=?, score=? where sno=?";
		
		try {
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			int count=0;
			pstmt.setString(++count, varClass.getSname());
			pstmt.setInt(++count, varClass.getSyear());
			pstmt.setString(++count, varClass.getGender());
			pstmt.setString(++count, varClass.getMajor());
			pstmt.setInt(++count, varClass.getScore());
			pstmt.setString(++count, varClass.getSno());
			
			int resultCount = pstmt.executeUpdate();
			
			close(pstmt, conn);
			return resultCount;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}//modifyData

	//������ ����
	public int deleteData(String sno) {
		String sql = "delete tbl_student where sno = '"+ sno +"'";
		
		try {
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			int resultCount = pstmt.executeUpdate();
			
			close(pstmt, conn);
			return resultCount;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}//deleteData
}