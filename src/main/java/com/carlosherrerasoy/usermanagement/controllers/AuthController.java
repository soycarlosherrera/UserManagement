package com.carlosherrerasoy.usermanagement.controllers;

import com.carlosherrerasoy.usermanagement.dao.UserDao;
import com.carlosherrerasoy.usermanagement.models.User;

import com.carlosherrerasoy.usermanagement.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Aqui gestiono el inicio de sesion y todo lo que tiene que ver con autenticacion

@RestController
public class AuthController {
    @Autowired
    private UserDao userDao;

    @Autowired //Porque jwt tiene anotacion de componente
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST) //Anotacion ruta de url para login de  usuarios
    public String loginUser(@RequestBody User user){ //Anotacion para convertir el Json que recibe a un usuario

        User userLogin = userDao.getUserByCredentials(user);

        if(userLogin != null){

            String tokenJwt = jwtUtil.create(String.valueOf(userLogin.getId()),userLogin.getEmail());

            return tokenJwt;
        }
        return "FAIL";
    }
}
