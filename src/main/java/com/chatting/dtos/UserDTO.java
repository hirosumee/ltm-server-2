/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chatting.dtos;


import com.chatting.engine.interfaces.User;
import com.chatting.utils.Common;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hirosume
 */
public class UserDTO implements User,  DTO {
    private String username;
    private String password;

    public UserDTO(String username, String password) throws NoSuchAlgorithmException {
        this.username = username;
        this.password = genHash(password).trim();
    }

    private UserDTO() {

    }

    public static String genHash(String str) throws NoSuchAlgorithmException {
        return Common.MD5(str);
    }

    public static UserDTO fromModel(ResultSet resultSet) throws SQLException, NoSuchAlgorithmException {
        UserDTO dto = new UserDTO();
        dto.setUsername(resultSet.getString(1).trim());
        dto.password = resultSet.getString(2).trim();
        return dto;
    }

    public boolean comparePassword(String str) {
        try {
            String hash = genHash(str);
            return password.equals(hash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("wtf");
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = genHash(password);
    }

}
