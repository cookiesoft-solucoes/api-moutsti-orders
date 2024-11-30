package br.com.alysonrodrigo.apimoutstiorders.mapper;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.Category;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Product;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Tax;
import br.com.alysonrodrigo.apimoutstiorders.dto.ProductDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.TaxDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    /**
     * Converte um objeto Tax para TaxDTO.
     *
     * @param tax Objeto Tax a ser convertido.
     * @return Objeto TaxDTO correspondente.
     */
    public ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
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
    public Product toEntity(ProductDTO productDTO, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        return product;
    }
}
