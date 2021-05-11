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
		System.out.println("	pwd:"+pwd);
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
		System.out.println("	pwd:"+pwd);
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
		System.out.println("	pwd:"+pwd);
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
		System.out.println("	pwd:"+pwd);
		logout(client);
		login(client,user,pwd);
		logout(client);
		//clean up
		login(client,"admin","admin");
		removeUser(client,user);
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
			test1(client);
			test2(client);
			

		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		//es.upm.fi.sos.client.UPMSocialReadingStub cliente1;
		//clientIns(client);



	}

	/* Tests the login of the admin superuser into the social network */
	private static void test_1(UPMSocialReadingStub stub, Login login, User user) throws RemoteException, IOException {

		System.out.println("Test 1: admin login\n");

		login.setArgs0(user);
		String status = (stub.login(login).get_return().localResponse) ? "OK" : "ERROR";
		System.out.printf("Status -> %s\n" + status);
	}

	private static void test_2(UPMSocialReadingStub stub, AddUser add_user, User user)
			throws RemoteException, IOException {

		System.out.println("Test 2: add user\n");

		//add_user.setArgs0(user);
		String status = (stub.addUser(add_user).get_return().localResponse) ? "OK" : "ERROR";
		System.out.printf("Status -> %s\n" + status);
	}

	private static void test_3(UPMSocialReadingStub stub, Login login, User user) throws RemoteException, IOException {

		System.out.println("Test 3: user login\n");

		login.setArgs0(user);
		String status = (stub.login(login).get_return().localResponse) ? "OK" : "ERROR";
		System.out.printf("Status -> %s\n" + status);
	}

	private static void test_4(UPMSocialReadingStub stub, Logout logout) throws RemoteException, IOException {

		System.out.println("Test 4: user logout\n");

		stub.logout(logout);
		String status = "OK";

		System.out.printf("Status -> %s\n" + status);
	}

	private static void test_5(UPMSocialReadingStub stub, ChangePassword change_pass, PasswordPair pass_pair, User user,
			Login login, Logout logout, String pass) throws RemoteException, IOException {

		System.out.println("Test 5: change password\n");

		/* Stores the results of the partial tests */
		boolean[] tests = { false, false, false };
		/* Try change password */
		change_pass.setArgs0(pass_pair);

		tests[0] = stub.changePassword(change_pass).get_return().localResponse;

		/* Logout and try to login with the old pass */
		stub.logout(logout);
		login.setArgs0(user);
		tests[1] = !stub.login(login).get_return().localResponse;

		/* Try login with new pass */
		user.setPwd(pass);
		login.setArgs0(user);
		tests[2] = stub.login(login).get_return().localResponse;

		String status = (!Arrays.asList(tests).contains(false)) ? "OK" : "ERROR";
		System.out.printf("Status -> %s\n" + status);
	}

	private static void test_6(UPMSocialReadingStub stub, RemoveUser remove_user, Username username, Login login,
			User user) throws IOException {

		System.out.println("Test 6: remove user\n");

		boolean[] tests = { false, false };
		remove_user.setArgs0(username);
		tests[0] = (stub.removeUser(remove_user).get_return().localResponse);

		login.setArgs0(user);
		tests[1] = (!stub.login(login).get_return().localResponse);

		String status = (!Arrays.asList(tests).contains(false)) ? "OK" : "ERROR";
		System.out.printf("Status -> %s\n" + status);
	}

}
