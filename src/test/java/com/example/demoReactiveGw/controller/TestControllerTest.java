package com.example.demoReactiveGw.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ServerWebExchange;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@Slf4j
@ExtendWith(SpringExtension.class)
public class TestControllerTest {
    private MockMvc mockMvc;

//    @DisplayName("매장 등록 성공")
//    @Test
//    void 매장등록() throws Exception {
//        ServerWebExchange exchange;
//        exchange.mutate()
//        //given
//        final var request = 매장등록요청_생성();
//        doNothing().when(storeService)
//                .createStore(any(StoreRequestDto.class));
//
//        //when
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders.post("/stores")
//                        .contentType("application/json")
//                        .content(new Gson().toJson(request))
//        );
//
//        //then
//        resultActions.andExpect(status().isCreated());
//
//    }



}
