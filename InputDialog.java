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
	
	JRadioButton rdoMale = new JRadioButton("����", true);
	JRadioButton rdoFemale = new JRadioButton("����");
	
	JButton btnConfirm = new JButton("Ȯ��");
	JButton btnCancle = new JButton("���");
			
	//���ο��� �Է� ���̾�α� ��ư�� ���� �Է����� ������� Ȯ�μ����ϴ� boolean ���� --------------------------------
	boolean CHECK_CONFIRM_OR_CANCLE_INPUTDIALOG = false;

	public InputDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		setSize(400, 300);
		setLocation(600, 400);
		setLayout(new GridLayout(7,2));

		//Color color = new Color(0xcccc99);
		//------------------- �й� ---------------------------
		JLabel lblSno = new JLabel("�й�:");
		lblSno.setBackground(Color.WHITE);
		lblSno.setOpaque(true);
		add(lblSno);
		add(txtSno);
		
		//------------------- �̸� ---------------------------
		JLabel lblSname = new JLabel("�̸�:");
		lblSname.setBackground(Color.white);
		lblSname.setOpaque(true);
		add(lblSname);
		add(txtSname);
		
		//------------------- �г� ---------------------------
		JLabel lblSyear = new JLabel("�г�:");
		lblSyear.setBackground(Color.white);
		lblSyear.setOpaque(true);
		add(lblSyear);
		add(txtSyear);
	
		//------------------- ���� ---------------------------
		JLabel lblGender = new JLabel("����:");
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
		
		
		
		//------------------- ���� ---------------------------
		JLabel lblMajor = new JLabel("����:");
		lblMajor.setBackground(Color.white);
		lblMajor.setOpaque(true);
		add(lblMajor);
		add(txtMajor);
		
		//------------------- ���� ---------------------------
		JLabel lblScore = new JLabel("����:");
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
		
		//---------------------- �ؽ�Ʈ �ʵ忡���� Ű �Է� ������ -------------------------------------//
		/*
		txtSno.addKeyListener(this);
		txtSname.addKeyListener(this);
		txtSyear.addKeyListener(this);
		txtMajor.addKeyListener(this);
		txtScore.addKeyListener(this);
		*/
		//---------------------- �ؽ�Ʈ �ʵ忡���� Ű �Է� ������ -------------------------------------//
		
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

	//------------------------------------------------���� ���� ����------------------------------------------------------------//
	//�ؽ�Ʈ ������ ��ĭ���� �ƴ��� ����
	public boolean checkBlankValues() {
		
		boolean check = true;
		
		//--------------------------- ��ĭ���� ���� ---------------------------------//
		String str = "";
		if(txtSno.getText().trim().equals("")) {//��ĭ���� ���縦 �Ѵ�
			str += "�й��Է� �ؿ��ߵ�\n";
			check = false;
		}else {
			int num = confrimCkeckNumber(txtSno);
			if(num>8) { //���� �ԷµǾ������� �ڸ��� �˻�
				str += "�й��� 8�� ���ϰ���\n";
				check = false;
			}
		}
		
		if(txtSname.getText().trim().equals("")) {
			str += "�̸��Է� �ؾߵ�\n";
			check = false;
		}else {
			int num = confrimCkeckNumber(txtSname);
			//System.out.println("num);
			if(num>10) {
				str += "�̸��� 10�� ���ϰ���\n";
				check = false;
			}
		}
		
		if(txtSyear.getText().trim().equals("")) {//��ĭ���� �˻�
			str += "�г��Է� �ؾߵ�\n";
			check = false;
		}else {	
			if(!confirmStringToNumber(txtSyear)) { //���� �ԷµǾ� ������ ���ڰ� �ƴϸ� ���޼����� ����
				str += "�г��� ���ڸ�����\n";
				check = false;
			}else { 
				int num = confrimCkeckNumber(txtSyear);
				if(num>1) { //���ڰ� �ԷµǾ� ������ �ڸ����� �˻�
					str += "�г��� 1�� ������\n";
					check = false;
				}
			}
		}
		
		if(txtMajor.getText().trim().equals("")) {
			str += "�����Է� �ؾߵ�\n";
			check = false;
		}else {
			int num = confrimCkeckNumber(txtMajor);
			if(num>10) {
				str += "������ 10�� ���ϰ���\n";
				check = false;
			}
		}
		
		if(txtScore.getText().trim().equals("")) {//������ ���� ������ 0���� ����
			txtScore.setText("0");
		}else {
			if(!confirmStringToNumber(txtScore)) { //���� �ԷµǾ� ������ ���ڰ� �ƴϸ� ���޼����� ����
				str += "������ ���ڸ�����\n";
				check = false;
			}else {
				int num = Integer.parseInt(txtScore.getText());
				
				if(num>100) { //���ڰ� �ԷµǾ� ������ �ڸ����� �˻�
					str += "������ 0~100������\n";
					check = false;
				}
			}
		}
		
		//����� ��� �޼��� ���
		if(!check) {
			JOptionPane.showMessageDialog(null, str);
		}
		return check;
	}
	
	//�ؽ�Ʈ�� �ڸ��� �˻�
	public int confrimCkeckNumber(JTextField txtValue) {
		
		String reverseStr = txtValue.getText().trim();
		char[] c = reverseStr.toCharArray();
		System.out.println("c.length:"+c.length);
		return c.length;
	}
	
	//�������� �ƴ��� ����
	public boolean confirmStringToNumber(JTextField txtValue) { //�������� �ƴ��� Ȯ��
		String reverseStr = txtValue.getText().trim();
		char[] c = reverseStr.toCharArray();
		
		//�����̸� true����
		boolean checkBool = true;
		for(int i=0 ; i<c.length ; i++) {
			if(c[i]<48 || c[i]>57) {
				checkBool = false;
				return checkBool;
			}
		}
		return checkBool;
	}//confirmStringToNumber
	
	public int confirmStringToBlank(JTextField txtValue) { //�������� �˻�
		if(txtValue.getText().trim().equals("")) {
			return 0;
		}else {
			return 1;
		}
	}//confirmStringToBlank

	//�ؽ�Ʈ�ʵ忡 �� �����
	public void clearTxt() {
		txtSno.setText("");
		txtSname.setText("");
		txtSyear.setText("");
		txtScore.setText("");
		txtMajor.setText("");
	}//clearTxt
	//------------------------------------------------���� ���� ��------------------------------------------------------------//
	
	//inputDialog�� �ִ� ������ �����ͼ� ���ο� �Ѱ��ش�
	public StudentVo returnInputData() {

		String sno = txtSno.getText();
		String sname = txtSname.getText();
		int syear = Integer.parseInt(txtSyear.getText());
		String major = txtMajor.getText();
		int score = Integer.parseInt(txtScore.getText());
		
		String gender = null;
		if(rdoMale.isSelected() == true) {
			gender = "��";
		}else if (rdoFemale.isSelected() == true) {
			gender = "��";
		}
		
		StudentVo var = new StudentVo(sno, sname, syear, gender, major, score);
		return var;
	}
	
	//------------------------------------ �Է� ���̾�α׿��� �Է����� ������� Ȯ�μ����ϴ� boolean ���� --------------------------------
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
	
	/*Ű �Է½� �ٷ� ���ڿ� ������ Ȯ��*/
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
	/*Ű �Է½� �ٷ� ���ڿ� ���� üũ*/
}
