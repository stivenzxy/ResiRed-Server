package com.project.resiRed.service.user;

import com.project.resiRed.dto.UserDto;
import com.project.resiRed.entity.User;
import com.project.resiRed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserAdminServiceImpl implements UserAdminService {
    @Autowired
    UserRepository userRepository;
    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList=userRepository.findAll();
        List<UserDto> userDtos=new ArrayList<>();
        for(User user:userList){
            userDtos.add(user.getDto());
        }
        return userDtos;
    }
}
