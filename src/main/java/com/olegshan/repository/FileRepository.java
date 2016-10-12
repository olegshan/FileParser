package com.olegshan.repository;

import com.olegshan.entity.SourceFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<SourceFiles, Long> {
}
