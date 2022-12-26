package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import board.BoardDTO;
import db.JdbcUtil;

public class MemberDAO {
	// 회원가입 수행하는 joinMember() 메소드 정의
	// 파라미터 : MemberDTO(member)  리턴타입 : int (joinCount)
	public int joinMember(MemberDTO member) {
		int joinCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "INSERT INTO member VALUES(?,?,?,?,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPass());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getPost_code());
			pstmt.setString(6, member.getAddress1());
			pstmt.setString(7, member.getAddress2());
			pstmt.setString(8, member.getPhone());
			pstmt.setString(9, member.getMobile());
			
			// 4 단계
			joinCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - joinMember()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return joinCount;
	}
	
	
	// ---------------------------------------------------------------------------
	// 회원 정보 1개 조회 작업 수행하는 selectMember() 메소드 정의 
	// => 파라미터 : Id(id)   리턴타입 : MemberDTO(member)
	public MemberDTO selectMember(String id) {
		MemberDTO member = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT * FROM member WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// 4 단계
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				member = new MemberDTO();
				member.setId(rs.getString("id"));
				member.setPass(rs.getString("pass"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setPost_code(rs.getString("post_code"));
				member.setAddress1(rs.getString("address1"));
				member.setAddress2(rs.getString("address2"));
				member.setPhone(rs.getString("phone"));
				member.setMobile(rs.getString("mobile"));
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectMember()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return member;
	}
	
	// ---------------------------------------------------------------------
	// 회원 ID 중복 체크하는 checkID() 메소드
	public boolean checkID(String id) {
		boolean isDup = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT * FROM member WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// 4 단계
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				isDup = true;
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - checkID()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		return isDup;
	}
	
	// -------------------------------------------------------------------------------------
	
	// 회원 정보 수정 작업 수행하는 updateMemberInfo() 메소드
	// => 파라미터 : MemberDTO 객체, 리턴타입 : int(updateCount)
	public int updateMemberInfo(MemberDTO member) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "UPDATE member SET pass=?, email=?, post_code=?, address1=?, address2=?, phone=?, mobile=? WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getPass());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPost_code());
			pstmt.setString(4, member.getAddress1());
			pstmt.setString(5, member.getAddress2());
			pstmt.setString(6, member.getPhone());
			pstmt.setString(7, member.getMobile());
			pstmt.setString(8, member.getId());
			
			// 4 단계
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - updateMemberInfo()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		return updateCount;
	}
	
	// -------------------------------------------------------------------------------------------------
	
	// 회원 목록 조회를 수행하는 selectMemberList() 메소드 정의 
	// => 파라미터 : 없음   리턴타입 : ArrayList(memberList)
	public ArrayList<MemberDTO> selectMemberList() {
		ArrayList<MemberDTO> memberList = null;
		
		// DB 작업에 사용할 변수 선언
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 0 ~ 2 단계
		con = JdbcUtil.getConnection();
		
		try {
			// 3단계
			String sql = "SELECT * FROM member WHERE id NOT IN (?) ORDER BY id";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "moji");
			
			// 4단계
			rs = pstmt.executeQuery();
			
			// 전체 게시물 목록을 저장할 ArrayList 객체 생성
			memberList = new ArrayList<MemberDTO>(); // MemberDTO 객체 저장 전용 ArrayList 객체 생성됨
			
			// 조회 결과가 있을 경우 레코드 접근 반복
			while(rs.next()) {
				// 1개 레코드를 저장할 MemberDTO 객체 생성 후 데이터 저장
				MemberDTO member = new MemberDTO();
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setAddress1(rs.getString("address1"));
				member.setAddress2(rs.getString("address2"));
				member.setMobile(rs.getString("mobile"));
				member.setDate(rs.getDate("date"));
				
				// 전체 레코드를 저장할 ArrayList<MemberDTO> 객체에 1개 레코드가 저장된 MemberDTO 객체 추가
				memberList.add(member);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectMemberList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return memberList;
	}
	
	// -------------------------------------------------------------------------------------------------------------
	// 회원 1명의 게시물 작성 수 조회 작업 수행하는 boardCount() 메소드 정의 
	// => 파라미터 : Id(id)   리턴타입 : int(boardCount)
	public int boardCount(String id) {
		int boardCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT COUNT(*) FROM board WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// 4 단계
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				boardCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - boardCount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return boardCount;
	}
	
	// -------------------------------------------------------------------------------------------
	// 회원 1명의 댓글 작성 수 조회 작업 수행하는 replyCount() 메소드 정의 
	// => 파라미터 : Id(id)   리턴타입 : int(replyCount)
	public int replyCount(String id) {
		int replyCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT COUNT(*) FROM reply WHERE name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// 4 단계
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				replyCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - replyCount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return replyCount;
	}
}
