
/**
 * UPMSocialReadingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package es.upm.fi.sos;
/**
 *  UPMSocialReadingSkeleton java skeleton for the axisService
 */
import java.rmi.RemoteException;
import java.util.HashMap;

import es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.*;
import es.upm.fi.sos.xsd.*;
import org.apache.axis2.AxisFault;
public class UPMSocialReadingSkeleton{
	private String AdminName = "admin";
	private String AdminPwd = "admin";
	private HashMap<String,Integer> currentUser = new HashMap<String,Integer>();
	private User user = new User(); /// accessible to all methods in class
	private UPMAuthenticationAuthorizationWSSkeletonStub stub = new UPMAuthenticationAuthorizationWSSkeletonStub();
	/**
	 * Auto generated method signature
	 * 
	 * @param logout 
	 * @return  
	 */
	public UPMSocialReadingSkeleton() throws AxisFault{

	}

	public void logout
	(
			es.upm.fi.sos.Logout logout
			)
	{
		//TODO : fill this with the necessary business logic


	}


	/**
	 * Auto generated method signature
	 * 
	 * @param addFriend 
	 * @return addFriendResponse 
	 */

	public es.upm.fi.sos.AddFriendResponse addFriend
	(
			es.upm.fi.sos.AddFriend addFriend
			)
	{
		//TODO : fill this with the necessary business logic
		throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#addFriend");
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param removeFriend 
	 * @return removeFriendResponse 
	 */

	public es.upm.fi.sos.RemoveFriendResponse removeFriend
	(
			es.upm.fi.sos.RemoveFriend removeFriend
			)
	{
		//TODO : fill this with the necessary business logic
		throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#removeFriend");
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param getMyFriendReadings 
	 * @return getMyFriendReadingsResponse 
	 */

	public es.upm.fi.sos.GetMyFriendReadingsResponse getMyFriendReadings
	(
			es.upm.fi.sos.GetMyFriendReadings getMyFriendReadings
			)
	{
		//TODO : fill this with the necessary business logic
		throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getMyFriendReadings");
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param getMyFriendList 
	 * @return getMyFriendListResponse 
	 */

	public es.upm.fi.sos.GetMyFriendListResponse getMyFriendList
	(
			es.upm.fi.sos.GetMyFriendList getMyFriendList
			)
	{
		//TODO : fill this with the necessary business logic
		throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getMyFriendList");
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param addUser 
	 * @return addUserResponse 
	 */

	public es.upm.fi.sos.AddUserResponse addUser
	(
			es.upm.fi.sos.AddUser addUser
			)
	{
		//TODO : fill this with the necessary business logic
		throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#addUser");
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param removeUser 
	 * @return removeUserResponse 
	 */

	public es.upm.fi.sos.RemoveUserResponse removeUser
	(
			es.upm.fi.sos.RemoveUser removeUser
			)
	{
		//TODO : fill this with the necessary business logic
		throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#removeUser");
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param getMyReadings 
	 * @return getMyReadingsResponse 
	 */

	public es.upm.fi.sos.GetMyReadingsResponse getMyReadings
	(
			es.upm.fi.sos.GetMyReadings getMyReadings
			)
	{
		//TODO : fill this with the necessary business logic
		throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getMyReadings");
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param addReading 
	 * @return addReadingResponse 
	 */

	public es.upm.fi.sos.AddReadingResponse addReading
	(
			es.upm.fi.sos.AddReading addReading
			)
	{
		//TODO : fill this with the necessary business logic
		throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#addReading");
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param changePassword 
	 * @return changePasswordResponse 
	 */

	public es.upm.fi.sos.ChangePasswordResponse changePassword
	(
			es.upm.fi.sos.ChangePassword changePassword
			)
	{
		//TODO : fill this with the necessary business logic
		throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#changePassword");
	}


	/**
	 * Auto generated method signature
	 * 
	 * @param login 
	 * @return loginResponse 
	 */

	public es.upm.fi.sos.LoginResponse login
	(
			es.upm.fi.sos.Login login
			)
	{
		//TODO : fill this with the necessary business logic
		/*
		 * El login del usuario admin no se debe gestionar a través del servicio
		 * UPMAuthenticationAuthorization.
		 */
		es.upm.fi.sos.LoginResponse response = new es.upm.fi.sos.LoginResponse();
		Response responseParam = new Response();
		String name = login.getArgs0().getName();
		String pwd = login.getArgs0().getPwd();

		if (!(currentUser.isEmpty() || (currentUser.containsKey(name)))){ // no se puede logear 
			responseParam.setResponse(false);
			response.set_return(responseParam);
			return response;
		}

		// solo puede logear si no hay nadie o si es el que esta logeado checkear esto
		
		if(name.equals(AdminName)&&pwd.equals(AdminPwd)){
			responseParam.setResponse(true);// login directo, no check authenAutho
			user.setName(name);// log in 
			user.setPwd(pwd);
			response.set_return(responseParam);
		}
		else{
			LoginBackEnd loginParams = new LoginBackEnd();
			loginParams.setName(name);
			loginParams.setPassword(pwd);
			es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.Login login6 = new es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.Login();
			login6.setLogin(loginParams);
			es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.LoginResponse rLogin = new es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.LoginResponse();
			try {
				rLogin = stub.login(login6);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean result = rLogin.get_return().getResult();
			if(result){
				user.setName(name);
				user.setPwd(pwd);
				responseParam.setResponse(result);
				response.set_return(responseParam);

			}
			else {
				Response param2 = new Response();
				param2.setResponse(false);
				response.set_return(param2);
				return response;
			}
		}
		// handle login log
		if (currentUser.isEmpty()){ //no hay nadie login
			currentUser.put(name, 1);
		}
		else if(currentUser.containsKey(name)){ // esta ya logeado
			currentUser.replace(name, currentUser.get(name)+1);
		}

		throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#login");
	}

}
