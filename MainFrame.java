package ui_swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame implements ActionListener {

	Container c = getContentPane();

	JButton btnInput = new JButton("입력");
	JButton btnModify = new JButton("수정");
	JButton btnDelete = new JButton("삭제");
	JComboBox<String> cmbSearch = new JComboBox<>(new String[] {"학생명","전공명"});
	
	JTextField txtData = new JTextField(10);
	JButton btnSearch = new JButton("검색");
	
	JTextArea txaData = new JTextArea();
	
	JTable table = new JTable();
	
	InputDialog inputDialog = new InputDialog(this, "입력 다이얼로그", true);
	ModifyDialog modifyDialog = new ModifyDialog(this, "수정 다이얼로그", true);
	StudentNumberInputDialog studentNumberInputDialog = new StudentNumberInputDialog(this, "학번입력", true); 
	
	SqlDAO dao = SqlDAO.getInstance();
	
	static boolean buttonCheckFromInputdialog = true;
	
	//Jtable 헤더
	String head[] = {"학번","이름","학년","성별","전공","점수"};
	
	public MainFrame() {
		setTitle("UI_Swing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocation(400, 200);
		setVisible(true);
		
		setUI();
		setAction();
	}
	
	private void setAction() {
		// TODO Auto-generated method stub
		btnInput.addActionListener(this);
		btnModify.addActionListener(this);
		btnDelete.addActionListener(this);
		txtData.addActionListener(this);
		btnSearch.addActionListener(this);
	}//setAction

	private void setUI() {
		// TODO Auto-generated method stub
		
		btnInput.setBackground(Color.getHSBColor(55, 99, 88));
		btnModify.setBackground(Color.getHSBColor(55,99, 88));
		btnDelete.setBackground(Color.getHSBColor(55,99, 88));
		btnSearch.setBackground(Color.getHSBColor(55,99, 88));
		
		JPanel plNorth = new JPanel();
		plNorth.setLayout(new GridLayout(1,6));
		plNorth.add(btnInput);
		plNorth.add(btnModify);
		plNorth.add(btnDelete);
		plNorth.add(cmbSearch);
		plNorth.add(txtData);
		plNorth.add(btnSearch);
		
		c.add(plNorth, BorderLayout.NORTH);
		
		//Center
		txaData.setFont(new Font("굴림체", Font.PLAIN, 13));
		JScrollPane sp = new JScrollPane(txaData);
		
		/*
		txaData.setText("\n*입력을 누르면 다이얼로그창이 뜨고 거기에 데이터를 입력합니다\n\n"
				+ "*수정을 누르면 학번을 먼저 입력 후 해당 학번을 가져오면\n 수정 다이얼로그에서 수정 후 확인 버튼 클릭합니다\n\n"
				+ "*삭제를 누르면 학번을 입력하는 다이얼로그창이 뜬 후\n 학번입력후 확인 버튼 누르면 바로 삭제합니다\n\n"
				+ "*텍스트 창에 데이터가 없으면 전체 검색, 데이터가 있으면 콤보박스의 컬럼으로 검색.");
				*/
	
		JScrollPane scroll = new JScrollPane(table);
		c.add(scroll, BorderLayout.CENTER);
	}//setUI

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainFrame();
	}//main

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		/* ---------------------------------------------------입력 버튼 -----------------------------------------------*/
		if(obj == btnInput) {
			inputDialog.setVisible(true);
			
			if(inputDialog.getCHECK_CONFIRM_OR_CANCLE_INPUTDIALOG()) { //입력 다이얼로그에서 확인버튼 클릭시
				StudentVo varClass = inputDialog.returnInputData();
			
				//-------------결과를 메세지창으로 보이고-------------------
				int resultCount = dao.inputData(varClass);
				JOptionPane.showMessageDialog(null, resultCount + "명이 입력됨");
				
				//----------------전체 데이터 출력-----------------
				ArrayList<StudentVo> list = dao.allSearch();
				settingTxaFromArrayList(list);
				
				//입력이 완료되면 수정 다이얼로그창에 입력받은 텍스트값들을 초기화
				inputDialog.clearTxt();
				
			}else { //입력 다이얼로그에서 취소버튼 클릭시
				inputDialog.clearTxt();
				System.out.println("작업 취소");
			}
		
		/* ---------------------------------------------------수정 버튼 -----------------------------------------------*/
		}else if(obj == btnModify) { //수정버튼 클릭시
				
			studentNumberInputDialog.setVisible(true); 
			//학번입력에서 확인, 취소 선택 
			
			if(studentNumberInputDialog.getCHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG()) {
				/*학번에서 확인버튼을 누르면 수정 다이얼로그에 데이터를 가져오는 부분*/
				
				//학번 다이얼로그에서 학번을 받아온다
				String sno = studentNumberInputDialog.getSno();
				String sql = "select * from tbl_student where sno='"+sno+"'";
				System.out.println("sql:"+sql);
				//입력받은 학번을 DAO에 넘기고 리스트를 받는다. DAO의 부분 검색을 이용해서 가져온다
				StudentVo var = dao.getModifyOfSno(sql);
				
				//---------------------------- 다오의 쿼리값이 없으면 경고메세지, 있으면 값 출력 ----------------------------
				if(var.getSno() == null) {
					JOptionPane.showMessageDialog(null, "학번에 해당하는 데이터가 없음");
				}else {
					//입력받은 리스트 값들을 수정다이얼로그에 넣고 수정다이얼로그를 보인다
					modifyDialog.settingModifyDialog(var);
					modifyDialog.setVisible(true);
					
					//수정 다이얼로그에서 확인인지 취소인지 판별하는 부분
					if(modifyDialog.getCHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG()) {
						var = modifyDialog.returnModifyData();
					
						int resultCount =  dao.modifyData(var);
						JOptionPane.showMessageDialog(null, resultCount + "명이 입력됨");
						
						//----------------전체 데이터 출력-----------------
						ArrayList<StudentVo> list = dao.allSearch();
						settingTxaFromArrayList(list);
						
						//수정이 완료되면 수정 다이얼로그창과 학번 다이얼로그창에 입력받은 텍스트값들을 초기화
						modifyDialog.clearTxt();
						studentNumberInputDialog.clearTxt();
					}else {//수정입력 다이얼로그에서 취소버튼시
						System.out.println("작업 취소");
						//JOptionPane.showMessageDialog(null, "작업 취소");
					}
				}
			}else {//학번입력 다이얼로그에서 취소버튼시 
				modifyDialog.clearTxt();
				studentNumberInputDialog.clearTxt();
				System.out.println("작업 취소");
				//JOptionPane.showMessageDialog(null, "작업 취소");
			}
			
		/* ---------------------------------------------------삭제 버튼 -----------------------------------------------*/
		}else if(obj == btnDelete) { //삭제버튼 클릭시
			
			studentNumberInputDialog.setVisible(true); 
			
			if(studentNumberInputDialog.getCHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG()) {
				
				String sno = studentNumberInputDialog.getSno();
				int resultCount = dao.deleteData(sno);
				
				//---------------------------- 다오의 쿼리값이 없으면 경고메세지, 있으면 값 출력 ----------------------------
				if(resultCount == 0) {
					JOptionPane.showMessageDialog(null, "학번에 해당하는 데이터가 없음");
				}else {
					JOptionPane.showMessageDialog(null, resultCount+"명이 삭제됨");
					//----------------전체 데이터 출력-----------------
					ArrayList<StudentVo> list = dao.allSearch();
					settingTxaFromArrayList(list);
				}
			}else {//학번입력 다이얼로그에서 취소버튼시 
				studentNumberInputDialog.clearTxt();
				System.out.println("학번입력에서 작업 취소");
			}
		/* ---------------------------------------------------검색 버튼 -----------------------------------------------*/
		}else if(obj == btnSearch || obj == txtData) { //검색버튼이나 테스트창에서 엔터를 쳤을시
			
			String txtSearch = txtData.getText();
			
			//------------------------검색 입력창이 공백일 시 전체 검색---------------------//
			if(txtSearch.equals("")) {
				ArrayList<StudentVo> list = dao.allSearch();
				settingTxaFromArrayList(list);
				
			//------------------------검색 입력창에 값이 있을 시 부분 검색---------------------//
			}else {
				//검색 입력창에 텍스트가 있고 학생명일 때
				if(cmbSearch.getSelectedIndex() == 0) {
					txtSearch = txtData.getText();
					
					String sql = "select * from tbl_student where sname like '%"+ txtSearch +"%'";
					//입력받은 학번을 DAO에 넘기고 리스트를 받는다. DAO의 부분 검색을 이용해서 가져온다
					ArrayList<StudentVo> list = dao.partSearch(sql);
					System.out.println("학생명list.size():"+list.size());
					
					if(list.size() == 0) {
						JOptionPane.showMessageDialog(null, "검색 데이터가 없음", "데이터 결과", 2);//3물음표 2삼각형느낌표 1동그라미느낌표
					} else {
						settingTxaFromArrayList(list);
					}
				//검색 입력창에 텍스트가 있고 전공명일 때
				}else if(cmbSearch.getSelectedIndex() == 1) {
				
					//dao.partSearch();//부분검색
					String major = txtData.getText();
					String sql = "select * from tbl_student where major like '%"+ major +"%'";

					//입력받은 학번을 DAO에 넘기고 리스트를 받는다. DAO의 부분 검색을 이용해서 가져온다
					ArrayList<StudentVo> list = dao.partSearch(sql);

					if(list.size() == 0) {
						JOptionPane.showMessageDialog(null, "검색 데이터가 없음", "데이터 결과", 2);//3물음표 2느낌표 2느낌표
					} else {
						settingTxaFromArrayList(list);
					}
				}
			}
		}//검색버튼과 텍스트창 엔터
	}//actionPerformed
	
	public void settingTxaFromArrayList(ArrayList<StudentVo> list) {
		
		String[][] arrayData = new String[list.size()][6];
		
		int countFirst = 0;
		int countSecond = 0;
		for(StudentVo vo : list) {
			countSecond = 0;
			arrayData[countFirst][countSecond++] = vo.getSno();
			arrayData[countFirst][countSecond++] = vo.getSname();
			arrayData[countFirst][countSecond++] = String.valueOf(vo.getSyear());
			arrayData[countFirst][countSecond++] = vo.getGender();
			arrayData[countFirst][countSecond++] = vo.getMajor();
			arrayData[countFirst][countSecond++] = String.valueOf(vo.getScore());
			
			countFirst++;
		}
		
		DefaultTableModel dtm = new DefaultTableModel(arrayData, head);
		
		table.setModel(dtm);
	}//settingTxaFromArrayList	
}//main