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

	JButton btnInput = new JButton("�Է�");
	JButton btnModify = new JButton("����");
	JButton btnDelete = new JButton("����");
	JComboBox<String> cmbSearch = new JComboBox<>(new String[] {"�л���","������"});
	
	JTextField txtData = new JTextField(10);
	JButton btnSearch = new JButton("�˻�");
	
	JTextArea txaData = new JTextArea();
	
	JTable table = new JTable();
	
	InputDialog inputDialog = new InputDialog(this, "�Է� ���̾�α�", true);
	ModifyDialog modifyDialog = new ModifyDialog(this, "���� ���̾�α�", true);
	StudentNumberInputDialog studentNumberInputDialog = new StudentNumberInputDialog(this, "�й��Է�", true); 
	
	SqlDAO dao = SqlDAO.getInstance();
	
	static boolean buttonCheckFromInputdialog = true;
	
	//Jtable ���
	String head[] = {"�й�","�̸�","�г�","����","����","����"};
	
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
		txaData.setFont(new Font("����ü", Font.PLAIN, 13));
		JScrollPane sp = new JScrollPane(txaData);
		
		/*
		txaData.setText("\n*�Է��� ������ ���̾�α�â�� �߰� �ű⿡ �����͸� �Է��մϴ�\n\n"
				+ "*������ ������ �й��� ���� �Է� �� �ش� �й��� ��������\n ���� ���̾�α׿��� ���� �� Ȯ�� ��ư Ŭ���մϴ�\n\n"
				+ "*������ ������ �й��� �Է��ϴ� ���̾�α�â�� �� ��\n �й��Է��� Ȯ�� ��ư ������ �ٷ� �����մϴ�\n\n"
				+ "*�ؽ�Ʈ â�� �����Ͱ� ������ ��ü �˻�, �����Ͱ� ������ �޺��ڽ��� �÷����� �˻�.");
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
		
		/* ---------------------------------------------------�Է� ��ư -----------------------------------------------*/
		if(obj == btnInput) {
			inputDialog.setVisible(true);
			
			if(inputDialog.getCHECK_CONFIRM_OR_CANCLE_INPUTDIALOG()) { //�Է� ���̾�α׿��� Ȯ�ι�ư Ŭ����
				StudentVo varClass = inputDialog.returnInputData();
			
				//-------------����� �޼���â���� ���̰�-------------------
				int resultCount = dao.inputData(varClass);
				JOptionPane.showMessageDialog(null, resultCount + "���� �Էµ�");
				
				//----------------��ü ������ ���-----------------
				ArrayList<StudentVo> list = dao.allSearch();
				settingTxaFromArrayList(list);
				
				//�Է��� �Ϸ�Ǹ� ���� ���̾�α�â�� �Է¹��� �ؽ�Ʈ������ �ʱ�ȭ
				inputDialog.clearTxt();
				
			}else { //�Է� ���̾�α׿��� ��ҹ�ư Ŭ����
				inputDialog.clearTxt();
				System.out.println("�۾� ���");
			}
		
		/* ---------------------------------------------------���� ��ư -----------------------------------------------*/
		}else if(obj == btnModify) { //������ư Ŭ����
				
			studentNumberInputDialog.setVisible(true); 
			//�й��Է¿��� Ȯ��, ��� ���� 
			
			if(studentNumberInputDialog.getCHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG()) {
				/*�й����� Ȯ�ι�ư�� ������ ���� ���̾�α׿� �����͸� �������� �κ�*/
				
				//�й� ���̾�α׿��� �й��� �޾ƿ´�
				String sno = studentNumberInputDialog.getSno();
				String sql = "select * from tbl_student where sno='"+sno+"'";
				System.out.println("sql:"+sql);
				//�Է¹��� �й��� DAO�� �ѱ�� ����Ʈ�� �޴´�. DAO�� �κ� �˻��� �̿��ؼ� �����´�
				StudentVo var = dao.getModifyOfSno(sql);
				
				//---------------------------- �ٿ��� �������� ������ ���޼���, ������ �� ��� ----------------------------
				if(var.getSno() == null) {
					JOptionPane.showMessageDialog(null, "�й��� �ش��ϴ� �����Ͱ� ����");
				}else {
					//�Է¹��� ����Ʈ ������ �������̾�α׿� �ְ� �������̾�α׸� ���δ�
					modifyDialog.settingModifyDialog(var);
					modifyDialog.setVisible(true);
					
					//���� ���̾�α׿��� Ȯ������ ������� �Ǻ��ϴ� �κ�
					if(modifyDialog.getCHECK_CONFIRM_OR_CANCLE_MODIFYDIALOG()) {
						var = modifyDialog.returnModifyData();
					
						int resultCount =  dao.modifyData(var);
						JOptionPane.showMessageDialog(null, resultCount + "���� �Էµ�");
						
						//----------------��ü ������ ���-----------------
						ArrayList<StudentVo> list = dao.allSearch();
						settingTxaFromArrayList(list);
						
						//������ �Ϸ�Ǹ� ���� ���̾�α�â�� �й� ���̾�α�â�� �Է¹��� �ؽ�Ʈ������ �ʱ�ȭ
						modifyDialog.clearTxt();
						studentNumberInputDialog.clearTxt();
					}else {//�����Է� ���̾�α׿��� ��ҹ�ư��
						System.out.println("�۾� ���");
						//JOptionPane.showMessageDialog(null, "�۾� ���");
					}
				}
			}else {//�й��Է� ���̾�α׿��� ��ҹ�ư�� 
				modifyDialog.clearTxt();
				studentNumberInputDialog.clearTxt();
				System.out.println("�۾� ���");
				//JOptionPane.showMessageDialog(null, "�۾� ���");
			}
			
		/* ---------------------------------------------------���� ��ư -----------------------------------------------*/
		}else if(obj == btnDelete) { //������ư Ŭ����
			
			studentNumberInputDialog.setVisible(true); 
			
			if(studentNumberInputDialog.getCHECK_CONFIRM_OR_CANCLE_STUDENTNUMBERINPUTDIALOG()) {
				
				String sno = studentNumberInputDialog.getSno();
				int resultCount = dao.deleteData(sno);
				
				//---------------------------- �ٿ��� �������� ������ ���޼���, ������ �� ��� ----------------------------
				if(resultCount == 0) {
					JOptionPane.showMessageDialog(null, "�й��� �ش��ϴ� �����Ͱ� ����");
				}else {
					JOptionPane.showMessageDialog(null, resultCount+"���� ������");
					//----------------��ü ������ ���-----------------
					ArrayList<StudentVo> list = dao.allSearch();
					settingTxaFromArrayList(list);
				}
			}else {//�й��Է� ���̾�α׿��� ��ҹ�ư�� 
				studentNumberInputDialog.clearTxt();
				System.out.println("�й��Է¿��� �۾� ���");
			}
		/* ---------------------------------------------------�˻� ��ư -----------------------------------------------*/
		}else if(obj == btnSearch || obj == txtData) { //�˻���ư�̳� �׽�Ʈâ���� ���͸� ������
			
			String txtSearch = txtData.getText();
			
			//------------------------�˻� �Է�â�� ������ �� ��ü �˻�---------------------//
			if(txtSearch.equals("")) {
				ArrayList<StudentVo> list = dao.allSearch();
				settingTxaFromArrayList(list);
				
			//------------------------�˻� �Է�â�� ���� ���� �� �κ� �˻�---------------------//
			}else {
				//�˻� �Է�â�� �ؽ�Ʈ�� �ְ� �л����� ��
				if(cmbSearch.getSelectedIndex() == 0) {
					txtSearch = txtData.getText();
					
					String sql = "select * from tbl_student where sname like '%"+ txtSearch +"%'";
					//�Է¹��� �й��� DAO�� �ѱ�� ����Ʈ�� �޴´�. DAO�� �κ� �˻��� �̿��ؼ� �����´�
					ArrayList<StudentVo> list = dao.partSearch(sql);
					System.out.println("�л���list.size():"+list.size());
					
					if(list.size() == 0) {
						JOptionPane.showMessageDialog(null, "�˻� �����Ͱ� ����", "������ ���", 2);//3����ǥ 2�ﰢ������ǥ 1���׶�̴���ǥ
					} else {
						settingTxaFromArrayList(list);
					}
				//�˻� �Է�â�� �ؽ�Ʈ�� �ְ� �������� ��
				}else if(cmbSearch.getSelectedIndex() == 1) {
				
					//dao.partSearch();//�κа˻�
					String major = txtData.getText();
					String sql = "select * from tbl_student where major like '%"+ major +"%'";

					//�Է¹��� �й��� DAO�� �ѱ�� ����Ʈ�� �޴´�. DAO�� �κ� �˻��� �̿��ؼ� �����´�
					ArrayList<StudentVo> list = dao.partSearch(sql);

					if(list.size() == 0) {
						JOptionPane.showMessageDialog(null, "�˻� �����Ͱ� ����", "������ ���", 2);//3����ǥ 2����ǥ 2����ǥ
					} else {
						settingTxaFromArrayList(list);
					}
				}
			}
		}//�˻���ư�� �ؽ�Ʈâ ����
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