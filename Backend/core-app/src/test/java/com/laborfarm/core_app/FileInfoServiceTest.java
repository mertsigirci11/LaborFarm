package com.laborfarm.core_app;

import com.laborfarm.core_app.entity.task.FileInfo;
import com.laborfarm.core_app.repository.FileInfoRepository;
import com.laborfarm.core_app.service.FileInfoService;
import com.laborfarm.core_app.service.dto.CustomResponseDto;
import com.laborfarm.core_app.service.dto.FileInfoResponseDto;
import com.laborfarm.core_app.service.exception.task.FileInfoNotFoundException;
import com.laborfarm.core_app.service.implementation.FileInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileInfoServiceTest {
    @Mock
    private FileInfoRepository fileInfoRepository;

    @InjectMocks
    private FileInfoServiceImpl fileInfoService;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        modelMapper.addMappings(new PropertyMap<FileInfo, FileInfoResponseDto>() {
            @Override
            protected void configure() {
                map().setTaskId(source.getTaskId());
                map().setUserId(source.getUserId());
            }
        });

        fileInfoService = new FileInfoServiceImpl(fileInfoRepository, modelMapper);
    }

    @Test
    public void getAllFileInfo_ReturnsAllFileInfos() {
        //Arrange
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName("test");
        List<FileInfo> fileInfos = List.of(fileInfo);
        List<FileInfoResponseDto> fileInfoResponseDtos = List.of(modelMapper.map(fileInfo, FileInfoResponseDto.class));
        when(fileInfoRepository.findByIsActiveTrue()).thenReturn(fileInfos);
        //Act
        CustomResponseDto<List<FileInfoResponseDto>> response = fileInfoService.getAllFileInfo();
        //Assert
        assertThat(response.getData().getFirst().getFileName()).isEqualTo(fileInfo.getFileName());

    }

    @Test
    public void getTaskFilesInfo_ReturnsAllTaskFilesInfo() {
        //Arrange
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(UUID.randomUUID());
        fileInfo.setFileName("test");
        List<FileInfo> fileInfos = List.of(fileInfo);
        List<FileInfoResponseDto> fileInfoResponseDtos = List.of(modelMapper.map(fileInfo, FileInfoResponseDto.class));
        when(fileInfoRepository.findByTaskIdAndIsActiveTrue(fileInfo.getId())).thenReturn(fileInfos);
        //Act
        CustomResponseDto<List<FileInfoResponseDto>> response = fileInfoService.getTaskFilesInfo(fileInfo.getId());
        //Assert
        assertThat(response.getData().getFirst().getFileName()).isEqualTo(fileInfo.getFileName());
    }

    @Test
    public void deleteFileInfo_ReturnsNoContent() {
        //Arrange
        UUID fileInfoId = UUID.randomUUID();
        FileInfo fileInfo = new FileInfo();
        fileInfo.setIsActive(true);
        fileInfo.setId(fileInfoId);
        when(fileInfoRepository.findByIdAndIsActiveTrue(fileInfoId)).thenReturn(fileInfo);
        when(fileInfoRepository.save(fileInfo)).thenReturn(fileInfo);
        //Act
        CustomResponseDto response = fileInfoService.deleteFileInfo(fileInfoId);
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(fileInfo.getIsActive()).isFalse();
    }

    @Test
    public void deleteFileInfo_WhenFileIsNotFound_ThrowsFileInfoNotFoundException(){
        //Arrange
        UUID fileInfoId = UUID.randomUUID();
        when(fileInfoRepository.findByIdAndIsActiveTrue(fileInfoId)).thenReturn(null);
        //Act
        //Assert
        assertThrows(FileInfoNotFoundException.class, () -> fileInfoService.deleteFileInfo(fileInfoId));
    }
}
