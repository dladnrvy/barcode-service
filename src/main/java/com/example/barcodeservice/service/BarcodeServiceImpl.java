package com.example.barcodeservice.service;


import com.example.barcodeservice.domain.Barcode;
import com.example.barcodeservice.dto.BarcodeCreateDto;
import com.example.barcodeservice.dto.BarcodeCreateResponseDto;
import com.example.barcodeservice.exception.NotFoundBarcodeException;
import com.example.barcodeservice.repository.BarcodeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BarcodeServiceImpl implements BarcodeService{

    private final ModelMapper modelMapper;
    private final BarcodeRepository barcodeRepository;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * 바코드 저장
     * @return code
     */
    @Override
    public BarcodeCreateResponseDto createCode(BarcodeCreateDto barcodeCreateDto){
//        log.info("barcode service");
        BarcodeCreateResponseDto rtnBarcodeResponse = new BarcodeCreateResponseDto();
        String randomCode = RandomStringUtils.randomNumeric(10);
        barcodeCreateDto.setBarcode(randomCode);

//        log.info("barcodeCreateDto barcode : "+barcodeCreateDto.getBarcode());
//        log.info("barcodeCreateDto userId : "+barcodeCreateDto.getUserId());

        //중복검증
        List<Barcode> barcodeList = barcodeRepository.findByUserId(barcodeCreateDto.getUserId());

        if(!barcodeList.isEmpty() && barcodeList.size() > 1) throw new IllegalStateException("여러개의 바코드가 존재합니다.");
        else if(!barcodeList.isEmpty()){
            rtnBarcodeResponse = modelMapper.map(barcodeList.get(0), BarcodeCreateResponseDto.class);
        }else{
            Barcode barcodeEntity = Barcode.builder()
                    .userId(barcodeCreateDto.getUserId())
                    .barcode(barcodeCreateDto.getBarcode())
                    .build();

//            log.info("barcodeEntity barcode : "+barcodeEntity.getBarcode());
//            log.info("barcodeEntity userId : "+barcodeEntity.getUserId());
            if(barcodeEntity == null) throw new NotFoundBarcodeException("barcode not found");

            barcodeRepository.save(barcodeEntity);
            rtnBarcodeResponse = modelMapper.map(barcodeEntity, BarcodeCreateResponseDto.class);
        }

        return rtnBarcodeResponse;
    }

    /**
     * 바코드 조회
     * @return barcodeId
     */
    @Override
    public Barcode findBarcode(String barcode) {
        Barcode findBarcode = barcodeRepository.findByBarcode(barcode);
        return findBarcode;
    }


}
