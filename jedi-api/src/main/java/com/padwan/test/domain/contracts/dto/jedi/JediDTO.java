package com.padwan.test.domain.contracts.dto.jedi;

import io.swagger.v3.oas.annotations.media.Schema;

public class JediDTO {

    @Schema(description = "Código único identificador do Jedi", example = "2")
    private Long id;
    @Schema(description = "Nome do Jedi", example = "Luke Skywalker")
    private java.lang.String name;
    @Schema(description = "Status do Jedi. Valores permitidos (Mestre Jedi, Jedi e Padawan")
    private String status;
    @Schema(description = "Quantidade de midichlorians do Jedi", example = "15000")
    private Long midichlorians;


    public JediDTO () {

    }

    public JediDTO(Long id, java.lang.String name, String status, Long midichlorians) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.midichlorians = midichlorians;
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

}
