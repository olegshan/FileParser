package com.olegshan.repository;

import com.olegshan.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by olegshan on 09.10.2016.
 */
@Repository
public interface FileRepository extends JpaRepository<Files, Long> {
}
