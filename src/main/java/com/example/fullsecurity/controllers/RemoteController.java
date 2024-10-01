package com.example.fullsecurity.controllers;

import com.example.fullsecurity.dtos.RemoteControllerDto;
import com.example.fullsecurity.services.RemoteControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

// Dit is de Controller klasse van de RemoteController entiteit en heeft vergelijkbare methodes als de TelevisionController.
@RequestMapping(value = "/remotecontrollers")
@RestController
public class RemoteController {
    private final RemoteControllerService remoteControllerService;

    public RemoteController(RemoteControllerService remoteControllerService) {
        this.remoteControllerService = remoteControllerService;
    }


    @GetMapping
    public ResponseEntity<List<RemoteControllerDto>> getAllRemotecontrollers() {

        List<RemoteControllerDto> dtos = remoteControllerService.getAllRemoteControllers();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RemoteControllerDto> getRemotecontroller(@PathVariable("id") Long id) {

        RemoteControllerDto dto = remoteControllerService.getRemoteController(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<RemoteControllerDto> addRemoteController(@RequestBody RemoteControllerDto dto) {
        RemoteControllerDto dto1 = remoteControllerService.addRemoteController(dto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + dto1.getId()).toUriString());

        return ResponseEntity.created(uri).body(dto1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRemoteController(@PathVariable("id") Long id) {
        remoteControllerService.deleteRemoteController(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RemoteControllerDto> updateTelevision(@PathVariable("id") Long id, @RequestBody RemoteControllerDto dto) {
        remoteControllerService.updateRemoteController(id, dto);
        return ResponseEntity.ok(dto);
    }

}