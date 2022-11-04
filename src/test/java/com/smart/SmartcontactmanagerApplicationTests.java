package com.smart;

import org.apache.catalina.startup.UserConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.smart.controller.HomeController;
import com.smart.controller.UserController;
import com.smart.dao.ContactRepository;
import com.smart.entities.Contact;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
class SmartcontactmanagerApplicationTests {

	@Autowired
	private UserController userController;

	@MockBean
	private ContactRepository repository;

	@Test
	public void getContactsTest() {
		when(repository.findAll()).thenReturn(Stream
				.of(new Contact(1,"test1","test11","test","test@gmail.com","123","image","desc"), new Contact(1,"test1","test11","test","test@gmail.com","123","image","desc")).collect(Collectors.toList()));
		assertEquals(1, userController.showContacts());
	}
	@Test
	public void deleteContactTest() {
		Contact contact = new Contact(1,"test1","test11","test","test@gmail.com","123","image","desc");
		userController.deleteContact(contact);
		verify(repository, times(1)).delete(contact);
	}

}
