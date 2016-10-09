package com.olegshan.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.File;

/**
 * Created by olegshan on 09.10.2016.
 */
@Entity
public class Files {

    @Id
    @GeneratedValue
    private long id;
    private File file;

    public Files() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
