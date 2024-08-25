package ui_swing;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class InputDialog extends JDialog implements ActionListener, KeyListener{
	
	JTextField txtSno = new JTextField(10);
	JTextField txtSname = new JTextField(10);
	JTextField txtSyear = new JTextField(10);
	JTextField txtMajor = new JTextField(10);
	JTextField txtScore = new JTextField(10);
	
	JRadioButton rdoMale = new JRadioButton("남자", true);
	JRadioButton rdoFemale = new JRadioButton("여자");
	
	JButton btnConfirm = new JButton("확인");
	JButton btnCancle = new JButton("취소");
			
	//메인에서 입력 다이얼로그 버튼의 값이 입력인지 취소인지 확인설정하는 boolean 변수 --------------------------------
	boolean CHECK_CONFIRM_OR_CANCLE_INPUTDIALOG = false;

	public InputDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		setSize(400, 300);
		setLocation(600, 400);
		setLayout(new GridLayout(7,2));

		//Color color = new Color(0xcccc99);
		//------------------- 학번 ---------------------------
		JLabel lblSno = new JLabel("학번:");
		lblSno.setBackground(Color.WHITE);
		lblSno.setOpaque(true);
		add(lblSno);
		add(txtSno);
		
		//------------------- 이름 ---------------------------
		JLabel lblSname = new JLabel("이름:");
		lblSname.setBackground(Color.white);
		lblSname.setOpaque(true);
		add(lblSname);
		add(txtSname);
		
		//------------------- 학년 ---------------------------
		JLabel lblSyear = new JLabel("학년:");
		lblSyear.setBackground(Color.white);
		lblSyear.setOpaque(true);
		add(lblSyear);
		add(txtSyear);
	
		//------------------- 성별 ---------------------------
		JLabel lblGender = new JLabel("성별:");
		lblGender.setBackground(Color.white);
		lblGender.setOpaque(true);
		add(lblGender);
		
		JPanel pl = new JPanel();
		pl.setBackground(Color.WHITE);
		ButtonGroup bg = new ButtonGroup();
		rdoMale.setBackground(Color.white);
		rdoFemale.setBackground(Color.white);
		bg.add(rdoMale);
		bg.add(rdoFemale);
		pl.add(rdoMale);
		pl.add(rdoFemale);
		add(pl);
		
		
		
		//------------------- 전공 ---------------------------
		JLabel lblMajor = new JLabel("전공:");
		lblMajor.setBackground(Color.white);
		lblMajor.setOpaque(true);
		add(lblMajor);
		add(txtMajor);
		
		//------------------- 점수 ---------------------------
		JLabel lblScore = new JLabel("점수:");
		lblScore.setBackground(Color.white);
		lblScore.setOpaque(true);
		add(lblScore);
		add(txtScore);
		
		btnConfirm.setBackground(Color.getHSBColor(22, 23, 33));
		btnCancle.setBackground(Color.getHSBColor(22, 23, 33));
		
		btnConfirm.addActionListener(this);
		btnCancle.addActionListener(this);
		
		add(btnConfirm);
		add(btnCancle);
		
		//---------------------- 텍스트 필드에서의 키 입력 리스너 -------------------------------------//
		/*
		txtSno.addKeyListener(this);
		txtSname.addKeyListener(this);
		txtSyear.addKeyListener(this);
		txtMajor.addKeyListener(this);
		txtScore.addKeyListener(this);
		*/
		//---------------------- 텍스트 필드에서의 키 입력 리스너 -------------------------------------//
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if((Object)e.getSource() == btnConfirm) {
			if(checkBlankValues()) {
				CHECK_CONFIRM_OR_CANCLE_INPUTDIALOG = true;
				//MainFrame.buttonCheckFromInputdialog = true;
				setVisible(false);
			}
			else {
				setVisible(true);
			}
		}else if((Object)e.getSource() == btnCancle) {
			System.out.println("btnCancle");
			CHECK_CONFIRM_OR_CANCLE_INPUTDIALOG = false;
			dispose();
			return;
		}
	}//actionPerformed

	//------------------------------------------------오류 수정 시작------------------------------------------------------------//
	//텍스트 값들이 빈칸인지 아닌지 조사
	public boolean checkBlankValues() {
		
		boolean check = true;
		
		//--------------------------- 빈칸인지 조사 ---------------------------------//
		String str = "";
		if(txtSno.getText().trim().equals("")) {//빈칸인지 조사를 한다
			str += "학번입력 해여야됨\n";
			check = false;
		}else {
			int num = confrimCkeckNumber(txtSno);
			if(num>8) { //값이 입력되어있으면 자리수 검사
				str += "학번은 8자 이하가능\n";
				check = false;
			}
		}
		
		if(txtSname.getText().trim().equals("")) {
			str += "이름입력 해야됨\n";
			check = false;
		}else {
			int num = confrimCkeckNumber(txtSname);
			//System.out.println("num);
			if(num>10) {
				str += "이름은 10자 이하가능\n";
				check = false;
			}
		}
		
		if(txtSyear.getText().trim().equals("")) {//빈칸인지 검사
			str += "학년입력 해야됨\n";
			check = false;
		}else {	
			if(!confirmStringToNumber(txtSyear)) { //값은 입력되어 있으나 숫자가 아니면 경고메세지를 띄운다
				str += "학년은 숫자만가능\n";
				check = false;
			}else { 
				int num = confrimCkeckNumber(txtSyear);
				if(num>1) { //숫자가 입력되어 있으면 자릿수를 검사
					str += "학년은 1자 만가능\n";
					check = false;
				}
			}
		}
		
		if(txtMajor.getText().trim().equals("")) {
			str += "전공입력 해야됨\n";
			check = false;
		}else {
			int num = confrimCkeckNumber(txtMajor);
			if(num>10) {
				str += "전공은 10자 이하가능\n";
				check = false;
			}
		}
		
		if(txtScore.getText().trim().equals("")) {//점수에 값이 없으면 0으로 설정
			txtScore.setText("0");
		}else {
			if(!confirmStringToNumber(txtScore)) { //값은 입력되어 있으나 숫자가 아니면 경고메세지를 띄운다
				str += "점수는 숫자만가능\n";
				check = false;
			}else {
				int num = Integer.parseInt(txtScore.getText());
				
				if(num>100) { //숫자가 입력되어 있으면 자릿수를 검사
					str += "점수는 0~100만가능\n";
					check = false;
				}
			}
		}
		
		//결과로 경고 메세지 출력
		if(!check) {
			JOptionPane.showMessageDialog(null, str);
		}
		return check;
	}
	
	//텍스트의 자릿수 검사
	public int confrimCkeckNumber(JTextField txtValue) {
		
		String reverseStr = txtValue.getText().trim();
		char[] c = reverseStr.toCharArray();
		System.out.println("c.length:"+c.length);
		return c.length;
	}
	
	//숫자인지 아닌지 조사
	public boolean confirmStringToNumber(JTextField txtValue) { //숫자인지 아닌지 확인
		String reverseStr = txtValue.getText().trim();
		char[] c = reverseStr.toCharArray();
		
		//숫자이면 true리던
		boolean checkBool = true;
		for(int i=0 ; i<c.length ; i++) {
			if(c[i]<48 || c[i]>57) {
				checkBool = false;
				return checkBool;
			}
		}
		return checkBool;
	}//confirmStringToNumber
	
	public int confirmStringToBlank(JTextField txtValue) { //공백인지 검사
		if(txtValue.getText().trim().equals("")) {
			return 0;
		}else {
			return 1;
		}
	}//confirmStringToBlank

	//텍스트필드에 값 지우기
	public void clearTxt() {
		txtSno.setText("");
		txtSname.setText("");
		txtSyear.setText("");
		txtScore.setText("");
		txtMajor.setText("");
	}//clearTxt
	//------------------------------------------------오류 수정 끝------------------------------------------------------------//
	
	//inputDialog에 있는 값들을 가져와서 메인에 넘겨준다
	public StudentVo returnInputData() {

		String sno = txtSno.getText();
		String sname = txtSname.getText();
		int syear = Integer.parseInt(txtSyear.getText());
		String major = txtMajor.getText();
		int score = Integer.parseInt(txtScore.getText());
		
		String gender = null;
		if(rdoMale.isSelected() == true) {
			gender = "남";
		}else if (rdoFemale.isSelected() == true) {
			gender = "여";
		}
		
		StudentVo var = new StudentVo(sno, sname, syear, gender, major, score);
		return var;
	}
	
	//------------------------------------ 입력 다이얼로그에서 입력인지 취소인지 확인설정하는 boolean 변수 --------------------------------
	/**
	 * @return the cHECK_CONFIRM_OR_CANCLE_INPUTDIALOG
	 */
	public boolean getCHECK_CONFIRM_OR_CANCLE_INPUTDIALOG() {
		return CHECK_CONFIRM_OR_CANCLE_INPUTDIALOG;
	}

	/**
	 * @param cHECK_CONFIRM_OR_CANCLE_INPUTDIALOG the cHECK_CONFIRM_OR_CANCLE_INPUTDIALOG to set
	 */
	public void setCHECK_CONFIRM_OR_CANCLE_INPUTDIALOG(boolean cHECK_CONFIRM_OR_CANCLE_INPUTDIALOG) {
		CHECK_CONFIRM_OR_CANCLE_INPUTDIALOG = cHECK_CONFIRM_OR_CANCLE_INPUTDIALOG;
	}
	
	/*키 입력시 바로 문자열 갯수를 확인*/
	@Override
	public void keyPressed(KeyEvent e) {
		
		String sno = txtSno.getText();
		String sname = txtSname.getText();
		String major = txtMajor.getText();
		
		int syear = Integer.parseInt(txtSyear.getText());
		int score = Integer.parseInt(txtScore.getText());
		
		if(e.getSource() == txtSno) {
			//System.out.println("txtSno");
			
			int snoNum = confrimCkeckNumber(txtSno);
			if(snoNum > 8) {
				//txtSno.
			}
			
		}
		
		if(e.getSource() == txtSname) {
			System.out.println("txtSname");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("keyReleased");
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("keyTyped");
	}
	/*키 입력시 바로 문자열 갯수 체크*/
}
