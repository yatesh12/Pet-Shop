package com.petshop.catalog.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.petshop.catalog.service.CatalogQueryService;
import com.petshop.shared.dto.HomePageDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CatalogQueryController.class)
class CatalogQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatalogQueryService catalogQueryService;

    @Test
    void shouldReturnHomePayload() throws Exception {
        when(catalogQueryService.getHomePage()).thenReturn(new HomePageDto(List.of(), List.of(), List.of(), List.of(), List.of(), List.of()));

        mockMvc.perform(get("/api/catalog/home"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.featuredProducts").isArray());
    }
}
