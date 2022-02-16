package com.vending.machine.application.service;

import com.vending.machine.application.exception.CoinNotAcceptedException;
import com.vending.machine.application.exception.UserNotFoundException;
import com.vending.machine.application.model.Coin;
import com.vending.machine.domain.model.User;
import com.vending.machine.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {

    private final UserRepository userRepository;

    @Transactional
    public Integer deposit(String username, Integer added) {
        if (!Coin.isAccepted(added)) {
            throw new CoinNotAcceptedException(added);
        }
        User user = getUser(username);
        user.setDeposit(user.getDeposit() + added);
        return userRepository.save(user).getDeposit();
    }

    @Transactional
    public Integer reset(String username) {
        User user = getUser(username);
        user.setDeposit(0);
        return userRepository.save(user).getDeposit();
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
