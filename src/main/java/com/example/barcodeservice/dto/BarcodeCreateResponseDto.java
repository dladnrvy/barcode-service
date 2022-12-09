package com.example.barcodeservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BarcodeCreateResponseDto {
    private String barcode;
}
