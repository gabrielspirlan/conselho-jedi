package com.padwan.test.application.services.jedi;

import com.padwan.test.domain.contracts.dto.jedi.*;
import com.padwan.test.domain.contracts.projection.jedi.MasterAndApprenticeProjection;
import com.padwan.test.domain.models.Jedi;
import com.padwan.test.infrastructure.postgre.repository.JediRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JediService implements IJediService {

    @Autowired
    public JediRepository jediRepository;

    @Override
    public List<JediDTO> getAll() {
        return jediRepository.findAll().stream().map(jedi -> new JediDTO(jedi.getId(), jedi.getName(), jedi.getStatus(), jedi.getMidichlorians())).collect(Collectors.toList());
    }

    public JediAndMasterDTO getById(Long id) {

        Jedi jedi = jediRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Não foi possível encontrar um Jedi com o ID: " + id));
        JediAndMasterDTO dto = new JediAndMasterDTO(jedi.getId(), jedi.getName(), jedi.getStatus(), jedi.getMidichlorians());
        if (jedi.getMentor() != null) {
            dto.setMentor_id(jedi.getMentor().getId());
        }
        return dto;

    }

    public JediCreatedDTO create(JediCreateDTO dto) {
        return this.createJediWithOrWithoutMentor(dto);
    }

    @Override
    public JediCreatedDTO update(Long id, JediCreateDTO dto) {

        // 1. Encontra o Jedi a ser atualizado ou lança uma exceção
        Jedi jediUpdate = jediRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não foi possível encontrar um Jedi com o ID: " + id));

        // 2. Atualiza os dados básicos
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            jediUpdate.setName(dto.getName());
        }

        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            jediUpdate.setStatus(dto.getStatus());
        }

        if (dto.getMidichlorians() != null) {
            jediUpdate.setMidichlorians(dto.getMidichlorians());
        }

        // 3. Lógica corrigida e segura para atualizar o mentor
        if (dto.getMentor_id() != null) {
            Jedi novoMentor = jediRepository.findById(dto.getMentor_id())
                    .orElseThrow(() -> new EntityNotFoundException("Não foi possível encontrar o Mentor com o ID: " + dto.getMentor_id()));
            jediUpdate.setMentor(novoMentor);
        } else {
            // Permite remover o mentor ao enviar mentor_id como nulo
            jediUpdate.setMentor(null);
        }

        // 4. Salva o Jedi com todas as alterações
        Jedi jediSalvo = jediRepository.save(jediUpdate);

        // 5. Retorna o DTO de resposta
        return jediSalvo.getMentor() != null ?
                new JediCreatedDTO(jediSalvo.getId(), jediSalvo.getName(), jediSalvo.getStatus(), jediSalvo.getMidichlorians(), jediSalvo.getMentor().getName()) :
                new JediCreatedDTO(jediSalvo.getId(), jediSalvo.getName(), jediSalvo.getStatus(), jediSalvo.getMidichlorians());
    }

    public List<JediDTO> getWithMidichlorinsAbove(Long quantity) {
        return jediRepository.findByMidichloriansAbove(quantity)
                .stream()
                .map(projection ->
                        new JediDTO(
                                projection.getId(),
                                projection.getName(),
                                projection.getStatus(),
                                projection.getMidichlorians())
                ).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Jedi jedi = jediRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não foi possível encontrar um Jedi com o ID: " + id));
        jediRepository.delete(jedi);
    }

    public List<JediCategoryCountDTO> getCategoriesCount() {
        return jediRepository.findCategoryAndCount()
                .stream()
                .map(projection -> new JediCategoryCountDTO(
                        projection.getStatus(), projection.getCount()))
                .collect(Collectors.toList());
    }

    public List<MasterAndApprenticesDTO> getMestersAndApprentices() {

        List<MasterAndApprenticeProjection> projections = jediRepository.findAllMastersAndApprentices();

        Map<Long, MasterAndApprenticesDTO> mastersMap = new HashMap<>();

        for (MasterAndApprenticeProjection projection : projections) {
            Long masterId = projection.getMasterId();
            String masterName = projection.getMasterName();
            Long apprenticeId = projection.getApprenticeId();
            String apprenticeName = projection.getApprenticeName();

            ApprenticeDTO apprenticeDTO = new ApprenticeDTO(apprenticeId, apprenticeName);

            MasterAndApprenticesDTO masterDTO = mastersMap.get(masterId);
            if (masterDTO == null) {
                masterDTO = new MasterAndApprenticesDTO(masterId, masterName, new ArrayList<>());
                mastersMap.put(masterId, masterDTO);
            }
            masterDTO.addApprentice(apprenticeDTO);
        }

        return new ArrayList<>(mastersMap.values());
    }


    private Jedi validateIfExistsMentor(Long id) {

        if (id == null || id <= 0) {
            return null;
        }

        Jedi mentor = jediRepository.findById(id).orElse(null);

        if (mentor == null) {
            throw new RuntimeException("Mentor não encontrado!");
        }

        return mentor;

    }

    private JediCreatedDTO createJediWithOrWithoutMentor(JediCreateDTO dto) {

        Jedi mentor = validateIfExistsMentor(dto.getMentor_id());

        Jedi jedi = jediRepository.save(new Jedi(dto.getName(), dto.getStatus(), dto.getMidichlorians(), mentor));

        return jedi.getMentor() != null ?
                new JediCreatedDTO(jedi.getId(), jedi.getName(), jedi.getStatus(), jedi.getMidichlorians(), jedi.getMentor().getName()) :
                new JediCreatedDTO(jedi.getId(), jedi.getName(), jedi.getStatus(), jedi.getMidichlorians());
    }
}

