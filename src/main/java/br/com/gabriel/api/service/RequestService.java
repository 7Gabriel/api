package br.com.gabriel.api.service;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.enums.RequestState;
import br.com.gabriel.api.exception.NotFoundException;
import br.com.gabriel.api.model.PageModel;
import br.com.gabriel.api.model.PageRequestModel;
import br.com.gabriel.api.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public Request save(Request request){
        request.setState(RequestState.OPEN);
        request.setCreationDate(LocalDateTime.now());
        return requestRepository.save(request);
    }

    public Request updateRequest(Request request){
        return requestRepository.save(request);
    }

    public Request getById(Long id){
        Optional<Request> request = requestRepository.findById(id);
        return request.orElseThrow(() -> new NotFoundException("There are not request with id " + id));
    }

    public List<Request> listAll(){
        return requestRepository.findAll();
    }

    public List<Request> findAllByOwnerId(Long owner){
        List<Request> allByOwnerId = requestRepository.findAllByOwnerId(owner);
        return allByOwnerId;
    }

    public PageModel<Request> findAllByOwnerIdOnLazyModel(Long ownerId, PageRequestModel pr) {
        Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
        Page<Request> page = requestRepository.findAllByOwnerId(ownerId, pageable);
        PageModel<Request> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());

        return pm;
    }
}
