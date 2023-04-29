package ru.grigoriev.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "files", indexes = @Index(columnList = "holder"))
public class File extends AbstractBaseEntity {

    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "size", nullable = false)
    private Long size;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "holder", nullable = false)
    private String holder;
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] data;
}