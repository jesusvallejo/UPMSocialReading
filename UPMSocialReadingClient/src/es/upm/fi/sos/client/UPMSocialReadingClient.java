package es.upm.fi.sos.client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;

import es.upm.fi.sos.client.UPMSocialReadingStub;
import es.upm.fi.sos.client.UPMSocialReadingStub.*;

public class UPMSocialReadingClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* Tests the login of the admin superuser into the social network */
	private static void test_1(UPMSocialReadingStub stub, Login login, User user) throws RemoteException, IOException {

		System.out.println("Test 1: admin login\n");

		login.setArgs0(user);
		String status = (stub.login(login).get_return().localResponse) ? "OK" : "ERROR";
		System.out.printf("Status -> %s\n" + status);
	}

	private static void test_2(UPMSocialReadingStub stub, AddUser add_user, Username username)
			throws RemoteException, IOException {

		System.out.println("Test 2: add user\n");

		add_user.setArgs0(username);
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
