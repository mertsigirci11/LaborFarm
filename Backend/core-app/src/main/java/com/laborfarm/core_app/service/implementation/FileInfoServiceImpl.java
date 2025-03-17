package com.laborfarm.core_app.service.implementation;

import com.laborfarm.core_app.entity.task.FileInfo;
import com.laborfarm.core_app.repository.FileInfoRepository;
import com.laborfarm.core_app.service.FileInfoService;
import com.laborfarm.core_app.service.dto.*;
import com.laborfarm.core_app.service.exception.task.FileInfoNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@Service
public class FileInfoServiceImpl implements FileInfoService {
    private final FileInfoRepository fileInfoRepository;
    private final ModelMapper modelMapper;
    private final String UPLOAD_DIR = "C:/Users/Mert/Desktop/LaborFarm/Backend/task-files";

    @Autowired
    public FileInfoServiceImpl(FileInfoRepository fileInfoRepository, ModelMapper modelMapper) {
        this.fileInfoRepository = fileInfoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomResponseDto<List<FileInfoResponseDto>> getAllFileInfo() {
        List<FileInfoResponseDto> fileInfoResponseList = fileInfoRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDto)
                .toList();

        return CustomResponseDto.success(HttpStatus.OK.value(), fileInfoResponseList);
    }

    @Override
    public CustomResponseDto<List<FileInfoResponseDto>> getTaskFilesInfo(UUID id) {
        List<FileInfoResponseDto> fileInfos = fileInfoRepository.findByTaskIdAndIsActiveTrue(id)
                .stream()
                .map(this::convertToDto)
                .toList();

        return CustomResponseDto.success(HttpStatus.OK.value(), fileInfos);
    }

    @Override
    public CustomResponseDto<FileInfoResponseDto> createFileInfo(MultipartFile file, FileInfoRequestDto fileInfoRequestDto) {
        try {
            //Get the file infos
            String filename = file.getOriginalFilename();
            String fileExtension = filename.substring(filename.lastIndexOf(".") + 1);
            String contentType = file.getContentType();

            // Save in directory
            Path filePath = Paths.get(UPLOAD_DIR + "/" + filename + "." + fileExtension);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save in db
            FileInfo fileInfo = FileInfo.builder()
                    .fileName(filename)
                    .extension(fileExtension)
                    .contentType(contentType)
                    .location(filePath.toString())
                    .userId(fileInfoRequestDto.getUserId())
                    .taskId(fileInfoRequestDto.getTaskId())
                    .isActive(true)
                    .build();
            FileInfo savedFileInfo = fileInfoRepository.save(fileInfo);
            FileInfoResponseDto response = convertToDto(savedFileInfo);

            return CustomResponseDto.success(HttpStatus.CREATED.value(), response);

        } catch (IOException e) {
            return CustomResponseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "File upload failed.");
        }
    }

    @Override
    public CustomResponseDto deleteFileInfo(UUID id) {
        FileInfo fileInfo = fileInfoRepository.findByIdAndIsActiveTrue(id);
        if (fileInfo == null) {
            throw new FileInfoNotFoundException();
        }
        fileInfo.setIsActive(false);
        fileInfoRepository.save(fileInfo);

        return CustomResponseDto.success(HttpStatus.NO_CONTENT.value());
    }

    //Helper Methods
    private FileInfoResponseDto convertToDto(FileInfo fileInfo) {
        return modelMapper.map(fileInfo, FileInfoResponseDto.class);
    }

    private FileInfo convertToEntity(FileInfoRequestDto fileInfoRequestDto) {
        return modelMapper.map(fileInfoRequestDto, FileInfo.class);
    }
}