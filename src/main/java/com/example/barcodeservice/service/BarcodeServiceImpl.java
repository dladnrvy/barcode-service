package com.example.barcodeservice.service;


import com.example.barcodeservice.domain.BarcodeEntity;
import com.example.barcodeservice.dto.BarcodeCreateDto;
import com.example.barcodeservice.dto.BarcodeCreateResponseDto;
import com.example.barcodeservice.repository.BarcodeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BarcodeServiceImpl implements BarcodeService{

    private final ModelMapper modelMapper;
    private final BarcodeRepository barcodeRepository;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * 바코드 저장
     * @return code
     */
    @Override
    @Transactional
    public BarcodeCreateResponseDto createCode(BarcodeCreateDto barcodeCreateDto){
        String randomCode = RandomStringUtils.randomAlphanumeric(16);
        barcodeCreateDto.setBarcode(randomCode);

        //중복검증
        Optional<BarcodeEntity> findBarcode = getBarcodeByUserId(barcodeCreateDto.getUserId());
        BarcodeCreateResponseDto rtnBarcodeResponse = modelMapper.map(findBarcode, BarcodeCreateResponseDto.class);

        if(!findBarcode.isPresent()){
            BarcodeEntity barcodeEntity = BarcodeEntity.builder().userId(barcodeCreateDto.getUserId()).barcode(barcodeCreateDto.getBarcode()).build();
            barcodeRepository.save(barcodeEntity);
            rtnBarcodeResponse = modelMapper.map(barcodeEntity, BarcodeCreateResponseDto.class);
        }

        return rtnBarcodeResponse;
    }

    /**
     * barcode를 통한 조회
     * @return barcodeId
     */
    @Override
    public Optional<BarcodeEntity> getBarcodeByCode(String barcode) {
        Optional<BarcodeEntity> findBarcode = Optional.ofNullable(barcodeRepository.findByBarcode(barcode));
        return findBarcode;
    }

    /**
     * userId를 통한 조회
     * @return barcodeId
     */
    @Override
    public Optional<BarcodeEntity> getBarcodeByUserId(Long userId) {
        Optional<BarcodeEntity> findBarcode = Optional.ofNullable(barcodeRepository.findByUserId(userId));
        return findBarcode;
    }


}
