package com.mammb.jakartaee.starter.app.example.fileupload;

import com.mammb.jakartaee.starter.domail.example.fileupload.UploadFile;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
@RequestScoped
public class UploadFileService {

    @Inject
    private EntityManager em;

    public long save(UploadFile file) {
        em.persist(file);
        return file.getId();
    }

    public List<UploadFile> list() {
        CriteriaQuery<UploadFile> query = em.getCriteriaBuilder().createQuery(UploadFile.class);
        Root<UploadFile> root = query.from(UploadFile.class);
        return em.createQuery(query.select(root)).getResultList();
    }

    public byte[] getFile(UploadFile file) {
        return em.getReference(UploadFile.class, file.getId()).getBytes();
    }

}
