package br.com.gabriel.api.service;

import br.com.gabriel.api.domain.User;
import br.com.gabriel.api.exeception.NotFoundException;
import br.com.gabriel.api.repository.UserRepository;
import br.com.gabriel.api.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        String hash = HashUtil.getSecureHash(user.getPassword());
        user.setPassword(hash);
        return userRepository.save(user);
    }

    public User update(User user){
        String hash = HashUtil.getSecureHash(user.getPassword());
        user.setPassword(hash);
        return userRepository.save(user);
    }

    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new NotFoundException("There are not user with id " + id));
    }

    public List<User> listAll(){
        return userRepository.findAll();
    }

    public User login(String email, String password) {
        password = HashUtil.getSecureHash(password);

        Optional<User> login = userRepository.login(email, password);
        return login.orElseThrow(() -> new NotFoundException("Login or Password Invalid"));
    }
}
