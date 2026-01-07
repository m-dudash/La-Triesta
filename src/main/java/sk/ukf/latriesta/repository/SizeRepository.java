package sk.ukf.latriesta.repository;

import sk.ukf.latriesta.entity.Size;
import org.springframework.data.jpa.repository. JpaRepository;
import org. springframework.stereotype.Repository;
import java.util.List;


import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {

    Optional<Size> findByName(String name);
    List<Size> findByDeletedFalse();
    Optional<Size> findByIdAndDeletedFalse(Integer id);
}