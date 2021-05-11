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

	/* Tests the login of the admin superuser into the social network */
	private static void test_1(UPMSocialReadingStub stub, Login login, User user, int client)
			throws RemoteException, IOException {

		System.out.println("Test 1: admin login (client" + client + ")\n");

		login.setArgs0(user);
		String status = (stub.login(login).get_return().localResponse) ? "OK" : "ERROR";
		System.out.printf("Status -> %s\n", status);
	}

	private static String test_2(UPMSocialReadingStub stub, AddUser add_user, Username username, int client)
			throws RemoteException, IOException {

		System.out.println("Test 2: add user (client" + client + ")\n");

		add_user.setArgs0(username);
		AddUserResponse res = stub.addUser(add_user).get_return();
		String status = (res.localResponse) ? "OK" : "ERROR";
		System.out.printf("Status -> %s\n", status);
		return res.getPwd();
	}

	private static void test_3(UPMSocialReadingStub stub, Login login, User user, int client)
			throws RemoteException, IOException {

		System.out.println("Test 3: user login (client" + client + ")\n");

		login.setArgs0(user);
		String status = (stub.login(login).get_return().localResponse) ? "OK" : "ERROR";
		System.out.printf("Status -> %s\n", status);
	}

	private static void test_4(UPMSocialReadingStub stub, Logout logout, int client)
			throws RemoteException, IOException {

		System.out.println("Test 4: user logout (client" + client + ")\n");

		stub.logout(logout);
		String status = "OK";

		System.out.printf("Status -> %s\n", status);
	}

	private static void test_5(UPMSocialReadingStub stub, RemoveUser remove_user, Username username, Login login,
			User user) throws IOException {

		System.out.println("Test 6: remove user\n");

		boolean[] tests = { false, false };
		remove_user.setArgs0(username);
		tests[0] = (stub.removeUser(remove_user).get_return().localResponse);

		login.setArgs0(user);
		tests[1] = (!stub.login(login).get_return().localResponse);

		String status = (!Arrays.asList(tests).contains(false)) ? "OK" : "ERROR";
		System.out.printf("Status -> %s\n", status);
	}

	private static void test_6(UPMSocialReadingStub stub, ChangePassword change_pass, PasswordPair pass_pair, User user,
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
		System.out.printf("Status -> %s\n", status);
	}

	public static void main(String[] args) throws AxisFault, RemoteException, Exception {
		// TODO Auto-generated method stub

		/* Define variables */
		String admin = "admin";
		String var_username_1 = "FranSus_1", var_username_2 = "FranSus_2";
		String var_pwd_1, var_pwd_2;

		es.upm.fi.sos.client.UPMSocialReadingStub cliente_1;
		es.upm.fi.sos.client.UPMSocialReadingStub cliente_2;

		try {
			cliente_1 = new es.upm.fi.sos.client.UPMSocialReadingStub();
			cliente_2 = new es.upm.fi.sos.client.UPMSocialReadingStub();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		cliente_1._getServiceClient().getOptions().setManageSession(true);
		cliente_1._getServiceClient().engageModule("addressing");
		cliente_2._getServiceClient().getOptions().setManageSession(true);
		cliente_2._getServiceClient().engageModule("addressing");

		/* Test 1 : login with the admin user */
		Login login_1 = new Login();
		User user_1 = new User();
		user_1.setName(admin);
		user_1.setPwd(admin);
		test_1(cliente_1, login_1, user_1, 1);
		/* login with the admin user from other client */
		login_1 = new Login();
		user_1 = new User();
		user_1.setName(admin);
		user_1.setPwd(admin);
		test_1(cliente_2, login_1, user_1, 2);

		/* Test 2: add user */
		AddUser add_user_1 = new AddUser();
		Username username_1 = new Username();
		username_1.setUsername(var_username_1);
		var_pwd_1 = test_2(cliente_1, add_user_1, username_1, 1);

		/* add user con client1 */
		add_user_1 = new AddUser();
		username_1 = new Username();
		username_1.setUsername(var_username_2);
		var_pwd_2 = test_2(cliente_2, add_user_1, username_1, 2);

		/* Test 3: regular user login */
		Login login_2 = new Login();
		User user_2 = new User();
		user_2.setName(var_username_1);
		user_2.setPwd(var_pwd_1);
		test_3(cliente_1, login_2, user_2, 1);

		/* ATENCION: AQUI PUEDE QUE HAYA QUE DEVOLVER FALSE */
		login_2 = new Login();
		user_2 = new User();
		user_2.setName(var_username_1);
		user_2.setPwd(var_pwd_1);
		test_3(cliente_1, login_2, user_2, 1);

		/* Test 4: regular user logout */

		Logout logout_1 = new Logout();
		test_4(cliente_1, logout_1, 1);

		/* Test 5: Remove user 
		RemoveUser remove_user_1 = new RemoveUser();
		Username username_2 = new Username();
		username_2.setUsername(var_username_2);
		test_5(cliente_1, remove_user_1, username_2, login, user);*/
		// log in Admin cliente para añadir user

		/*
		 * Login login1 = new Login(); User param = new User(); param.setName("admin");
		 * param.setPwd("admin"); login1.setArgs0(param); LoginResponse r =
		 * cliente.login(login1); System.out.println("SALIDA LOGIN ADMIN EN CLIENTE: " +
		 * r.get_return().getResponse());
		 * 
		 * login1 = new Login(); param = new User(); param.setName("admin");
		 * param.setPwd("admin"); login1.setArgs0(param); r = cliente1.login(login1);
		 * System.out.println("SALIDA LOGIN ADMIN EN CLIENTE1: " +
		 * r.get_return().getResponse());
		 * 
		 * // añadir user client AddUser addUser9 = new AddUser(); Username param2 = new
		 * Username(); param2.setUsername("jvc00"); addUser9.setArgs0(param2);
		 * System.out.println("PARAM2: " + addUser9.getArgs0().getUsername());
		 * AddUserResponseE r2 = cliente.addUser(addUser9);
		 * System.out.println("SALIDA ADDUSER EN CLIENTE: " +
		 * r2.get_return().getResponse()); String pwd = r2.get_return().getPwd();
		 * System.out.println("SALIDA 	CONTRASEÑA: " + pwd);
		 * 
		 * // añadir user client1 addUser9 = new AddUser(); param2 = new Username();
		 * param2.setUsername("jvc01"); addUser9.setArgs0(param2);
		 * System.out.println("PARAM2: " + addUser9.getArgs0().getUsername()); r2 =
		 * cliente.addUser(addUser9); System.out.println("SALIDA ADDUSER EN CLIENTE: " +
		 * r2.get_return().getResponse()); String pwd1 = r2.get_return().getPwd();
		 * System.out.println("SALIDA 	CONTRASEÑA: " + pwd1); // Remove User -- ok
		 * 
		 * RemoveUser removeUser5 = new RemoveUser(); Username param9 = new Username();
		 * param9.setUsername("jvc00"); removeUser5.setArgs0(param9); RemoveUserResponse
		 * r12 = cliente.removeUser(removeUser5);
		 * System.out.println("SALIDA REMOVEUSER: " + r12.get_return().getResponse());
		 * removeUser5 = new RemoveUser(); param9 = new Username();
		 * param9.setUsername("jvc01"); removeUser5.setArgs0(param9); r12 =
		 * cliente.removeUser(removeUser5); System.out.println("SALIDA REMOVEUSER: " +
		 * r12.get_return().getResponse());
		 * 
		 * Logout logout4 = new Logout(); cliente.logout(logout4); logout4 = new
		 * Logout(); cliente1.logout(logout4); // log in nuevos users Login login14 =
		 * new Login(); User param3 = new User(); param3.setName("jvc00");
		 * param3.setPwd(pwd); login14.setArgs0(param3); LoginResponse r3 =
		 * cliente.login(login14); System.out.println("SALIDA LOGIN USER CLIENTE: " +
		 * r3.get_return().getResponse());
		 * 
		 * login14 = new Login(); param3 = new User(); param3.setName("jvc01");
		 * param3.setPwd(pwd); login14.setArgs0(param3); r3 = cliente1.login(login14);
		 * System.out.println("SALIDA LOGIN CLIENTE1: " +
		 * r3.get_return().getResponse());
		 */
		// Remove User -- ok
		/*
		 * RemoveUser removeUser5 = new RemoveUser(); Username param9 = new Username();
		 * param9.setUsername("jvc00"); removeUser5.setArgs0(param9); RemoveUserResponse
		 * r12 = cliente.removeUser(removeUser5);
		 * System.out.println("SALIDA REMOVEUSER: " + r12.get_return().getResponse());
		 */
	}

}
