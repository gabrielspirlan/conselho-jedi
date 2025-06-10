package com.padwan.test.domain.contracts.dto.jedi;

import io.swagger.v3.oas.annotations.media.Schema;

public class JediAndMasterDTO {

    @Schema(description = "Código único identificador do Jedi", example = "2")
    private Long id;
    @Schema(description = "Nome do Jedi", example = "Luke Skywalker")
    private String name;
    @Schema(description = "Status do Jedi. Valores permitidos (Mestre Jedi, Jedi e Padawan")
    private String status;
    @Schema(description = "Quantidade de midichlorians do Jedi", example = "15000")
    private Long midichlorians;
    private Long mentor_id;

    public JediAndMasterDTO() {

    }

    public JediAndMasterDTO(Long id, String name, String status, Long midichlorians) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.midichlorians = midichlorians;
    }

    public JediAndMasterDTO(Long id, String name, String status, Long midichlorians, Long mentor_id) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.midichlorians = midichlorians;
        this.mentor_id = mentor_id;
    }

    public Long getMentor_id() {
        return mentor_id;
    }

    public void setMentor_id(Long mentor_id) {
        this.mentor_id = mentor_id;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
