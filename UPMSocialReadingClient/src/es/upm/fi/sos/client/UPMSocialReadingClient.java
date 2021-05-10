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

import es.upm.fi.sos.client.UPMSocialReadingStub;
import es.upm.fi.sos.client.UPMSocialReadingStub.*;

public class UPMSocialReadingClient {

	public static void main(String[] args) throws AxisFault ,RemoteException {
		// TODO Auto-generated method stub
		es.upm.fi.sos.client.UPMSocialReadingStub cliente;
		es.upm.fi.sos.client.UPMSocialReadingStub cliente1;

		try {
			cliente = new es.upm.fi.sos.client.UPMSocialReadingStub();
			cliente1= new es.upm.fi.sos.client.UPMSocialReadingStub();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		//cliente._getServiceClient().getOptions().setManageSession();
		//cliente._getServiceClient().engageModule("addressing");
		//cliente1._getServiceClient().getOptions().setManageSession(true);
		//cliente1._getServiceClient().engageModule("addressing");
		// log in Admin cliente para añadir user

		Login login1 = new Login();
		User param = new User();
		param.setName("admin");
		param.setPwd("admin");
		login1.setArgs0(param);
		LoginResponse r = cliente.login(login1);
		System.out.println("SALIDA LOGIN ADMIN EN CLIENTE: " + r.get_return().getResponse());
		/*
		login1 = new Login();
		param = new User();
		param.setName("admin");
		param.setPwd("admin");
		login1.setArgs0(param);
		r = cliente1.login(login1);
		System.out.println("SALIDA LOGIN ADMIN EN CLIENTE1: " + r.get_return().getResponse());
		*/
		// añadir user client
		AddUser addUser9 = new AddUser();
		Username param2 = new Username();
		param2.setUsername("jvc00");
		addUser9.setArgs0(param2);
		System.out.println("PARAM2: " + addUser9.getArgs0().getUsername());
		AddUserResponseE r2 = cliente.addUser(addUser9);
		/*
		System.out.println("SALIDA ADDUSER EN CLIENTE: " + r2.get_return().getResponse());
		String pwd = r2.get_return().getPwd();
		System.out.println("SALIDA 	CONTRASEÑA: " + pwd);

		// añadir user client1
		addUser9 = new AddUser();
		param2 = new Username();
		param2.setUsername("jvc01");
		addUser9.setArgs0(param2);
		System.out.println("PARAM2: " + addUser9.getArgs0().getUsername());
		r2 = cliente.addUser(addUser9);
		System.out.println("SALIDA ADDUSER EN CLIENTE: " + r2.get_return().getResponse());
		pwd = r2.get_return().getPwd();
		System.out.println("SALIDA 	CONTRASEÑA: " + pwd);

		Logout logout4 = new Logout();
		cliente.logout(logout4);
		logout4 = new Logout();
		cliente1.logout(logout4);

		// log in nuevos users
		Login login14 = new Login();
		User param3 = new User();
		param3.setName("jvc00");
		param3.setPwd(pwd);
		login14.setArgs0(param3);
		LoginResponse r3 = cliente.login(login14);
		System.out.println("SALIDA LOGIN USER CLIENTE: " + r3.get_return().getResponse());
		
		login14 = new Login();
		param3 = new User();
		param3.setName("jvc00");
		param3.setPwd(pwd);
		login14.setArgs0(param3);
		r3 = cliente1.login(login14);
		System.out.println("SALIDA LOGIN CLIENTE1: " + r3.get_return().getResponse());
		*/

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

		add_user.setArgs0(user);
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
