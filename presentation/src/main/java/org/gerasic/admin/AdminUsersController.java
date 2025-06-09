package org.gerasic.admin;

import jakarta.validation.Valid;
import org.gerasic.contracts.admins.UserService;
import org.gerasic.dto.admin.users.CreateUserRequest;
import org.gerasic.dto.admin.users.FriendshipRequest;
import org.gerasic.users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "Admin Users", description = "Administrative operations related to users")
public class AdminUsersController {

    private final UserService userService;

    public AdminUsersController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        userService.createUser(
                createUserRequest.login(),
                createUserRequest.name(),
                createUserRequest.age(),
                createUserRequest.gender(),
                createUserRequest.hairColor()
        );
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all users", description = "Returns a list of all users, with optional filters for name, gender, hair color, and age.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String hairColor,
            @RequestParam(required = false) Integer age) {
        return ResponseEntity.ok(userService.getAllUsers(name, gender, hairColor, age));
    }

    @Operation(summary = "Get user information", description = "Returns detailed information about the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{login}")
    public ResponseEntity<User> getUser(@PathVariable String login) {
        return ResponseEntity.ok(userService.getUserInfo(login));
    }

    @Operation(summary = "Delete a user", description = "Deletes the specified user and all associated data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{login}")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        userService.deleteUser(login);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get user's friends", description = "Returns a list of all friends of the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friends retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{login}/friends")
    public ResponseEntity<String> getFriends(@PathVariable String login) {
        return ResponseEntity.ok(userService.getFriends(login).toString());
    }

    @Operation(summary = "Make users friends", description = "Creates a friendship relationship between two users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users are now friends"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "One or both users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/friendships")
    public ResponseEntity<Void> makeUsersFriends(@Valid @RequestBody FriendshipRequest makeUsersFriendsRequest) {
        userService.addFriend(makeUsersFriendsRequest.login(), makeUsersFriendsRequest.friendLogin());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove friendship", description = "Removes the friendship relationship between two users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Friendship removed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "One or both users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/friendships")
    public ResponseEntity<Void> unmakeUsersFriends(@Valid @RequestBody FriendshipRequest unmakeUsersFriendsRequest) {
        userService.removeFriend(unmakeUsersFriendsRequest.login(), unmakeUsersFriendsRequest.friendLogin());
        return ResponseEntity.ok().build();
    }
}
