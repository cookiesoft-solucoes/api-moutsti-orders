package br.com.alysonrodrigo.apimoutstiorders.mapper;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepProduct;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepProductDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    /**
     * Converte um objeto Tax para TaxDTO.
     *
     * @param category Objeto Tax a ser convertido.
     * @return Objeto TaxDTO correspondente.
     */
    public RepCategoryDTO toDTO(RepCategory category){
        RepCategoryDTO repCategoryDTO = new RepCategoryDTO();
        repCategoryDTO.setId(category.getId());
        repCategoryDTO.setName(category.getName());
        repCategoryDTO.setDescription(category.getDescription());
        return repCategoryDTO;
    }

    /**
     * Converte um objeto TaxDTO para Tax.
     *
     * @param repCategoryDTO Objeto RepCategoryDTO a ser convertido.
     * @return Objeto Tax correspondente.
     */
    public RepCategory toEntity(RepCategoryDTO repCategoryDTO) {
        RepCategory repCategory = new RepCategory();
        repCategory.setId(repCategoryDTO.getId());
        repCategory.setName(repCategoryDTO.getName());
        repCategory.setDescription(repCategoryDTO.getDescription());
        return repCategory;
    }
}
