package com.moft.xfapply.logic.network;

import java.io.InputStream;

public class FormFile {
    private byte[] data;
    private InputStream inStream;
    private String fileName;
    private String Formnames;
    private String contentType;
    public FormFile(byte[] data, String fileName, String formnames, String ct) {
        this.data = data;
        this.fileName = fileName;
        Formnames = formnames;
        contentType = ct;
    }
    public FormFile(InputStream inStream, String fileName, String formnames, String ct) {
        this.inStream = inStream;
        this.fileName = fileName;
        Formnames = formnames;
        contentType = ct;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    public InputStream getInStream() {
        return inStream;
    }
    public void setInStream(InputStream inStream) {
        this.inStream = inStream;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFormnames() {
        return Formnames;
    }
    public void setFormnames(String formnames) {
        Formnames = formnames;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    } 
}
