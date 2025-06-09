package org.gerasic.admin;

import org.gerasic.contracts.admins.TransactionService;
import org.gerasic.transactions.Transaction;
import org.gerasic.transactions.TransactionType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin/transactions")
@Tag(name = "Admin Transactions", description = "Administrative operations for viewing account transactions")
public class AdminTransactionsController {

    private final TransactionService transactionService;

    public AdminTransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(
            summary = "Get all transactions",
            description = "Returns a list of transactions, optionally filtered by amount, account ID, or transaction type."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) TransactionType type) {
        return ResponseEntity.ok(transactionService.getAllTransactions(accountId, amount, type));
    }
}
