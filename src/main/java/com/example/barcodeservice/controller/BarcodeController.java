package com.example.barcodeservice.controller;


import com.example.barcodeservice.dto.BarcodeCreateDto;
import com.example.barcodeservice.dto.BarcodeCreateRequestDto;
import com.example.barcodeservice.dto.BarcodeCreateResponseDto;
import com.example.barcodeservice.service.BarcodeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/barcode/")
public class BarcodeController {

    private final BarcodeServiceImpl barcodeServiceImpl;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity createBarcode(@Validated @RequestBody BarcodeCreateRequestDto barcodeReqDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        BarcodeCreateDto barcodeCreateDto = modelMapper.map(barcodeReqDto, BarcodeCreateDto.class);

        BarcodeCreateResponseDto barcodeCreateResponseDto = barcodeServiceImpl.createCode(barcodeCreateDto);
        return ResponseEntity.ok(barcodeCreateResponseDto);
    }
}
