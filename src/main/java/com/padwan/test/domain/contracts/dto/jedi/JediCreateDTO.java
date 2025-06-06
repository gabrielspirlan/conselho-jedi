package com.padwan.test.domain.contracts.dto.jedi;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;

public class JediCreateDTO {

    @Schema(description = "Nome do Jedi", example = "Luke Skywalker")
    @NotBlank (message = "O campo name é obrigatório e não pode estar vazio")
    private java.lang.String name;

    @NotBlank(message = "O campo status é obrigatório e só aceita como valores os seguintes: Padawan, Jedi e Mestre Jedi")
    @Pattern(regexp = "Padawan|Jedi|Mestre Jedi|", message = "Campo inválido")
    @Schema(description = "Status do Jedi. Valores permitidos (Mestre Jedi, Jedi e Padawan", example = "Jedi")
    private String status;
    @Schema(description = "Quantidade de midichlorians do Jedi", example = "15000")
    @Min(value = 0, message = "O campo midichlorians deve ser maior do que 0")
    private Long midichlorians;
    @Schema(description = "Código único identificador do mentor do Jedi", example = "1")
    private Long mentor_id;


    public JediCreateDTO() {

    }

    public JediCreateDTO(java.lang.String name, String status, Long midichlorians) {
        this.name = name;
        this.status = status;
        this.midichlorians = midichlorians;
    }

    public JediCreateDTO(java.lang.String name, String status, Long midichlorians, Long mentor_id) {
        this.name = name;
        this.status = status;
        this.midichlorians = midichlorians;
        this.mentor_id = mentor_id;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMidichlorians() {
        return midichlorians;
    }

    public void setMidichlorians(Long midichlorians) {
        this.midichlorians = midichlorians;
    }

    public Long getMentor_id() {
        return mentor_id;
    }

    public void setMentor_id(Long mentor_id) {
        this.mentor_id = mentor_id;
    }
}
