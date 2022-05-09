package com.mammb.jakartaee.starter.view.example.fileupload;

import com.mammb.jakartaee.starter.app.example.fileupload.UploadFileService;
import com.mammb.jakartaee.starter.domail.example.fileupload.UploadFile;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class UploadFileModel implements Serializable {

    private static final Logger log = Logger.getLogger(UploadFileModel.class.getName());

    @Inject
    private UploadFileService service;

    private List<UploadFile> list;

    private Part file;

    @PostConstruct
    public void postConstruct() {
        log.info("#### @PostConstruct");
        this.list = service.list();
    }

    public void upload() throws IOException {
        service.save(UploadFile.of(
            Paths.get(file.getSubmittedFileName()).getFileName().toString(), // for MS IE.
            file.getContentType(),
            file.getInputStream().readAllBytes()
        ));
    }

    public List<UploadFile> getList() {
        return list;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
}
