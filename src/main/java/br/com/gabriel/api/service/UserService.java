package br.com.gabriel.api.service;

import br.com.gabriel.api.domain.User;
import br.com.gabriel.api.dto.UserUpdateRoleDTO;
import br.com.gabriel.api.exception.NotFoundException;
import br.com.gabriel.api.model.PageModel;
import br.com.gabriel.api.model.PageRequestModel;
import br.com.gabriel.api.repository.UserRepository;
import br.com.gabriel.api.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

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
        return login.orElseThrow(() -> new NotFoundException("Email or Password Invalid"));
    }

    public PageModel<User> listAllOnLazyMode(PageRequestModel pr){
        Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
        Page<User> page = userRepository.findAll(pageable);
        PageModel<User> pm = new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());

        return pm;
    }

    public int updateRole(User user){
        return userRepository.updateRole(user.getId(), user.getRole());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByEmail(username);
        if(result.isEmpty()) throw new UsernameNotFoundException("dosen t exist user with email " + username);
         User user = result.get();
         List<GrantedAuthority> authorizers = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorizers);

        return userDetails;
    }
}
