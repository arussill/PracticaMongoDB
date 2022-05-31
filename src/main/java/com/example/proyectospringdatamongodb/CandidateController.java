package com.example.proyectospringdatamongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public Candidate getOne(@PathVariable String id) {
        return candidateRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);/*Si el registro no está presente, throws una excepción de tiempo de ejecución personalizada ResourceNotFoundException es una clase personalizada que regresa 404 estado si se lanza:*/
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
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        candidateRepository.delete((candidate));
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