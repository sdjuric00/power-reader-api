package com.powerreaderapi.powerreaderapi.repository;

import com.powerreaderapi.powerreaderapi.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
