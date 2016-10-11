package com.olegshan.entity;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Table(name = "LINES_TABLE")
public class Lines {

    @Id
    @GeneratedValue
    private Long id;
    @Column(columnDefinition = "LONGBLOB")
    private HashMap<String, Integer> map;

    public Lines() {
    }

    public Lines(HashMap<String, Integer> map) {
        this.map = map;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HashMap<String, Integer> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Integer> map) {
        this.map = map;
    }
}