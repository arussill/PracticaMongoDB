package com.example.proyectospringdatamongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "candidate")
/* @Documento: Esto marca la clase como un objeto de dominio que se
conservará en la base de datos. El nombre de colección predeterminado que se
utiliza es el nombre de la clase (primer carácter en minúsculas). Podemos mapear a
una colección diferente en la base de datos usando el collection atributo de la
anotación */
public class Candidate {
    /*@Carné de identidad: Esto marca el campo utilizado con fines de identidad.*/
    @Id
    private String id;
    private String name;
    private double exp;
    /*@Indexed (único = verdadero): Se aplica al campo que se indexará con
    una restricción de único*/
    @Indexed(unique = true)
    private String email;

    // getters and setters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getExp() {
        return exp;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}