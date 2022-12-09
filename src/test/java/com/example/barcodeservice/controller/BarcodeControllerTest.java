package com.example.barcodeservice.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class BarcodeControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private BarcodeController barcodeController;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(barcodeController).build();
    }

    @Test
    void 바코드생성_빈값_테스트() throws Exception {
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
    void 바코드생성_문자열_테스트() throws Exception {
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
    void 바코드생성_성공_테스트() throws Exception {
        // given
        // when
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/barcode/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content("{\"userId\":123456789}"))
                .andExpect(status().isCreated())
                .andDo(print());
        // then
    }
}