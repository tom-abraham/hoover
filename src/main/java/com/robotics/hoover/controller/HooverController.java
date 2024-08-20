package com.robotics.hoover.controller;

import com.robotics.hoover.model.HooverRequest;
import com.robotics.hoover.model.HooverResponse;
import com.robotics.hoover.service.HooverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hoover")
public class HooverController {
    HooverService hooverService;
    @Autowired
    public HooverController( HooverService hooverService )
    {
        this.hooverService = hooverService;
    }
    @PostMapping("/clean")
    public ResponseEntity <HooverResponse> cleanRequest(@RequestBody HooverRequest hooverRequest){
        System.out.println("Received request: "+hooverRequest);
        HooverResponse hooverResponse = hooverService.clean(hooverRequest);
        return  ResponseEntity.ok(hooverResponse);
    }
}
