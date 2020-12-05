package br.com.gabriel.api.security;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.domain.User;
import br.com.gabriel.api.exception.NotFoundException;
import br.com.gabriel.api.repository.UserRepository;
import br.com.gabriel.api.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("accessManager")
public class AccessManager {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RequestService requestService;

    public boolean isOwner(Long id){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(!userOptional.isEmpty()) new NotFoundException("There are not user with email "+ email);

        User user = userOptional.get();

        return user.getId() == id;
    }

    public boolean isRequestOwner(Long id){

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(!userOptional.isEmpty()) new NotFoundException("There are not user with email "+ email);

        User user = userOptional.get();
        Request request = requestService.getById(id);

        return user.getId() == request.getOwner().getId();

    }
}
