package com.padwan.test.webapp.controller;

import com.padwan.test.application.services.jedi.JediService;
import com.padwan.test.domain.contracts.dto.jedi.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/jedi")
@Tag(name = "Jedis", description = "Clique aqui para ver todos os endpoints disponíveis de Jedis")
public class JediController {
    @Autowired
    private JediService jediService;

    @Operation(
            summary = "Lista todos os Jedis com alto nível de midichlorians",
            description = "Retorna uma lista de Jedis cujo nível de midichlorians é maior que o valor informado. "
                    + "Por padrão, se não for informado via RequestParam, retorna os Jedis com midichlorians acima"
                    + " de 9000, mas o usuário pode fornecer outro valor se desejar.",
            tags = {"Jedis"}
    )
    @GetMapping("/midichlorians")
    public ResponseEntity<List<JediDTO>> findAllJedisWithHighMidichlorians(
            @Parameter(description = "Quantidade mínima de midichlorians", example = "9000")
            @RequestParam(defaultValue = "9000") Long quantity) {
        List<JediDTO> dto = this.jediService.getWithMidichlorinsAbove(quantity);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @Operation(
            summary = "Consultar todos os mestres e aprendizes",
            description = "Retorna uma lista com o nome e ID dos metres e o nome e ID dos aprendizes desse mestre Jedi",
            tags = {"Jedis"}
    )
    @GetMapping("/mestres-aprendizes")
    public ResponseEntity<List<MasterAndApprenticesDTO>> findMastersAndApprentices() {
        List<MasterAndApprenticesDTO> dto = jediService.getMestersAndApprentices();
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @Operation(
            summary = "Consultar quantidade de Jedis cadastrados por categoria",
            description = "Consulta a quantidade de Jedis cadastrados por categorias",
            tags = {"Jedis"}
    )
    @GetMapping("/categorias")
    public ResponseEntity<List<JediCategoryCountDTO>> findJedisCategory() {
        List<JediCategoryCountDTO> dto = jediService.getCategoriesCount();
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @Operation(
            summary = "Consultar todos os Jedis cadastrados",
            description = "Retorna todos os Jedis que foram cadastrados no sistema",
            tags = {"Jedis"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Jedi encontrado com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    @GetMapping()
    public ResponseEntity<List<JediDTO>> getAll() {
        return new ResponseEntity<>(jediService.getAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Conultar um Jedi",
            description = "Consulta as informações de um Jedi através do código identificador dele (ID)",
            tags = {"Jedis"},
            parameters = {
                    @Parameter(name = "id", description = "Código do Jedi que será consultado", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Jedi encontrado com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<JediAndMasterDTO> getJedi(@PathVariable Long id) {
        JediAndMasterDTO dto = this.jediService.getById(id);
        return new ResponseEntity<JediAndMasterDTO>(dto, HttpStatus.OK);
    }

    @Operation(
            summary = "Cadastrar um Jedi",
            description = "Cadastra as informações de um Jedi",
            tags = {"Jedis"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Jedi cadastrado com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    @PostMapping()
    public ResponseEntity<JediCreatedDTO> create(@Valid @RequestBody JediCreateDTO dto) {
        JediCreatedDTO createdDTO = jediService.create(dto);
        return new ResponseEntity(createdDTO, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Atualizar as informações de um Jedi",
            description = "Atualiza as informações um Jedi",
            tags = {"Jedis"},
            parameters = {
                    @Parameter(name = "id", description = "Código do Jedi que terá as informações atualizadas", example = "2")
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Informações do Jedi atualizadas com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<JediCreatedDTO> update(@PathVariable Long id, @RequestBody JediCreateDTO dto) {
        JediCreatedDTO updateDTO = jediService.update(id, dto);
        return new ResponseEntity<>(updateDTO, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Deletar um Jedi",
            description = "Deleta o cadastro um Jedi",
            tags = {"Jedis"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Jedi deletado com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        jediService.delete(id);
        return new ResponseEntity<>("Jedi: " + id + " deletado com sucesso", HttpStatus.OK);
    }
}
