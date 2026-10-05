package com.usfq.bankpulse.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest @AutoConfigureMockMvc class AccountApiAcceptanceTest {
  @Autowired MockMvc mvc;
  @Test @WithMockUser(username="demo",roles="CLIENT") void authenticatedUserCanListAccounts() throws Exception {
    mvc.perform(get("/api/accounts")).andExpect(status().isOk());
  }
}
