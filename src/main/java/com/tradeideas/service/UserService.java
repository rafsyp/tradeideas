package com.tradeideas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tradeideas.domain.User;
import com.tradeideas.repositories.UserRepository;
import com.tradeideas.security.Authority;

/**
 * Business service to register new users and update existing users.
 */

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepo;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  public User findbyUsername (String name) {
	  return userRepo.findByUsername(name);
  }
  
  public void update(User user) {
	  userRepo.save(user);
  }
  
  /**
   * Method used to register a new user.  Their password is encrypted and they are added an authority then saved to the Database 
   * by the userrepo.
   */
  
  public User save (User user) {
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    
    Authority authority = new Authority();
    authority.setAuthority("ROLE_USER");
    authority.setUser(user);
    
    user.getAuthorities().add(authority);
    
    return userRepo.save(user);
  }
  
  
}
