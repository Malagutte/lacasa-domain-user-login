package br.com.lacasaburger.login.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lacasaburger.login.dtos.requests.LoginDTORequest;
import br.com.lacasaburger.login.dtos.responses.LoginDTOResponse;
import br.com.lacasaburger.login.helpers.SecurityHelper;
import br.com.lacasaburger.login.services.IUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/login/")
@Api(tags = { "Login" })
@CrossOrigin
public class LoginController {

    @Autowired
    private IUserService userService;

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok", response = LoginDTOResponse.class) })
    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> auth(@RequestBody LoginDTORequest request) {
        var optUser = userService.loadUserByEmail(request.getEmail());

        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        var user = optUser.get();
        var passwordEncoder = new BCryptPasswordEncoder();

        var isPasswordValid = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (isPasswordValid && user.getEnable()) {

            var expiration = Date.from(LocalDateTime.now().plusHours(12).atZone(ZoneId.systemDefault()).toInstant());

            String token = Jwts.builder()
                    .signWith(Keys.hmacShaKeyFor(SecurityHelper.JWT_SECRET.getBytes()), SignatureAlgorithm.HS512)
                    .setIssuer("user-api").setHeaderParam("type", SecurityHelper.TOKEN_TYPE).setExpiration(expiration)
                    .setSubject(user.getId()).compact();

            var response = LoginDTOResponse.builder().token(token).build();

            return ResponseEntity.status(HttpStatus.OK).body(response);

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
