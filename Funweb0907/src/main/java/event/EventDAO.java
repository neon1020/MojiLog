package event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.JdbcUtil;

public class EventDAO {
	
	// 전체 게시물 수 조회하는 selectEventListCount() 메소드 정의
	// 파라미터 : 없음, 리턴타입 : int (eventListCount)
	public int selectEventListCount() {
		int eventListCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT COUNT(num) FROM event";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				eventListCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectEventListCount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return eventListCount;
	}
	
	// -----------------------------------------------------------------------------------------------
	
	// 전체 게시물 수 조회하는 selectEventListCount() 메소드 정의 => 검색 기능 추가
	// 파라미터 : 없음, 리턴타입 : int (eventListCount)
	public int selectEventListCount(String keyword) {
		int eventListCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT COUNT(num) FROM event WHERE subject LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				eventListCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectEventListCount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return eventListCount;
	}
		
		// -----------------------------------------------------------------------------------------------
	
	// 게시물 목록 조회를 수행하는 selectEventList() 메소드 정의
	// => 파라미터 : 시작 행번호, 페이지 당 게시물 목록 수   리턴타입 : ArrayList(boardList)
	public ArrayList<EventDTO> selectEventList(int startRow, int listLimit) {
		ArrayList<EventDTO> eventList = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 0 ~ 2 단계
		con = JdbcUtil.getConnection();
		
		try {
			// 3단계
			// => event 테이블의 모든 레코드 조회
			// (num 기준 내림차순 정렬, 10개로 제한)
			// 시작행 번호부터 페이지 당 게시물 목록 수만큼만 조회 (LIMIT 시작행 번호, 페이지 당 게시물 목록 수)
			String sql = "SELECT * FROM event ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, listLimit);
			
			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();
			
			// 전체 게시물 목록을 저장할 ArrayList 객체 생성
			eventList = new ArrayList<EventDTO>();
			
			while(rs.next()) {
				// 1개 레코드를 저장할 EventDTO 객체 생성 후 데이터 저장
				EventDTO event = new EventDTO();
				event.setNum(rs.getInt("num"));
				event.setSubject(rs.getString("subject"));
				event.setContent(rs.getString("content"));
				event.setStart_date(rs.getTimestamp("start_date"));
				event.setEnd_date(rs.getTimestamp("end_date"));
				event.setLink(rs.getString("link"));
				event.setOriginal_file(rs.getString("original_file"));
				event.setReal_file(rs.getString("real_file"));
				
				// 전체 레코드를 저장할 ArrayList<EventDTO> 객체에 1개 레코드가 저장된 EventDTO 객체 추가
				eventList.add(event);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectEventList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return eventList;
	}
	
	// ----------------------------------------------------------------------------------
	
	// 게시물 목록 조회를 수행하는 selectEventList() 메소드 정의 => 검색 기능 추가
	// => 파라미터 : 시작 행번호, 페이지 당 게시물 목록 수, 검색어     리턴타입 : ArrayList(boardList)
	public ArrayList<EventDTO> selectEventList(int startRow, int listLimit, String keyword) {
		ArrayList<EventDTO> eventList = null;
		
		// DB 작업에 사용할 변수 선언
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 0 ~ 2 단계
		con = JdbcUtil.getConnection();
		
		try {
			// 3단계
			// => event 테이블의 모든 레코드 조회
			// (num 기준 내림차순 정렬, 10개로 제한)
			// 시작행 번호부터 페이지 당 게시물 목록 수만큼만 조회 (LIMIT 시작행 번호, 페이지 당 게시물 목록 수)
			String sql = "SELECT * FROM event WHERE subject LIKE ? ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			
			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();
			
			// 전체 게시물 목록을 저장할 ArrayList 객체 생성
			eventList = new ArrayList<EventDTO>();
			
			while(rs.next()) {
				// 1개 레코드를 저장할 EventDTO 객체 생성 후 데이터 저장
				EventDTO event = new EventDTO();
				event.setNum(rs.getInt("num"));
				event.setSubject(rs.getString("subject"));
				event.setContent(rs.getString("content"));
				event.setStart_date(rs.getTimestamp("start_date"));
				event.setEnd_date(rs.getTimestamp("end_date"));
				event.setLink(rs.getString("link"));
				event.setOriginal_file(rs.getString("original_file"));
				event.setReal_file(rs.getString("real_file"));
				
				// 전체 레코드를 저장할 ArrayList<EventDTO> 객체에 1개 레코드가 저장된 EventDTO 객체 추가
				eventList.add(event);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectEventList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return eventList;
	}
		
		// ----------------------------------------------------------------------------------
	
	// 글쓰기 작업을 수행하는 insertEvent() 메소드 정의
	// => 파라미터 : EventDTO 객체(event)   리턴타입 : int(insertCount)
	public int insertEvent(EventDTO event) {
		int insertCount = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null;
		
		// 0 ~ 2 단계
		con = JdbcUtil.getConnection();
		
		try {
			// 새 게시물의 번호 계산 작업 수행
			int num = 1;
			
			// 3 - 1 단계. SQL 구문 작성 및 전달
			// 현재 게시물 중 가장 큰 번호(num) 조회 = max() 함수 활용
			String sql = "SELECT MAX(num) FROM event";
			pstmt = con.prepareStatement(sql);

			// 4 - 1 단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();
			
			// 만약, 조회 결과가 있을 경우 해당 글 번호 + 1 값을 새 글 번호(num) 로 저장
			if(rs.next()) {
				num = rs.getInt(1) + 1;
			}
			
			// -------------------------------------------------------------------------
			// 3 - 2 단계. SQL 구문 작성 및 전달
			// => 글 쓰기(INSERT) 작업 수행
			sql = "INSERT INTO event VALUES (?,?,?,now(),now()+interval 7 day,?,?,?)";
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setString(2, event.getSubject());
			pstmt2.setString(3, event.getContent());
			pstmt2.setString(4, event.getLink());
			pstmt2.setString(5, event.getOriginal_file());
			pstmt2.setString(6, event.getReal_file());
			
			// 4단계. SQL 구문 실행 및 결과 처리
			insertCount = pstmt2.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - insertEvent()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(pstmt2);
			JdbcUtil.close(con);
		}
		
		return insertCount;
	}
	
	// -------------------------------------------------------------------------
	
	// 최근 게시물 1개 조회 작업 수행하는 selectRecentEvent() 메소드 정의 
	// => 파라미터 : 글번호(num)   리턴타입 : EventDTO(event)
	public EventDTO selectRecentEvent() {
		EventDTO event = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT * FROM event ORDER BY num DESC LIMIT 1";
			pstmt = con.prepareStatement(sql);
			
			// 4 단계
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				event = new EventDTO();
				
				event.setNum(rs.getInt("num"));
				event.setSubject(rs.getString("subject"));
				event.setContent(rs.getString("content"));
				event.setStart_date(rs.getTimestamp("start_date"));
				event.setEnd_date(rs.getTimestamp("end_date"));
				event.setLink(rs.getString("link"));
				event.setOriginal_file(rs.getString("original_file"));
				event.setReal_file(rs.getString("real_file"));
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectRecentEvent()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return event;
	}
		
	// -----------------------------------------------------------------------------------------
	
	// 게시물 1개 조회 작업 수행하는 selectEvent() 메소드 정의 
	// => 파라미터 : 글번호(num)   리턴타입 : EventDTO(event)
	public EventDTO selectEvent(int num) {
		EventDTO event = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT * FROM event WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			// 4 단계
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				event = new EventDTO();
				
				event.setNum(rs.getInt("num"));
				event.setSubject(rs.getString("subject"));
				event.setContent(rs.getString("content"));
				event.setStart_date(rs.getTimestamp("start_date"));
				event.setEnd_date(rs.getTimestamp("end_date"));
				event.setLink(rs.getString("link"));
				event.setOriginal_file(rs.getString("original_file"));
				event.setReal_file(rs.getString("real_file"));
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectEvent()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return event;
	}
	
	// -----------------------------------------------------------------------------------------
	
	// 글 이동 후 해당 테이블에서 삭제 작업 수행하는 deleteEvent() 메소드
	// => 파라미터 : 글번호     리턴타입 : int(deleteCount)
	public int deleteEvent(int num) {
		int deleteCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "DELETE FROM event WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			// 4 단계
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - deleteEvent()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		return deleteCount;
	}
}