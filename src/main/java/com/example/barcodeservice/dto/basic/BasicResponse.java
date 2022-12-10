package com.example.barcodeservice.dto.basic;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse<T> {
    private T data;
    private String Code;
}
