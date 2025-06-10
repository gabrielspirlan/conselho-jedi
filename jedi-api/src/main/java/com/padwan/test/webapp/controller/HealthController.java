package com.padwan.test.webapp.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping()
@Tag(name = "Health", description = "Clique aqui para ver todos os endpoints de saúde da API")
public class HealthController {

    @ResponseBody
    @GetMapping("/health")
    @Operation(summary = "Consultar se a API está disponível",
            description = "Retorna uma mensagem se a API estiver disponível",
            tags = {"Health"})
    public ResponseEntity health() {
        JSONObject json = new JSONObject();
        json.put("kenobi", "Hello there!");
        json.put("grievous", "general Kenobi!");
        return new ResponseEntity(json.toString(), HttpStatus.OK);
    }

    @Operation(summary = "Redirecionar para a página da documentação",
            description = "Essa rota serve apenas para redirecionar o usuário a página da documentação do Swagger",
            tags = {"Health"}
    )
    @GetMapping("/")
    public void redirectToSwagger(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/index.html");
    }


}
