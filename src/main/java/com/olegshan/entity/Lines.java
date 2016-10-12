package com.olegshan.entity;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "LINES_TABLE")
public class Lines {

    @Id
    @GeneratedValue
    private Long id;
    @Column(columnDefinition = "LONGBLOB")
    @ElementCollection
    private Map<String, Integer> map;

    public Lines() {
    }

    public Lines(Map<String, Integer> map) {
        this.map = map;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }
}