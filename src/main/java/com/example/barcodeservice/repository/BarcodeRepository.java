package com.example.barcodeservice.repository;

import com.example.barcodeservice.domain.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BarcodeRepository extends JpaRepository<Barcode, Long> {
    List<Barcode> findByUserId(Long id);
    Barcode findByBarcode(String barcode);
}
