//�ߺ�Ȯ�� (db����)
mbOverlab.setOnAction((e) ->{
	db2ArrayList=MemberDAO.getIdData();
	for(String str2:db2ArrayList) {
		if(mbId.getText().equals(str2)) {
			callAlent("�ߺ�Ȯ�� : �̹� ������� ���̵��Դϴ�.");
			return;
		}
	}
	for(String str2:db2ArrayList) {
		if(mbId.getText().equals(str2)==false){
			callAlent("�ߺ�Ȯ�� : ��밡���� ���̵��Դϴ�.");
			return;
		}
	}
});

// ������ ����Ʈ�� ����(db����)
  private void loginListView() {
	  userList=UserDAO.getCheckData();
		for(String user:userList) {
			RootController.loginList.add(user);
		}
	  svListView.setItems(RootController.loginList);
}

// ���ΰ�ħ �̺�Ʈ(db����)
private void handlerNew() {
	//��õ�뷡 ���̺�
	mrList.removeAll(mrList);
	singList=MemberDAO.getTopSingerData();
	for(MusicRecommend mr:singList) {
		mrList.add(mr);
	}
	svTableView.setItems(mrList);
	//������ Ȯ��
	RootController.loginList.removeAll(RootController.loginList);
	userList.clear();
	svListView.setItems(RootController.loginList);
	userList=UserDAO.getCheckData();
	for(String user:userList) {
		RootController.loginList.add(user);
	}
	svListView.setItems(RootController.loginList);
	//ȸ�� ����
	mbTable.removeAll(mbTable);
	dbArrayList.clear();
	dbArrayList=MemberDAO.getMemberTableData();
	for(MemberTable member:dbArrayList) {
		mbTable.add(member);
	}
	memberInfo.setItems(mbTable);
}

//���̺�信 ���˻� ��� �ɾ�α�
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
			srStage.setTitle("���� �˻�â");
					
			Button btnExit=(Button)root.lookup("#btnExit");
			WebView link=(WebView)root.lookup("#link");
			WebEngine web=link.getEngine();
			web.load("https://www.youtube.com/results?search_query="+selectSr.getSinger()+" "+selectSr.getSingTitle());
		}catch(Exception e3) {}	
	}else {
		return;
	}
});