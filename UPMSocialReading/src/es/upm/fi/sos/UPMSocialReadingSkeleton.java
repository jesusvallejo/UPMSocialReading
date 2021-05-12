
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.ChangePassword;
import es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.RemoveUser;
import es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.Username;
import es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.*;
import es.upm.fi.sos.xsd.*;

import org.apache.axis2.AxisFault;


public class UPMSocialReadingSkeleton{
	private String AdminName = "admin";
	private String AdminPwd = "admin";
	private ArrayList<String> users = new ArrayList<String>();
	private HashMap<String, ArrayList<String>> friendList = new HashMap<String, ArrayList<String>>();
	private HashMap<String,TreeMap<String, Book>> bookList = new HashMap<String,TreeMap<String,Book>>();
	private HashMap<String,Integer> loginList = new HashMap<String,Integer>(); // nombre y sesiones abiertas
	User user = new User(); /// accessible to all methods in class
	private UPMAuthenticationAuthorizationWSSkeletonStub stub = new UPMAuthenticationAuthorizationWSSkeletonStub();
	int k = 0;
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
		String userName = user.getName();
		if (loginList.containsKey(userName)) {
			loginList.put(userName, loginList.get(userName)-1);
		} // Eliminamos una sesion
		if(loginList.get(userName)==0){ //   si no quedan sesiones activas en el cliente
			loginList.remove(userName); // eliminamos del log el user
			user.setName(null); // vaciamos el user name
			user.setPwd(null); // vaciamos el user pwd
		}

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
		AddFriendResponse response = new AddFriendResponse();
		Response responseParam = new Response();
		String friend = addFriend.getArgs0().getUsername();
		String userName = user.getName();

		Username param = new Username();
		param.setName(friend);
		ExistUser _existUser = new ExistUser();
		_existUser.setUsername(param);

