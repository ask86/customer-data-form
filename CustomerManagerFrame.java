package customDatapack;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;

public class CustomerManagerFrame extends JFrame {

	private JPanel contentPane;
	private static JTable table;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerManagerFrame frame = new CustomerManagerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public static ArrayList<customer> getUsersList() {
		ArrayList<customer> transactionList = new ArrayList<customer>();

		String user = "ask86";
		String password = "7iQ4wZLa7";
		String url = "jdbc:mysql://sql2.njit.edu/ask86";

		Connection con = null;
		Statement statement = null;
		ResultSet resultSet = null;

		customer transss;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			statement = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String query = "SELECT * FROM customer;";
			//SELECT first_name FROM customer WHERE id=x;
			ResultSet rs = statement.executeQuery(query);
			//System.out.println(rs);

			while (rs.next()) {
				

				transss = new customer(rs.getInt("id"),rs.getString("first_name"),
						rs.getString("last_name"), rs.getString("email_id"));
				
			
                    String s = rs.getString("first_name");
                    
                    transactionList.add(transss);	
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return transactionList;

	}
	
	public static void Show_Transaction_In_JTable() {
		ArrayList<customer> list = getUsersList();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Object[] rowdata = new Object[4];
		for (int i = 0; i < list.size(); i++) {
			rowdata[0] = list.get(i).getcust_id();
			rowdata[1] = list.get(i).getf_name();
			rowdata[2] = list.get(i).getl_name();
			rowdata[3] = list.get(i).getemail();
			model.addRow(rowdata);
		}
	}
	
	
	
	
	public static void refreshmethod()
	{
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);	
		Show_Transaction_In_JTable();
	}
	
	/**
	 * Create the frame.
	 */
	
	public CustomerManagerFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 848, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		String col[] = { "customer id","First Name", "Last Name", "Email_id" };
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 27, 506, 336);
		contentPane.add(scrollPane);
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		
		//perform mouse listener over table for selecting table values
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int i = table.getSelectedRow();

				TableModel model = table.getModel();

				// Display Slected Row In JTexteFields
				textField.setText(model.getValueAt(i, 0).toString());
				
			}
		});
		
		
		//adding action listens to go to Add customer frame
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean flag=true;
				new CustomerForm(1,0).setVisible(true);
			}
		});
		btnAdd.setBounds(38, 391, 115, 29);
		contentPane.add(btnAdd);
		
		//adding action listener to go Edit customer frame
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean flag=false;
				String trans_id = textField.getText();

				if (trans_id.equals("")) 
				{
					JOptionPane.showMessageDialog(null, "Please select record to edit.");
				} else 
				
				{
					int x=Integer.parseInt(trans_id);
					new CustomerForm(0,x).setVisible(true);
				}
				
				
			}
		});
		btnEdit.setBounds(231, 391, 115, 29);
		contentPane.add(btnEdit);
		
		//deleting the entry from table
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String trans_id = textField.getText();
				String user = "ask86";
				String password = "7iQ4wZLa7";
				String url = "jdbc:mysql://sql2.njit.edu/ask86";

				Connection con = null;
				Statement statement = null;
				ResultSet resultSet = null;
				if (trans_id.equals("")) {
					JOptionPane.showMessageDialog(null, "Please select record to be deleted.");
				} 
				else {

					try {
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection(url, user, password);
						statement = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);
						String query = "delete from  customer where id='" + trans_id + "';";
						statement.execute(query);
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.setRowCount(0);
						Show_Transaction_In_JTable();
						textField.setText("");
						
						JOptionPane.showMessageDialog(null, "Record deleted Successfully.");

					} catch (Exception erp) {
						erp.printStackTrace();
					}
				}
			
				
			}
		});
		btnDelete.setBounds(429, 391, 115, 29);
		contentPane.add(btnDelete);
		
		textField = new JTextField();
		textField.setBounds(559, 110, 146, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		//displaying result in the Jtable
		
		Show_Transaction_In_JTable();
	}
}