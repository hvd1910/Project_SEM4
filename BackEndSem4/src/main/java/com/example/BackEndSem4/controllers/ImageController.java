package com.example.BackEndSem4.controllers;


import com.example.BackEndSem4.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "uploads/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAvatar(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body(new Response("error", "No file provided", null));
            }

            // Kiểm tra kích thước file và định dạng
            if (file.getSize() > 5 * 1024 * 1024) { // Kích thước > 5MB
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body(new Response("error", "File is too large! Maximum size is 5MB", null));
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body(new Response("error", "File must be an image", null));
            }

            // Lưu file và trả về tên tệp tin
            String filename = storeFile(file);

            return ResponseEntity.ok(new Response("success", "Avatar uploaded successfully", filename));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("error", "Failed to upload avatar", null));
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Tạo tên file dựa trên thời gian hiện tại
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String uniqueFilename = timestamp + "_" + filename;

        // Đường dẫn đến thư mục mà bạn muốn lưu file
        Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }



    @RequestMapping("/view/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable("imageName") String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notFound.png").toUri()));
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
