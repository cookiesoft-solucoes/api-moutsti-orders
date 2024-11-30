package br.com.alysonrodrigo.apimoutstiorders.listener;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepUser;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepCategoryService;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepUserService;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepUserDTO;
import br.com.alysonrodrigo.apimoutstiorders.util.ConstantsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategorySyncListener {

    private final RepCategoryService categoryService;

    public CategorySyncListener(RepCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RabbitListener(queues = ConstantsUtil.CATEGORY_SYNC_QUEUE)
    public void handleUserSync(RepCategoryDTO repCategoryDTO) {
        Optional<RepCategory> existingCategory = categoryService.findByIdOutException(repCategoryDTO.getId());

        if (existingCategory.isPresent()) {
            // Atualiza o usuário existente
            RepCategory repCategory = existingCategory.get();
            BeanUtils.copyProperties(repCategoryDTO, repCategory, "id");
            categoryService.save(repCategory); // Atualiza o registro no banco
        } else {
            // Cria um novo usuário se não existir
            RepCategory newCategory = new RepCategory();
            newCategory.setId(repCategoryDTO.getId());
            newCategory.setName(repCategoryDTO.getName());
            newCategory.setDescription(repCategoryDTO.getDescription());
            categoryService.save(newCategory); // Salva o novo registro no banco
        }

        System.out.println("Category synchronized: " + repCategoryDTO);
    }
}
