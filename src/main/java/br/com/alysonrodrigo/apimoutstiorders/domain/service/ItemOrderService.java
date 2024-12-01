package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.ItemOrder;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.ItemOrderRepository;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.RepCategoryRepository;
import br.com.alysonrodrigo.apimoutstiorders.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemOrderService {


    private final ItemOrderRepository itemOrderRepository;

    public ItemOrderService(ItemOrderRepository itemOrderRepository) {
        this.itemOrderRepository = itemOrderRepository;
    }

    /**
     * Retorna todas as categorias.
     *
     * @return Lista de categorias.
     */
    public List<ItemOrder> findAll() {
        return itemOrderRepository.findAll();
    }

    public ItemOrder findById(Long id) {
        return itemOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item Order não encontrado com o ID: " + id));
    }

    public Optional<ItemOrder> findByIdOutException(Long id) {
        return itemOrderRepository.findById(id);
    }

    public ItemOrder save(ItemOrder itemOrder) {
        return itemOrderRepository.save(itemOrder);
    }


    public void delete(Long id) {
        itemOrderRepository.deleteById(id);
    }

}
