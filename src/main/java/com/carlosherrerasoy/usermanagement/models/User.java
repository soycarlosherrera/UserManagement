package com.carlosherrerasoy.usermanagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users") //Indica a que tabla de la base de datos hace referencia
public class User {

    @Id //Indica clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Para que sepa que el id se genera automaticamente
    @Getter @Setter @Column(name = "id") //Modificadores de acceso de lombok, indico a hibernate la columna correspondiente en bd
    private Long id;
    @Getter @Setter @Column(name = "name")
    private String name;
    @Getter @Setter @Column(name = "last_name")
    private String lastName;
    @Getter @Setter @Column(name = "email")
    private String email;
    @Getter @Setter @Column(name = "phone_number")
    private String phoneNumber;
    @Getter @Setter @Column(name = "password")
    private String password;

    /* Reemplazado por la libreria Lombok con anotaciones de getter y setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    */
}
