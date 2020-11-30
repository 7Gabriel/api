package br.com.gabriel.api.service;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.domain.RequestStage;
import br.com.gabriel.api.enums.RequestState;
import br.com.gabriel.api.exception.NotFoundException;
import br.com.gabriel.api.model.PageModel;
import br.com.gabriel.api.model.PageRequestModel;
import br.com.gabriel.api.repository.RequestRepository;
import br.com.gabriel.api.repository.RequestStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RequestStageService {

    @Autowired
    private RequestStageRepository stageRepository;
    @Autowired
    private RequestRepository requestRepository;

    public RequestStage save(RequestStage requestStage){
        requestStage.setRealizationDate(LocalDateTime.now());
        RequestStage stage = stageRepository.save(requestStage);
        Long requestId = requestStage.getRequest().getId();
        RequestState state = stage.getState();
        requestRepository.updateStatus(requestId, state);
        return stage;
    }

    public RequestStage getById(Long id){
        Optional<RequestStage> stage = stageRepository.findById(id);
        return stage.orElseThrow(() -> new NotFoundException("There are not stage with id " + id));
    }

    public List<RequestStage> listAllByRequestId(Long requestId){
        return stageRepository.findAllByRequestId(requestId);
    }

    public PageModel<RequestStage> findAllByRequestIdOnLazyModel(Long requestId, PageRequestModel pr) {
        Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
        Page<RequestStage> page = stageRepository.findAllByRequestId(requestId, pageable);
        PageModel<RequestStage> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());

        return pm;
    }
}
