package com.laborfarm.core_app.service;

import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.FileInfoRequestDto;
import com.laborfarm.core_app.service.dto.FileInfoResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileInfoService {
    CustomResponseDto<List<FileInfoResponseDto>> getAllFileInfo();
    CustomResponseDto<List<FileInfoResponseDto>> getTaskFilesInfo(UUID id);
    CustomResponseDto<FileInfoResponseDto> createFileInfo(MultipartFile file, FileInfoRequestDto fileInfoRequestDto);
    CustomResponseDto deleteFileInfo(UUID id);
}