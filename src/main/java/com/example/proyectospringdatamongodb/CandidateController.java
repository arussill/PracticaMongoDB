package com.example.proyectospringdatamongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    private CandidateRepository candidateRepository;

    //    Insertar
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Candidate add(@RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }
    /*Usamos el save() método en el candidateRepository objeto. los Candidate el objeto es capturado por @RequestBody y se utiliza directamente en el save() método.
     */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //    Actualizar
    @PutMapping(value = "/{id}")
    public Candidate update(@PathVariable String id, @RequestBody Candidate updatedCandidate) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        candidate.setName(updatedCandidate.getName());
        candidate.setExp(updatedCandidate.getExp());
        candidate.setEmail(updatedCandidate.getEmail());
        return candidateRepository.save(candidate);
    }
    /*Primero comprobamos si el Candidate con lo dado id está presente o no. Si no, devolvemos un 404 estado, de lo contrario, actualizamos todo el objeto y lo guardamos usando el save() método*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //    Leer
    @GetMapping
    public List<Candidate> getAll() {
        return candidateRepository.findAll();
    }
    /*findAll() devolverá todos los registros en nuestra base de datos*/

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Candidate> candidatoActual = null;
        candidatoActual = candidateRepository.findById(id);
        if (candidatoActual != null){
            response.put("Mensaje: ", candidatoActual);
        }else {
            response.put("Mensaje: ", "No se encontro el candidato con el id ".concat(id));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
       /*Aqui se esta usando el manejo de errores con el try catch*/
    }
    /*findById() El método devolverá un único registro basado en la ID pasada*/

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException() {
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Borrar
    @DeleteMapping(value = "/{id}")
//    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<?> delete(@PathVariable String id) {
        //ResponseEntity se maneja de manera generico lo que retorna el metodo eliminar, para poder manejar las excepciones
        Map<String, Object> response = new HashMap<>();
        //retornara un map con el string que sera el mensaje y un object que sera el status de la respuesta
        try{
            Candidate candidate = candidateRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
            candidateRepository.delete((candidate));
            response.put("Mensaje", "Se elimino el candidato con id ".concat(id));
        }catch (DataAccessException e){
            response.put("Mensaje","Ocurrio un error al eliminar el candidato con id ".concat(id));
            response.put("Error: ", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    /*Usamos el delete() método en el candidateRepository para borrar la entrada*/

    ////////////////////////////////////////////////////////////////

    //Metodos personalizados
    @GetMapping("/searchByEmail")
    public Candidate searchByEmail(@RequestParam(name = "email") String email) {
        return candidateRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException());
    }

    @GetMapping("/searchByExp")
    public List<Candidate> searchByExp(@RequestParam(name = "espFrom")Double expFrom, @RequestParam(name = "expTo", required = false) Double expTo){
        List<Candidate> result = new ArrayList<>();
        if (expTo != null){
            result = candidateRepository.findByExpBetween(expFrom, expTo);
        }else{
            result = candidateRepository.findByExpGreaterThanEqual(expFrom);
        }
        return result;
    }
}