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
            session.setAttribute("id", userDTO.getId());
            return true;
        }
        return false;
    }

    public String signUp(UserDTO userDTO) {
        // userId 중복 확인
        if (userRepository.findByUserId(userDTO.getUserId()) != null) {
            return "이미 생성된 Id 입니다.";
        }

        // email 중복 확인
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            return "이미 사용 중인 이메일 입니다.";
        }

        // phoneNumber 중복 확인
        if (userRepository.findByPhoneNumber(userDTO.getPhoneNumber()) != null) {
            return "이미 사용 중인 전화번호 입니다.";
        }

        // 중복이 없으면 가입 진행
        userRepository.save(UserMapper.INSTANCE.toEntity(userDTO));
        return "가입에 성공했습니다";
    }

    public boolean isAdmin(HttpSession session){
        Integer role = (Integer) session.getAttribute("role");
        if(role != null){
            return role.equals(2) || role.equals(3);
        }
        return false;
    }

    //로그인 여부
    public boolean isLoggedIn(HttpSession session) {
        // 세션에서 사용자 정보가 존재하는지 확인 (예: 사용자 ID 또는 이메일 정보)
        Object user = session.getAttribute("id");  // "user"는 세션에 저장된 사용자 객체나 사용자 ID일 수 있음
        return user != null;  // 사용자 정보가 존재하면 로그인 상태, null이면 로그인하지 않음
    }


    // 아이디 찾기
    public String findUserIdByEmail(String email) {
        if (userRepository.findByEmail(email) != null) {
            return userRepository.findByEmail(email).getUserId();
        }
        return null;
    }


    // 비밀번호 찾기 (예: 새 비밀번호를 반환하거나 메일로 전송)
    public String findPassword(String userId, String email) {
        if (userRepository.findByUserIdAndEmail(userId, email) != null) {
            return userRepository.findByUserIdAndEmail(userId, email).getPassword();
        }
        return null;
    }

}
