package com.smart;

import org.apache.catalina.startup.UserConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.smart.controller.HomeController;
import com.smart.controller.UserController;
import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@WebMvcTest(value = UserController.class)
@SpringBootTest
class SmartcontactmanagerApplicationTests {
	@MockBean
	private ContactRepository repo;

	@MockBean 
	private UserRepository repo2;

	private UserController userController;
	private HomeController homeController;

	//contact test cases
	@Test
	public void addContactsTest() {
		when(repo.findAll()).thenReturn(new Contact(1,"test1","test11","test","test@gmail.com","123","image","desc")).collect(Collectors.toList());
		assertEquals(1, userController.addContacts());
	}
	@Test
	public void deleteContactTest() {
		Contact contact = new Contact(1,"test1","test11","test","test@gmail.com","123","image","desc");
		userController.deleteContact(contact);
		verify(repository, times(1)).delete(contact);
	}
    
	@Test
	public void getAllContactsTest() {
		when(repo.findAll());
	}

     //user test cases

	@Test
	public void addUserTest() {
		when(repo2.findAll()).thenReturn(new User(1,"name","email","111")).collect(Collectors.toList()));
		assertEquals(1, homeController.addUsers());
	}

	@Test
	public void deleteUserTest() {
		User user = new User(1,"newuser",nil);
		homeController.deleteContact(contact);
		verify(repository, times(1)).delete(user);
	}
	@Test
	public void getAllUsersTest() {
		when(repo2.findAll());
	}

}
