package com.padwan.test.domain.contracts.dto.jedi;

import io.swagger.v3.oas.annotations.media.Schema;

public class ApprenticeDTO {
    @Schema(description = "Código único identificador do Jedi aprendiz", example = "2")
    Long id;
    @Schema(description = "Nome do Jedi aprendiz", example = "Luke Skywalker")
    String name;

    public ApprenticeDTO () {

    }

    public ApprenticeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
