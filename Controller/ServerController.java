package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.function.LongToIntFunction;

import Model.BlackList;
import Model.MemberInfomation;
import Model.MemberTable;
import Model.MusicRecommend;
import Model.RcSinger;
import Model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ServerController implements Initializable {
	public Stage serverStage;
	public Stage clientStage;
   @FXML private TableView<MusicRecommend>svTableView;
   @FXML private ListView<String> svListView;
   @FXML private Button svBtnNew;
   @FXML private Button svMusicList;
   @FXML private Button svChat;
   @FXML private Button svExit;
   @FXML public TableView<MemberTable> memberInfo;
   @FXML private TextField txtSearch;
   @FXML private Button btnSearch;
   @FXML private Button blackRegister;
   @FXML private Button blackClear;
   @FXML private Button infoEdit;
   @FXML private Button infoDelete;
   private TableView<RcSinger> mlTableView;
   ObservableList<RcSinger> recList = FXCollections.observableArrayList();
   public static ObservableList<MusicRecommend> mrList = FXCollections.observableArrayList();
   public ObservableList<MemberInfomation> mbList= FXCollections.observableArrayList();
   public static ObservableList<MemberTable> mbTable= FXCollections.observableArrayList();
   ObservableList<String> searchList = FXCollections.observableArrayList();
   public MemberInfomation selectMi;
   public int selectMiIndex;
	public MusicRecommend selectSr;
	public int selectSrIndex;
	public static MemberTable selectMb;
	public static int selectMbIndex;
	public BlackList selectBl;
	public int selectBlIndex;
	public RcSinger selectRc;
	public int selectRcIndex;
	public Client selectLogin;
	public int selectLoginIndex;
	private ServerSocket serverSocket;
	private Thread mainthread;
	Socket socket;
	TextArea ctTxtArea=null;
	public static List<Client> list=new Vector<Client>();
	public static ArrayList<MemberTable> dbArrayList;
	ArrayList<MusicRecommend> singList;
	ArrayList<RcSinger> rsList;
	ArrayList<String> userList;
	ObservableList<String> cmGenre = FXCollections.observableArrayList();
	ArrayList<String> db2ArrayList;
	ArrayList<MemberInfomation> memberList;
	
	
   @Override
   public void initialize(URL location, ResourceBundle resources) {
	   if(serverStage==null) {
		   startServer();
	   }
	 //탭1. 노래추천구간
	  singRecommendTab();
	  //접속자 리스트뷰 정보
	  loginListView();
	  // 새로고침 이벤트
	  svBtnNew.setOnAction((e)->{
		  loginListView();
		  handlerNew();});
	  //추천목록 이벤트
	  svMusicList.setOnAction((e)->{ handlerMusicList();}); 
	  //채팅창 이벤트
	  svChat.setOnAction((e)->{handlerChatting();});
	  //나가기 이벤트
	  svExit.setOnAction((e)-> {
		  stopServer();
		  serverStage.close();
		  });
	 
	//탭2. 회원관리테이블
	  memberInformationTab();
	  //검색버튼 이벤트
	  btnSearch.setOnAction((e)->{handlerMemberSearch();});
	  //제재등록 이벤트
	  blackRegister.setOnAction((e)-> {handlerBlackList();});
	  //제재해제 이벤트
	  blackClear.setOnAction((e)-> {handlerBlackclear();});
	  //회원정보 수정 이벤트
	  infoEdit.setOnAction((e)-> {handlerMemberEdit();});
	  //회원 삭제 이벤트
	  infoDelete.setOnAction((e)-> {handlerMemberDel();});

   }
// 접속자 리스트뷰 정보
  private void loginListView() {
	  userList=UserDAO.getCheckData();
		for(String user:userList) {
			RootController.loginList.add(user);
		}
	  svListView.setItems(RootController.loginList);
}

// 새로고침 이벤트
private void handlerNew() {
	//추천노래 테이블
	mrList.removeAll(mrList);
	singList=MemberDAO.getTopSingerData();
	for(MusicRecommend mr:singList) {
		mrList.add(mr);
	}
	svTableView.setItems(mrList);
	//접속자 확인
	RootController.loginList.removeAll(RootController.loginList);
	userList.clear();
	svListView.setItems(RootController.loginList);
	userList=UserDAO.getCheckData();
	for(String user:userList) {
		RootController.loginList.add(user);
	}
	svListView.setItems(RootController.loginList);
	//회원 관리
	mbTable.removeAll(mbTable);
	dbArrayList.clear();
	dbArrayList=MemberDAO.getMemberTableData();
	for(MemberTable member:dbArrayList) {
		mbTable.add(member);
	}
	memberInfo.setItems(mbTable);
}

//탭1. 노래추천구간
   private void singRecommendTab() {
		TableColumn tcSinger = svTableView.getColumns().get(0);
		tcSinger.setCellValueFactory(new PropertyValueFactory<>("singer"));
		tcSinger.setStyle("-fx-alignment : CENTER;");
		TableColumn tcSingTitle = svTableView.getColumns().get(1);
		tcSingTitle.setCellValueFactory(new PropertyValueFactory<>("singTitle"));
		tcSingTitle.setStyle("-fx-alignment : CENTER;");
		TableColumn tcGenre = svTableView.getColumns().get(2);
		tcGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		tcGenre.setStyle("-fx-alignment : CENTER;");
		TableColumn tcRcNum = svTableView.getColumns().get(3);
		tcRcNum.setCellValueFactory(new PropertyValueFactory<>("rcNum"));
		tcRcNum.setStyle("-fx-alignment : CENTER;");

		singList=MemberDAO.getTopSingerData();
		for(MusicRecommend mr:singList) {
			mrList.add(mr);
		}
		svTableView.setItems(mrList);
		svTableView.setOnMouseClicked((e)-> {
			if(e.getClickCount()==2) {
				selectSr = svTableView.getSelectionModel().getSelectedItem();
				selectSrIndex = svTableView.getSelectionModel().getSelectedIndex();
				try {
					Stage srStage=new Stage();
					FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/link.fxml"));
					Parent root=loader.load();
					Scene scene=new Scene(root);
					srStage.setScene(scene);
					srStage.show();
					srStage.setTitle("음악 검색창");
					
					Button btnExit=(Button)root.lookup("#btnExit");
					WebView link=(WebView)root.lookup("#link");
					WebEngine web=link.getEngine();
					web.load("https://www.youtube.com/results?search_query="+selectSr.getSinger()+" "+selectSr.getSingTitle());
					
					btnExit.setOnAction((e2)-> {
						srStage.close();
						web.load(null);
					});
				}catch(Exception e3) {}
			}else {
				return;					
			}
	});
   }
//추천목록 이벤트
   private void handlerMusicList() {
	try {
		Stage mlStage=new Stage();
		FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/music_list.fxml"));
		Parent root=loader.load();
		Scene scene=new Scene(root);
		mlStage.setScene(scene);
		mlStage.setTitle("추천목록");
		mlStage.show();
		
		mlTableView=(TableView)root.lookup("#listTableView");
		TextField mlTxtSearch=(TextField)root.lookup("#listTxtSearch");
		Button mlBtnSearch=(Button)root.lookup("#listBtnSearch");
		Button mlBtnClear=(Button)root.lookup("#listBtnClear");
		Button mlBtnDelete=(Button)root.lookup("#listBtnDelete");
		Button mlBtnExit=(Button)root.lookup("#listBtnExit");
		ComboBox<String> mlCmbList=(ComboBox)root.lookup("#cmbList");
		
		TableColumn tcSinger = mlTableView.getColumns().get(0);
		tcSinger.setCellValueFactory(new PropertyValueFactory<>("singer"));
		tcSinger.setStyle("-fx-alignment : CENTER;");
		TableColumn tcSingTitle = mlTableView.getColumns().get(1);
		tcSingTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		tcSingTitle.setStyle("-fx-alignment : CENTER;");
		TableColumn tcGenre = mlTableView.getColumns().get(2);
		tcGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		tcGenre.setStyle("-fx-alignment : CENTER;");
		TableColumn tcRecommend = mlTableView.getColumns().get(3);
		tcRecommend.setCellValueFactory(new PropertyValueFactory<>("userid"));
		tcRecommend.setStyle("-fx-alignment : CENTER;");
		
			mlTableView.setItems(recList);
			recList.clear();
			rsList=MemberDAO.getRcSingerData();
			for(RcSinger rs:rsList) {
				recList.add(rs);
			}
			searchList.clear();
			searchList.addAll("가수","노래제목","장르","추천인");
			mlCmbList.setItems(searchList);
		
		mlBtnSearch.setOnAction((e)->{
			for (RcSinger mr : rsList) {
				if (mlTxtSearch.getText().trim().equals(mr.getSinger())){
					mlTableView.getSelectionModel().select(mr);
				}else if(mlTxtSearch.getText().trim().equals(mr.getTitle())) {
					mlTableView.getSelectionModel().select(mr);
				}else if(mlTxtSearch.getText().trim().equals(mr.getGenre())) {
					mlTableView.getSelectionModel().select(mr);
				}else if(mlTxtSearch.getText().trim().equals(mr.getUserid())) {
					mlTableView.getSelectionModel().select(mr);
				}
			}
		});
		mlBtnClear.setOnAction((e)->{ 
			mlCmbList.getSelectionModel().clearSelection();
			mlTxtSearch.clear();
			mlTableView.getSelectionModel().clearSelection();
		});
		mlBtnDelete.setOnAction((e)->{
			selectRc = mlTableView.getSelectionModel().getSelectedItem();
			selectRcIndex=mlTableView.getSelectionModel().getSelectedIndex();
			int count=MemberDAO.deleteSingData(selectRc.getSinger());
			if(count!=0) {
				recList.remove(selectRcIndex);
				rsList.remove(selectRc);
			}
		});
		mlBtnExit.setOnAction((e)->{mlStage.close();});
	} catch (Exception e) {}
}

   //채팅방 이벤트
   public void handlerChatting() {
	try {
		Stage chatStage=new Stage();
		FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/chatting.fxml"));
		Parent root=loader.load();
		Scene scene=new Scene(root);
		chatStage.setScene(scene);
		chatStage.setTitle("채팅방");
		chatStage.show();
		
		TextField ctTxtField=(TextField)root.lookup("#ctTxtField");
		ctTxtArea=(TextArea)root.lookup("#ctTxtArea");
		Button ctBtnSend=(Button)root.lookup("#ctBtnSend");
		ctTxtArea.setEditable(false);
		
		ctBtnSend.setOnAction((e) ->{
			Client client =new Client(socket);
			client.send(ctTxtField.getText());
			ctTxtArea.appendText(("관리자 :"+ctTxtField.getText()+"\n"));
			ctTxtField.clear();
		});
		
		ctTxtField.setOnKeyPressed(event -> {
			 if (event.getCode().equals(KeyCode.ENTER)) {
				 Client client =new Client(socket);
				 client.send(ctTxtField.getText());
				 ctTxtArea.appendText(("관리자 :"+ctTxtField.getText()+"\n"));
				 ctTxtField.clear();
	            }
		});
	}catch(Exception e) {}
}

//탭2. 회원관리테이블
   private void memberInformationTab() {
	
	TableColumn tcMbId = memberInfo.getColumns().get(0);
	tcMbId.setCellValueFactory(new PropertyValueFactory<>("userid"));
	tcMbId.setStyle("-fx-alignment : CENTER;");
	TableColumn tcMbName = memberInfo.getColumns().get(1);
	tcMbName.setCellValueFactory(new PropertyValueFactory<>("name"));
	tcMbName.setStyle("-fx-alignment : CENTER;");
	TableColumn tcMbAge = memberInfo.getColumns().get(2);
	tcMbAge.setCellValueFactory(new PropertyValueFactory<>("age"));
	tcMbAge.setStyle("-fx-alignment : CENTER;");
	TableColumn tcMbNick = memberInfo.getColumns().get(3);
	tcMbNick.setCellValueFactory(new PropertyValueFactory<>("userNick"));
	tcMbNick.setStyle("-fx-alignment : CENTER;");
	TableColumn tcMbGender = memberInfo.getColumns().get(4);
	tcMbGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
	tcMbGender.setStyle("-fx-alignment : CENTER;");
	TableColumn tcMbGenre = memberInfo.getColumns().get(5);
	tcMbGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
	tcMbGenre.setStyle("-fx-alignment : CENTER;");
	TableColumn tcMbBlack = memberInfo.getColumns().get(6);
	tcMbBlack.setCellValueFactory(new PropertyValueFactory<>("blacklist"));
	tcMbBlack.setStyle("-fx-alignment : CENTER;");
	TableColumn tcMbBc = memberInfo.getColumns().get(7);
	tcMbBc.setCellValueFactory(new PropertyValueFactory<>("blcause"));
	tcMbBc.setStyle("-fx-alignment : CENTER;");
	
	dbArrayList=MemberDAO.getMemberTableData();
	for(MemberTable member:dbArrayList) {
		mbTable.add(member);
	}
	memberInfo.setItems(mbTable);
}
//회원 검색 이벤트
   private void handlerMemberSearch() {
	for (MemberTable mb : dbArrayList) {
		if (txtSearch.getText().trim().equals(mb.getName())) {
			memberInfo.getSelectionModel().select(mb);
		}
	}
}
//제재 등록 이벤트
   private void handlerBlackList() {
	try {
		Stage blackStage=new Stage();
		FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/blacklist.fxml"));
		Parent root=loader.load();
		Scene scene=new Scene(root);
		blackStage.setScene(scene);
		blackStage.setTitle("제재 등록");
		blackStage.show();
		
		TextField blackId=(TextField)root.lookup("#blackId");
		TextArea blackCause=(TextArea)root.lookup("#blackCause");
		Button blackBtnOk=(Button)root.lookup("#blackBtnOk");
		Button blackBtnExit=(Button)root.lookup("#blackBtnExit");
		
		selectMb=memberInfo.getSelectionModel().getSelectedItem();
		selectMbIndex=memberInfo.getSelectionModel().getSelectedIndex();
		
		blackId.setText(selectMb.getUserid());
		blackId.setDisable(true);
		blackBtnOk.setOnAction((e)->{
			MemberTable blackList = new MemberTable(
					selectMb.getUserid(), 
					selectMb.getName(), 
					selectMb.getAge(), 
					selectMb.getUserNick(), 
					selectMb.getGender(), 
					selectMb.getGenre(), 
					"O",
					blackCause.getText());
			int count =MemberDAO.updateBlackData(blackList);
			if(count!=0) {
				mbTable.remove(selectMbIndex);
				mbTable.add(selectMbIndex,blackList);
			}
			callAlert("제재완료 : "+selectMb.getUserid()+"님이 제재되었습니다.");
			blackStage.close();
		});
		blackBtnExit.setOnAction((e)->{blackStage.close();});
	}catch(Exception e) {}
}
 //제재 해제 이벤트
   private void handlerBlackclear() {
	   selectMb=memberInfo.getSelectionModel().getSelectedItem();
	   selectMbIndex=memberInfo.getSelectionModel().getSelectedIndex();
	   
	   MemberTable blackClear = new MemberTable(
				selectMb.getUserid(), 
				selectMb.getName(), 
				selectMb.getAge(), 
				selectMb.getUserNick(), 
				selectMb.getGender(), 
				selectMb.getGenre(), 
				"x",
				"해당없음");
		int count =MemberDAO.updateBlackData(blackClear);
		if(count!=0) {
			mbTable.remove(selectMbIndex);
			mbTable.add(selectMbIndex,blackClear);
		}
		callAlert("제재해제 완료 : "+selectMb.getUserid()+"님이 제재해제 되었습니다.");
   }

   //회원 수정 이벤트
   private void handlerMemberEdit() {
	   try {
			Stage editMbStage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/join.fxml"));
			Parent root=loader.load();
			Scene scene=new Scene(root);
			editMbStage.setScene(scene);
			editMbStage.setTitle("회원수정");
			editMbStage.show();
		
			selectMb=memberInfo.getSelectionModel().getSelectedItem();
			selectMbIndex=memberInfo.getSelectionModel().getSelectedIndex();
			
			TextField mbId = (TextField) root.lookup("#mbId");
			TextField mbName = (TextField) root.lookup("#mbName");
			TextField mbAge = (TextField) root.lookup("#mbAge");
			TextField mbNick = (TextField) root.lookup("#mbNick");
			PasswordField mbPass=(PasswordField) root.lookup("#mbPass");
			ObservableMap<String, Object>map=loader.getNamespace();
			ToggleGroup group=(ToggleGroup)map.get("group");
			RadioButton tgMan=(RadioButton)root.lookup("#tgMan");
			RadioButton tgWoman=(RadioButton)root.lookup("#tgWoman");
			Button mbOverlab=(Button)root.lookup("#mbOverlab");
			Button mbBtnOk=(Button)root.lookup("#mbBtnOk");
			Button mbBtnCancel=(Button)root.lookup("#mbBtnCancel");
			ComboBox<String> mbGenre=(ComboBox)root.lookup("#mbGenre");
			
			cmGenre.addAll("POP","발라드","재즈","클래식","락","힙합","전자");
			mbGenre.setItems(cmGenre);
			
			mbId.setText(selectMb.getUserid());
			mbId.setDisable(true);
			mbPass.setDisable(true);
			mbName.setText(selectMb.getName());
			mbName.setDisable(true);
			mbAge.setText(selectMb.getAge());
			mbAge.setDisable(true);
			mbNick.setText(selectMb.getUserNick());
			mbGenre.getSelectionModel().getSelectedItem();
			if(selectMb.getGender().equals("남자")) {
				tgMan.setSelected(true);
			}else {
				tgWoman.setSelected(true);
			}
			tgMan.setDisable(true);
			tgWoman.setDisable(true);
			mbBtnOk.setOnAction((e)->{
				MemberTable member=new MemberTable(
						mbId.getText(),
						mbName.getText(), 
						mbAge.getText(), 
						mbNick.getText(), 
						group.getSelectedToggle().getUserData().toString(),
						mbGenre.getSelectionModel().getSelectedItem(), 
						mbTable.get(selectMbIndex).getBlacklist(),
						mbTable.get(selectMbIndex).getBlcause());
				
				int count =MemberDAO.updateMemberData(member);
				if(count!=0) {
					mbTable.remove(selectMbIndex);
					mbTable.add(selectMbIndex,member);
					int arrayIndex=dbArrayList.indexOf(selectMb);
					dbArrayList.set(arrayIndex, member);
					callAlert("수정완료 : " + selectMb.getUserid() + "님이 수정되었습니다.");
					editMbStage.close();
				}else {
					return;
				}
			});
			mbBtnCancel.setOnAction((e)->{editMbStage.close();});
	   }catch(Exception e){}
   }
 //회원 삭제 이벤트
   private void handlerMemberDel() {
	   selectMb=memberInfo.getSelectionModel().getSelectedItem();
	   selectMbIndex=memberInfo.getSelectionModel().getSelectedIndex();
	   int count=MemberDAO.deleteMemberData(selectMb.getUserid());
	   if(count!=0){
		   mbTable.remove(selectMbIndex);
		   dbArrayList.remove(selectMb);
		   callAlert("삭제완료 : "+selectMb.getUserid()+"님이 삭제 되었습니다. ");
	   }else {
		   return;
	   }
   }
//클라이언트
  public class Client{
		Socket socket;
		
		public Client(Socket socket){
			this.socket = socket;
			recieve();
		}
		private void recieve() {
			Runnable runnable=new Runnable() {
				
				@Override
				public void run() {
					try {
					while(true) {
							InputStream is=socket.getInputStream();
							InputStreamReader isr=new InputStreamReader(is);
							BufferedReader br=new BufferedReader(isr);
							String receiveMessage=br.readLine();
							if(receiveMessage==null) {throw new IOException();}
							Platform.runLater(()-> {
								ctTxtArea.appendText(receiveMessage+"\n");
								for(Client client:list) {
									client.send(receiveMessage+"\n");
								}
						});
					}//end of while
					} catch (IOException e) {
						list.remove(Client.this);
					}
					
				}
			};
			Thread thread=new Thread(runnable);
			thread.start();
		}

		public void send(String message) {
			Runnable runnable=new Runnable() {
				@Override
				public void run() {
					try {
						OutputStream os=socket.getOutputStream();
						PrintWriter pw=new PrintWriter(os);
					
							pw.println(message);
						
						pw.flush();
					} catch (IOException e) {
						Platform.runLater(()-> {
							callAlert("통신 오류 : 잘못된 접근입니다");
					});
						list.remove(Client.this);
						try {
							socket.close();
						} catch (IOException e1) {}
					}
				}
			};
			Thread thread=new Thread(runnable);
			thread.start();
		}
	}
 //서버 시작 멈춤
   public void startServer() {
	   //서버 만들기
		 try {
				serverSocket=new ServerSocket();
				serverSocket.bind(new InetSocketAddress("192.168.0.188", 42135));
				
			} catch (IOException e) {
				if(!serverSocket.isClosed()) {
					stopServer();
				}
				return;
			}
			Runnable runnable=new Runnable() {
				
				@Override
				public void run() {
					//일반스레드에서 javaFX application Thread에서 해야될 일을(UI수정하려면)
					//Platform.runLater()를 불러서 작업을 요청해야된다.
					Platform.runLater(()-> {
							callAlert("서버시작 : 서버가 연결되었습니다.");
					});
					while(true) {
						try {
							socket=serverSocket.accept();		//대기상태
							Client client =new Client(socket);
							list.add(client);
						} catch (IOException e) {
							if(!serverSocket.isClosed()) {
								stopServer();
							}
							break;
						}
					}//end of while
				}
			};//end of Runnable
			mainthread=new Thread(runnable);
			mainthread.start();
   }
   public void stopServer() {
		 try {
			 if(!socket.isClosed()&&socket!=null) {
					try {
						socket.close();
					} catch (IOException e) {}
			 }
				} catch (Exception e) {
					if(mainthread.isAlive()) {
						mainthread.stop();
					}
				}
				if(!serverSocket.isClosed()&& serverSocket!=null) {
					try {
						callAlert("서버종료 : 서버를 종료합니다.");
						serverSocket.close();
						int count=UserDAO.deleteUserData(RootController.selectId);
						   if(count!=0){
							   RootController.loginList.remove(RootController.selectId);
						   }else {
							   return;
						   }
					} catch (IOException e1) {}
				}
	}
   
 //기타. 경고창함수 : 중간에 : 적어줄것, 예시 : "오류 : 오류발생"
   public static void callAlert(String contentText) {
      Alert alert= new Alert(AlertType.INFORMATION);
      alert.setTitle("알림창");
      alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
      alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
      alert.showAndWait();
   }
}