package com.olegshan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Files {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(columnDefinition = "LONGBLOB")
    private byte[] bytes;

    public Files() {
    }

    public Files(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

}