package com.example.EventApplication.Controller;


import com.example.EventApplication.Exception.ControllerAdvice;
import com.example.EventApplication.Exception.NotFoundException;
import com.example.EventApplication.Service.UserService;
import com.example.EventApplication.model.Event;
import com.example.EventApplication.model.User;
import com.example.EventApplication.model.request.UserBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User management APIs")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(
            summary = "Get All users)",
            description = "Get All users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping(value = "/get-all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            summary = "Get user by user Id)",
            description = "get Id return user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/get-by-id")
    public ResponseEntity<Optional<User>> getUserById(@RequestParam(name = "id") long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            summary = "Add user",
            description = "Get UserBuilder return User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/add")
    public ResponseEntity<? extends Object> addUser(@RequestBody UserBuilder userBuilder) {
        try {
            return ResponseEntity.ok(userService.addUser(userBuilder));
        } catch (Exception ex) {
            return new ControllerAdvice().handleException(ex);
        }
    }

    @Operation(
            summary = "Update user",
            description = "Get UserBuilder return User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/update")
    public ResponseEntity<? extends Object> updateUser(@RequestBody UserBuilder userBuilder, @RequestParam(name = "id") long id) {
        try {
            return ResponseEntity.ok(userService.updateUser(userBuilder, id));
        } catch (NotFoundException ex) {
            return new ControllerAdvice().handleNotFoundException(ex);
        } catch (Exception ex) {
            return new ControllerAdvice().handleException(ex);
        }
    }

    @Operation(
            summary = "Delete user",
            description = "Get ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam(name = "id") long id) {
        userService.deleteById(id);
    }
}
