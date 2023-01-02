package com.example.barcodeservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.FileStore;

@Data
public class BarcodeCreateResponseDto {
    private String barcode;

}
