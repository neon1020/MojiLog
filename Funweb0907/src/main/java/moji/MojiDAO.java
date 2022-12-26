package moji;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.JdbcUtil;

public class MojiDAO {
	
	// 전체 게시물 수 조회하는 selectMojiListCount() 메소드 정의
	// 파라미터 : 없음, 리턴타입 : int (mojiListCount)
	public int selectMojiListCount() {
		int mojiListCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT COUNT(num) FROM moji";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				mojiListCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectMojiListCount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return mojiListCount;
	}
	
	// -----------------------------------------------------------------------------------------------
	
	// 게시물 목록 조회를 수행하는 selectMojiList() 메소드 정의
	// => 파라미터 : 시작 행번호, 페이지 당 게시물 목록 수   리턴타입 : ArrayList(boardList)
	public ArrayList<MojiDTO> selectMojiList(int startRow, int listLimit) {
		ArrayList<MojiDTO> mojiList = null;
		
		// DB 작업에 사용할 변수 선언
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 0 ~ 2 단계
		con = JdbcUtil.getConnection();
		
		try {
			// 3단계
			// => moji 테이블의 모든 레코드 조회
			// (num 기준 내림차순 정렬, 10개로 제한)
			// 시작행 번호부터 페이지 당 게시물 목록 수만큼만 조회 (LIMIT 시작행 번호, 페이지 당 게시물 목록 수)
			String sql = "SELECT * FROM moji ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, listLimit);
			
			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();
			
			// 전체 게시물 목록을 저장할 ArrayList 객체 생성
			mojiList = new ArrayList<MojiDTO>();
			
			while(rs.next()) {
				// 1개 레코드를 저장할 MojiDTO 객체 생성 후 데이터 저장
				MojiDTO board = new MojiDTO();
				board.setNum(rs.getInt("num"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setReadcount(rs.getInt("readcount"));
				board.setDate(rs.getTimestamp("date"));
				board.setOriginal_file(rs.getString("original_file"));
				board.setReal_file(rs.getString("real_file"));
				
				// 전체 레코드를 저장할 ArrayList<MojiDTO> 객체에 1개 레코드가 저장된 MojiDTO 객체 추가
				mojiList.add(board);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectMojiList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return mojiList;
	}
	
	// ----------------------------------------------------------------------------------
	
	// 전체 게시물 수 조회하는 selectMojiListCount() 메소드 정의 => 검색 기능 추가
	// 파라미터 : 검색어, 리턴타입 : int (mojiListCount)
	public int selectMojiListCount(String keyword) {
		int mojiListCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT COUNT(num) FROM moji WHERE subject LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				mojiListCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectMojiListCount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return mojiListCount;
	}
		
	// -----------------------------------------------------------------------------------------------
	
	// 게시물 목록 조회를 수행하는 selectMojiList() 메소드 정의 => 검색 기능 추가
	// => 파라미터 : 시작 행번호, 페이지 당 게시물 목록 수, 검색어   리턴타입 : ArrayList(boardList)
	public ArrayList<MojiDTO> selectMojiList(int startRow, int listLimit, String keyword) {
		ArrayList<MojiDTO> mojiList = null;
		
		// DB 작업에 사용할 변수 선언
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 0 ~ 2 단계
		con = JdbcUtil.getConnection();
		
		try {
			// 3 단계
			// => moji 테이블의 모든 레코드 조회
			// (num 기준 내림차순 정렬, 10개로 제한)
			// 시작행 번호부터 페이지 당 게시물 목록 수만큼만 조회 (LIMIT 시작행 번호, 페이지 당 게시물 목록 수)
			String sql = "SELECT * FROM moji WHERE subject LIKE ? ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			
			// 4 단계
			rs = pstmt.executeQuery();
			
			// 전체 게시물 목록을 저장할 ArrayList 객체 생성
			mojiList = new ArrayList<MojiDTO>();
			
			while(rs.next()) {
				// 1개 레코드를 저장할 MojiDTO 객체 생성 후 데이터 저장
				MojiDTO board = new MojiDTO();
				board.setNum(rs.getInt("num"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setReadcount(rs.getInt("readcount"));
				board.setDate(rs.getTimestamp("date"));
				board.setOriginal_file(rs.getString("original_file"));
				board.setReal_file(rs.getString("real_file"));
				
				// 전체 레코드를 저장할 ArrayList<MojiDTO> 객체에 1개 레코드가 저장된 MojiDTO 객체 추가
				mojiList.add(board);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectMojiList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return mojiList;
	}
	
	// ----------------------------------------------------------------------------------
	
	// 글쓰기 작업을 수행하는 insertMoji() 메소드 정의
	// => 파라미터 : MojiDTO 객체(moji)   리턴타입 : int(insertCount)
	public int insertMoji(MojiDTO moji) {
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
			String sql = "SELECT MAX(num) FROM moji";
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
			sql = "INSERT INTO moji VALUES (?,?,?,now(),0,?,?)";
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setString(2, moji.getSubject());
			pstmt2.setString(3, moji.getContent());
			pstmt2.setString(4, moji.getOriginal_file());
			pstmt2.setString(5, moji.getReal_file());
			
			// 4단계. SQL 구문 실행 및 결과 처리
			insertCount = pstmt2.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - insertMoji()");
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
	
	// ------------------------------------------------------------------------------
	
	// 게시물 조회수 증가 작업 수행하는 updateReadcount() 메소드 정의
	// => 파라미터 : 글번호(num)   리턴타입 : void
	public void updateReadcount(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "UPDATE moji SET readcount=readcount+1 WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			// 4 단계
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - updateReadcount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
	}

	// -------------------------------------------------------------------------
	
	// 게시물 1개 조회 작업 수행하는 selectMoji() 메소드 정의 
	// => 파라미터 : 글번호(num)   리턴타입 : MojiDTO(moji)
	public MojiDTO selectMoji(int num) {
		MojiDTO moji = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT * FROM moji WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			// 4 단계
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				moji = new MojiDTO();
				
				moji.setNum(rs.getInt("num"));
				moji.setSubject(rs.getString("subject"));
				moji.setContent(rs.getString("content"));
				moji.setReadcount(rs.getInt("readcount"));
				moji.setDate(rs.getTimestamp("date"));
				moji.setOriginal_file(rs.getString("original_file"));
				moji.setReal_file(rs.getString("real_file"));
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectMoji()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return moji;
	}
	
	// ------------------------------------------------------------------------------------
	
	// 글 삭제 작업 수행하는 deleteMoji() 메소드
	// => 파라미터 : 글번호, 패스워드     리턴타입 : int(deleteCount)
	public int deleteMoji(int num) {
		int deleteCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "DELETE FROM moji WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			// 4 단계
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - deleteMoji()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		return deleteCount;
	}
}