package ru.grigoriev.cloudstorage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.grigoriev.cloudstorage.service.FileService;
import ru.grigoriev.cloudstorage.web.response.FileResponse;

import java.io.IOException;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor

public class Storage {
    private final FileService fileService;

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestHeader("auth-token") String authToken,
                                        @RequestParam("filename") String filename,
                                        @RequestBody MultipartFile file) throws IOException {
        log.info("IN uploadFile");
        fileService.uploadFile(authToken, filename, file);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileResponse>> getAllFiles(@RequestHeader("auth-token") String authToken,
                                                          @RequestParam("limit") int limit) {
        return ResponseEntity.ok(fileService.getFiles(authToken, limit));
    }


}
