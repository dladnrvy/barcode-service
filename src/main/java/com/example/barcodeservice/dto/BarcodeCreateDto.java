package com.example.barcodeservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BarcodeCreateDto {
    private Long userId;
    private String barcode;
}
