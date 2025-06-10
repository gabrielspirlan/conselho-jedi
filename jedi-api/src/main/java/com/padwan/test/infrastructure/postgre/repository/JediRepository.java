package com.padwan.test.infrastructure.postgre.repository;

import com.padwan.test.domain.models.Jedi;
import com.padwan.test.domain.contracts.projection.jedi.JediCategoryCountProjection;
import com.padwan.test.domain.contracts.projection.jedi.JediProjection;
import com.padwan.test.domain.contracts.projection.jedi.MasterAndApprenticeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JediRepository extends JpaRepository<Jedi, Long> {

    @Query(value = "SELECT j.id, j.name, j.status, j.midichlorians FROM jedis j" +
            " WHERE j.midichlorians >= :quantity ORDER BY j.midichlorians DESC", nativeQuery = true)
    List<JediProjection> findByMidichloriansAbove(@Param("quantity") Long quantity);

    @Query (value = "SELECT status AS status, COUNT(*) AS count FROM jedis GROUP BY status ", nativeQuery = true)
    List<JediCategoryCountProjection> findCategoryAndCount();

    @Query (value = "SELECT m.id as masterId, m.name AS masterName, a.id as apprenticeId, a.name AS apprenticeName" +
            " FROM jedis a JOIN jedis m ON a.mentor_id = m.id WHERE m.status = 'Mestre Jedi' ORDER BY m.name",
            nativeQuery = true)
    List<MasterAndApprenticeProjection> findAllMastersAndApprentices();
}
