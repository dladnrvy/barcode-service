package com.example.barcodeservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.FileStore;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class BarcodeCreateResponseDto {
    private String barcode;

    @Builder
    public BarcodeCreateResponseDto(String barcode) {
        this.barcode = barcode;
    }
}
