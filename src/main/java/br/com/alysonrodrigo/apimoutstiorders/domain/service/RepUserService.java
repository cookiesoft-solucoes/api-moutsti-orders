package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepUser;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.RepUserRepository;
import br.com.alysonrodrigo.apimoutstiorders.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepUserService {


    private final RepUserRepository userRepository;

    public RepUserService(RepUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retorna todos usuários.
     *
     * @return Lista de usuários.
     */
    public List<RepUser> findAll() {
        return userRepository.findAll();
    }

    /**
     * Retorna uma categoria por ID.
     *
     * @param id ID do usuário.
     * @return Objeto Optional contendo o usuário, se encontrada.
     */
    public RepUser findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
    }

    public Optional<RepUser> findByIdOutException(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Salva ou atualiza uma categoria.
     *
     * @param repUser Objeto da categoria a ser salvo ou atualizado.
     * @return RepUser salva.
     */
    public RepUser save(RepUser repUser) {
        return userRepository.save(repUser);
    }

    /**
     * Exclui uma categoria pelo ID.
     *
     * @param id ID da categoria a ser excluída.
     */
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
