package com.myspring.springmaster.service;

import com.myspring.springmaster.dataAccess.DAO.UserDAO;
import com.myspring.springmaster.dataAccess.DTO.UserDTO;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;

public class UserService {
    final UserDAO userDAO = new UserDAO();

    public boolean loginCheckAndGetSession(UserDTO userDTO, HttpSession session) throws SQLException, ClassNotFoundException {
        if(userDAO.isExistIdAndPw(userDTO.getUserId(), userDTO.getPw())){
            session.setAttribute("user", userDTO.getId());
            return true;
        }
        return false;
    }
    public String signUp(UserDTO user) throws SQLException, ClassNotFoundException {
        if(userDAO.findById(user.getUserId())){
            return "이미 생성된 Id 입니다.";
        }
        userDAO.save(user);
        return "가입에 성공했습니다";
    }

    public boolean isAdmin(HttpSession session){
        String role = (String) session.getAttribute("user");
        if(role != null){
            return role.equals("admin") || role.equals("super_admin");
        }
        return false;
    }
}
