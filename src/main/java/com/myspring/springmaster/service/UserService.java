package com.myspring.springmaster.service;

import com.myspring.springmaster.dataAccess.DAO.UserDAO;
import com.myspring.springmaster.dataAccess.DTO.UserDTO;
import com.myspring.springmaster.dataAccess.mapper.UserMapper;
import com.myspring.springmaster.dataAccess.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(UserDTO userDTO, HttpSession session) {
        userDTO = UserMapper.INSTANCE.toDTO(userRepository.findByUserIdAndPassword(userDTO.getUserId(), userDTO.getPassword()));
        if(userDTO != null){
            session.setAttribute("userId", userDTO.getUserId());
            session.setAttribute("name", userDTO.getName());
            session.setAttribute("role", userDTO.getRoleId());
            return true;
        }
        return false;
    }

    public String signUp(UserDTO userDTO) {
        if(userRepository.findByUserId(userDTO.getUserId()) == null){
            userRepository.save(UserMapper.INSTANCE.toEntity(userDTO));
            return "가입에 성공했습니다";
        }
        return "이미 생성된 Id 입니다.";
    }

    public boolean isAdmin(HttpSession session){
        Integer role = (Integer) session.getAttribute("role");
        if(role != null){
            return role.equals(2) || role.equals(3);
        }
        return false;
    }
}
