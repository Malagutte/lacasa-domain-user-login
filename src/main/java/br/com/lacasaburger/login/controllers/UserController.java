package br.com.lacasaburger.login.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lacasaburger.login.dtos.responses.LoginDTOResponse;
import br.com.lacasaburger.login.helpers.JwtHelper;
import br.com.lacasaburger.login.helpers.SecurityHelper;
import br.com.lacasaburger.login.services.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/user")
@Api(tags = { "User" })
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok", response = LoginDTOResponse.class) })
    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> auth(@RequestHeader(SecurityHelper.TOKEN_HEADER) String token) {

        var id = JwtHelper.getValueFieldFromTokem(token, SecurityHelper.TOKEN_USER_ID);
        var optUser = userService.loadUserById(id);

        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        var user = optUser.get();
       
        return ResponseEntity.status(HttpStatus.OK).body(user.toDTO());
    }

}
