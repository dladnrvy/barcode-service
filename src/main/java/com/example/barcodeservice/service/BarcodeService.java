package com.example.barcodeservice.service;


import com.example.barcodeservice.domain.BarcodeEntity;
import com.example.barcodeservice.dto.BarcodeCreateDto;
import com.example.barcodeservice.dto.BarcodeCreateResponseDto;

import java.util.Optional;

public interface BarcodeService {

     BarcodeCreateResponseDto createCode(BarcodeCreateDto barcodeCreateDto);
     Optional<BarcodeEntity> getBarcodeByCode(String barcode);
     Optional<BarcodeEntity> getBarcodeByUserId(Long userId);
}
