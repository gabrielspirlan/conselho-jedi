package com.padwan.test.domain.contracts.dto.jedi;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class MasterAndApprenticesDTO {

    @Schema(description = "Código único identificador do Jedi Mentor", example = "1")
    private Long id;
    @Schema(description = "Nome do Jedi Mentor", example = "Yoda")
    private String master;
    @Schema(description = "Aprendizes do Jedi")
    private List<ApprenticeDTO> apprentices;

    public MasterAndApprenticesDTO() {

    }

    public MasterAndApprenticesDTO(Long id, String master, List<ApprenticeDTO> apprentices) {
        this.id = id;
        this.master = master;
        this.apprentices = apprentices;
    }

    public Long getId (){
        return id;
    }

    public void setIdI(Long id) {
        this.id = id;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public List<ApprenticeDTO> getApprentices() {
        return apprentices;
    }

    public void setApprentices(List<ApprenticeDTO> apprentices) {
        this.apprentices = apprentices;
    }

    public void addApprentice (ApprenticeDTO apprentice) {
        this.apprentices.add(apprentice);
    }
}
