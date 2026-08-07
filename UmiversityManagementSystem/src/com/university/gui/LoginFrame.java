package com.university.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;

import com.university.exception.InvalidLoginException;
import com.university.model.User;
import com.university.service.AuthService;

import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldEmail;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 929, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setContentPane(contentPane);
		
		JLabel lblTitle = new JLabel("SVNIT UNIVERSITY PORTAL");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTitle.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitle.setForeground(new Color(0, 128, 192));
		
		JLabel lblEmail = new JLabel("E-Mail");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEmail.setAlignmentY(Component.TOP_ALIGNMENT);
		lblEmail.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setForeground(new Color(0, 128, 192));
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldEmail.setBackground(new Color(192, 192, 192));
		textFieldEmail.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setForeground(new Color(0, 128, 192));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    AuthService authService = new AuthService();
		JButton Login = new JButton("Login");
		Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = textFieldEmail.getText();
				String password = new String(passwordField.getPassword());

				try {
					User user = authService .login(email, password);
					JOptionPane.showMessageDialog(
						    LoginFrame.this,
						    "Welcome " + user.getName()
						);
				    // open next frame based on role
				} catch(InvalidLoginException e1) {
					JOptionPane.showMessageDialog(
						    LoginFrame.this,
						    "❌ " + e1.getMessage(),
						    "Login Failed",
						    JOptionPane.ERROR_MESSAGE
						);
				}
			}
		});
		JLabel lblName = new JLabel("Name");
		lblName.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblName.setForeground(new Color(0, 0, 0));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setBackground(new Color(0, 128, 192));
		
		JTextArea textAreaName = new JTextArea();
		textAreaName.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		textAreaName.setBackground(new Color(192, 192, 192));
		textAreaName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		
		Login.setBackground(new Color(192, 192, 192));
		Login.setBorder(new LineBorder(new Color(255, 128, 128), 3, true));
		Login.setForeground(new Color(0, 128, 192));
		Login.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnSignup = new JButton("Sign Up");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String name = textAreaName.getText();
				    String email = textFieldEmail.getText();
				    String password = new String(passwordField.getPassword());
				    
				    // get role from JComboBox or JRadioButton
				    authService.signUpStudent(name, email, password);
				    
				    JOptionPane.showMessageDialog(
				        LoginFrame.this,
				        "✅ Account created! Please login.",
				        "Success",
				        JOptionPane.INFORMATION_MESSAGE
				    );
				} catch(Exception e1) {
				    JOptionPane.showMessageDialog(
				        LoginFrame.this,
				        "❌ " + e1.getMessage(),
				        "Signup Failed",
				        JOptionPane.ERROR_MESSAGE
				    );
				}
			}
		});
		btnSignup.setForeground(new Color(0, 128, 192));
		btnSignup.setBorder(new LineBorder(new Color(255, 128, 128), 3, true));
		btnSignup.setBackground(new Color(192, 192, 192));
		btnSignup.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		passwordField = new JPasswordField();
		passwordField.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setBackground(new Color(192, 192, 192));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(288)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(textAreaName, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(textFieldEmail, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(Login, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
							.addGap(50)
							.addComponent(btnSignup, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addGap(21)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(textAreaName, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldEmail, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
					.addGap(43)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(Login, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSignup, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)))
		);
		contentPane.setLayout(gl_contentPane);
		
		

	}
}
