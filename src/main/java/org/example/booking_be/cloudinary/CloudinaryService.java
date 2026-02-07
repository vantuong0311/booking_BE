package org.example.booking_be.cloudinary;

import com.cloudinary.Cloudinary;

import lombok.RequiredArgsConstructor;
import org.example.booking_be.dto.responce.CloudinaryUploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    // Upload ảnh
    public CloudinaryUploadResponse uploadImage(MultipartFile file, String folder) {
        try {
            Map<String, Object> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of(
                            "folder", folder,
                            "resource_type", "image"
                    )
            );

            return CloudinaryUploadResponse.builder()
                    .posterUrl(result.get("secure_url").toString())
                    .posterPublicId(result.get("public_id").toString())
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Upload image failed", e);
        }
    }


    // Xóa ảnh
    public void deleteImage(String publicId) {
        try {
            if (publicId != null && !publicId.isBlank()) {
                cloudinary.uploader().destroy(
                        publicId,
                        Map.of("resource_type", "image")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Delete image failed", e);
        }
    }


}
