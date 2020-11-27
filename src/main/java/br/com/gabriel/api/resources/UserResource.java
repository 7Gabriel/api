package br.com.gabriel.api.resources;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.domain.User;
import br.com.gabriel.api.dto.UserLoginDTO;
import br.com.gabriel.api.service.RequestService;
import br.com.gabriel.api.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService userService;
    @Autowired
    private RequestService requestService;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user){
        User created = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User user){
        user.setId(id);
        User update = userService.update(user);
        return ResponseEntity.ok(update);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id){
        User byId = userService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @GetMapping
    public ResponseEntity<List<User>> listAll(){
        return ResponseEntity.ok(userService.listAll());
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserLoginDTO loginDTO){
        User loggedUser = userService.login(loginDTO.getEmail(), loginDTO.getPassword());
        return ResponseEntity.ok(loggedUser);
    }

    @GetMapping("/{id}/requests")
    public ResponseEntity<List<Request>> listAllRequestById(@PathVariable("id") Long id){
        List<Request> requests = requestService.findAllByOwnerId(id);
        return ResponseEntity.ok(requests);
    }
}
