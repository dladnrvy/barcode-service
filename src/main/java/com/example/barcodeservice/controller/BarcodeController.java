package com.example.barcodeservice.controller;


import com.example.barcodeservice.domain.Barcode;
import com.example.barcodeservice.dto.BarcodeCreateDto;
import com.example.barcodeservice.dto.BarcodeCreateRequestDto;
import com.example.barcodeservice.dto.BarcodeCreateResponseDto;
import com.example.barcodeservice.dto.basic.BasicResponse;
import com.example.barcodeservice.dto.basic.RtnCode;
import com.example.barcodeservice.service.BarcodeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/barcode/")
public class BarcodeController {

    private final BarcodeServiceImpl barcodeServiceImpl;
    private final ModelMapper modelMapper;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

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

    @GetMapping("/find")
    public BasicResponse getBarcodeId(@RequestParam String barcode){
        BasicResponse basicResponse = new BasicResponse<>();
        Barcode barcodeData = barcodeServiceImpl.findBarcode(barcode);
        if(barcodeData == null) basicResponse.setCode(RtnCode.FAIL);
        else {
            basicResponse.setCode(RtnCode.SUCCESS);
            basicResponse.setData(barcodeData.getId());
        }

        log.info(basicResponse.toString());
        return basicResponse;
    }
}
