package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.JdbcUtil;

public class FileBoardDAO {
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
			String sql = "SELECT COUNT(num) FROM file_board";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
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
			String sql = "SELECT COUNT(num) FROM file_board WHERE subject LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");

			rs = pstmt.executeQuery();

			if (rs.next()) {
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

	// ---------------------------------------------------------------------------------

	// 게시물 목록 조회를 수행하는 selectBoardList() 메소드 정의
	// => 파라미터 : 시작 행번호, 페이지 당 게시물 목록 수 리턴타입 : ArrayList(boardList)
	public ArrayList<FileBoardDTO> selectBoardList(int startRow, int listLimit) {
		ArrayList<FileBoardDTO> boardList = null;

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
			String sql = "SELECT * FROM file_board ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, listLimit);

			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();

			// 전체 게시물 목록을 저장할 ArrayList 객체 생성
			boardList = new ArrayList<FileBoardDTO>();

			while (rs.next()) {
				// 1개 레코드를 저장할 FileBoardDTO 객체 생성 후 데이터 저장
				FileBoardDTO board = new FileBoardDTO();
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setOriginal_file(rs.getString("original_file"));
				board.setReal_file(rs.getString("real_file"));
				board.setReadcount(rs.getInt("readcount"));
				board.setDate(rs.getTimestamp("date"));

				// 전체 레코드를 저장할 ArrayList<FileBoardDTO> 객체에 1개 레코드가 저장된 FileBoardDTO 객체 추가
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
	// => 파라미터 : 시작 행번호, 페이지 당 게시물 목록 수, 검색어    리턴타입 : ArrayList(boardList)
	public ArrayList<FileBoardDTO> selectBoardList(int startRow, int listLimit, String keyword) {
		ArrayList<FileBoardDTO> boardList = null;

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
			String sql = "SELECT * FROM file_board WHERE subject LIKE ? ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);

			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();

			// 전체 게시물 목록을 저장할 ArrayList 객체 생성
			boardList = new ArrayList<FileBoardDTO>();

			while (rs.next()) {
				// 1개 레코드를 저장할 FileBoardDTO 객체 생성 후 데이터 저장
				FileBoardDTO board = new FileBoardDTO();
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setReadcount(rs.getInt("readcount"));
				board.setDate(rs.getTimestamp("date"));

				// 전체 레코드를 저장할 ArrayList<FileBoardDTO> 객체에 1개 레코드가 저장된 FileBoardDTO 객체 추가
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

	// ------------------------------------------------------------------------------------------------------

	// 글쓰기 작업을 수행하는 insertFileBoard() 메소드 정의
	// => 파라미터 : FileBoardDTO 객체(board) 리턴타입 : int(insertCount)
	public int insertFileBoard(FileBoardDTO board) {
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
			String sql = "SELECT MAX(num) FROM file_board";
			pstmt = con.prepareStatement(sql);

			// 4 - 1 단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();

			// 만약, 조회 결과가 있을 경우 해당 글 번호 + 1 값을 새 글 번호(num) 로 저장
			if (rs.next()) {
				num = rs.getInt(1) + 1;
			}

			// -------------------------------------------------------------------------
			// 3 - 2 단계. SQL 구문 작성 및 전달
			// => 글 쓰기(INSERT) 작업 수행
			sql = "INSERT INTO file_board VALUES (?,?,?,?,?,?,?,now())";
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setString(2, board.getName());
			pstmt2.setString(3, board.getSubject());
			pstmt2.setString(4, board.getContent());
			pstmt2.setString(5, board.getOriginal_file());
			pstmt2.setString(6, board.getReal_file());
			pstmt2.setInt(7, 0); // 조회수

			// 4단계. SQL 구문 실행 및 결과 처리
			insertCount = pstmt2.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - insertFileBoard()");
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
	// => 파라미터 : 글번호(num) 리턴타입 : void
	public void updateReadcount(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();

			// 3 단계
			String sql = "UPDATE file_board SET readcount=readcount+1 WHERE num=?";
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
	
	// ------------------------------------------------------------------------------

	// 게시물 1개 조회 작업 수행하는 selectFileBoard() 메소드 정의
	// => 파라미터 : 글번호(num) 리턴타입 : FileBoardDTO(board)
	public FileBoardDTO selectFileBoard(int num) {
		FileBoardDTO board = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();

			// 3 단계
			String sql = "SELECT * FROM file_board WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			// 4 단계
			rs = pstmt.executeQuery();

			if (rs.next()) {
				board = new FileBoardDTO();

				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setOriginal_file(rs.getString("original_file"));
				board.setReal_file(rs.getString("real_file"));
				board.setReadcount(rs.getInt("readcount"));
				board.setDate(rs.getTimestamp("date"));
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectFileBoard()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}

		return board;
	}

	// ------------------------------------------------------------------------------------

	// real_file 1개 조회 작업 수행하는 selectRealFile() 메소드 정의
	// => 파라미터 : 글번호(num) 리턴타입 : String (real_file)
	public String selectRealFile(int num) {
		String realFile = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();

			// 3 단계
			String sql = "SELECT real_file FROM file_board WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			// 4 단계
			rs = pstmt.executeQuery();

			if (rs.next()) {
				realFile = rs.getString("real_file");
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - selectRealFile()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}

		return realFile;
	}
	
	// -----------------------------------------------------------------------
	
	// 글 삭제 작업 수행하는 deleteFileBoard() 메소드
	// => 파라미터 : 글번호, 패스워드 리턴타입 : int(deleteCount)
	public int deleteFileBoard(int num) {
		int deleteCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();

			// 3 단계
			String sql = "DELETE FROM file_board WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			// 4 단계
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - deleteFileBoard()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}
		return deleteCount;
	}

	// ---------------------------------------------------------------------------------

	// 글 수정 작업 수행하는 updateFileBoard() 메소드
	// => 파라미터 : FileBoardDTO 객체, 리턴타입 : int(deleteCount)
	public int updateFileBoard(FileBoardDTO board) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 0 ~ 2 단계
			con = JdbcUtil.getConnection();

			// 3 단계
			String sql = "UPDATE file_board SET subject=?, content=?, original_file=?, real_file=?  WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getSubject());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getOriginal_file());
			pstmt.setString(4, board.getReal_file());
			pstmt.setInt(5, board.getNum());

			// 4 단계
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - updateFileBoard()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(con);
		}

		return updateCount;
	}
}
