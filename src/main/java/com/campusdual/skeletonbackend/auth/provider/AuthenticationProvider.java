package com.campusdual.skeletonbackend.auth.provider;

import com.campusdual.skeletonbackend.model.Section;
import com.campusdual.skeletonbackend.model.User;
import com.campusdual.skeletonbackend.model.dao.UserDao;
import com.campusdual.skeletonbackend.utils.CipherUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Component
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

	@Autowired
	private UserDao userDao;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationProvider.class);
	
    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
		try {
			return getUserInfo(authentication.getName(), authentication.getCredentials().toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    private UsernamePasswordAuthenticationToken getUserInfo(String userName, String password) throws Exception {
    	CipherUtils cipher = new CipherUtils();
		Set<GrantedAuthority> listAuthorities = new HashSet<>();
		User user = new User();
		Optional<User> optUser = userDao.findByLogin(userName);
		if (!optUser.isPresent()) {
			logger.error("The user not exists in database");
			throw new BadCredentialsException("USER_NOT_EXISTS");
		} else {
			user = optUser.get();
		if (user.getPassword().equals(cipher.encrypt(userName, password))) {
			for (Section section : user.getSections()) {
				listAuthorities.add(new SimpleGrantedAuthority(section.getAlias()));
			}
			return new UsernamePasswordAuthenticationToken(userName, password, listAuthorities);
		} else {
			logger.error("Wrong password");
			throw new BadCredentialsException("WRONG_PASSWORD");
		}
	}
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }  
}