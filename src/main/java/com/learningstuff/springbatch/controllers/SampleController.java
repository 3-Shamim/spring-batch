package com.learningstuff.springbatch.controllers;

import com.learningstuff.springbatch.services.SampleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * User: Md. Shamim Molla
 * Email: shamim.molla@vivasoftltd.com
 */

@RestController
@RequestMapping(value = "/samples")
@AllArgsConstructor
public class SampleController {

    private final SampleService  sampleService;

    @GetMapping(value = "")
    public ResponseEntity<?> getAllSamples() {
        return ResponseEntity.status(HttpStatus.OK).body(sampleService.findAll());
    }

}
