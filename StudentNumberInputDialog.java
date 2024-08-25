package ui_swing;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StudentNumberInputDialog extends JDialog implements ActionListener{

	JTextField txtSno = new JTextField(10);
	JButton btnConfirm = new JButton("Ȯ��");
	JButton btnCancle = new JButton("���");
	
	String sno = null;

	//���ο��� �й� ���̾�α� ��ư�� ���� �Է����� ������� Ȯ�μ����ϴ� boolean ���� --------------------------------
	boolean CHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG = false;
	
	public StudentNumberInputDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		setSize(270, 100);
		setLocation(600, 400);
		setLayout(new GridLayout(2, 2));
		add(new JLabel("�й��Է�:"));
		add(txtSno);
		add(btnConfirm);
		add(btnCancle);
		
		//���̾�α׸� �ҷ����� �� �̹� �ԷµǾ��ִ� �� �ʱ�ȭ
		txtSno.setText("");
		
		btnConfirm.setBackground(Color.getHSBColor(22, 23, 33));
		btnCancle.setBackground(Color.getHSBColor(22, 23, 33));
		
		btnConfirm.addActionListener(this);
		btnCancle.addActionListener(this);
	}//StudentNumberInputDialog

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnConfirm) {
			if(checkBlankValues()) {
				setSno(txtSno.getText());
				CHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG = true;
				setVisible(false);
			}
		}else if(e.getSource() == btnCancle) {
			txtSno.setText("");
			CHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG = false;
			setVisible(false);
		}
	}//actionPerformed

	//�й� �ؽ�Ʈ�� ������� �˻�
	public boolean checkBlankValues() {
		boolean checkBlank = true;
		
		if(txtSno.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "�й��� �Է��ؾߵ�");
			checkBlank = false;
		}
		
		return checkBlank;
	}//checkBlankValues
	
	
	public void clearTxt() {
		txtSno.setText("");
	}
	/**
	 * @return the sno
	 */
	public String getSno() {
		return sno;
	}

	/**
	 * @param sno the sno to set
	 */
	public void setSno(String sno) {
		this.sno = sno;
	}

	/**
	 * @return the cHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG
	 */
	public boolean getCHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG() {
		return CHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG;
	}

	/**
	 * @param cHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG the cHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG to set
	 */
	public void setCHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG(
			boolean cHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG) {
		CHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG = cHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG;
	}	
}
