package br.com.gabriel.api.resources;

import br.com.gabriel.api.domain.RequestStage;
import br.com.gabriel.api.service.RequestStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("request-stages")
public class RequestStageResource {

    @Autowired
    private RequestStageService stageService;

    @PostMapping
    public ResponseEntity<RequestStage> save(@RequestBody RequestStage stage){
        RequestStage save = stageService.save(stage);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(save);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestStage> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(stageService.getById(id));
    }
}
