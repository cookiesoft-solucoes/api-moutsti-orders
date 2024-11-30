package br.com.alysonrodrigo.apimoutstiorders.mapper;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepProduct;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    /**
     * Converte um objeto Tax para TaxDTO.
     *
     * @param product Objeto RepProduct a ser convertido.
     * @return Objeto TaxDTO correspondente.
     */
    public RepProductDTO toDTO(RepProduct product) {
        RepProductDTO productDTO = new RepProductDTO();
        productDTO.setId(product.getId());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());
        return productDTO;
    }

    /**
     * Converte um objeto TaxDTO para Tax.
     *
     * @param taxDTO Objeto TaxDTO a ser convertido.
     * @param category Categoria associada ao imposto.
     * @return Objeto Tax correspondente.
     */
    public RepProduct toEntity(RepProductDTO productDTO, RepCategory category) {
        RepProduct product = new RepProduct();
        product.setCategory(category);
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        return product;
    }
}
