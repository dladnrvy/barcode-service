package com.example.barcodeservice.service;


import com.example.barcodeservice.domain.Barcode;
import com.example.barcodeservice.dto.BarcodeCreateDto;
import com.example.barcodeservice.dto.BarcodeCreateResponseDto;

public interface BarcodeService {

     BarcodeCreateResponseDto createCode(BarcodeCreateDto barcodeCreateDto);
     Barcode findBarcode(String barcode);
}
