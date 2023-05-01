package ru.grigoriev.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.grigoriev.cloudstorage.model.File;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

    Optional<List<File>> findAllByHolder(@Param("holder") String holder);

    void removeByFileNameAndHolder(String fileName, String holder);

    Optional<File> findByFileNameAndHolder(String fileName, String holder);

    @Modifying
    @Query("update File f set f.fileName = :newName where f.fileName = :fileName and f.holder = :holder")
    void renameFile(@Param("fileName") String fileName, @Param("newName") String newName, @Param("holder") String holder);
}