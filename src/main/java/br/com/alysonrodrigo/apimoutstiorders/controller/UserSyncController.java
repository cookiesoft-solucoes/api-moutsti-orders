package br.com.alysonrodrigo.apimoutstiorders.controller;


import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepUser;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepCategoryService;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepUserService;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepUserDTO;
import br.com.alysonrodrigo.apimoutstiorders.mapper.CategoryMapper;
import br.com.alysonrodrigo.apimoutstiorders.mapper.UserMapper;
import br.com.alysonrodrigo.apimoutstiorders.producer.CategorySyncProducer;
import br.com.alysonrodrigo.apimoutstiorders.producer.UserSyncProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserSyncController {

    private final UserSyncProducer userSyncProducer;

    private final RepUserService repUserService;

    private final UserMapper userMapper;

    public UserSyncController(UserSyncProducer userSyncProducer,
                              RepUserService repUserService,
                              UserMapper userMapper) {
        this.userSyncProducer = userSyncProducer;
        this.repUserService = repUserService;
        this.userMapper = userMapper;
    }

    /**
     * Endpoint para enviar um evento de sincronização de usuário.
     *
     * @param userDTO Dados do usuário a ser sincronizado.
     * @return Resposta indicando que o evento foi enviado.
     */
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/sync")
    public ResponseEntity<String> syncUser(@RequestBody RepUserDTO userDTO) {
        userSyncProducer.sendUserCreatedMessage(userDTO);
        return ResponseEntity.ok("User sync message sent for user: " + userDTO.getName());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<List<RepUserDTO>> allUsers() {
        List<RepUser> users = repUserService.findAll(); // Método no serviço para buscar todos os produtos
        List<RepUserDTO> userDTOs = users.stream()
                .map(userMapper::toDTO) // Converter entidades para DTOs
                .toList();

        return ResponseEntity.ok(userDTOs);
    }
}
