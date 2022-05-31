package com.example.proyectospringdatamongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends MongoRepository<Candidate,String> {
    /*CandidateRepository extiende el MongoRepository interfaz y enchufa el tipo de
    datos del documento con el que estamos trabajando, es
    decir Candidate y String respectivamente.*/
    /*Esto nos dará acceso a todas las operaciones CRUD en torno a la colección
    MongoDB.*/

    /*Podemos agregar algunos métodos a nuestro CandidateRepository para tener
    alguna funcionalidad adicional basada en nuestros requisitos comerciales:*/
    Optional<Candidate> findByEmail(String Email);

    List<Candidate> findByExpGreaterThanEqual(double exp);

    List<Candidate> findByExpBetween(double from, double to);
    /*Arriba, agregamos la funcionalidad de búsqueda basada en el correo electrónico y
    la experiencia. Todo lo que tenemos que hacer es seguir una convención de
    nomenclatura establecida por Datos de Spring.
    Después de la findBy() método escribimos el nombre del atributo en caso de
    camello, seguido de cualquier otra restricción que queramos aplicar. Los
    argumentos del método deben coincidir con la expectativa de la cláusula where.
    Spring Data creará consultas reales para usted durante el inicio de la aplicación
    utilizando esta interfaz.*/

}
