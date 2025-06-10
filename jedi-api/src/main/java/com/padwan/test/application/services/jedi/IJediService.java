package com.padwan.test.application.services.jedi;

import com.padwan.test.domain.contracts.dto.jedi.*;

import java.util.List;

public interface IJediService {


    public List<JediDTO> getAll();

    public JediAndMasterDTO getById(Long id);

    public JediCreatedDTO create(JediCreateDTO dto);

    public JediCreatedDTO update(Long id, JediCreateDTO dto);

    public void delete(Long id);

    public List<JediDTO> getWithMidichlorinsAbove(Long quantity);

    public List<JediCategoryCountDTO> getCategoriesCount();

    public List<MasterAndApprenticesDTO> getMestersAndApprentices();
}
