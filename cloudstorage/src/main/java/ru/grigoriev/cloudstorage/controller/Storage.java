package ru.grigoriev.cloudstorage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.grigoriev.cloudstorage.model.File;
import ru.grigoriev.cloudstorage.service.FileService;
import ru.grigoriev.cloudstorage.web.response.FileResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor

public class Storage {
    private final FileService fileService;

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String fileName,
                                        @RequestBody MultipartFile file) throws IOException {
        log.info("IN uploadFile");
        fileService.uploadFile(authToken, fileName, file);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileResponse>> getFiles(@RequestHeader("auth-token") String authToken,
                                                       @RequestParam("limit") int limit) {
        log.info("IN getFiles");
        return ResponseEntity.ok(fileService.getFiles(authToken, limit));
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String fileName) {
        log.info("IN deleteFile");
        fileService.deleteFile(authToken, fileName);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestHeader("auth-token") String authToken,
                                          @RequestParam("filename") String fileName) {
        log.info("IN getFile");
        File file = fileService.getFile(authToken, fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getData());
    }

    @PutMapping("/file")
    public ResponseEntity<?> renameFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String fileName,
                                        @RequestBody Map<String, String> newFileName) {
        log.info("IN renameFile");
        fileService.renameFile(authToken, fileName, newFileName.get("filename"));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
