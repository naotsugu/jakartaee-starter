package com.mammb.jakartaee.starter.domail.example.fileupload;

import com.mammb.jakartaee.starter.domail.BaseEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;

@Entity(name = UploadFile.NAME)
public class UploadFile extends BaseEntity<UploadFile> {

    public static final String NAME = "UPLOAD_FILES";

    private String name;

    private String mime;

    private int size;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] bytes;

    protected UploadFile() {
    }

    private UploadFile(String name, String mime, int size, byte[] bytes) {
        this.name = name;
        this.mime = mime;
        this.size = size;
        this.bytes = bytes;
    }

    public static UploadFile of(String name, String mime, byte[] bytes) {
        return new UploadFile(name, mime, bytes.length, bytes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
