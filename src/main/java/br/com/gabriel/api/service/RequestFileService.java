package br.com.gabriel.api.service;

import br.com.gabriel.api.domain.Request;
import br.com.gabriel.api.domain.RequestFile;
import br.com.gabriel.api.model.PageModel;
import br.com.gabriel.api.model.PageRequestModel;
import br.com.gabriel.api.model.UploadedFileModel;
import br.com.gabriel.api.repository.RequestFileRepository;
import br.com.gabriel.api.service.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestFileService {

    @Autowired
    private RequestFileRepository fileRepository;
    @Autowired
    private S3Service s3Service;

    public List<RequestFile> upload(Long requestId, MultipartFile[] files){
        List<UploadedFileModel> uploadedFileModels = new ArrayList<>();
        List<RequestFile> requestFiles = new ArrayList<>();

        uploadedFileModels.forEach(uploadedFileModel -> {
            RequestFile file = new RequestFile();
            file.setName(uploadedFileModel.getName());
            file.setLocation(uploadedFileModel.getLocation());

            Request request = new Request();
            request.setId(requestId);

            file.setRequest(request);
            requestFiles.add(file);
        });

        return fileRepository.saveAll(requestFiles);
    }

    public PageModel<RequestFile> listAllByRequestId(Long id, PageRequestModel prm){
        Pageable pageable = PageRequest.of(prm.getPage(), prm.getSize());
        Page<RequestFile> page = fileRepository.findAllByRequestId(id, pageable);
        PageModel<RequestFile> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
        return pm;
    }
}
