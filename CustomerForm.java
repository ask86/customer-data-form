package customDatapack;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CustomerForm extends JFrame {

	private JPanel contentPane;
	//private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("null")
	public CustomerForm(int a, int b) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 589, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(55, 40, 97, 20);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(55, 128, 97, 20);
		contentPane.add(lblLastName);
		
		JLabel lblEmail = new JLabel("Email ");
		lblEmail.setBounds(55, 215, 97, 20);
		contentPane.add(lblEmail);
		
		textField = new JTextField();
		textField.setBounds(274, 37, 146, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(274, 125, 146, 26);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(274, 212, 146, 26);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(180, 300, 115, 29);
		contentPane.add(btnSave);
		

		if(a==0)
		{

			// connection to DB
			String user = "ask86";
			String password = "7iQ4wZLa7";
			String url = "jdbc:mysql://sql2.njit.edu/ask86";

			Connection con1 = null;
			Statement statement2 = null;
			ResultSet resultSet2 = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
				con1 = DriverManager.getConnection(url, user, password);
				statement2 = (Statement)con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    
				//getting the values of selected entry from database
				
				resultSet2=statement2.executeQuery("SELECT first_name FROM customer WHERE id="+b+";");
			    while (resultSet2.next()) {
			    	String s = resultSet2.getString("first_name");
			    	
			    	textField.setText(s);
			    }
			    resultSet2=statement2.executeQuery("SELECT last_name  FROM customer WHERE id="+b+";");
			    while (resultSet2.next()) {
			    	String s = resultSet2.getString("last_name");
			    	
			    	textField_1.setText(s);
			    }
			    resultSet2=statement2.executeQuery("SELECT email_id FROM customer WHERE id="+b+";");
			    while (resultSet2.next()) {
			    	String s = resultSet2.getString("email_id");
			    	
			    	textField_2.setText(s);
			    }
			    
			} 
			
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//performing save action after editing the values 
					String first_name = textField.getText();
					String last_name = textField_1.getText();
					String email = textField_2.getText();
					

					String user = "ask86";
					String password = "7iQ4wZLa7";
					String url = "jdbc:mysql://sql2.njit.edu/ask86";

					Connection con = null;
					Statement statement = null;
					ResultSet resultSet = null;

					try {
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection(url, user, password);
						statement = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					
						statement.executeUpdate("UPDATE ask86.customer SET first_name='"+first_name+"',last_name= '"+last_name+"',email_id='"+email+"' WHERE customer.id='"+b+"'");
						
						CustomerManagerFrame.refreshmethod();
				
						
						JOptionPane.showMessageDialog(null, "Record Updated Successfully.");

						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				
			}
		});
		
		}
		else
		{
			//inserting the new entry to database
			
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
						String first_name = textField.getText();
						String last_name = textField_1.getText();
						String email = textField_2.getText();
						

						String user = "ask86";
						String password = "7iQ4wZLa7";
						String url = "jdbc:mysql://sql2.njit.edu/ask86";

						Connection con = null;
						Statement statement = null;
						ResultSet resultSet = null;

						try {
							Class.forName("com.mysql.jdbc.Driver");
							con = DriverManager.getConnection(url, user, password);
							statement = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
									ResultSet.CONCUR_READ_ONLY);

							statement.executeUpdate(
									"insert into customer (first_name,last_name,email_id) values('"
											+ first_name + "','" + last_name + "','" + email + "')");
					
							
							CustomerManagerFrame.refreshmethod();
					
							
							JOptionPane.showMessageDialog(null, "Record Inserted Successfully.");

						} catch (Exception e) {
							e.printStackTrace();
						}
					
				}
			});
		}
	}
}
	