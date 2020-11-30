package br.com.gabriel.api.resources;

import br.com.gabriel.api.domain.RequestStage;
import br.com.gabriel.api.dto.RequestStageSaveDTO;
import br.com.gabriel.api.service.RequestStageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("request-stages")
public class RequestStageResource {

    @Autowired
    private RequestStageService stageService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<RequestStage> save(@RequestBody @Valid RequestStageSaveDTO stageDTO){
        RequestStage stage = mapper.map(stageDTO, RequestStage.class);
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
