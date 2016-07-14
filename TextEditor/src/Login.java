import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.JPanel;

public class Login extends JPanel implements ActionListener  {
  JLabel userL=new JLabel("Username: ");
  JTextField userTF=new JTextField();
  JLabel passL=new JLabel("Password: ");
  JPasswordField passTF=new JPasswordField();
	JPanel loginP=new JPanel(new GridLayout(3,2));
	JPanel panel =new JPanel();
	JButton login=new JButton("Login");
	JButton register=new JButton("Register");
	CardLayout cl;
	Login(){
	   setLayout(new CardLayout());
	   loginP.add(userL);
	   loginP.add(userTF);
	   loginP.add(passL);
	   loginP.add(passTF);
	   login.addActionListener(this);
	   register.addActionListener(this);
	   loginP.add(login);
	   loginP.add(register);
	   
	   panel.add(loginP);
	   add(panel,"login"); //panel to CardLayout
	  cl=(CardLayout)getLayout();
   }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//allows user to register and saves users password  
   if(e.getSource()==login){  
	   try {
		BufferedReader input=new BufferedReader(new FileReader("passwords.txt"));
	String pass=null;
	String line =input.readLine();
	   while(line!=null){
	 StringTokenizer st=new StringTokenizer(line);
	 while(st.hasMoreTokens())
	  if(userTF.getText().equals(st.nextToken()))
	 
		  pass=st.nextToken();//if user matched then password check karenge ab 
	 line=input.readLine();
	  //checking it to stored password
	 }
	  input.close();
	  
	  //recreated encrpyted password   and rerun
	  MessageDigest md=MessageDigest.getInstance("SHA-256");
		md.update(new String(passTF.getPassword()).getBytes());
		byte byteData[]=md.digest();
		StringBuffer sb=new StringBuffer(); //allows us to change pass to hex format
		for(int i=0;i<byteData.length;i++)
			sb.append(Integer.toString((byteData[i] & 0xFF)+0x100,16).substring(1));
	  
	
		
		if(pass.equals(sb.toString()))
		   
			add(new FileBrowser(userTF.getText()),"fb");
			cl.show(this, "fb");
			
			
	   } 
	   
	   catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (NoSuchAlgorithmException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
   }
   
   
		if(e.getSource()==register){
		add(new Register(), "register");     //register is key
	    cl.show(this, "register");      	//key
     }
	}
	public static void main(String[] a){
		JFrame frame=new JFrame("Text Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		Login login=new Login();
		frame.add(login);
		frame.setVisible(true);
	}

}
