package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.User;

public class UserDAO {
	
	public static ArrayList<String> userAarryList= new ArrayList<>();

	public static int getUsersData(User userid){
		// 데이터베이스 멤버테이블로 로그인 정보만 가져오는 쿼리문
		StringBuffer insertMember=new StringBuffer();
		insertMember.append("insert into usertbl ");
		insertMember.append("(userid) ");
		insertMember.append("values ");
		insertMember.append("(?) ");
		
		Connection con =null;
		
		PreparedStatement psmt=null;
		int count=0;
		try {
			con=DBUtility.getConnection();
			psmt=con.prepareStatement(insertMember.toString());
			psmt.setString(1, userid.getUserid());
			count=psmt.executeUpdate();
			if(count==0) {
				RootController.callAlent("삽입쿼리 실패 : DB삽입쿼리 실패");
				return count;
			}
		} catch (SQLException e) {
			RootController.callAlent("로그인 실패 : 로그인 중입니다.");
		} finally {
			try {
				if(psmt!=null) {psmt.close();}
				if(con!=null) {con.close();}
				} catch (SQLException e) {
					RootController.callAlent("자원닫기 실패 : psmt, con 닫기 실패");
				}
		}
		return count;
	}
	
	public static ArrayList<String> getCheckData(){
		// 데이터베이스 멤버테이블로 로그인 정보만 가져오는 쿼리문
		String selectUser="select * from usertbl ";
				
				Connection con =null;
				
				PreparedStatement psmt=null;
				
				ResultSet rs=null;
				try {
					con=DBUtility.getConnection();
					psmt=con.prepareStatement(selectUser);
					rs=psmt.executeQuery();
					if(rs==null) {
						RootController.callAlent("Select 실패 : Select 실패하였습니다");
						return null;
					}
					while(rs.next()) {
						User user=new User(
								rs.getString(1));
						userAarryList.add(user.getUserid());
					}
				} catch (SQLException e) {
					RootController.callAlent("삽입 실패 : DB삽입 실패");
				} finally {
					try {
						if(psmt!=null) {psmt.close();}
						if(con!=null) {con.close();}
						} catch (SQLException e) {
							RootController.callAlent("자원닫기 실패 : psmt, con 닫기 실패");
						}
				}
		return userAarryList;
	}
	
			public static int deleteUserData(String user) {
				// TableView에서 선택한 레코드를 데이타베이스에서 삭제하는 함수.
				String deleteMember="delete from usertbl where userid =? ";
				
				Connection con =null;
				
				PreparedStatement psmt=null;
				
				int count=0;
				try {
					con=DBUtility.getConnection();
					psmt=con.prepareStatement(deleteMember);
					psmt.setString(1, user);
					count=psmt.executeUpdate();
					if(count==0) {
						RootController.callAlent("Delete 실패 : Delete 실패하였습니다");
						return count;
					}
				} catch (SQLException e) {
					RootController.callAlent("Delete 실패 : DBDelete 실패");
				} finally {
					try {
						if(psmt!=null) {psmt.close();}
						if(con!=null) {con.close();}
					} catch (SQLException e) {
						RootController.callAlent("자원닫기 실패 : psmt, con 닫기 실패");
					}
				}
				return count;
			}
}
