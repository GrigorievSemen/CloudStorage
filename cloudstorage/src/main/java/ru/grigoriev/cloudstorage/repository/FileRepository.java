package ru.grigoriev.cloudstorage.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.grigoriev.cloudstorage.model.File;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

    Optional<List<File>> findAllByHolder(@Param("holder") String holder);
}