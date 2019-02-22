package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.BlackList;
import Model.Login;
import Model.MemberInfomation;
import Model.MemberTable;
import Model.MusicRecommend;
import Model.RcNum;
import Model.RcSinger;
import Model.Sing;
import Model.SingRecommend;
import Model.User;

	
public class MemberDAO {
	public static ArrayList<RcSinger> rsList= new ArrayList<>();
	public static ArrayList<MusicRecommend> mrList= new ArrayList<>();
	public static ArrayList<MemberInfomation> dbAarryList= new ArrayList<>();
	public static ArrayList<MemberTable> memberTb= new ArrayList<>();
	public static ArrayList<Login> loginAarryList= new ArrayList<>();
	public static ArrayList<BlackList> blackAarryList= new ArrayList<>();
	public static ArrayList<String> loginList= new ArrayList<>();
	public static ArrayList<String> idList= new ArrayList<>();
	
	public static int insertMemberData(MemberInfomation member) {
		// 데이터베이스 회원테이블에 입력하는 쿼리문
		StringBuffer insertMember=new StringBuffer();
		insertMember.append("insert into membertbl ");
		insertMember.append("(userid,password,name,age,usernick,gender,genre,blacklist,blcause) ");
		insertMember.append("values ");
		insertMember.append("(?,?,?,?,?,?,?,?,?) ");
		
		Connection con =null;
		
		PreparedStatement psmt=null;
		int count=0;
		try {
			con=DBUtility.getConnection();
			psmt=con.prepareStatement(insertMember.toString());
			
			psmt.setString(1, member.getMbId());
			psmt.setString(2, member.getMbPassword());
			psmt.setString(3, member.getMbName());
			psmt.setString(4, member.getMbAge());
			psmt.setString(5, member.getMbNickName());
			psmt.setString(6, member.getMbGender());
			psmt.setString(7, member.getMbGenre());
			psmt.setString(8, "x");
			psmt.setString(9, "해당없음");
			
			count=psmt.executeUpdate();
			if(count==0) {
				RootController.callAlent("삽입쿼리 실패 : DB삽입쿼리 실패");
				return count;
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
		return count;
	}
	
	public static int insertSingData(SingRecommend sr) {
		// 데이터베이스 노래추천테이블에 입력하는 쿼리문
		StringBuffer insertSing=new StringBuffer();
		insertSing.append("insert into topsinger ");
		insertSing.append("(singer,title,genre,userid) ");
		insertSing.append("values ");
		insertSing.append("(?,?,?,?) ");
		
		Connection con =null;
		
		PreparedStatement psmt=null;
		int count=0;
		try {
			con=DBUtility.getConnection();
			psmt=con.prepareStatement(insertSing.toString());
			
			psmt.setString(1, sr.getSinger());
			psmt.setString(2, sr.getSingTitle());
			psmt.setString(3, sr.getGenre()); 
			psmt.setString(4, sr.getUserid());
		
			count=psmt.executeUpdate();
			if(count==0) {
				RootController.callAlent("삽입쿼리 실패 : DB삽입쿼리 실패");
				return count;
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
		return count;
	}
	
	public static int insertBlackData(BlackList bl) {
		// 데이터베이스 노래추천테이블에 입력하는 쿼리문
		StringBuffer insertSing=new StringBuffer();
		insertSing.append("insert into member ");
		insertSing.append("(blacklist,blcause) ");
		insertSing.append("values ");
		insertSing.append("(?,?) ");
		
		Connection con =null;
		
		PreparedStatement psmt=null;
		int count=0;
		try {
			con=DBUtility.getConnection();
			psmt=con.prepareStatement(insertSing.toString());
			
			psmt.setString(1, bl.getBlackid());
			psmt.setString(2, bl.getBlackCause());
			
			count=psmt.executeUpdate();
			if(count==0) {
				RootController.callAlent("삽입쿼리 실패 : DB삽입쿼리 실패");
				return count;
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
		return count;
	}
	
	public static ArrayList<MemberInfomation> getMemberTotalData(){
		// 데이터베이스 회원테이블로 모두 가져오는 쿼리문
		String selectMember="select * from membertbl ";
				
				Connection con =null;
				
				PreparedStatement psmt=null;
				
				ResultSet rs=null;
				try {
					con=DBUtility.getConnection();
					psmt=con.prepareStatement(selectMember);
					rs=psmt.executeQuery();
					if(rs==null) {
						RootController.callAlent("Select 실패 : Select 실패하였습니다");
						return null;
					}
					while(rs.next()) {
						MemberInfomation member=new MemberInfomation(
							rs.getString(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getString(7));
					dbAarryList.add(member);
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
		return dbAarryList;
	}
	
	public static ArrayList<MemberTable> getMemberTableData(){
		// 데이터베이스 회원테이블로 관리만 가져오는 쿼리문
		String selectMember="select userid,name,age,usernick,gender,genre,blacklist,blcause from membertbl ";
				
				Connection con =null;
				
				PreparedStatement psmt=null;
				
				ResultSet rs=null;
				try {
					con=DBUtility.getConnection();
					psmt=con.prepareStatement(selectMember);
					rs=psmt.executeQuery();
					if(rs==null) {
						RootController.callAlent("Select 실패 : Select 실패하였습니다");
						return null;
					}
					while(rs.next()) {
						MemberTable member2=new MemberTable(
							rs.getString(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getString(7),
							rs.getString(8));
						memberTb.add(member2);
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
		return memberTb;
	}
	
	public static ArrayList<Login> getLoginData(){
		//데이터베이스 멤버테이블로 로그인 정보만 가져오는 쿼리문
		String selectLogin="select userid,password from membertbl ";
				
				Connection con =null;
				
				PreparedStatement psmt=null;
				
				ResultSet rs=null;
				try {
					con=DBUtility.getConnection();
					psmt=con.prepareStatement(selectLogin);
					rs=psmt.executeQuery();
					if(rs==null) {
						RootController.callAlent("Select 실패 : Select 실패하였습니다");
						return null;
					}
					while(rs.next()) {
						Login login=new Login(
								rs.getString(1),
								rs.getString(2));
						loginAarryList.add(login);
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
		return loginAarryList;
	}
	
	public static String getBlackData(String userid){
		String page=null;
		// 데이터베이스 멤버테이블로 블랙 정보만 가져오는 쿼리문
		String selectLogin="select blacklist from membertbl where userid= "+"'"+userid+"'";
	
		Connection con =null;
		
		PreparedStatement psmt=null;
		
		ResultSet rs=null;
		try {
			con=DBUtility.getConnection();
			psmt=con.prepareStatement(selectLogin);
			rs=psmt.executeQuery();
			if(rs==null) {
				RootController.callAlent("Select 실패 : Select 실패하였습니다");
				return null;
			}
				while(rs.next()) {
				page=rs.getString("blacklist");
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
		return page;
	}
		
	public static ArrayList<String> getIdData(){
		// 데이터베이스 멤버테이블로 아이디 정보만 가져오는 쿼리문
		String selectId="select userid from membertbl ";
				
				Connection con =null;
				
				PreparedStatement psmt=null;
				
				ResultSet rs=null;
				try {
					con=DBUtility.getConnection();
					psmt=con.prepareStatement(selectId);
					rs=psmt.executeQuery();
					if(rs==null) {
						RootController.callAlent("Select 실패 : Select 실패하였습니다");
						return null;
					}
					while(rs.next()) {
						idList.add(rs.getString(1));
					}
				} catch (SQLException e) {
					RootController.callAlent("삽입 실패 : DB삽입 실패");
				} finally {
					//1.6 자원객체를 닫아야 한다.
					try {
						if(psmt!=null) {psmt.close();}
						if(con!=null) {con.close();}
						} catch (SQLException e) {
							RootController.callAlent("자원닫기 실패 : psmt, con 닫기 실패");
						}
				}
		return idList;
	}
	public static ArrayList<MusicRecommend> getTopSingerData(){
		// 데이터베이스 top10노래추천으로 모두 가져오는 쿼리문
		String selectMember="select singer, title, genre, sum(rcnum) from topsinger GROUP BY singer,title order by sum(rcnum) desc ";
				
				Connection con =null;
				
				PreparedStatement psmt=null;
				
				ResultSet rs=null;
				try {
					con=DBUtility.getConnection();
					psmt=con.prepareStatement(selectMember);
					rs=psmt.executeQuery();
					if(rs==null) {
						RootController.callAlent("Select 실패 : Select 실패하였습니다");
						return null;
					}
					mrList.clear();
					while(rs.next()) {
						MusicRecommend member=new MusicRecommend(
							rs.getString(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4));
						mrList.add(member);
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
		return mrList;
	}
	
	public static ArrayList<RcSinger> getRcSingerData(){
		// 데이터베이스 top10노래추천으로 모두 가져오는 쿼리문
		String selectMember="select singer,title,genre,userid from topsinger ";
				
				Connection con =null;
				
				PreparedStatement psmt=null;
				
				ResultSet rs=null;
				try {
					con=DBUtility.getConnection();
					psmt=con.prepareStatement(selectMember);
					rs=psmt.executeQuery();
					if(rs==null) {
						RootController.callAlent("Select 실패 : Select 실패하였습니다");
						return null;
					}
					rsList.clear();
					while(rs.next()) {
						RcSinger singer=new RcSinger(
							rs.getString(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4));
						rsList.add(singer);
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
		return rsList;
	}
	
		public static int deleteMemberData(String userid) {
			// TableView에서 선택한 레코드를 데이타베이스에서 삭제하는 함수.
					String deleteMember="delete from membertbl where userid =? ";
							
							Connection con =null;
						
							PreparedStatement psmt=null;
							int count=0;
							try {
								con=DBUtility.getConnection();
								psmt=con.prepareStatement(deleteMember);
								psmt.setString(1, userid);
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
		
		public static int deleteSingData(String singer) {
			// TableView에서 선택한 레코드를 데이타베이스에서 삭제하는 함수.
			String deleteMember="delete from topsinger where singer =? ";
			
			Connection con =null;
			
			PreparedStatement psmt=null;
			
			int count=0;
			try {
				con=DBUtility.getConnection();
				psmt=con.prepareStatement(deleteMember);
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
	
		public static int updateMemberData(MemberTable member) {
			// 테이블뷰에서 수정한 레코드를 데이터베이스 테이블에 수정하는 함수.
			StringBuffer updateMember=new StringBuffer();
			updateMember.append("update membertbl set ");
			updateMember.append("usernick=?, genre=? where userid=? ");
	
			Connection con =null;
		
			PreparedStatement psmt=null;
			int count=0;
			try {
				con=DBUtility.getConnection();
				psmt=con.prepareStatement(updateMember.toString());
				psmt.setString(1, member.getUserNick());
				psmt.setString(2, member.getGenre());
				psmt.setString(3, member.getUserid());
				
				count=psmt.executeUpdate();
				if(count==0) {
					RootController.callAlent("수정쿼리 실패 : DB수정쿼리 실패");
					return count;
				}
			} catch (SQLException e) {
				RootController.callAlent("수정 실패 : DB수정 실패");
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
		
		public static int updateBlackData(MemberTable black) {
			// 테이블뷰에서 블랙리스트를 추가하는 함수.
			StringBuffer updateMember2=new StringBuffer();
			updateMember2.append("update membertbl set ");
			updateMember2.append("userid=?, name=?, age=?, usernick=?, gender=?, genre=?, blacklist=?, blcause=? where userid=? ");
			
			Connection con =null;
			
			PreparedStatement psmt=null;
			int count=0;
			try {
				con=DBUtility.getConnection();
				psmt=con.prepareStatement(updateMember2.toString());
				
				psmt.setString(1, black.getUserid());
				psmt.setString(2, black.getName());
				psmt.setString(3, black.getAge());
				psmt.setString(4, black.getUserNick());
				psmt.setString(5, black.getGender());
				psmt.setString(6, black.getGenre());
				psmt.setString(7, black.getBlacklist());
				psmt.setString(8, black.getBlcause());
				psmt.setString(9, black.getUserid());
				
				count=psmt.executeUpdate();
				if(count==0) {
					RootController.callAlent("수정쿼리 실패 : DB수정쿼리 실패");
					return count;
				}
			} catch (SQLException e) {
				RootController.callAlent("수정 실패 : DB수정 실패");
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


