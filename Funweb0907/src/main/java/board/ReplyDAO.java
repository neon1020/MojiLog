package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.JdbcUtil;

public class ReplyDAO {
	
	// 댓글 등록하는 insertReply() 메소드
	// => 파라미터 : ReplyDTO 객체 (reply) 리턴타입 : int(insertCount)
	public int insertReply(ReplyDTO reply) {
		int insertCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계 : num 은 Auto_Increment 옵션 설정되어있으므로 null값 전달 시 자동 증가
			String sql = "INSERT INTO reply VALUES (null,?,?,?,?,now(),?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reply.getBoardType());
			pstmt.setInt(2, reply.getRefNum());
			pstmt.setString(3, reply.getName());
			pstmt.setString(4, reply.getContent());
			pstmt.setString(5, reply.getNickName());
			
			// 4단계. SQL 구문 실행 및 결과 처리
			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - insertReply()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return insertCount;
	}
	
	// --------------------------------------------------------------------------------------------------
	// 댓글 목록 가져오는 selectReplyList() 메소드
	// => 파라미터 : 글번호(num)  리턴타입 : List<ReplyDTO> (replyList)
	public List<ReplyDTO> selectReplyList(int num) {
		List<ReplyDTO> replyList = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			// reply 테이블의 원본 글번호(refNum)가 일치하는 레코드 조회 => 댓글번호 역순으로 정렬
			String sql = "SELECT * FROM reply WHERE refNum=? ORDER BY num DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();
			
			// 전체 댓글 목록을 저장할 ArrayList 객체 생성
			replyList = new ArrayList<ReplyDTO>();
			
			// 조회 결과가 있을 경우 레코드 접근 반복
			while(rs.next()) {
				// 1개 레코드를 저장할 BoardDTO 객체 생성 후 데이터 저장
				ReplyDTO reply = new ReplyDTO();
				reply.setNum(rs.getInt("num"));
				reply.setBoardType(rs.getString("boardType"));
				reply.setRefNum(rs.getInt("refNum"));
				reply.setName(rs.getString("name"));
				reply.setContent(rs.getString("content"));
				reply.setDate(rs.getTimestamp("date"));
				reply.setNickName(rs.getString("nickName"));
				
				// 전체 레코드를 저장할 ArrayList<BoardDTO> 객체에 1개 레코드가 저장된 BoardDTO 객체 추가
				replyList.add(reply);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectReplyList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		return replyList;
	}
	
	// --------------------------------------------------------------------------------------------------------------
	
	// 댓글 삭제 작업 수행하는 deleteReply() 메소드
	// => 파라미터 : 글번호     리턴타입 : int(deleteCount)
	public int deleteReply(int num, String sId) {
		int deleteCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "DELETE FROM reply WHERE num=? AND name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, sId);
			
			// 4 단계
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - deleteReply()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		return deleteCount;
	}
}
