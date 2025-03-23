package com.laborfarm.core_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laborfarm.core_app.service.FileInfoService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.FileInfoRequestDto;
import com.laborfarm.core_app.service.dto.FileInfoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FileInfoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FileInfoService fileInfoService;

    @InjectMocks
    private FileInfoController fileInfoController;

    private UUID fileId;
    private FileInfoResponseDto fileInfoResponseDto;
    private CustomResponseDto<FileInfoResponseDto> responseDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileInfoController).build();

        fileId = UUID.randomUUID();
        fileInfoResponseDto = new FileInfoResponseDto();
        fileInfoResponseDto.setId(fileId);
        fileInfoResponseDto.setFileName("example.pdf");

        responseDto = CustomResponseDto.success(200, fileInfoResponseDto);
    }

    @Test
    void getAllFileInfos_ReturnsFileInfoList() throws Exception {
        List<FileInfoResponseDto> fileList = List.of(fileInfoResponseDto);
        CustomResponseDto<List<FileInfoResponseDto>> listResponse = CustomResponseDto.success(200, fileList);

        when(fileInfoService.getAllFileInfo()).thenReturn(listResponse);

        mockMvc.perform(get("/api/fileinfos/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].fileName").value("example.pdf"));
    }

    @Test
    void getTaskFilesInfo_ReturnsFilesByTaskId() throws Exception {
        List<FileInfoResponseDto> fileList = List.of(fileInfoResponseDto);
        CustomResponseDto<List<FileInfoResponseDto>> listResponse = CustomResponseDto.success(200, fileList);

        when(fileInfoService.getTaskFilesInfo(fileId)).thenReturn(listResponse);

        mockMvc.perform(get("/api/fileinfos/v1/{id}", fileId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].fileName").value("example.pdf"));
    }

    @Test
    void createFileInfo_ReturnsCreatedFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "example.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "PDF content".getBytes()
        );

        FileInfoRequestDto fileInfoRequestDto = new FileInfoRequestDto();
        fileInfoRequestDto.setTaskId(UUID.randomUUID());

        MockMultipartFile metadata = new MockMultipartFile(
                "metadata",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                new ObjectMapper().writeValueAsBytes(fileInfoRequestDto)
        );

        when(fileInfoService.createFileInfo(any(), any())).thenReturn(responseDto);

        mockMvc.perform(multipart("/api/fileinfos/v1")
                        .file(file)
                        .file(metadata)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fileName").value("example.pdf"));
    }

    @Test
    void deleteFileInfo_ReturnsSuccessResponse() throws Exception {
        CustomResponseDto<Void> deleteResponse = CustomResponseDto.success(200, null);
        when(fileInfoService.deleteFileInfo(fileId)).thenReturn(deleteResponse);

        mockMvc.perform(delete("/api/fileinfos/v1/{id}", fileId))
                .andExpect(status().isOk());
    }
}
