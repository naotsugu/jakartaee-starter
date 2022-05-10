package com.mammb.jakartaee.starter.view.example.fileupload;

import com.mammb.jakartaee.starter.app.example.fileupload.UploadFileService;
import com.mammb.jakartaee.starter.domail.example.fileupload.UploadFile;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.mail.internet.MimeUtility;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.OutputStream;
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

    @Inject
    private FacesContext facesContext;

    @Inject
    private ExternalContext externalContext;

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
        this.list = service.list();
    }

    public void download(UploadFile uploadFile) {
        try {
            externalContext.responseReset();
            externalContext.setResponseContentType(uploadFile.getMime() + "; charset=UTF-8");
            externalContext.setResponseContentLength(uploadFile.getSize());
            externalContext.setResponseCharacterEncoding("UTF-8");
            var name = MimeUtility.encodeWord(uploadFile.getName(), "UTF-8", "B");
            if (externalContext.getRequestHeaderMap().getOrDefault("User-Agent", "").contains("Firefox")) {
                externalContext.setResponseHeader("Content-Disposition", "attachment; filename*=" + name);
            } else {
                externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + name);
            }
            try (OutputStream os = externalContext.getResponseOutputStream()) {
                os.write(service.getFile(uploadFile));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        facesContext.responseComplete();
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
