package com.example.tcc.cadastro_controller;

import com.example.tcc.cadastro_model.CadastroModel;
import com.example.tcc.cadastro_service.CadastroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final CadastroService cadastroService;

    public LoginController(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> dadosLogin) {
        String email = dadosLogin.get("email");
        String senha = dadosLogin.get("senha");

        Optional<CadastroModel> usuario = cadastroService.autenticar(email, senha);

        if (usuario.isPresent()) {
            CadastroModel user = usuario.get();
            return ResponseEntity.ok(Map.of(
                    "mensagem", "Login realizado com sucesso!",
                    "id", user.getId(),
                    "nome", user.getNome(),
                    "email", user.getEmail()
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("erro", "Email ou senha inválidos"));
        }
    }
}