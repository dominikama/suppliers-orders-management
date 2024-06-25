package com.stockmanagement.adapters.persistence;

import com.stockmanagement.domain.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
