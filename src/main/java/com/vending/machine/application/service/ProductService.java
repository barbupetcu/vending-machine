package com.vending.machine.application.service;

import com.vending.machine.application.exception.InvalidProductCostException;
import com.vending.machine.application.exception.ProductNotFoundException;
import com.vending.machine.application.mapper.ProductMapper;
import com.vending.machine.application.model.ProductCommand;
import com.vending.machine.application.model.ProductResult;
import com.vending.machine.domain.model.Product;
import com.vending.machine.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResult createOrUpdateProduct(ProductCommand productCommand) {
        if (productCommand.getCost() % 5 > 0) {
            throw new InvalidProductCostException(productCommand.getCost());
        }
        if (productCommand.getId() != null) {
            productRepository.findById(productCommand.getId())
                    .orElseThrow(() -> new ProductNotFoundException(productCommand.getId()));
        }
        Product product = productRepository.save(ProductMapper.fromCommand(productCommand));
        return ProductMapper.fromProduct(product);
    }

    public ProductResult getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.fromProduct(product);
    }

    public List<ProductResult> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::fromProduct)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
