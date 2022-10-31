package com.smart.controller;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ContactRepository contactRepository;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal){
		String userName = principal.getName();
		User user = userRepository.getUserByUserName(userName);
		model.addAttribute("user", user);
	}

	// dashboard home
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "User Dashboard");
		return "normal/user_dashboard";
	}

	//add contacts
	@RequestMapping("/add-contact")
	public String addContact(Model model){
		model.addAttribute("title", "add-contact");
		model.addAttribute("contact", new Contact());
		return "normal/add-contact";
	}

	//processing add-contact

	@PostMapping(value="/process-contact")
	public String processContact(@ModelAttribute Contact contact, Principal principal, HttpSession session){
		try{

			String name = principal.getName();
			User user = userRepository.getUserByUserName(name);
			user.getContacts().add(contact);
			contact.setUser(user);
			// System.out.println("----------------------"+contact + "///////////" + user);
			session.setAttribute("message", new Message("contact saved", "success"));
			this.userRepository.save(user);

		}catch(Exception e){

			e.printStackTrace();
			session.setAttribute("message", new Message("something went wrong", "danger"));

		}

		return "normal/add-contact";
	}

	@RequestMapping("/show-contacts")
	public String showContacts(Model model, Principal principal){
		model.addAttribute("title", "your-contacts");

		String userName = principal.getName();
		User user = userRepository.getUserByUserName(userName);

		List<Contact> contacts = this.contactRepository.getContactByUserId(user.getId());
		model.addAttribute("contacts", contacts);	
		// System.out.println(contacts.get(0).getPhone());	


		return "normal/show-contacts";
	}
	
	@RequestMapping("/profile")
	public String userProfile(Model model){
		model.addAttribute("title", "profile");
		return "normal/profile";
	}



		// delete contact handler

		@RequestMapping("/delete/{cid}")
		public String deleteContact(@PathVariable("cid") Integer cId, Model model, HttpSession session,
				Principal principal) {
	
			Contact contact = this.contactRepository.findById(cId).get();
	
			User user = this.userRepository.getUserByUserName(principal.getName());
	
			user.getContacts().remove(contact);
	
			this.userRepository.save(user);
	

		    session.setAttribute("message", new Message("Contact deleted succesfully...", "success"));
	
			return "redirect:/user/show-contacts/0";
		}

}
