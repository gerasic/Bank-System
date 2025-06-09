package org.gerasic.contracts.admins;

import org.gerasic.accounts.Account;
import org.gerasic.persistance.entities.TransactionEntity;
import org.gerasic.transactions.Transaction;
import org.gerasic.transactions.TransactionType;
import org.gerasic.users.Gender;
import org.gerasic.users.HairColor;
import org.gerasic.users.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface providing administrative services for user and account management.
 * Includes operations for creating users, managing friendships, and handling financial transactions.
 */
public interface AdminService {

    /**
     * Creates a new user with the specified details if the user does not already exist.
     * @param login the login of the user
     * @param name the name of the user
     * @param age the age of the user
     * @param gender the gender of the user
     * @param hairColor the hair color of the user
     */
    void createUser(String login, String name, int age, Gender gender, HairColor hairColor);

    /**
     * Retrieves information about a user as a string.
     * @param login the login of the user
     * @return a string representing user information, or null if the user is not found
     */
    String getUserInfo(String login);

    List<User> getAllUsers(String name, String gender, String hairColor, Integer age);

    /**
     * Adds a friend to the specified user.
     * @param login the login of the user
     * @param friendLogin the login of the friend to add
     */
    void addFriend(String login, String friendLogin);

    /**
     * Removes a friend from the specified user.
     * @param login the login of the user
     * @param friendLogin the login of the friend to remove
     */
    void removeFriend(String login, String friendLogin);

    List<String> getFriends(String login);

    /**
     * Creates a new account for the specified user.
     * @param login the login of the user
     */
    void createAccount(String login);

    /**
     * Retrieves all accounts associated with the specified user.
     * @param login the login of the user
     * @return a list of accounts associated with the user
     */
    List<Account> getUserAccounts(String login);

    List<Account> getAllAccounts(String ownerLogin);

    void deleteUser(String login);

    /**
     * Retrieves the balance of the specified account.
     * @param accountId the account ID
     * @return the balance of the account, or null if the account is not found
     */
    BigDecimal getBalance(Long accountId);

    /**
     * Deposits a specified amount into the specified account.
     * @param accountId the account ID
     * @param amount the amount to deposit
     */
    void deposit(Long accountId, BigDecimal amount);

    /**
     * Withdraws a specified amount from the specified account if sufficient balance is available.
     * @param accountId the account ID
     * @param amount the amount to withdraw
     */
    void withdraw(Long accountId, BigDecimal amount);

    /**
     * Transfers a specified amount from one account to another, including commission if applicable.
     * @param fromAccountId the source account ID
     * @param toAccountId the target account ID
     * @param amount the amount to transfer
     */
    void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount);

    List<Transaction> getAllTransactions(Long accountId, BigDecimal amount, TransactionType type);
}
