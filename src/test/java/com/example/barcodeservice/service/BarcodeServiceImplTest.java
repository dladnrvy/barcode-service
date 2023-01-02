package com.example.barcodeservice.service;


import com.example.barcodeservice.repository.BarcodeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(MockitoExtension.class)
@Transactional
class BarcodeServiceImplTest {

    @Autowired ModelMapper modelMapper;
    @Mock
    BarcodeRepository barcodeRepository;
    @InjectMocks
    BarcodeServiceImpl barcodeServiceImpl;

    private static Logger log = LoggerFactory.getLogger(BarcodeServiceImplTest.class);

    @Nested
    @DisplayName("성공케이스")
    class SuccessCase {

    }

    @Nested
    @DisplayName("실패케이스")
    class FailCase {

    }

}