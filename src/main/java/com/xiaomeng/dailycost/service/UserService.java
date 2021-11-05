package com.xiaomeng.dailycost.service;

import com.xiaomeng.dailycost.domain.User;
import com.xiaomeng.dailycost.domain.UserRepository;
import com.xiaomeng.dailycost.dto.UserSignupDto;
import com.xiaomeng.dailycost.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.xiaomeng.dailycost.base.ReturnCode.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String create(UserSignupDto userSignupDto) throws BusinessException {
        User user = new User();
        user.setUsername(userSignupDto.getUsername());
        user.setEmail(userSignupDto.getEmail());
        user.setAvatar(userSignupDto.getAvatar());

        if(isUsernameExist(user.getUsername())) {
            throw new BusinessException(RC_USERNAME_EXIST);
        }
        if(isEmailExist(user.getEmail())) {
            throw new BusinessException(RC_EMAIL_EXIST);
        }

        if(user.getAvatar() == null) {
            user.setAvatar("https://profile.csdnimg.cn/7/4/2/1_github_37242766");
        }

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()){
            return user.get();
        }else{
            throw new UsernameNotFoundException("Username not found");
        }
    }

    private boolean isUsernameExist(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    private boolean isEmailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }
}
