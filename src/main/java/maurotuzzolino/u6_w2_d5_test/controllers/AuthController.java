package maurotuzzolino.u6_w2_d5_test.controllers;

import jakarta.validation.Valid;
import maurotuzzolino.u6_w2_d5_test.entities.User;
import maurotuzzolino.u6_w2_d5_test.exceptions.UnauthorizedException;
import maurotuzzolino.u6_w2_d5_test.payloads.LoginDTO;
import maurotuzzolino.u6_w2_d5_test.repositories.UserRepository;
import maurotuzzolino.u6_w2_d5_test.tools.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTools jwtTools;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User userInput) {
        if (userRepository.existsByEmail(userInput.getEmail())) {
            throw new IllegalArgumentException("Email già registrata");
        }

        userInput.setPassword(passwordEncoder.encode(userInput.getPassword()));
        User savedUser = userRepository.save(userInput);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Credenziali non valide"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Credenziali non valide");
        }

        String token = jwtTools.createToken(user);
        return ResponseEntity.ok(Map.of("token", token));
    }
}

