package ru.grigoriev.cloudstorage.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.grigoriev.cloudstorage.jwt_token.jwt.JwtTokenProvider;
import ru.grigoriev.cloudstorage.model.File;
import ru.grigoriev.cloudstorage.repository.FileRepository;
import ru.grigoriev.cloudstorage.web.response.FileResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {
    private final FileRepository fileRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public void uploadFile(String authToken, String filename, MultipartFile file) throws IOException {
        fileRepository.save(File.builder()
                .fileName(filename)
                .size(file.getSize())
                .type(file.getContentType())
                .holder(jwtTokenProvider.getUsername(authToken.replace("Bearer ", "")))
                .data(file.getBytes())
                .build());
    }

    public List<FileResponse> getFiles(String authToken, int limit) {
        String holder = jwtTokenProvider.getUsername(authToken.replace("Bearer ", ""));
        Optional<List<File>> fileList = fileRepository.findAllByHolder(holder);
        return fileList.get().stream()
                .map(val -> new FileResponse(val.getFileName(), Math.toIntExact(val.getSize())))
                .limit(limit)
                .collect(Collectors.toList());
    }
}