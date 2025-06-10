package com.padwan.test.application.services.jedi;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.padwan.test.domain.contracts.dto.jedi.JediAndMasterDTO;
import com.padwan.test.domain.contracts.dto.jedi.JediCategoryCountDTO;
import com.padwan.test.domain.contracts.dto.jedi.JediDTO;
import com.padwan.test.domain.contracts.dto.jedi.MasterAndApprenticesDTO;
import com.padwan.test.domain.contracts.projection.jedi.JediCategoryCountProjection;
import com.padwan.test.domain.contracts.projection.jedi.JediProjection;
import com.padwan.test.domain.contracts.projection.jedi.MasterAndApprenticeProjection;
import com.padwan.test.domain.models.Jedi;
import com.padwan.test.infrastructure.postgre.repository.JediRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import javax.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class JediServiceTest {

    @Mock
    private JediRepository jediRepository;

    @InjectMocks
    private JediService jediService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById_JediFound() {

        // Arrange
        Long id = 1L;
        Jedi jedi = new Jedi(id, "Luke Skywalker", "Mestre Jedi", 15000L, null);
        when(jediRepository.findById(id)).thenReturn(Optional.of(jedi));

        // Action
        JediAndMasterDTO result = jediService.getById(id);

        // Assert
        assertNotNull(result);
        assertEquals("Luke Skywalker", result.getName());
        assertEquals("Mestre Jedi", result.getStatus());
        assertEquals(15000, result.getMidichlorians());
        verify(jediRepository, times(1)).findById(id);
    }

    @Test
    void testGetById_JediNotFound() {
        // Arrange
        Long id = 2L;
        when(jediRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> jediService.getById(id));
        verify(jediRepository, times(1)).findById(id);
    }

    @Test
    void testGetWithMidchloriansAbove() {
        // Arrange
        Long quantity = 9000L;

        JediProjection jedi1 = mock(JediProjection.class);
        when(jedi1.getId()).thenReturn(1L);
        when(jedi1.getName()).thenReturn("Obi-Wan Kenobi");
        when(jedi1.getStatus()).thenReturn("Mestre Jedi");
        when(jedi1.getMidichlorians()).thenReturn(12000L);

        JediProjection jedi2 = mock(JediProjection.class);
        when(jedi2.getId()).thenReturn(2L);
        when(jedi2.getName()).thenReturn("Anakin Skywalker");
        when(jedi2.getStatus()).thenReturn("Jedi");
        when(jedi2.getMidichlorians()).thenReturn(15000L);

        when(jediRepository.findByMidichloriansAbove(quantity)).thenReturn(Arrays.asList(jedi1, jedi2));

        // Action

        List<JediDTO> result = jediService.getWithMidichlorinsAbove(quantity);

        //Asset
        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals("Obi-Wan Kenobi", result.get(0).getName());
        assertEquals("Mestre Jedi", result.get(0).getStatus());
        assertEquals(12000L, result.get(0).getMidichlorians());

        assertEquals(2L, result.get(1).getId());
        assertEquals("Anakin Skywalker", result.get(1).getName());
        assertEquals("Jedi", result.get(1).getStatus());
        assertEquals(15000L, result.get(1).getMidichlorians());

        verify(jediRepository, times(1)).findByMidichloriansAbove(quantity);
    }

    @Test
    void getWithMidichlorinsAbove_ShouldReturnEmptyList_WhenNoJedisFound() {
        // Arrange
        Long quantity = 10000L;

        when(jediRepository.findByMidichloriansAbove(quantity)).thenReturn(List.of());

        // Action
        List<JediDTO> result = jediService.getWithMidichlorinsAbove(quantity);

        // Assert
        assertTrue(result.isEmpty());
        verify(jediRepository, times(1)).findByMidichloriansAbove(quantity);
    }

    @Test
    void testGetCategoriesCount () {

        //Arrange
        JediCategoryCountProjection category1 = mock(JediCategoryCountProjection.class);
        when(category1.getStatus()).thenReturn("Mestre Jedi");
        when(category1.getCount()).thenReturn(10L);

        JediCategoryCountProjection category2 = mock(JediCategoryCountProjection.class);
        when(category2.getStatus()).thenReturn("Jedi");
        when(category2.getCount()).thenReturn(20L);

        JediCategoryCountProjection category3 = mock(JediCategoryCountProjection.class);
        when(category3.getStatus()).thenReturn("Padawan");
        when(category3.getCount()).thenReturn(30L);

        when(jediRepository.findCategoryAndCount()).thenReturn(Arrays.asList(category1, category2, category3));

        // Action
        List<JediCategoryCountDTO> result = jediService.getCategoriesCount();

        // Assert

        assertEquals(3, result.size());

        assertEquals("Mestre Jedi", result.get(0).getStatus());
        assertEquals(10L, result.get(0).getCount());

        assertEquals("Jedi", result.get(1).getStatus());
        assertEquals(20L, result.get(1).getCount());

        assertEquals("Padawan", result.get(2).getStatus());
        assertEquals(30L, result.get(2).getCount());

        verify(jediRepository, times(1)).findCategoryAndCount();
    }


    @Test
    void testGetCategoriesCount_ShouldReturnEmptyList_WhenNoCategoriesFound () {

        // Arrange
        when(jediRepository.findCategoryAndCount()).thenReturn(List.of());

        // Action
        List<JediCategoryCountDTO> result = jediService.getCategoriesCount();

        // Assert
        assertTrue(result.isEmpty());
        verify(jediRepository, times(1)).findCategoryAndCount();
    }

    @Test
    void testGetMestersAndApprentices () {

        // Arrange

        MasterAndApprenticeProjection masterAndApprentices1 = mock(MasterAndApprenticeProjection.class);
        
        when(masterAndApprentices1.getMasterId()).thenReturn(1L);
        when(masterAndApprentices1.getMasterName()).thenReturn("Yoda");
        when(masterAndApprentices1.getApprenticeId()).thenReturn(3L);
        when(masterAndApprentices1.getApprenticeName()).thenReturn("Luke Skywalker");


        MasterAndApprenticeProjection masterAndApprentices2 = mock(MasterAndApprenticeProjection.class);

        when(masterAndApprentices2.getMasterId()).thenReturn(1L);
        when(masterAndApprentices2.getMasterName()).thenReturn("Yoda");
        when(masterAndApprentices2.getApprenticeId()).thenReturn(4L);
        when(masterAndApprentices2.getApprenticeName()).thenReturn("Dookan");

        MasterAndApprenticeProjection masterAndApprentices3 = mock(MasterAndApprenticeProjection.class);

        when(masterAndApprentices3.getMasterId()).thenReturn(2L);
        when(masterAndApprentices3.getMasterName()).thenReturn("Obi-Wan Kenobi");
        when(masterAndApprentices3.getApprenticeId()).thenReturn(5L);
        when(masterAndApprentices3.getApprenticeName()).thenReturn("Anakin Skywalker");

        MasterAndApprenticeProjection masterAndApprentices4 = mock(MasterAndApprenticeProjection.class);

        when(masterAndApprentices4.getMasterId()).thenReturn(2L);
        when(masterAndApprentices4.getMasterName()).thenReturn("Obi-Wan Kenobi");
        when(masterAndApprentices4.getApprenticeId()).thenReturn(3L);
        when(masterAndApprentices4.getApprenticeName()).thenReturn("Luke Skywalker");

        when(jediRepository.findAllMastersAndApprentices()).thenReturn(Arrays.asList(masterAndApprentices1, masterAndApprentices2, masterAndApprentices3, masterAndApprentices4));

        // Action
        List<MasterAndApprenticesDTO> result = jediService.getMestersAndApprentices();

        // Assert
        assertEquals(2, result.size());



        assertEquals(1L, result.get(0).getId());
        assertEquals("Yoda", result.get(0).getMaster());
        assertEquals(2, result.get(0).getApprentices().size());
        assertEquals(3L, result.get(0).getApprentices().get(0).getId());
        assertEquals("Luke Skywalker", result.get(0).getApprentices().get(0).getName());
        assertEquals(4L, result.get(0).getApprentices().get(1).getId());
        assertEquals("Dookan", result.get(0).getApprentices().get(1).getName());

        assertEquals(2L, result.get(1).getId());
        assertEquals("Obi-Wan Kenobi", result.get(1).getMaster());
        assertEquals(2, result.get(1).getApprentices().size());
        assertEquals(5L, result.get(1).getApprentices().get(0).getId());
        assertEquals("Anakin Skywalker", result.get(1).getApprentices().get(0).getName());
        assertEquals(3L, result.get(1).getApprentices().get(1).getId());
        assertEquals("Luke Skywalker", result.get(1).getApprentices().get(1).getName());

        verify(jediRepository, times(1)).findAllMastersAndApprentices();

    }

    @Test
    void testGetMastersAndApprentices_ShouldReturnEmptyList_WhenNoMastersFound () {

        // Arrange
        when(jediRepository.findAllMastersAndApprentices()).thenReturn(List.of());

        // Action
        List<MasterAndApprenticesDTO> result = jediService.getMestersAndApprentices();

        // Assert
        assertTrue(result.isEmpty());
        verify(jediRepository, times(1)).findAllMastersAndApprentices();
    }
}
