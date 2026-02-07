package org.example.booking_be.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class CloudinaryUploadResponse {
    private String posterUrl;
    private String posterPublicId;

}
