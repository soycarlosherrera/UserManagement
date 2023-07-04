package com.carlosherrerasoy.usermanagement.controllers;

import com.carlosherrerasoy.usermanagement.dao.UserDao;
import com.carlosherrerasoy.usermanagement.models.User;
import com.carlosherrerasoy.usermanagement.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController //Anotacion para definir un controlador
public class UserController {

    @Autowired              //Hace que automaticamente la clase UserDaoImp cree un objeto y lo almacene en userDao, quedara compartido en memoria y no es necesario crear varios de estos, inyeccion de dependencias
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.GET) //Anotacion ruta de url para obtener un usuario por su id
    public User getUser(@PathVariable Long id){ //Anotacion indicando obtener la variable como parametro

        User user = new User();
        user.setId(id);
        user.setName("Carlos");
        user.setLastName("Herrera");
        user.setEmail("carlos@gmail.com");
        user.setPhoneNumber("123456789");

        return user;
    }

    //Listar Usuarios
    @RequestMapping(value = "api/users", method = RequestMethod.GET) //Anotacion ruta de url para obtener una lista de  usuarios
    public List<User> getUsers(@RequestHeader(value="Authorization")String token){ //para guardar en la variable token el valor que viene en la cabecera

        if (!validateToken(token)){return null;}

        return userDao.getUsers();
    }

    //Validar Token
    private boolean validateToken(String token){
        String userId = jwtUtil.getKey(token);
        return userId != null;
    }

    //Registrar Usuario
    @RequestMapping(value = "api/users", method = RequestMethod.POST) //Anotacion ruta de url para registro de  usuarios
    public void registerUser(@RequestBody User user){ //Anotacion para convertir el Json que recibe a un usuario

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id); // creo el objeto de la libreria que me ayuda a crear el has para la contraseña
        String hash = argon2.hash(1,1024,1,user.getPassword()); //1-Cantidad de iteraciones mas alto el numero es mas seguro, 1024 uso de memoria, 1 numero de hilos para no tardar mucho, el ultimo es el valor para el hash
        user.setPassword(hash);

        userDao.registerUsers(user);
    }

    @RequestMapping(value = "user1") //Anotacion ruta de url para este contenido
    public User updateUser(){

        User user = new User();
        user.setName("Carlos");
        user.setLastName("Herrera");
        user.setEmail("carlos@gmail.com");
        user.setPhoneNumber("123456789");

        return user;
    }

    //Eliminar usuario
    @RequestMapping(value = "api/users/{id}", method = RequestMethod.DELETE) //Anotacion ruta de url para este contenido
    public void deletUser(@RequestHeader(value="Authorization")String token,@PathVariable Long id){

        if (!validateToken(token)){return;}
        userDao.deletUser(id);

    }

}
