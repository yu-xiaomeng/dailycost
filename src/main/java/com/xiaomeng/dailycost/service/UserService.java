package com.xiaomeng.dailycost.service;

import com.xiaomeng.dailycost.domain.User;
import com.xiaomeng.dailycost.domain.UserRepository;
import com.xiaomeng.dailycost.dto.UserSignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String create(UserSignupDto userSignupDto) {
        User user = new User();
        user.setUsername(userSignupDto.getUsername());
        user.setEmail(userSignupDto.getEmail());
        user.setAvatar(userSignupDto.getAvatar());
        user.setCreatedTime(System.currentTimeMillis());
        user.setUpdatedTime(System.currentTimeMillis());
        user.setPassword(passwordEncoder.encode(userSignupDto.getPassword()));
        return userRepository.save(user).getId();
    }

    public String login(String username, String password) {
        String encodePassword = passwordEncoder.encode(password);
        User user = userRepository.findByUsernameAndPassword(username,encodePassword);
        if( user != null) {
            return user.getId();
        } else {
            return "请输入正确的用户名和密码";
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);

        if (user.isPresent()){
            return user.get();
        }else{
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
