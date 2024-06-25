package com.stockmanagement.adapters.persistence;

import com.stockmanagement.domain.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    Optional<Warehouse> findByCode(String code);

}
