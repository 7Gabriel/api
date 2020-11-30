package br.com.gabriel.api.resources;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.domain.User;
import br.com.gabriel.api.dto.UserLoginDTO;
import br.com.gabriel.api.dto.UserSaveDTO;
import br.com.gabriel.api.dto.UserUpdateDTO;
import br.com.gabriel.api.dto.UserUpdateRoleDTO;
import br.com.gabriel.api.model.PageModel;
import br.com.gabriel.api.model.PageRequestModel;
import br.com.gabriel.api.service.RequestService;
import br.com.gabriel.api.service.UserService;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log
@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService userService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid UserSaveDTO userDTO){
        User saved = mapper.map(userDTO, User.class);
        User created = userService.save(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateDTO user){
        User userUpdate = mapper.map(user, User.class);
        userUpdate.setId(id);
        User update = userService.update(userUpdate);
        return ResponseEntity.ok(update);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id){
        User byId = userService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @GetMapping
    public ResponseEntity<PageModel<User>> listAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size){
        PageRequestModel pr = new PageRequestModel(page, size);
        PageModel<User> pm = userService.listAllOnLazyMode(pr);
        return ResponseEntity.ok(pm);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginDTO loginDTO){
        User loggedUser = userService.login(loginDTO.getEmail(), loginDTO.getPassword());
        return ResponseEntity.ok(loggedUser);
    }

    @GetMapping("/{id}/requests")
    public ResponseEntity<PageModel<Request>> listAllRequestById(@PathVariable("id") Long id,
        @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size){
        PageRequestModel pr = new PageRequestModel(page, size);
        PageModel<Request> requests = requestService.findAllByOwnerIdOnLazyModel(id, pr);
        return ResponseEntity.ok(requests);
    }

    @PatchMapping("/role/{id}")
    public ResponseEntity<?> updateRole(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateRoleDTO userDTO){

        User user = new User();
        user.setId(id);
        user.setRole(userDTO.getRole());
        userService.updateRole(user);
        return ResponseEntity.ok().build();
    }
}
