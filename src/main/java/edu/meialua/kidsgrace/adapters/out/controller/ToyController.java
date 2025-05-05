package edu.meialua.kidsgrace.adapters.out.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.meialua.kidsgrace.adapters.in.Toy;
import edu.meialua.kidsgrace.adapters.in.repositories.ToyRepository;
import edu.meialua.kidsgrace.model.ToyDTO;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping ("/toys")
public class ToyController {
    @Autowired
    ToyRepository toyRepository;

    @Autowired
    ObjectMapper objectMapper;

    // http://localhost:8080/toys/findAll
    @GetMapping("/findAll")
    public ResponseEntity<String> getToys() throws JsonProcessingException {

        try {
            List<Toy> toys = toyRepository.findAll();

            return ResponseEntity.ok(objectMapper.writeValueAsString(toys));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("BRINQUEDOS NÃO ENCONTRADOS");
        }
        

        
    }

    // http://localhost:8080/toys/findByName
    @GetMapping("/findByName/{name}")
    public ResponseEntity<String> getToyByName(@PathVariable("name") String name) throws JsonProcessingException {
        Optional<Toy> toy = toyRepository.findByName(name);

        if(!toy.isEmpty()) {
            return ResponseEntity.ok(objectMapper.writeValueAsString(toy));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BRINQUEDO NÃO ENCONTRADO COM ESSE NOME");
    }

    // http://localhost:8080/toys/findAllByBrand
    @GetMapping("/findAllByBrand/{brand}")
    public ResponseEntity<String> getToysByBrand(@PathVariable("brand") String brand) throws JsonProcessingException {
        List<Toy> toys = toyRepository.findAllByBrand(brand);

        if(!toys.isEmpty()) {
            return ResponseEntity.ok(objectMapper.writeValueAsString(toys));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BRINQUEDOS NÃO ENCONTRADOS COM ESSA MARCA");
    }

    // http://localhost:8080/toys/findAllByCategpry
    @GetMapping("/findAllByCategory/{category}")
    public ResponseEntity<String> getToysByCategory(@PathVariable("category") String category) throws JsonProcessingException {
        List<Toy> toys = toyRepository.findAllByCategory(category);

        if(!toys.isEmpty()) {
            return ResponseEntity.ok(objectMapper.writeValueAsString(toys));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BRINQUEDOS NÃO ENCONTRADOS COM ESSA CATEGORIA");
    }


    // http://localhost:8080/toys/insert
    @PostMapping(value = "/insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> createToy(@ModelAttribute ToyDTO toy) throws IOException{
        Map<String, String> response = new HashMap<>();
        Toy addToy = new Toy();
        addToy.setName(toy.getName());
        addToy.setDescription(toy.getDescription());
        addToy.setBrand(toy.getBrand());
        addToy.setValue(toy.getValue());
        addToy.setCategory(toy.getCategory());
        addToy.setImage(toy.getImage().getBytes());
        
        try{
            toyRepository.save(addToy);
            response.put("message", "BRINQUEDO CADASTRADO COM SUCESSO");

            return ResponseEntity.ok().body(response);
        }
        catch (Exception e){
            response.put("message", "ERRO AO CADASTRAR BRINQUEDO! " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    // http://localhost:8080/toys/deleteById/{id}
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Map<String, String>> deleteById(@PathVariable("id") Long id) {
        Map<String, String> response = new HashMap<>();
        // Verifica se existe um toy com o ID fornecido
        Optional<Toy> existingToy = toyRepository.findById(id);
        if (existingToy.isPresent()) {
            toyRepository.deleteById(id);
            response.put("message", "BRINQUEDO DELETADO COM SUCESSO PELO ID.");
            return ResponseEntity.ok().body(response);
        } else {
            response.put("message", "NENHUM BRINQUEDO ENCONTRADO COM ESSE ID.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // http://localhost:8080/toys/update
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> updateToy(@PathVariable("id") Long id , @ModelAttribute ToyDTO toy) {
        Map<String, String> response = new HashMap<>();


        // Verificar se o Toy existe
        Optional<Toy> optionalToy = toyRepository.findById(id);
        if (optionalToy.isEmpty()) {
            response.put("message", "BRINQUEDO COM ESSES DADOS NÃO ENCONTRADO");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        try {
            Toy updToy = optionalToy.get();
            updToy.setName(toy.getName());
            updToy.setDescription(toy.getDescription());
            updToy.setBrand(toy.getBrand());
            updToy.setValue(toy.getValue());
            updToy.setCategory(toy.getCategory());

            if (toy.getImage() != null && !toy.getImage().isEmpty()) {
                updToy.setImage(toy.getImage().getBytes());
            }
    
            toyRepository.save(updToy);
            response.put("message", "BRINQUEDO ATUALIZADO COM SUCESSO");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Error processing image");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }


}
