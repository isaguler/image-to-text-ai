package com.isaguler.image_to_text_ai.controller;

import com.isaguler.image_to_text_ai.service.ImageDescribeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
public class ImageDescribeController {

    private final ImageDescribeService imageDescribeService;

    public ImageDescribeController(ImageDescribeService imageDescribeService) {
        this.imageDescribeService = imageDescribeService;
    }

    @PostMapping(value = "/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(description = "Describe PNG Images")
    public ResponseEntity<Object> imageToText(@RequestParam("multipartFile") MultipartFile multipartFile) {
        String description = imageDescribeService.imageToText(multipartFile);

        return new ResponseEntity<>(description, HttpStatus.OK);
    }
}
