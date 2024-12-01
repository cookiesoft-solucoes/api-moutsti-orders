package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepProduct;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepUser;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.RepUserRepository;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepUserDTO;
import br.com.alysonrodrigo.apimoutstiorders.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private RepUserRepository userRepository;

    @InjectMocks
    private RepUserService userService;

    @Test
    public void testCreateUser() {
        RepUser userCreate = new RepUser();
        userCreate.setId(1L);
        userCreate.setName("Alyson Rodrigo");
        userCreate.setEmail("alyson@gmail.com");

        when(userRepository.save(any(RepUser.class))).thenReturn(userCreate);

        // Testar o método de criação
        RepUser savedUser = userService.save(userCreate);

        // Verificar o resultado
        assertNotNull(savedUser);
        assertEquals("Alyson Rodrigo", savedUser.getName());
        assertEquals("alyson@gmail.com", savedUser.getEmail());

        // Verificar interação com o repositório
        verify(userRepository, times(1)).save(userCreate);
    }

    @Test
    public void testGetUserById() {

        RepUser userFind = new RepUser();
        userFind.setId(2L);
        userFind.setName("Test User");
        userFind.setEmail("testuser@example.com");

        when(userRepository.findById(2L)).thenReturn(Optional.of(userFind));

        RepUser result = userService.findById(2L);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Test User", result.getName());

        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    public void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(1L));
        verify(userRepository, times(1)).findById(1L);
    }
}