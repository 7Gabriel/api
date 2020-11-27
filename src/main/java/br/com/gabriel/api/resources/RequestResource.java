package br.com.gabriel.api.resources;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestResource {

    @Autowired
    private RequestService requestService;

    @PostMapping
    public ResponseEntity<Request> save(@RequestBody Request request){
        Request createdRequest = requestService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @PutMapping("{id}")
    public ResponseEntity<Request> update(@PathVariable("id") Long id, @RequestBody Request request){
        request.setId(id);
        Request updated = requestService.save(request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("{id}")
    public ResponseEntity<Request> getById(@PathVariable("id") Long id){
        Request request = requestService.getById(id);
        return ResponseEntity.ok(request);
    }

    @GetMapping
    public ResponseEntity<List<Request>> listAll(){
        return ResponseEntity.ok(requestService.listAll());
    }


}
