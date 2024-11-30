package br.com.alysonrodrigo.apimoutstiorders.controller;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepUser;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepUserService;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepUserDTO;
import br.com.alysonrodrigo.apimoutstiorders.mapper.UserMapper;
import br.com.alysonrodrigo.apimoutstiorders.producer.UserSyncProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserSyncController.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserSyncProducer userSyncProducer;

    @MockBean
    private RepUserService repUserService;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() throws Exception {

        RepUser user = new RepUser();
        user.setId(1L);
        user.setName("Alyson Rodrigo");
        user.setEmail("alyson@gmail.com");

        RepUserDTO userDTO = new RepUserDTO();
        userDTO.setId(1L);
        userDTO.setName("Alyson Rodrigo");
        userDTO.setEmail("alyson@gmail.com");

        when(repUserService.save(any(RepUser.class))).thenReturn(user);

        mockMvc.perform(post("/users/sync")
                        .with(httpBasic("manager", "manager123")) // Credenciais
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testGetAllUsers() throws Exception {

        mockMvc.perform(get("/categories")
                        .with(httpBasic("manager", "manager123")) // Credenciais
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }
}