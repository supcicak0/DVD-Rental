package Function;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.DriverManager;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class CreateRental extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private int paid = 0;
	private int completed = 0;
	private String custName;
	private String dvdName;

	public CreateRental(JFrame frame) {
		super(frame, "Create Rental", true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 753, 394);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblCustomerName = new JLabel("Customer name:");
			lblCustomerName.setHorizontalAlignment(SwingConstants.TRAILING);
			lblCustomerName.setBounds(30, 32, 101, 14);
			contentPanel.add(lblCustomerName);
		}
		{
			JLabel lblDvdName = new JLabel("Dvd Name:");
			lblDvdName.setHorizontalAlignment(SwingConstants.TRAILING);
			lblDvdName.setBounds(30, 72, 101, 14);
			contentPanel.add(lblDvdName);
		}
		{
			JComboBox comboBoxCust = new JComboBox();
			comboBoxCust.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					custName = (String) comboBoxCust.getSelectedItem();
					custName = custName.substring(0, custName.indexOf(" ")); 
					System.out.println(custName);
				}
			});
			comboBoxCust.setBounds(158, 29, 505, 20);
			contentPanel.add(comboBoxCust);
			comboBoxCust.addItem(null);
			
			try {
			      Connection con = null;
			      Statement st = null;
			      ResultSet rs = null;
				
		          con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/oop","root","");
		          st = (Statement) con.createStatement();
			    String s = "select customerId, customerName from customer";
			    rs = (ResultSet) st.executeQuery(s);
			
				 while(rs.next())
			        {
					 comboBoxCust.addItem(rs.getString(1)+" - "+rs.getString(2));
			        }
			}catch(Exception e){}

		}
		{
			JComboBox comboBoxDvd = new JComboBox();
			comboBoxDvd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dvdName = (String) comboBoxDvd.getSelectedItem();
					dvdName = dvdName.substring(0, dvdName.indexOf(" ")); 
					System.out.println(dvdName);
				}
			});
			comboBoxDvd.setBounds(158, 66, 505, 20);
			contentPanel.add(comboBoxDvd);
			
			comboBoxDvd.addItem(null);
			
			try {
				Connection con = null;
				Statement st = null;
				ResultSet rs = null;
								
				con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/oop","root","");
				st = (Statement) con.createStatement();
				String s = "select dvdId, dvdName, rentalRate from dvds where status = '1'";
				rs = (ResultSet) st.executeQuery(s);
			
				 while(rs.next())
			        {
					 comboBoxDvd.addItem(rs.getString(1)+" - "+rs.getString(2)+" RM "+rs.getFloat(3));
 
			        }
				}catch(Exception e){}
			
			
		}
		{
			JLabel lblStatus = new JLabel("Status:");
			lblStatus.setHorizontalAlignment(SwingConstants.TRAILING);
			lblStatus.setBounds(30, 115, 101, 14);
			contentPanel.add(lblStatus);
		}
		{
			JCheckBox chckbxPaid = new JCheckBox("Paid",false);
			chckbxPaid.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					
					if(chckbxPaid.isSelected() == true)
					{
						paid = 1;
					}
					else if(chckbxPaid.isSelected() == false)
					{
						paid = 0;
					}
					
				}
			});
			chckbxPaid.setBounds(158, 111, 97, 23);
			contentPanel.add(chckbxPaid);
			
		}
		{
			JCheckBox chckbxCompleted = new JCheckBox("Completed",false);
			chckbxCompleted.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(chckbxCompleted.isSelected() == true)
					{
						completed = 1;
					}
					else if(chckbxCompleted.isSelected() == false)
					{
						completed = 0;
					}
				}
			});
			chckbxCompleted.setBounds(158, 137, 97, 23);
			contentPanel.add(chckbxCompleted);

		}
		{
			JButton button = new JButton("Submit");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					System.out.println("Customer id: "+custName);
					System.out.println("Dvd id: "+dvdName);
					System.out.println("Paid: "+paid);
					System.out.println("Completed: "+completed);
					
			         try{
			             theQuery("insert into rental (dvdId, customerId , 	paidStatus, completedStatus) values('"+dvdName+"','"+custName+"','"+paid+"','"+completed+"')");
			         }
			         catch(Exception ex){}
				dispose();
				}
				
			});
			button.setActionCommand("OK");
			button.setBounds(262, 227, 126, 43);
			contentPanel.add(button);
		}
		{
			JButton button = new JButton("Cancel");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			button.setActionCommand("Cancel");
			button.setBounds(398, 227, 115, 43);
			contentPanel.add(button);
		}
	}
	
	
	  public void theQuery(String query){
	      Connection con = null;
	      Statement st = null;
	      try{
	          con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/oop","root","");
	          st = (Statement) con.createStatement();
	          st.executeUpdate(query);
	          JOptionPane.showMessageDialog(null,"Query Executed");
	      }catch(Exception ex){
	          JOptionPane.showMessageDialog(null,ex.getMessage());
	      }
	  }
}