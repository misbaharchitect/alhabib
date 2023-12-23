package com.roytuts.spring.boot.soap.producer.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import https.roytuts_com.userservice.Address;
import https.roytuts_com.userservice.AddressType;
import https.roytuts_com.userservice.User;

@Component
public class UserRepository {
	private List<User> users = new ArrayList<User>();

	public UserRepository() {
		User u1 = new User();
		u1.setId(1);
		u1.setName("Hamid Jawaid");
		u1.setEmail("hamid.jawaid@email.com");

		Address a1 = new Address();
		a1.setStreet("Wall");
		a1.setCity("Hyderabad");
		a1.setState("TS");
		a1.setCountry("India");
		a1.setZip(5000000);
		a1.setAddressType(AddressType.COMMUNICATION);
		u1.setAddress(a1);

		User u2 = new User();
		u2.setId(2);
		u2.setName("John Doe");
		u2.setEmail("john.doe@email.com");

		Address a2 = new Address();
		a2.setStreet("Fall");
		a2.setCity("NY");
		a2.setState("NY");
		a2.setCountry("US");
		a2.setZip(700130);
		a2.setAddressType(AddressType.COMMUNICATION);
		u2.setAddress(a2);

		User u3 = new User();
		u3.setId(3);
		u3.setName("Jane");
		u3.setEmail("jane@email.com");

		Address a3 = new Address();
		a3.setStreet("Tall");
		a3.setCity("Seattle");
		a3.setState("WA");
		a3.setCountry("US");
		a3.setZip(700150);
		a3.setAddressType(AddressType.COMMUNICATION);
		u3.setAddress(a3);

	

		users.add(u1);
		users.add(u2);
		users.add(u3);

	}

	public List<User> getUsers(String name) {
		List<User> userList = new ArrayList<>();
		for (User user : users) {
			if (user.getName().toLowerCase().contains(name.toLowerCase())) {
				userList.add(user);
			}
		}
		return userList;
	}
}