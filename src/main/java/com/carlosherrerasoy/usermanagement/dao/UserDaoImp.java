package com.carlosherrerasoy.usermanagement.dao;

import com.carlosherrerasoy.usermanagement.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;

@Repository     //Hace referencia a la conexion con la base de datos, funcionalidad para poder acceder al repositorio de la BD
@Transactional  //Le da la funcionalidad a esta clase de poder armar las consultas SQL a la base de datos, como tratar las consultas y ejecutarlas, en fragmentos de transaccion

public class UserDaoImp implements UserDao{

    @PersistenceContext                     //Contexto en el lugar de la BD que va a utilizar
    private EntityManager entityManager;    //Nos va a servir para realizar la conexion con la BD

    @Override
    @Transactional
    public List<User> getUsers() {

        String query = "FROM User"; //Consulta sobre Hibernate, User es nuestra clase en Java,
        return entityManager.createQuery(query).getResultList(); //el entity ejecuta el query, retorna una lista y esta es la que retorna la funcion

    }

    @Override
    public void deletUser(Long id) {

        User user = entityManager.find(User.class,id); //Buscamos el usuario en la BD para luego eliminarlo
        entityManager.remove(user);

    }

    @Override
    public void registerUsers(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUserByCredentials(User user) {
        String query = "FROM User WHERE email = :email"; //Consulta sobre Hibernate, User es nuestra clase en Java, se usa :y la variable por seguridad para evitar inyeccion sql en el login
        List<User> list = entityManager.createQuery(query)
                .setParameter("email",user.getEmail())
                .getResultList(); //el entity ejecuta el query, retorna una lista y esta es la que retorna la funcion

        /*if (list.isEmpty()){
            return false;
        }else {
            return true;
        }*/
        //Mas simple
        //return !list.isEmpty();

        if (list.isEmpty()){ //Para evitar null pointer exep, ya que la lista podria estar vacia.
            return null;
        }

        String passwordHashed = list.get(0).getPassword(); //Password de la BD

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id); // creo el objeto de la libreria que me ayuda a crear el has para la contraseña

        if(argon2.verify(passwordHashed,user.getPassword())){ //Comparamos la contraseña con el hash de la BD
            return list.get(0); //Retorno el usuario
        }

        return null;
    }
}
