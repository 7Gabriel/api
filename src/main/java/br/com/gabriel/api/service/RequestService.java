package br.com.gabriel.api.service;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.enums.RequestState;
import br.com.gabriel.api.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Request updadeRequest(Request request){
        return requestRepository.save(request);
    }

    public Request getById(Long id){
        Optional<Request> request = requestRepository.findById(id);
        return request.get();
    }

    public List<Request> listAll(){
        return requestRepository.findAll();
    }

    public List<Request> findAllByOwnerId(Long owner){
        List<Request> allByOwnerId = requestRepository.findAllByOwnerId(owner);
        return allByOwnerId;
    }
}
