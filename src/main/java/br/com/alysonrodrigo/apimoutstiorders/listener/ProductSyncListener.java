package br.com.alysonrodrigo.apimoutstiorders.listener;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepProduct;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepCategoryService;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepProductService;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepProductDTO;
import br.com.alysonrodrigo.apimoutstiorders.util.ConstantsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductSyncListener {

    private final RepProductService repProductService;

    private final RepCategoryService repCategoryService;

    public ProductSyncListener(RepProductService repProductService,
                               RepCategoryService repCategoryService) {
        this.repProductService = repProductService;
        this.repCategoryService = repCategoryService;
    }

    @RabbitListener(queues = ConstantsUtil.PRODUCT_SYNC_QUEUE)
    public void handleUserSync(RepProductDTO repProductDTO) {
        Optional<RepProduct> existingProduct = repProductService.findByIdOutException(repProductDTO.getId());

        Optional<RepCategory> category = repCategoryService.findByIdOutException(repProductDTO.getCategoryId());


        if (existingProduct.isPresent() && category.isPresent()) {
            // Atualiza o usuário existente
            RepProduct repProduct = existingProduct.get();
            BeanUtils.copyProperties(repProductDTO, repProduct, "id","category");
            repProduct.setCategory(category.get());
            repProductService.save(repProduct); // Atualiza o registro no banco
        } else {
            // Cria um novo usuário se não existir
            RepProduct newProduct = new RepProduct();
            newProduct.setId(repProductDTO.getId());
            newProduct.setName(repProductDTO.getName());
            newProduct.setPrice(repProductDTO.getPrice());
            newProduct.setQuantity(repProductDTO.getQuantity());
            newProduct.setCategory(category.get());
            repProductService.save(newProduct); // Salva o novo registro no banco
        }

        System.out.println("Category synchronized: " + repProductDTO);
    }
}
