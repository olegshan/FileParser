package com.olegshan.repository;

import com.olegshan.entity.Lines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinesRepository extends JpaRepository<Lines, Long> {
}
