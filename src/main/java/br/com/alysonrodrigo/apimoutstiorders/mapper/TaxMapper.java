package br.com.alysonrodrigo.apimoutstiorders.mapper;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Tax;
import br.com.alysonrodrigo.apimoutstiorders.dto.TaxDTO;
import org.springframework.stereotype.Component;

@Component
public class TaxMapper {

    /**
     * Converte um objeto Tax para TaxDTO.
     *
     * @param tax Objeto Tax a ser convertido.
     * @return Objeto TaxDTO correspondente.
     */
    public TaxDTO toDTO(Tax tax) {
        TaxDTO taxDTO = new TaxDTO();
        taxDTO.setTaxType(tax.getTaxType());
        taxDTO.setRate(tax.getRate());
        taxDTO.setDescription(tax.getDescription());
        taxDTO.setCategoryId(tax.getCategory().getId());
        return taxDTO;
    }

    /**
     * Converte um objeto TaxDTO para Tax.
     *
     * @param taxDTO Objeto TaxDTO a ser convertido.
     * @param category Categoria associada ao imposto.
     * @return Objeto Tax correspondente.
     */
    public Tax toEntity(TaxDTO taxDTO, RepCategory category) {
        Tax tax = new Tax();
        tax.setTaxType(taxDTO.getTaxType());
        tax.setRate(taxDTO.getRate());
        tax.setDescription(taxDTO.getDescription());
        tax.setCategory(category);
        return tax;
    }
}
