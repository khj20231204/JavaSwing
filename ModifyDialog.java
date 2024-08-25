package ui_swing;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ModifyDialog extends JDialog implements ActionListener{

	JTextField txtSno = new JTextField(10);
	JTextField txtSname = new JTextField(10);
	JTextField txtSyear = new JTextField(10);
	JTextField txtMajor = new JTextField(10);
	JTextField txtScore = new JTextField(10);
	
	JRadioButton rdoMale = new JRadioButton("����", true);
	JRadioButton rdoFemale = new JRadioButton("����");
	
	JButton btnConfirm = new JButton("����");
	JButton btnCancle = new JButton("���");
	
	//���ο��� ���� ���̾�α� ��ư�� ���� �Է����� ������� Ȯ�μ����ϴ� boolean ���� --------------------------------
	boolean CHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG = false;
	
	public ModifyDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
	
		setSize(400, 300);
		setLocation(600, 400);
		setLayout(new GridLayout(7,2));

		add(new JLabel("�й�:"));
		add(txtSno);
		add(new JLabel("�̸�:"));
		add(txtSname);
		add(new JLabel("�г�:"));
		add(txtSyear);
		
		add(new JLabel("����:"));
		JPanel pl = new JPanel();
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdoMale);
		bg.add(rdoFemale);
		pl.add(rdoMale);
		pl.add(rdoFemale);
		add(pl);
		
		add(new JLabel("����:"));
		add(txtMajor);
		add(new JLabel("����:"));
		add(txtScore);
		
		btnConfirm.addActionListener(this);
		btnCancle.addActionListener(this);
		
		add(btnConfirm);
		add(btnCancle);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(e.getSource() == btnConfirm) {
			if(checkBlankValues()) {
			CHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG = true;
			setVisible(false);
			}else {
				setVisible(true);
			}
		}else if(e.getSource() == btnCancle) {
			CHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG = false;
			setVisible(false);
		}
	}//actionPerformed
	
	//���ο��� ���� �Ѱܹ޾Ƽ� ���̾�α��� ui�� �Է��ϴ� �Լ�
		public void settingModifyDialog(StudentVo var) {

			txtSno.setText(var.getSno());
			txtSname.setText(var.getSname());
			txtSyear.setText(String.valueOf(var.getSyear()));
			txtMajor.setText(var.getMajor());
			txtScore.setText(String.valueOf(var.getScore()));
			
			if(var.getGender().equals("male")) {
				rdoMale.setSelected(true);
			}else if(var.getGender().equals("female")) {
				rdoFemale.setSelected(true);
			}
		}//settingModifyDialog
	
	//�ش� ���̾�α� ui���� �������� �Ѱ��ִ� �Լ�
	public StudentVo returnModifyData() {
		
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
	}//returnModifyData

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
	
	//------------------------------------ ���� ���̾�α׿��� �Է����� ������� Ȯ�μ����ϴ� boolean ���� --------------------------------//
	/**
	 * @return the cHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG
	 */
	public boolean getCHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG() {
		return CHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG;
	}

	/**
	 * @param cHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG the cHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG to set
	 */
	public void setCHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG(boolean cHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG) {
		CHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG = cHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG;
	}
}
