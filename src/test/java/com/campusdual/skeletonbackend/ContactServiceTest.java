package com.campusdual.skeletonbackend;

import com.campusdual.skeletonbackend.model.Contact;
import com.campusdual.skeletonbackend.model.dao.ContactDao;
import com.campusdual.skeletonbackend.model.dto.ContactDTO;
import com.campusdual.skeletonbackend.model.dto.dtomapper.ContactMapper;
import com.campusdual.skeletonbackend.service.ContactService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

	@Mock
	ContactDao contactDao;

	@InjectMocks
	ContactService contactService;

	@Test
	void getAllContactsTest() {
		List<Contact> contactsList = new ArrayList<Contact>();


		Contact contactOne = new Contact("One");
		Contact contactTwo = new Contact("Two");
		Contact contactThree = new Contact("Three");

		contactsList.add(contactOne);
		contactsList.add(contactTwo);
		contactsList.add(contactThree);

		when(this.contactDao.findAll()).thenReturn(contactsList);

		List<ContactDTO> empList = contactService.queryAllContact();

		verify(contactDao, times(1)).findAll();
		assertEquals(3, empList.size());
	}

	@Test
	void getOneContactTest() {
		Contact contact = new Contact();
		contact.setId(1);

		when(this.contactDao.getReferenceById(1)).thenReturn(contact);

		ContactDTO contactDTO = new ContactDTO();
		contactDTO.setId(1);

		ContactDTO contactResult = contactService.queryContact(contactDTO);

		assertNotNull(contactResult);
	}

	@Test
	void contactNotPresentInDb() {
		when(this.contactDao.getReferenceById(1)).thenReturn(null);

		ContactDTO contactDTO = new ContactDTO();
		contactDTO.setId(1);

		assertNull(contactService.queryContact(contactDTO));

	}

	@Test
	void addContactTest() {
		Contact contact = new Contact();
		contact.setId(1);
		contact.setName("One");
		contact.setSurname1("Surname1One");
		contact.setSurname2("Surname2One");
		contact.setPhone("666555444");
		contact.setEmail("contact-one@gmail.com");

		ContactDTO createContactDTO = ContactMapper.INSTANCE.toDTO(contact);

		when(this.contactDao.saveAndFlush(any(Contact.class))).thenReturn(contact);
		int contactInsertedId = contactService.insertContact(createContactDTO);

		verify(contactDao, times(0)).findAll();
		verify(contactDao, times(1)).saveAndFlush(any(Contact.class));
		assertNotNull(contactInsertedId);
		assertEquals(1, contactInsertedId);
	}

	@Test
	void editContactTest() {
		Contact contact = new Contact();
		contact.setId(1);
		contact.setName("One");
		contact.setSurname1("Surname1One");
		contact.setSurname2("Surname2One");
		contact.setPhone("666555444");
		contact.setEmail("contact-one@gmail.com");

		ContactDTO editContactDTO = ContactMapper.INSTANCE.toDTO(contact);

		when(this.contactDao.saveAndFlush(any(Contact.class))).thenReturn(contact);

		int editContactId = contactService.updateContact(editContactDTO);

		assertNotNull(editContactId);
		assertEquals(1, editContactId);
	}

	@Test
	void deleteContactTest() {
		Contact contact = new Contact();
		contact.setId(1);
		contact.setName("One");
		contact.setSurname1("Surname1One");
		contact.setSurname2("Surname2One");
		contact.setPhone("666555444");
		contact.setEmail("contact-one@gmail.com");

		ContactDTO editContactDTO = ContactMapper.INSTANCE.toDTO(contact);

		int deleteContactId = contactService.deleteContact(editContactDTO);

		assertNotNull(deleteContactId);
	}
}
