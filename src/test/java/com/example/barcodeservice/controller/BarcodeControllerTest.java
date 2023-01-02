package com.example.barcodeservice.controller;


import com.example.barcodeservice.domain.BarcodeEntity;
import com.example.barcodeservice.service.BarcodeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class BarcodeControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private BarcodeController barcodeController;

    @MockBean
    private BarcodeServiceImpl barcodeService;


    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(barcodeController).build();
    }

    @Test
    void 바코드생성_유효성_테스트_빈값() throws Exception {
        // given
        // when
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/barcode/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content("{\"userId\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
        // then
    }

    @Test
    void 바코드생성_유효성_테스트_문자열() throws Exception {
        // given
        // when
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/barcode/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content("{\"userId\":\"testString\"}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
        // then
    }

    @Test
    void 바코드생성_유효성_성공_테스트() throws Exception {
        // given
        // when
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/barcode/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content("{\"userId\":123456789}"))
                .andExpect(status().isOk())
                .andDo(print());
        // then
    }

    @Test
    void 바코드아이디_반환_성공_테스트() throws Exception {
        // given
        BarcodeEntity barcode = BarcodeEntity.builder()
                .barcodeId(123L)
                .build();

        given(barcodeService.getBarcodeByCode("1111"))
                .willReturn(Optional.ofNullable(barcode));
        // when & then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/barcode/find")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .param("barcode", "1111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barcodeId").value("123"))
                .andReturn();
    }

    @Test
    void 바코드아이디_반환_실패_테스트() throws Exception {
        // given
        // when & then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/barcode/find")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .param("barcode", "2222"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("404"))
                .andReturn();
    }
}