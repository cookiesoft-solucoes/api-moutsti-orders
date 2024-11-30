package br.com.alysonrodrigo.apimoutstiorders.mapper;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepUser;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepUserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    /**
     * Converte um objeto Tax para TaxDTO.
     *
     * @param repUser Objeto RepUser a ser convertido.
     * @return Objeto RepUserDTO correspondente.
     */
    public RepUserDTO toDTO(RepUser repUser){
        RepUserDTO repUserDTO = new RepUserDTO();
        repUserDTO.setId(repUser.getId());
        repUserDTO.setName(repUser.getName());
        repUserDTO.setEmail(repUser.getEmail());
        return repUserDTO;
    }

    /**
     * Converte um objeto TaxDTO para Tax.
     *
     * @param repUserDTO Objeto RepUserDTO a ser convertido.
     * @return Objeto RepUser correspondente.
     */
    public RepUser toEntity(RepUserDTO repUserDTO) {
        RepUser repUser = new RepUser();
        repUser.setId(repUserDTO.getId());
        repUser.setName(repUserDTO.getName());
        repUser.setEmail(repUserDTO.getEmail());
        return repUser;
    }
}
