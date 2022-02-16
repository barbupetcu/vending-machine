package com.vending.machine.application.service;

import com.vending.machine.application.exception.InsufficientDepositException;
import com.vending.machine.application.exception.InsufficientProductStockException;
import com.vending.machine.application.exception.ProductNotFoundException;
import com.vending.machine.application.exception.UserNotFoundException;
import com.vending.machine.application.model.AddDepositCommand;
import com.vending.machine.application.model.BuyProductCommand;
import com.vending.machine.application.model.BuyProductResult;
import com.vending.machine.application.model.UserRequest;
import com.vending.machine.domain.model.Product;
import com.vending.machine.domain.model.User;
import com.vending.machine.domain.repository.ProductRepository;
import com.vending.machine.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Integer deposit(AddDepositCommand depositAdd) {
        return userRepository.findById(depositAdd.getUserId())
                .map(user -> user.addDeposit(depositAdd.getCoin()))
                .map(userRepository::save)
                .map(User::getDeposit)
                .orElseThrow(() -> new UserNotFoundException(depositAdd.getUserId()));
    }

    @Transactional
    public Integer reset(UserRequest transactionRequest) {
        User user = getUser(transactionRequest.getUserId());
        user.setDeposit(0);
        return userRepository.save(user).getDeposit();
    }

    @Transactional
    public BuyProductResult buyProduct(BuyProductCommand buyProductCommand) {
        Product product = getProduct(buyProductCommand.getProductId());
        if (product.getAmountAvailable() < buyProductCommand.getAmountOfProducts()) {
            throw new InsufficientProductStockException(product.getAmountAvailable(), buyProductCommand.getAmountOfProducts());
        }
        User user = getUser(buyProductCommand.getUserId());
        Integer transactionCost = product.getCost() * buyProductCommand.getAmountOfProducts();
        if (transactionCost < user.getDeposit()) {
            throw new InsufficientDepositException(user.getDeposit(), transactionCost);
        }
        userRepository.save(user.subtract(transactionCost));
        productRepository.save(product.buyProduct(buyProductCommand));

        return BuyProductResult.builder()
                .productName(product.getName())
                .rest(user.getDeposit())
                .totalSpent(transactionCost)
                .build();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
}
