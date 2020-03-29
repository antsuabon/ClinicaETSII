package org.springframework.clinicaetsii.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.User;
import org.springframework.clinicaetsii.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public User findUserByUsername(final String username) {
		return this.userRepository.findUserByUsername(username);
	}
}
