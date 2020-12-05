package br.com.gabriel.api.service;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.domain.RequestFile;
import br.com.gabriel.api.model.PageModel;
import br.com.gabriel.api.model.PageRequestModel;
import br.com.gabriel.api.repository.RequestFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RequestFileService {

    @Autowired
    private RequestFileRepository fileRepository;

    public PageModel<RequestFile> listAllByRequestId(Long id, PageRequestModel prm){
        Pageable pageable = PageRequest.of(prm.getPage(), prm.getSize());
        Page<RequestFile> page = fileRepository.findAllByRequestId(id, pageable);
        PageModel<RequestFile> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
        return pm;
    }
}
