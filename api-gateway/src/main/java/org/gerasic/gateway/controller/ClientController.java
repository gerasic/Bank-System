package org.gerasic.gateway.controller;

import org.gerasic.gateway.dto.*;
import org.gerasic.gateway.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe() {
        return clientService.getMe()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponse>> getMyAccounts() {
        return ResponseEntity.ok(clientService.getMyAccounts());
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountResponse> findAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getAccountById(id));
    }

    @PostMapping("/friends/{friendLogin}")
    public ResponseEntity<Void> addFriend(@PathVariable String friendLogin) {
        clientService.addFriend(friendLogin);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/friends/{friendLogin}")
    public ResponseEntity<Void> removeFriend(@PathVariable String friendLogin) {
        clientService.removeFriend(friendLogin);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accounts/{id}/deposit")
    public ResponseEntity<TransactionResponse> deposit(
            @PathVariable("id") Long id,
            @RequestBody AmountRequest request
    ) {
        return ResponseEntity.ok(clientService.deposit(id, request.amount()));
    }

    @PostMapping("/accounts/{id}/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(
            @PathVariable("id") Long id,
            @RequestBody AmountRequest request
    ) {
        return ResponseEntity.ok(clientService.withdraw(id, request.amount()));
    }

    @PostMapping("/accounts/{id}/transfer")
    public ResponseEntity<TransactionResponse> transfer(
            @PathVariable("id") Long id,
            @RequestBody TransferRequest request
    ) {
        return ResponseEntity.ok(
                clientService.transfer(id, request.toAccountId(), request.amount())
        );
    }
}