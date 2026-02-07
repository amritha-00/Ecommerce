package com.example.Application.controller;

import com.example.Application.model.item;
import com.example.Application.service.itemService;
import jakarta.servlet.ServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ItemController {
    private itemService  Service;


    public ItemController(itemService service) {
        Service = service;
    }

    @PostMapping("add")
    public ResponseEntity<?> addItem(@RequestParam(required = false) Long id ,
                                     @RequestParam(required = false) String name ,
                                     @RequestParam(required = false) String description ,
                                     @RequestParam(required = false) MultipartFile imageName,
                                     ServletResponse servletResponse) throws IOException {
        System.out.println("Its working");

        if (id == null) {
            Map<String, String> error = new HashMap<>();
            error.put("id", "Id is required");
            return ResponseEntity.badRequest().body(error);
        }
        if (name == null) {
            Map<String, String> error = new HashMap<>();
            error.put("name", "Name is required");
            return ResponseEntity.badRequest().body(error);
        }
        if (imageName.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("Image", "Image is required");
            return ResponseEntity.badRequest().body(error);
        }
        String fileName = imageName.getOriginalFilename();
        String uploadDir = "uploads";
        Files.createDirectories(Paths.get(uploadDir));
        Files.copy(imageName.getInputStream(),
                Paths.get(uploadDir ,fileName),
                StandardCopyOption.REPLACE_EXISTING);
        item item = new item(id, name, description,uploadDir+"/"+ fileName);

        return ResponseEntity.ok(Service.addItem(item));


    }

    @GetMapping("/")
    public List<item> getAllItems(){
        System.out.println("called get method");
        return Service.getItem();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById( @PathVariable Long id){
        try{
            item item = Service.getItemById(id);
            return ResponseEntity.ok(item);
        }
        catch (RuntimeException e ){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }

    }
}
