package com.powerreaderapi.powerreaderapi.repository;

import com.powerreaderapi.powerreaderapi.model.PowerReader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerReaderRepository extends JpaRepository<PowerReader, Long> {
}
