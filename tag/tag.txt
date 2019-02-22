//중복확인 (db연동)
mbOverlab.setOnAction((e) ->{
	db2ArrayList=MemberDAO.getIdData();
	for(String str2:db2ArrayList) {
		if(mbId.getText().equals(str2)) {
			callAlent("중복확인 : 이미 사용중인 아이디입니다.");
			return;
		}
	}
	for(String str2:db2ArrayList) {
		if(mbId.getText().equals(str2)==false){
			callAlent("중복확인 : 사용가능한 아이디입니다.");
			return;
		}
	}
});

// 접속자 리스트뷰 정보(db연동)
  private void loginListView() {
	  userList=UserDAO.getCheckData();
		for(String user:userList) {
			RootController.loginList.add(user);
		}
	  svListView.setItems(RootController.loginList);
}

// 새로고침 이벤트(db연동)
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

//테이블뷰에 웹검색 기능 걸어두기
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
		}catch(Exception e3) {}	
	}else {
		return;
	}
});