		try {
			if (stub.existUser(_existUser).get_return().getResult()){
				if( loginList.containsKey(userName)){ // esta dado de alta el amigo y estamos logeados
					if(!(friendList.get(userName).contains(friend))){ //no se alamcenan amigos repetidos
						System.out.println("addFriend:"+friend+"ahora es amigo de "+userName);
						friendList.get(userName).add(friend);
						System.out.println("La nueva lista de amigos es: "+friendList.get(userName).toString());
						responseParam.setResponse(true);
						response.set_return(responseParam);
					}
					else{
						System.out.println("addFriend:ya es amigo");
						responseParam.setResponse(false);
						response.set_return(responseParam);
					}
				}
				else{
					System.out.println("addFriend:no estas logeado");
					responseParam.setResponse(false);
					response.set_return(responseParam);
				}
			}
			else{
				System.out.println("addFriend:el amigo no es usuario correcto en bbdd");
				responseParam.setResponse(false);
				response.set_return(responseParam);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;

		//throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#addFriend");
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
		RemoveFriendResponse response = new RemoveFriendResponse();
		Response responseParam = new Response();
		String friend = removeFriend.getArgs0().getUsername();
		String userName = user.getName();

		Username param = new Username();
		param.setName(friend);
		ExistUser _existUser = new ExistUser();
		_existUser.setUsername(param);

		try {
			if (stub.existUser(_existUser).get_return().getResult()){
				if( loginList.containsKey(userName)){ // esta dado de alta el amigo y estamos logeados
					if(friendList.get(userName).contains(friend)){ //no se alamcenan amigos repetidos
						friendList.get(userName).remove(friend);
						responseParam.setResponse(true);
						response.set_return(responseParam);
					}
					else{
						System.out.println("ya es amigo");
						responseParam.setResponse(false);
						response.set_return(responseParam);
					}
				}
				else{
					System.out.println("no estas logeado");
					responseParam.setResponse(false);
					response.set_return(responseParam);
				}
			}
			else{
				System.out.println("el amigo no es usuario correcto");
				responseParam.setResponse(false);
				response.set_return(responseParam);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
		//throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#removeFriend");
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

		GetMyFriendReadingsResponse response = new GetMyFriendReadingsResponse();
		TitleList titleList = new TitleList();
		String userName = user.getName();
		String friend = getMyFriendReadings.getArgs0().getUsername();

		if(loginList.containsKey(userName)){// esta logeado
			if (friendList.get(userName).contains(friend)){ // son amigos
				String [] array;
				array = bookList.get(friend).keySet().toArray(new String [0]);
				System.out.println("La lista de libros leidos por amigo es: " + Arrays.toString(array) + "\n");
				// hay que dar de mas recientes a menos, invertir
				for (int i = 0; i < array.length / 2; i++) {
					String temp = array[i];
					array[i] = array[array.length - 1 - i];
					array[array.length - 1 - i] = temp;
				}
				System.out.println("La lista INVERTIDA de libros leidos por amigo es: " + Arrays.toString(array) + "\n");
				titleList.setTitles(array);
				titleList.setResult(true);
			}
			else{
				System.out.println("no sois amigos");
				titleList.setResult(false); // no sois amigos
			}
		}
		else{
			System.out.println("no estas logeado");
			titleList.setResult(false); // no estas logeadp
		}

		response.set_return(titleList);
		return response;
		//throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getMyFriendReadings");
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
		GetMyFriendListResponse response = new GetMyFriendListResponse();
		//Response responseParam = new Response();
		FriendList friends = new FriendList();
		String userName = user.getName();
		if(loginList.containsKey(userName)){
			System.out.println("El usuario "+userName+" tiene lista de amigos\n");
			String [] friendsArray = friendList.get(userName).toArray(new String[0]);
			System.out.println("Lista de amigos de " + userName + " es: " + Arrays.toString(friendsArray) + "\n");
			friends.setFriends(friendsArray);
			friends.setResult(true);
		}
		else{
			System.out.println("no estas logeado");
			friends.setResult(false); // no estas logeadp
		}
		response.set_return(friends);
		return response;
		//throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getMyFriendList");
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
		boolean result;
		AddUserResponse response = new AddUserResponse();
		es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.AddUser _addUser = new es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.AddUser();
		UserBackEnd addUserParams = new UserBackEnd();
		es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.AddUserResponse rAddUser = new es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.AddUserResponse();
		es.upm.fi.sos.xsd.AddUserResponse returnParam = new es.upm.fi.sos.xsd.AddUserResponse();
		String userName =addUser.getArgs0().getUsername();
		addUserParams.setName(userName);
		_addUser.setUser(addUserParams);

		if(loginList.containsKey(user.getName()) ){
			if(user.getName().equals(AdminName)){// si somos el admin adelante
				System.out.println("add: "+user.getName()+" puede añadir nuevos usuarios");
				try {
					rAddUser = stub.addUser(_addUser);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result = rAddUser.get_return().getResult();
				if(result){
					// nuevo user
					// inicializamos en lista de amigos , mb tambien para los libros
					users.add(userName);
					friendList.put(userName, new ArrayList<String>());
					bookList.put(userName, new TreeMap<String, Book>());
					System.out.println("add: Usuario Añadido");
				}
				returnParam.setPwd(rAddUser.get_return().getPassword());
				returnParam.setResponse(result);
				response.set_return(returnParam);

			}
			else{ // solo el admin puede realizar esta tarea, devolver false
				System.out.println("add: "+user.getName()+" no puede añadir nuevos usuarios");
				returnParam.setResponse(false);
				response.set_return(returnParam);

			}
		}
		else{
			System.out.println("add: no estas logeado");
			returnParam.setResponse(false);
			response.set_return(returnParam);
		}
		return response;




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
		boolean result;
		RemoveUserResponse response = new RemoveUserResponse();
		Response returnParams = new Response();
		String userName = removeUser.getArgs0().getUsername();
		Username param = new Username();
		param.setName(userName);
		System.out.println("eliminar"+userName);
		ExistUser _existUser = new ExistUser();
		_existUser.setUsername(param);
		if (loginList.containsKey(user.getName())){
			if(user.getName().equals(AdminName) || user.getName().equals(userName)){
				if(!userName.equals(AdminName)){ // if ususario logeado es admin y no se va a leminar admin
					try {
						if(stub.existUser(_existUser).get_return().getResult()){
							//if(name.equals("Cambiar esto")){ // habra que ver si es un usuario nuestro creo CAMBIAR!!
							RemoveUser removeParam = new RemoveUser();
							RemoveUserE _removeUser = new RemoveUserE();
							RemoveUserResponseE rRemove= new RemoveUserResponseE();

							removeParam.setName(userName);
							_removeUser.setRemoveUser(removeParam);
							rRemove = stub.removeUser(_removeUser);
							result =rRemove.get_return().getResult();
							returnParams .setResponse(result);
							if(result){
								// eliminar libros y amigos de las listas
								users.remove(userName);
								System.out.println("remove: Usuario Eliminado");
							}
							response.set_return(returnParams);


							//}
							//else{
							//	returnParams.setResponse(false);
							//	response.set_return(returnParams);
							//}
						}
						else{
							System.out.println("Remove: No se encuentra el ususario en la bbdd");
							returnParams.setResponse(false);
							response.set_return(returnParams);
						}

					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					System.out.println("remove: No se puede eliminar al Admin");
					returnParams.setResponse(false);
					response.set_return(returnParams);
				}		
			}
			else{
				System.out.println("remove: No eres Admin");
				returnParams.setResponse(false);
				response.set_return(returnParams);
			}
		}
		else{
			System.out.println("remove: No estas logeado");
			returnParams.setResponse(false);
			response.set_return(returnParams);
		}
		return response;

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
		GetMyReadingsResponse response = new GetMyReadingsResponse();
		TitleList titleList = new TitleList();
		String userName = user.getName();
		if(loginList.containsKey(userName)){
			String [] array;
			array = bookList.get(userName).keySet().toArray(new String[0]);
			System.out.println("La lista de libros leidos es: " + Arrays.toString(array) + "\n");
			// hay que dar de mas recientes a menos, invertir
			for (int i = 0; i < array.length / 2; i++) {
				String temp = array[i];
				array[i] = array[array.length - 1 - i];
				array[array.length - 1 - i] = temp;
			}
			System.out.println("La lista INVERTIDA de libros leidos es: " + Arrays.toString(array) + "\n");
			titleList.setTitles(array);
			titleList.setResult(true);

		}
		else{
			System.out.println("no estas logeado");
			titleList.setResult(false);; // no estas logeadp
		}

		response.set_return(titleList);
		return response;
		//throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#getMyReadings");
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
		AddReadingResponse response = new AddReadingResponse();
		Response responseParam = new Response();
		String userName = user.getName();
		String Author = addReading.getArgs0().getAuthor();
		String title = addReading.getArgs0().getTitle();
		int calificacion = addReading.getArgs0().getCalification();

		if( loginList.containsKey(userName)){

			Book book = new Book();
			book.setAuthor(Author);
			book.setTitle(title);
			book.setCalification(calificacion);
			bookList.get(userName).put(title, book);

			/*if (!bookList.get(userName).containsKey(title)){
				bookList.get(userName).put(title, book);
			}
			else{
				bookList.get(userName).put(title, book);
			}*/
			System.out.println(	"Libro: " + bookList.get(userName).get(title).getTitle() + 
								"Author: " + bookList.get(userName).get(title).getAuthor() +
								"Rating: " + bookList.get(userName).get(title).getCalification());
			responseParam.setResponse(true);
			response.set_return(responseParam);
		}
		else{
			System.out.println("Usuario no logueado\n");
			responseParam.setResponse(false);
			response.set_return(responseParam);
		}

		return response;

		//throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#addReading");
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
		ChangePasswordResponse response = new ChangePasswordResponse();
		Response responseParam = new Response();
		String userName = user.getName();
		String newPwd = changePassword.getArgs0().getNewpwd();
		String oldPwd = changePassword.getArgs0().getOldpwd();
		ChangePassword _changePassword = new ChangePassword();
		ChangePasswordBackEnd changeParam = new ChangePasswordBackEnd();
		ChangePasswordResponseE rChangePassword = new ChangePasswordResponseE();
		boolean result;
		
		if(loginList.containsKey(userName)){ // estoy logeado
			if(userName.equals(AdminName)&&oldPwd.equals(AdminPwd)){
				System.out.println("changin pass"+userName);
				AdminPwd = newPwd;
				result = true;
			}
			else{
				changeParam.setName(userName);
				changeParam.setOldpwd(oldPwd);
				changeParam.setNewpwd(newPwd);
				_changePassword.setChangePassword(changeParam);
				try {
					System.out.println("changin pass"+userName);
					rChangePassword = stub.changePassword(_changePassword);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = false;
					responseParam.setResponse(result);
					response.set_return(responseParam);
					return response;
				}
				result = rChangePassword.get_return().getResult();
			}
		}

		else{
			System.out.println("not logged in");
			result = false;
		}


		responseParam.setResponse(result);
		response.set_return(responseParam);
		return response;

		//throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#changePassword");
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
		LoginResponse response = new LoginResponse();
		Response responseParam = new Response();
		String name = login.getArgs0().getName();
		String pwd = login.getArgs0().getPwd();

		if (!(loginList.isEmpty() || (loginList.containsKey(name)))){ // no se puede logear 
			System.out.println("cannot login");
			responseParam.setResponse(false);
			response.set_return(responseParam);
			return response;
		}

		// solo puede logear si no hay nadie logeado o si es él el que esta logeado checkear esto

		if(name.equals(AdminName)&&pwd.equals(AdminPwd)){
			responseParam.setResponse(true);// login directo, no check authenAutho
			user.setName(name);// log in 
			user.setPwd(pwd);
			response.set_return(responseParam);
			System.out.println("logeado: Admin");

		}
		else{
			LoginBackEnd loginParams = new LoginBackEnd();
			loginParams.setName(name);
			loginParams.setPassword(pwd);
			es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.Login _login = new es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.Login();
			_login.setLogin(loginParams);
			es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.LoginResponse rLogin = new es.upm.fi.sos.UPMAuthenticationAuthorizationWSSkeletonStub.LoginResponse();
			try {
				rLogin = stub.login(_login);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean result = rLogin.get_return().getResult();
			System.out.println("logeado: "+name);
			if(result){
				user.setName(name);
				user.setPwd(pwd);
				responseParam.setResponse(result);
				response.set_return(responseParam);

			}
			else {
				System.out.println("no se puedo logear: "+name);
				Response returnParam = new Response();
				returnParam.setResponse(false);
				response.set_return(returnParam);
				return response;
			}
		}
		// handle login log
		if (loginList.isEmpty()){ //no hay nadie login
			loginList.put(name, 1);
		}
		else if(loginList.containsKey(name)){ // esta ya logeado
			loginList.put(name, loginList.get(name)+1);
		}

		return response;
	}

}
