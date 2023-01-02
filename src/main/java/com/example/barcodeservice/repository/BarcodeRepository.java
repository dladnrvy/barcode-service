package com.example.barcodeservice.repository;

import com.example.barcodeservice.domain.BarcodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BarcodeRepository extends JpaRepository<BarcodeEntity, Long> {
    BarcodeEntity findByUserId(Long id);
    BarcodeEntity findByBarcode(String barcode);
}
