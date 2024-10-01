package com.example.fullsecurity.services;

import com.example.fullsecurity.controllers.RemoteControllerOutputDto;
import com.example.fullsecurity.dtos.RemoteControllerDto;
import com.example.fullsecurity.models.RemoteController;
import com.example.fullsecurity.repositories.RemoteControllerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RemoteControllerServiceTest {

    @Mock
    RemoteControllerRepository remoteControllerRepository;

    @InjectMocks
    RemoteControllerService remoteControllerService;

    @Test
    void createRemoteController() {
        RemoteControllerDto remoteControllerDto = new RemoteControllerDto();
        remoteControllerDto.setId(1L);
        remoteControllerDto.setCompatibleWith("yes");
        remoteControllerDto.setBatteryType("aaa");
        remoteControllerDto.setName("RC54");
        remoteControllerDto.setBrand("Sony");
        remoteControllerDto.setPrice(25.9);
        remoteControllerDto.setOriginalStock(6);

        // gedrag van de mock weergeven
        Mockito.when(remoteControllerRepository.save(Mockito.any())).thenReturn(remoteControllerService.transferToRemoteController(remoteControllerDto));
        //als je een mapper heb
        // Mockito.when(remoteControllerRepository.save(Mockito.any())).thenReturn(remoteControllerMapper.fromInputDtoToModel(remotecontrollerInputDto));

        //act
//        remoteControllerService.c
      RemoteControllerDto remoteControllerDtos =remoteControllerService.addRemoteController(remoteControllerDto);

        //Assert
        assertEquals(1, remoteControllerDtos.getId());
        assertEquals("yes", remoteControllerDtos.getCompatibleWith());
        assertEquals("aaa", remoteControllerDtos.getBatteryType());
        assertEquals("RC54",  remoteControllerDtos.getName());
    }
}