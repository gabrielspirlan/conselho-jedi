package com.padwan.test.domain.contracts.dto.jedi;

import io.swagger.v3.oas.annotations.media.Schema;

public class JediCreatedDTO {

    @Schema(description = "Código único identificador do Jedi", example = "2")
    private Long id;
    @Schema(description = "Nome do Jedi", example = "Luke Skywalker")
    private java.lang.String name;
    @Schema(description = "Status do Jedi. Valores permitidos (Mestre Jedi, Jedi e Padawan", example = "Jedi")
    private String status;
    @Schema(description = "Quantidade de midichlorians do Jedi", example = "15000")
    private Long midichlorians;
    @Schema(description = "Nome do mentor do Jedi", example = "Yoda")
    private java.lang.String mentor_name;

    public JediCreatedDTO() {

    }

    public JediCreatedDTO(Long id, java.lang.String name, String status, Long midichlorians) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.midichlorians = midichlorians;
    }


    public JediCreatedDTO(Long id, java.lang.String name, String status, Long midichlorians, java.lang.String mentor_name) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.midichlorians = midichlorians;
        this.mentor_name = mentor_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public java.lang.String getMentor_name() {
        return mentor_name;
    }

    public void setMentor_name(java.lang.String mentor_name) {
        this.mentor_name = mentor_name;
    }
}
