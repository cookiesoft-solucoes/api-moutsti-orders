package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepUser;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.RepCategoryRepository;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.RepUserRepository;
import br.com.alysonrodrigo.apimoutstiorders.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private RepUserService userService;

    @Mock
    private RepUserRepository userRepository;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        RepUser user = new RepUser();
        user.setId(1L);
        user.setName("Alyson Rodrigo");
        user.setEmail("alyson@gmail.com");


        when(userRepository.save(any(RepUser.class))).thenReturn(user);

        RepUser result = userRepository.save(user);

        assertNotNull(result);
        assertEquals("Alyson Rodrigo", result.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUserById() {
        RepUser user = new RepUser();
        user.setId(1L);
        user.setName("Alyson Rodrigo");
        user.setEmail("alyson@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        RepUser result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(1L));
        verify(userRepository, times(1)).findById(1L);
    }
}