package es.upm.fi.sos.client;
import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import es.upm.fi.sos.client.UPMSocialReadingStub.AddUser;
import es.upm.fi.sos.client.UPMSocialReadingStub.AddUserResponseE;
import es.upm.fi.sos.client.UPMSocialReadingStub.Login;
import es.upm.fi.sos.client.UPMSocialReadingStub.LoginResponse;
import es.upm.fi.sos.client.UPMSocialReadingStub.Logout;
import es.upm.fi.sos.client.UPMSocialReadingStub.User;
import es.upm.fi.sos.client.UPMSocialReadingStub.Username;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;

import javax.ws.rs.core.NewCookie;

import es.upm.fi.sos.client.UPMSocialReadingStub;
import es.upm.fi.sos.client.UPMSocialReadingStub.*;

public class UPMSocialReadingClient {

	static HashMap<String, String> userPwd = new HashMap<String, String>();

	static UPMSocialReadingStub clientIns(UPMSocialReadingStub client){
		try {
			client = new UPMSocialReadingStub();
			client._getServiceClient().getOptions().setManageSession(true);
			client._getServiceClient().engageModule("addressing");
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return client;
	}
	static boolean login(UPMSocialReadingStub client,String userName, String userPwd){
		Login login1 = new Login();
		User param = new User();
		param.setName(userName);
		param.setPwd(userPwd);
		login1.setArgs0(param);
		try {
			LoginResponse r = client.login(login1);
			//System.out.println("SALIDA LOGIN ADMIN EN CLIENTE: " + r.get_return().getResponse());
			return r.get_return().getResponse();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	static void logout(UPMSocialReadingStub client){
		Logout logout4 = new Logout();
		try {
			client.logout(logout4);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
	static boolean addUser(UPMSocialReadingStub client,String userName)
	{
		AddUser addUser9 = new AddUser();
		Username param2 = new Username();
		param2.setUsername(userName);
		addUser9.setArgs0(param2);
		AddUserResponseE r2;
		try {
			r2 = client.addUser(addUser9);
			String pwd = r2.get_return().getPwd();
			userPwd.put(userName, pwd);
			return r2.get_return().getResponse();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	static boolean removeUser(UPMSocialReadingStub client,String userName){
		RemoveUser removeUser5 = new RemoveUser();
		Username param9 = new Username();
		param9.setUsername(userName);
		removeUser5.setArgs0(param9);
		RemoveUserResponse r12;
		try {
			r12 = client.removeUser(removeUser5);
			return r12.get_return().getResponse();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	static boolean changePassword(UPMSocialReadingStub client,String oldPwd,String newPwd){
		ChangePassword changePassword = new ChangePassword();
		PasswordPair param = new PasswordPair();
		param.setOldpwd(oldPwd);
		param.setNewpwd(newPwd);
		changePassword.setArgs0(param);
		ChangePasswordResponse r;
		try {
			r = client.changePassword(changePassword);
			return r.get_return().getResponse();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	static boolean addFriend(UPMSocialReadingStub client, String friend){
		AddFriend addFriend = new AddFriend();
		Username param = new Username();
		param.setUsername(friend);
		addFriend.setArgs0(param);
		AddFriendResponse r;
		try {
			r = client.addFriend(addFriend);
			return r.get_return().getResponse();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}



	static void testPrint(String message,boolean passed){
		if( passed)
			System.out.println("OK ++ Test "+ message +  " ++ OK");
		else
			System.out.println("BAD -- Test "+ message + " -- BAD");
	}
	static void test1(UPMSocialReadingStub client){
		/*
		 * 1.  login
				1.1 login Admin == True
				1.2 login Usuario sin añadir == False
				1.3 login Usuario añadido                         --> 3.2
		 */
		//1.1 login Admin == True
		boolean status =login(client,"admin","admin");
		logout(client);
		testPrint("1.1 login Admin",status);


		//1.2 login Usuario sin añadir == False
		status = login(client,"noexiste00","noexiste00");
		//logout(client);
		testPrint("1.2 login noexiste",!status);


		// 1.3.1 login Usuario añadido == True

		String user="jvc00";
		login(client,"admin","admin");
		addUser(client,user);
		String pwd = userPwd.get(user);
		//System.out.println("	pwd:"+pwd);
		logout(client);
		status = login(client,user,pwd);
		testPrint("1.3 login newUser",status);
		logout(client);
		//clean up
		login(client,"admin","admin");
		removeUser(client,user);
		logout(client);
		// 1.3.2 login Usuario añadido == True
		user="jvc01";
		login(client,"admin","admin");
		addUser(client,user);
		pwd = userPwd.get(user);
		//System.out.println("	pwd:"+pwd);
		logout(client);
		status = login(client,user,pwd);
		testPrint("1.3 login newUser",status);
		logout(client);
		//cleanup
		login(client,"admin","admin");
		removeUser(client,user);
		logout(client);
	}
	static void test2(UPMSocialReadingStub client){
		// 2.1  addUser by user == False
		String user="jvc00";
		String user1="jvc01";
		login(client,"admin","admin");
		addUser(client,user);
		String pwd = userPwd.get(user);
		//System.out.println("	pwd:"+pwd);
		logout(client);
		login(client,user,pwd);
		boolean status = addUser(client,user1);
		testPrint("2.1 addUser by user",!status);
		logout(client);
		//cleanup
		login(client,"admin","admin");
		removeUser(client,user);
		logout(client);

		// 2.2 addUser no login == False
		status = addUser(client,user1);
		testPrint("2.2 addUser not logged in",!status);

		// 2.3 usuario añadido por admin == True

		user="jvc00";
		login(client,"admin","admin");
		status = addUser(client,user);
		testPrint("2.3 addUser by admin",status);
		pwd = userPwd.get(user);
		//System.out.println("	pwd:"+pwd);
		logout(client);
		login(client,user,pwd);
		logout(client);
		//clean up
		login(client,"admin","admin");
		removeUser(client,user);
		logout(client);

	}

	static void test3(UPMSocialReadingStub client){
		testPrint("3.1 logout",true);
	}
	static void test4(UPMSocialReadingStub client){
		//4.1 eliminar admin por admin == False
		login(client,"admin","admin");
		boolean status = removeUser(client,"admin");
		testPrint("4.1 remove admin by admin",!status);
		logout(client);
		//4.2 eliminar notauser por admin == False
		login(client,"admin","admin");
		status = removeUser(client,"notavaliduser");
		testPrint("4.2 remove notauser by admin",!status);
		logout(client);
		//4.3 eliminar sin login == false
		status = removeUser(client,"whatever");
		testPrint("4.2 remove without log in",!status);
		//4.4 eliminar siendo admin == true
		String user="jvc00";
		login(client,"admin","admin");
		addUser(client,user);
		String pwd = userPwd.get(user);
		//System.out.println("	pwd:"+pwd);
		logout(client);
		login(client,user,pwd);
		logout(client);
		//clean up
		login(client,"admin","admin");
		status = removeUser(client,user);
		testPrint("2.3 addUser by admin",status);
		logout(client);
	}

	static void test5(UPMSocialReadingStub client){

		//5.1 change admin pwd == false
		String newPwd = "newPwd";
		login(client,"admin","admin");
		boolean status = changePassword(client,"admin",newPwd);
		testPrint("5.1 changepassword by admin",status);
		logout(client); 
		//5.2 change user password == true
		String user="jvc00";
		login(client,"admin","admin");
		addUser(client,user);
		String pwd = userPwd.get(user);
		//System.out.println("	pwd:"+pwd);
		logout(client);
		login(client,user,pwd);
		changePassword(client, pwd, newPwd);
		testPrint("5.2 changePassword by user",status);
		logout(client);
		//clean up
		login(client,"admin","admin");
		removeUser(client,user);
		logout(client);
		//5.3 change pass not logged in == false
		status = changePassword(client,"whatever", newPwd);
		testPrint("5.3 changePassword no login",!status);
		//5.3 change pass not correct in == false
		user="jvc00";
		login(client,"admin","admin");
		addUser(client,user);
		pwd = userPwd.get(user);
		//System.out.println("	pwd:"+pwd);
		logout(client);
		login(client,user,pwd);
		changePassword(client, "whatever", newPwd);
		testPrint("5.2 changePassword by user old not correct",!status);
		logout(client);
		//clean up
		login(client,"admin","admin");
		removeUser(client,user);
		logout(client);
	}
	static void test6(UPMSocialReadingStub client){
		//6.1 addFriend not in bbdd == false
		String user="jvc00";
		String user1="jvc01";
		login(client,"admin","admin");
		addUser(client,user);
		String pwd = userPwd.get(user);
		//System.out.println("	pwd:"+pwd);
		logout(client);
		login(client,user,pwd);
		boolean status = addFriend(client,user1);
		testPrint("6.1 addFriend not in db",!status);
		logout(client);
		//clean up
		login(client,"admin","admin");
		removeUser(client,user);
		removeUser(client,user1);
		logout(client);
		//6.2 addFriend not logged in == false
		status = addFriend(client,user1);
		testPrint("6.2 addFriend no login",!status);
		//6.3 addFriend by user == true
		user="jvc00";
		user1="jvc01";
		login(client,"admin","admin");
		addUser(client,user);
		addUser(client,user1);
		pwd = userPwd.get(user);
		//System.out.println("	pwd:"+pwd);
		logout(client);
		login(client,user,pwd);
		status = addFriend(client,user1);
		testPrint("6.3 addFriend by user",status);
		logout(client);
		//clean up
		login(client,"admin","admin");
		removeUser(client,user);
		removeUser(client,user1);
		logout(client);
		//6.3 addFriend twice by user == false
		user="jvc00";
		user1="jvc01";
		login(client,"admin","admin");
		addUser(client,user);
		addUser(client,user1);
		pwd = userPwd.get(user);
		//System.out.println("	pwd:"+pwd);
		logout(client);
		login(client,user,pwd);
		status = addFriend(client,user1);
		status = addFriend(client,user1);
		testPrint("6.3 addFriend by user",!status);
		logout(client);
		//clean up
		login(client,"admin","admin");
		removeUser(client,user);
		removeUser(client,user1);
		logout(client);
	}



	public static void main(String[] args) throws AxisFault ,RemoteException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		es.upm.fi.sos.client.UPMSocialReadingStub client;
		try {
			client = new UPMSocialReadingStub();
			client._getServiceClient().getOptions().setManageSession(true);
			client._getServiceClient().engageModule("addressing");
			//test1(client);
			//test2(client);
			//test3(client);
			//test4(client);
			//test5(client);
			test6(client);
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
