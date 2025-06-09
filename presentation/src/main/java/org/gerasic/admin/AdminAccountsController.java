package org.gerasic.admin;

import jakarta.validation.Valid;
import org.gerasic.accounts.Account;
import org.gerasic.contracts.admins.AccountService;
import org.gerasic.contracts.admins.TransactionService;
import org.gerasic.dto.admin.accounts.ChangeBalanceRequest;
import org.gerasic.dto.admin.accounts.TransferRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin/accounts")
@Tag(name = "Admin Accounts", description = "Administrative operations on user accounts")
public class AdminAccountsController {

    private final AccountService accountService;

    private final TransactionService transactionService;

    public AdminAccountsController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Operation(summary = "Create an account for user", description = "Creates a new account for the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account created successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{login}")
    public ResponseEntity<Void> createAccount(@PathVariable String login) {
        accountService.createAccount(login);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get account by id", description = "Getting account by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account got successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @Operation(summary = "Get all accounts", description = "Returns a list of all accounts or only accounts belonging to the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameter"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(required = false) String ownerLogin) {
        return ResponseEntity.ok(accountService.getAllAccounts(ownerLogin));
    }

    @Operation(summary = "Get account balance", description = "Returns the current balance of the specified account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long accountId) {
        BigDecimal balance = accountService.getBalance(accountId);
        return ResponseEntity.ok(balance);
    }

    @Operation(summary = "Deposit funds", description = "Increases the balance of an account by a specified amount.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funds deposited successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format or negative amount"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@Valid @RequestBody ChangeBalanceRequest changeBalanceRequest) {
        transactionService.deposit(changeBalanceRequest.accountId(), changeBalanceRequest.amount());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Withdraw funds", description = "Decreases the balance of an account by a specified amount.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funds withdrawn successfully"),
            @ApiResponse(responseCode = "400", description = "Insufficient funds or invalid request format"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@Valid @RequestBody ChangeBalanceRequest changeBalanceRequest) {
        transactionService.withdraw(changeBalanceRequest.accountId(), changeBalanceRequest.amount());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Transfer funds", description = "Transfers a specified amount from one account to another.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer completed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid transfer parameters"),
            @ApiResponse(responseCode = "404", description = "One or both accounts not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequest transferRequest) {
        transactionService.transfer(transferRequest.fromAccountId(), transferRequest.toAccountId(), transferRequest.amount());
        return ResponseEntity.ok().build();
    }
}
