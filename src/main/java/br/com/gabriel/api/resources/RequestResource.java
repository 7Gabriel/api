package br.com.gabriel.api.resources;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.domain.RequestFile;
import br.com.gabriel.api.domain.RequestStage;
import br.com.gabriel.api.dto.RequestSaveDTO;
import br.com.gabriel.api.dto.RequestUpdateDTO;
import br.com.gabriel.api.model.PageModel;
import br.com.gabriel.api.model.PageRequestModel;
import br.com.gabriel.api.security.AccessManager;
import br.com.gabriel.api.service.RequestFileService;
import br.com.gabriel.api.service.RequestService;
import br.com.gabriel.api.service.RequestStageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestResource {

    @Autowired
    private RequestService requestService;
    @Autowired
    private RequestStageService stageService;
    @Autowired
    private RequestFileService fileService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<Request> save(@RequestBody @Valid RequestSaveDTO requestDTO){
        Request request = mapper.map(requestDTO, Request.class);
        Request createdRequest = requestService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @PreAuthorize("@accessManager.isRequestOwner(#id)")
    @PutMapping("{id}")
    public ResponseEntity<Request> update(@PathVariable("id") Long id, @RequestBody @Valid RequestUpdateDTO requestDTO){
        Request request = mapper.map(requestDTO, Request.class);
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

    @GetMapping("/{id}/request-stages")
    public ResponseEntity<PageModel<RequestStage>> listAllStagesById(@PathVariable("id") Long id,
        @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size){
        PageRequestModel pageRequestModel = new PageRequestModel(page, size);
        PageModel<RequestStage> pageModel = stageService.findAllByRequestIdOnLazyModel(id, pageRequestModel);
        return ResponseEntity.ok(pageModel);
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<PageModel<RequestFile>> listAllFilesById(@PathVariable("id") Long id,
                                                                    @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size){
        PageRequestModel pageRequestModel = new PageRequestModel(page, size);
        PageModel<RequestFile> pageModel = fileService.listAllByRequestId(id, pageRequestModel);
        return ResponseEntity.ok(pageModel);
    }

    @PostMapping("/{id}/files")
    public ResponseEntity<List<RequestFile>> upload(@RequestParam("files") MultipartFile[] files, @PathVariable("id") Long id){
        List<RequestFile> upload = fileService.upload(id, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(upload);
    }

}
