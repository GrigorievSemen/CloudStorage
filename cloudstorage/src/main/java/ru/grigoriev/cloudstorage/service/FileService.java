package ru.grigoriev.cloudstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.grigoriev.cloudstorage.exception.NotFoundException;
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

    public void uploadFile(String authToken, String fileName, MultipartFile file) throws IOException {
        fileRepository.save(File.builder()
                .fileName(fileName)
                .size(file.getSize())
                .type(file.getContentType())
                .holder(jwtTokenProvider.getUsername(authToken.replace("Bearer ", "")))
                .data(file.getBytes())
                .build());
    }

    public List<FileResponse> getFiles(String authToken, int limit) {
        Optional<List<File>> fileList = fileRepository.findAllByHolder(getHolder(authToken));
        return fileList.get().stream()
                .map(val -> new FileResponse(val.getFileName(), Math.toIntExact(val.getSize())))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void deleteFile(String authToken, String fileName) {
        fileRepository.removeByFileNameAndHolder(fileName, getHolder(authToken));
    }

    public File getFile(String authToken, String fileName) {
        Optional<File> result = Optional.ofNullable(fileRepository.findByFileNameAndHolder(fileName, getHolder(authToken))
                .orElseThrow(() ->
                        new NotFoundException("File does not exist in the database")));
        return result.get();
    }

    public void renameFile(String authToken, String fileName, String newFileName) {
        fileRepository.renameFile(fileName, newFileName, getHolder(authToken));
    }

    private String getHolder(String authToken) {
        return jwtTokenProvider.getUsername(authToken.replace("Bearer ", ""));
    }
}