import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.*;

public class Register extends JPanel implements ActionListener {
JLabel userL=new JLabel("Chooser a Username: ");
JTextField userTF=new JTextField();
JLabel passL=new JLabel("Password: ");
JPasswordField passTF=new JPasswordField();
JLabel passLC=new JLabel("Confirm Password ");
JPasswordField passC=new JPasswordField();
JButton register=new JButton("Register");
JButton back=new JButton("Back");

public Register(){
	JPanel loginP=new JPanel();
	loginP.setLayout(new GridLayout(4,2));
	loginP.add(userL);
	loginP.add(userTF);
	loginP.add(passL);
	loginP.add(passTF);
	loginP.add(passLC);
	loginP.add(passC);
	loginP.add(register);
	loginP.add(back);
	 register.addActionListener(this);
	   back.addActionListener(this);
	add(loginP);//adding LoginP to gridLayout
}

@Override
public void actionPerformed(ActionEvent e) {
	if(e.getSource()==register && passTF.getPassword().length>0 && userTF.getText().length()>0 ){
	String pass=new String(passTF.getPassword());
	String confirm=new String(passC.getPassword());
	if(pass.equals(confirm)){
		//now check is user is already exists ? 
		try {
			BufferedReader input=new BufferedReader(new FileReader("passwords.txt"));
		String line=input.readLine(); //read line by line 
		while(line !=null){
			StringTokenizer st=new StringTokenizer(line);
			while(st.hasMoreTokens())
			if(userTF.getText().equals(st.nextToken())){
				System.out.println("User Already Exists");
			    return;
			}
			line =input.readLine();//read next line 
		}
		input.close();
		
		
		//for encryption  hash our password
		MessageDigest md=MessageDigest.getInstance("SHA-256");
		md.update(pass.getBytes());
		byte byteData[]=md.digest();
		StringBuffer sb=new StringBuffer(); //allows us to change pass to hex format
		for(int i=0;i<byteData.length;i++)
			sb.append(Integer.toString((byteData[i] & 0xFF)+0x100,16).substring(1));//16 means hexadecimal
		BufferedWriter output=new BufferedWriter(new FileWriter("passwords.txt",true));
		output.write("\n"+ userTF.getText()+"  "+sb.toString()+"\n");
		output.close();
		Login login=(Login) getParent();
		login.cl.show(login,"login");
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			
			e1.printStackTrace();
		}
	}
	}
	if(e.getSource()==back){
		Login login=(Login) getParent();
		login.cl.show(login,"login");
	}
	
}



}
