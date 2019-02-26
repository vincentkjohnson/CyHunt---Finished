package com.COMS309.demo2retry.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;


    public String toString()
    {
    	return "{user id " + getId()+" Username:"+ getName()+" User password:"+ getPassword() +" User email:"+ getEmail()+ "}";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}