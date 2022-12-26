package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.JdbcUtil;

public class BoardDAO {
	
	// 전체 게시물 수 조회하는 selectBoardListCount() 메소드 정의
	// 파라미터 : 없음, 리턴타입 : int (boardListCount)
	public int selectBoardListCount() {
		int boardListCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT COUNT(num) FROM board";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardListCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectBoardListCount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return boardListCount;
	}
	
	// --------------------------------------------------------------------------
	
	// 전체 게시물 수 조회하는 selectBoardListCount() 메소드 정의 => 검색 기능 추가
	// 파라미터 : 검색어, 리턴타입 : int (boardListCount)
	public int selectBoardListCount(String keyword) {
		int boardListCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			// 검색 기능 추가됐으므로 WHERE 절에 검색어 추가 (제목 검색)
			String sql = "SELECT COUNT(num) FROM board WHERE subject LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardListCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectBoardListCount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return boardListCount;
	}
		
	// -------------------------------------------------------------------------------------------------
	
	// 게시물 목록 조회를 수행하는 selectBoardList() 메소드 정의 
	// => 파라미터 : 시작 행번호, 페이지 당 게시물 목록 수   리턴타입 : ArrayList(boardList)
	public ArrayList<BoardDTO> selectBoardList(int startRow, int listLimit) {
		ArrayList<BoardDTO> boardList = null;
		
		// DB 작업에 사용할 변수 선언
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 0 ~ 2 단계
		con = JdbcUtil.getConnection();
		
		try {
			// 3단계
			// => board 테이블의 모든 레코드 조회
			// (num 기준 내림차순 정렬, 10개로 제한)
			// 시작행 번호부터 페이지 당 게시물 목록 수만큼만 조회 (LIMIT 시작행 번호, 페이지 당 게시물 목록 수)
			String sql = "SELECT * FROM board ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, listLimit);
			
			// 4단계
			rs = pstmt.executeQuery();
			
			// 전체 게시물 목록을 저장할 ArrayList 객체 생성
			boardList = new ArrayList<BoardDTO>();
			
			while(rs.next()) {
				// 1개 레코드를 저장할 BoardDTO 객체 생성 후 데이터 저장
				BoardDTO board = new BoardDTO();
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setPass(rs.getString("pass"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setReadcount(rs.getInt("readcount"));
				board.setDate(rs.getTimestamp("date"));
				board.setId(rs.getString("id"));
				
				// 전체 레코드를 저장할 ArrayList<BoardDTO> 객체에 1개 레코드가 저장된 BoardDTO 객체 추가
				boardList.add(board);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectBoardList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return boardList;
	}
	
	// ---------------------------------------------------------------------------------
	// 게시물 목록 조회를 수행하는 selectBoardList() 메소드 정의 => 검색 기능 추가
	// => 파라미터 : 시작 행번호, 페이지 당 게시물 목록 수, 검색어   리턴타입 : ArrayList(boardList)
	public ArrayList<BoardDTO> selectBoardList(int startRow, int listLimit, String keyword) {
		ArrayList<BoardDTO> boardList = null;
		
		// DB 작업에 사용할 변수 선언
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 0 ~ 2 단계
		con = JdbcUtil.getConnection();
		
		try {
			// 3단계
			// => board 테이블의 모든 레코드 조회
			// (num 기준 내림차순 정렬, 10개로 제한)
			// 시작행 번호부터 페이지 당 게시물 목록 수만큼만 조회 (LIMIT 시작행 번호, 페이지 당 게시물 목록 수)
			// 검색 기능 추가!
			String sql = "SELECT * FROM board WHERE subject LIKE ? ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			
			// 4단계
			rs = pstmt.executeQuery();
			
			// 전체 게시물 목록을 저장할 ArrayList 객체 생성
			boardList = new ArrayList<BoardDTO>();
			
			while(rs.next()) {
				// 1개 레코드를 저장할 BoardDTO 객체 생성 후 데이터 저장
				BoardDTO board = new BoardDTO();
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setPass(rs.getString("pass"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setReadcount(rs.getInt("readcount"));
				board.setDate(rs.getTimestamp("date"));
				board.setId(rs.getString("id"));
				
				// 전체 레코드를 저장할 ArrayList<BoardDTO> 객체에 1개 레코드가 저장된 BoardDTO 객체 추가
				boardList.add(board);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectBoardList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return boardList;
	}
	
	// ----------------------------------------------------------------------------------
	// 최근 게시물 5개 목록 조회하는 selectRecentlyBoardList() 메소드 
	// => 파라미터 : 없음, 리턴타입 : ArrayList<BoardDTO>(boardList)
	public ArrayList<BoardDTO> selectRecentlyBoardList() {
		ArrayList<BoardDTO> boardList = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT * FROM board ORDER BY num DESC LIMIT 5";
			pstmt = con.prepareStatement(sql);
			
			// 4 단계
			rs = pstmt.executeQuery();
			boardList = new ArrayList<BoardDTO>();
			
			while(rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setNum(rs.getInt("num"));
				board.setSubject(rs.getString("subject"));
				board.setName(rs.getString("name"));
				board.setDate(rs.getTimestamp("date"));
				board.setId(rs.getString("id"));
				boardList.add(board);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectRecentlyBoardList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		
		
		return boardList;
	}
	
	
	// ----------------------------------------------------------------------------------
	
	// 글쓰기 작업을 수행하는 insertBoard() 메소드 정의
	// => 파라미터 : BoardDTO 객체(board)   리턴타입 : int(insertCount)
	public int insertBoard(BoardDTO board) {
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
			String sql = "SELECT MAX(num) FROM board";
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
			sql = "INSERT INTO board VALUES (?,?,?,?,?,?,now(),?)";
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setString(2, board.getName());
			pstmt2.setString(3, board.getPass());
			pstmt2.setString(4, board.getSubject());
			pstmt2.setString(5, board.getContent());
			pstmt2.setInt(6, 0); // 조회수
			pstmt2.setString(7, board.getId());
			
			// 4단계. SQL 구문 실행 및 결과 처리
			insertCount = pstmt2.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - insertBoard()");
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
			String sql = "UPDATE board SET readcount=readcount+1 WHERE num=?";
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
	
	// -------------------------------------------------------------------------------------

	// 게시물 1개 조회 작업 수행하는 selectBoard() 메소드 정의 
	// => 파라미터 : 글번호(num)   리턴타입 : BoardDTO(board)
	public BoardDTO selectBoard(int num) {
		BoardDTO board = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "SELECT * FROM board WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			// 4 단계
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board = new BoardDTO();
				
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setPass(rs.getString("pass"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setReadcount(rs.getInt("readcount"));
				board.setDate(rs.getTimestamp("date"));
				board.setId(rs.getString("id"));
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectBoard()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return board;
	}
	
	// -----------------------------------------------------------------------------------------
	
	// 글 삭제 작업 수행하는 deleteBoard() 메소드
	// => 파라미터 : 글번호, 패스워드     리턴타입 : int(deleteCount)
	public int deleteBoard(int num, String pass) {
		int deleteCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "DELETE FROM board WHERE num=? AND pass=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, pass);
			
			// 4 단계
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - deleteBoard()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		return deleteCount;
	}
	
	// -----------------------------------------------------------------------------------
	
	// 글 수정 작업 수행하는 updateBoard() 메소드
	// => 파라미터 : BoardDTO 객체, 리턴타입 : int(deleteCount)
	public int updateBoard(BoardDTO board) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();
			
			// 3 단계
			String sql = "UPDATE board SET subject=?, content=?  WHERE num=? AND pass=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getSubject());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getNum());
			pstmt.setString(4, board.getPass());
			
			// 4 단계
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - updateBoard()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		
		return updateCount;
	}
}