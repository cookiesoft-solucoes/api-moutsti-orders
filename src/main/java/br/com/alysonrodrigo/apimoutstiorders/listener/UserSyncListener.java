package br.com.alysonrodrigo.apimoutstiorders.listener;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepUser;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.RepUserRepository;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepUserService;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepUserDTO;
import br.com.alysonrodrigo.apimoutstiorders.util.ConstantsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserSyncListener {

    private final RepUserService userService;

    public UserSyncListener(RepUserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = ConstantsUtil.USER_SYNC_QUEUE)
    public void handleUserSync(RepUserDTO userDTO) {
        Optional<RepUser> existingUser = userService.findByIdOutException(userDTO.getId());

        if (existingUser.isPresent()) {
            // Atualiza o usuário existente
            RepUser repUser = existingUser.get();
            BeanUtils.copyProperties(userDTO, repUser, "id");
            userService.save(repUser); // Atualiza o registro no banco
        } else {
            // Cria um novo usuário se não existir
            RepUser newUser = new RepUser();
            newUser.setId(userDTO.getId());
            newUser.setName(userDTO.getName());
            newUser.setEmail(userDTO.getEmail());
            userService.save(newUser); // Salva o novo registro no banco
        }

        System.out.println("User synchronized: " + userDTO);
    }
}
