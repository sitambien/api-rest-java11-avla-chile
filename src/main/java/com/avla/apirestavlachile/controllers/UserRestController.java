package com.avla.apirestavlachile.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avla.apirestavlachile.entities.User;
import com.avla.apirestavlachile.services.IUserService;
import com.avla.apirestavlachile.util.ErrorMessage;
import com.avla.apirestavlachile.util.SuccessMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "User Rest Controller", description = "Controller de la clase User")
@RestController
@RequestMapping("/api")
public class UserRestController {

    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private IUserService userService;
    
    @Operation(summary = "Devuelve los usuarios registrados")
    @GetMapping("/usuarios")
    public List<User> index() {
        return userService.findAll();
    }
    
    @Operation(summary = "Obtiene un usuario por ID", description = "Obtiene un usuario registrado en el sistema según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {

        User usuarioBuscado = userService.findById(id);
        Optional<User> optionalUser = Optional.ofNullable(usuarioBuscado);   

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            logger.info("Se recibio una solicitud GET exitosa para el usuario con ID: {}", id);
            return ResponseEntity.ok(user);
        } else {
            logger.warn("Se estaba intentando encontrar un usuario con ID: {} no existente", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("El usuario con el ID "+ id +" no fue encontrado."));
        }
    }
    
    @Operation(summary = "Crea un nuevo usuario, de cumplir con los requisitos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Devuelve algunos campos del usuario creado", content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))})
    @PostMapping("/usuarios")
    public ResponseEntity<?> create(@RequestBody User user) {

        try {
            logger.info("Se recibio una solicitud POST exitosa para crear el usuario: {}", user);
            User savedUser = userService.save(user);
            String token = generateToken(savedUser);

            UserResponse userResponse = new UserResponse();
            userResponse.setId(savedUser.getId());
            userResponse.setCreated(savedUser.getCreated());
            userResponse.setModified(savedUser.getModified());
            userResponse.setToken(token);

            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
            
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Error interno del servidor"));
        }
        
    }

    @Operation(summary = "Actualiza un usuario por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hecho el update al usuario seleccionado por ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "404", description = "No se encontró el usuario con el ID, posiblemente no existe", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User user) {

        try {
            User usuarioBuscado = userService.findById(id);
            Optional<User> optionalUser = Optional.ofNullable(usuarioBuscado);   

            if (!optionalUser.isPresent()) {
                logger.warn("Se estaba intentando modificar un usuario con ID: {} no existente", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("El usuario con el ID "+ id +" no fue encontrado."));
            }

            logger.info("Se recibio una solicitud PUT exitosa para actualizar el usuario con ID: {}", id);

            User usuarioExistente = optionalUser.get();

            usuarioExistente.setName(user.getName());
            usuarioExistente.setEmail(user.getEmail());
            usuarioExistente.setPassword(user.getPassword());
            usuarioExistente.setPhones(user.getPhones());
            usuarioExistente.setModified(new Date());

            User updatedUser = userService.save(usuarioExistente);
            String token = generateToken(updatedUser);

            UserResponse userResponse = new UserResponse();
            userResponse.setId(updatedUser.getId());
            userResponse.setCreated(updatedUser.getCreated());
            userResponse.setModified(updatedUser.getModified());
            userResponse.setToken(token);

            return ResponseEntity.ok(userResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Error interno del servidor"));
        }
    }

    @Operation(summary = "Borra un usuario, seleccionándolo por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El usuario se eliminó correctamente de los registros", content = @Content(schema = @Schema(implementation = SuccessMessage.class))),
        @ApiResponse(responseCode = "404", description = "No se encontró el usuario con el ID, posiblemente no existe", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        User usuarioBuscado = userService.findById(id);
        Optional<User> optionalUser = Optional.ofNullable(usuarioBuscado);   

        if (optionalUser.isPresent()) {
            logger.info("Se recibio una solicitud DELETE exitosa para eliminar el usuario con ID: {}", id);
            userService.delete(id);
            // Usé el status ok para poder devolver una respuesta porque ResponseEntity.noContent().build(); no tiene contenido en la respuesta
            return ResponseEntity.ok(new SuccessMessage("Usuario "+ id +" eliminado correctamente"));
        } else {
            logger.warn("El usuario con el ID {} no fue encontrado.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("El usuario con el ID "+ id +" no fue encontrado."));
        }
    }

    private String generateToken(User user) {
        return "token temporal";
    }

    // LOGS
    public void metodoQueRegistraLog() {
        logger.debug("Este es un mensaje de log de nivel DEBUG");
        logger.info("Este es un mensaje de log de nivel INFO");
        logger.warn("Este es un mensaje de log de nivel WARN");
        logger.error("Este es un mensaje de log de nivel ERROR");
    }
    
}
