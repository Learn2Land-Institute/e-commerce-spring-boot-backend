package com.mm.ecommerce.controller;

import com.mm.ecommerce.config.URLConstants;
import com.mm.ecommerce.dto.MerchantRegisterRequest;
import com.mm.ecommerce.exception.MerchantException;
import com.mm.ecommerce.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(URLConstants.MERCHANT_ENDPOINTS)
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @Value("${upload_download.directory}")
    private String dir;

    @Value("${prefixdir}")
    private String prefix;

    @PostMapping
    public ResponseEntity<?> registerMerchant(
           @RequestBody MerchantRegisterRequest merchantRegisterRequest){
        System.out.println("registerMerchant controller");
        return new ResponseEntity<>(merchantService.registerMerchant(
                merchantRegisterRequest), HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(
        @RequestParam("files") List<MultipartFile> multipartFiles){
        List<String> filenames = new ArrayList<>();
        // Create a Path object for the directory
        Path directory = Paths.get(System.getProperty(this.prefix), this.dir);
        System.out.println("file uploadFiles method");
        try{
            // Create directory if it does not exist
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }
            for(MultipartFile file : multipartFiles){
                if(file.isEmpty()){
                    throw new MerchantException("No file found.");
                }
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                // Create a Path object for the destination file
                Path destinationPath = Paths.get(directory.toString(), fileName);
                // Copy file to destination
                Files.copy(file.getInputStream(), destinationPath);
                filenames.add(fileName);
            }
        }catch (IOException e){
            throw new MerchantException("Error processing files: " + e.getMessage());
        }
        return ResponseEntity.ok().body(filenames);
    }

    @GetMapping
    public ResponseEntity<?> getAllMerchants(){
        return new ResponseEntity<>(merchantService.getAllMerchants(),HttpStatus.OK);
    }



}
