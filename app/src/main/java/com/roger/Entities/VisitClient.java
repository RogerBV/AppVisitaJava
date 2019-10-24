package com.roger.Entities;

public class VisitClient {
    private String cClient;
    private String cDocument;
    private double nLatitude;
    private double nLength;

    public String getcClient() {
        return cClient;
    }

    public void setcClient(String cClient) {
        this.cClient = cClient;
    }

    public String getcDocument() {
        return cDocument;
    }

    public void setcDocument(String cDocument) {
        this.cDocument = cDocument;
    }

    public double getnLatitude() {
        return nLatitude;
    }

    public void setnLatitude(double nLatitude) {
        this.nLatitude = nLatitude;
    }

    public double getnLength() {
        return nLength;
    }

    public void setnLength(double nLength) {
        this.nLength = nLength;
    }
}
