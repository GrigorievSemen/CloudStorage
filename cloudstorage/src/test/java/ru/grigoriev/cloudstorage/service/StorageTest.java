package ru.grigoriev.cloudstorage.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.grigoriev.cloudstorage.jwt_token.jwt.JwtTokenProvider;
import ru.grigoriev.cloudstorage.model.File;
import ru.grigoriev.cloudstorage.repository.FileRepository;
import ru.grigoriev.cloudstorage.web.response.FileResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.grigoriev.cloudstorage.DataForTest.*;

@ExtendWith(MockitoExtension.class)
class StorageTest {
    @InjectMocks
    private FileService fileService;
    @Mock
    FileRepository fileRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void uploadFileTest() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile(FILE_NAME, DATA);
        fileService.uploadFile(TOKEN, FILE_NAME, multipartFile);
        verify(fileRepository, times(1)).save(getFile());
    }

    @Test
    void getFilesTest() {
        given(jwtTokenProvider.getUsername(TOKEN)).willReturn(LOGIN);
        given(fileRepository.findAllByHolder(LOGIN)).willReturn(Optional.of(FILE_LIST));

        List<FileResponse> responseList = fileService.getFiles(TOKEN, LIMIT);

        assertEquals(responseList.get(0).getFilename(), getFile().getFileName());
    }

    @Test
    void deleteFileTest() {
        given(jwtTokenProvider.getUsername(TOKEN)).willReturn(LOGIN);
        fileService.deleteFile(TOKEN, FILE_NAME);

        verify(fileRepository, times(1)).removeByFileNameAndHolder(FILE_NAME, LOGIN);
    }

    @Test
    void getFileTest() {
        given(jwtTokenProvider.getUsername(TOKEN)).willReturn(LOGIN);
        given(fileRepository.findByFileNameAndHolder(FILE_NAME, LOGIN)).willReturn(Optional.ofNullable(getFile()));

        File newFile = fileService.getFile(TOKEN, FILE_NAME);

        assertEquals(getFile().getFileName(), newFile.getFileName());
    }

    @Test
    void renameFileTest() {
        given(jwtTokenProvider.getUsername(TOKEN)).willReturn(LOGIN);

        fileService.renameFile(TOKEN, FILE_NAME, FILE_NAME);

        verify(fileRepository, times(1)).renameFile(FILE_NAME, FILE_NAME, LOGIN);
    }
}