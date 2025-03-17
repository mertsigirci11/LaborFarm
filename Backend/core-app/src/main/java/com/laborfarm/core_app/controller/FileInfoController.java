package com.laborfarm.core_app.controller;

import com.laborfarm.core_app.service.FileInfoService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.FileInfoRequestDto;
import com.laborfarm.core_app.service.dto.FileInfoResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fileinfos")
public class FileInfoController {
    private final FileInfoService fileInfoService;

    public FileInfoController(FileInfoService fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    @GetMapping("/v1")
    public ResponseEntity<CustomResponseDto<List<FileInfoResponseDto>>> getAllFileInfos() {
        CustomResponseDto<List<FileInfoResponseDto>> response = fileInfoService.getAllFileInfo();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto<List<FileInfoResponseDto>>> getTaskFilesInfo(@PathVariable UUID id) {
        CustomResponseDto<List<FileInfoResponseDto>> response = fileInfoService.getTaskFilesInfo(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping(value = "/v1", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CustomResponseDto<FileInfoResponseDto>> reateFileInfo(@RequestPart("file") MultipartFile file,
                                                                                @RequestPart("metadata") FileInfoRequestDto fileInfoRequestDto)
    {
        CustomResponseDto<FileInfoResponseDto> response = fileInfoService.createFileInfo(file, fileInfoRequestDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<CustomResponseDto> deleteFileInfo(@PathVariable UUID id) {
        CustomResponseDto response = fileInfoService.deleteFileInfo(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
