package ashina.carrental.media;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaStorageService storage;

    @PostMapping(path = "/photos", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> uploadPhotos(
            @RequestParam("files") MultipartFile[] files,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sign in to upload photos.");
        }

        List<String> urls = storage.store(files);
        return ResponseEntity.ok(Map.of("urls", urls, "count", urls.size()));
    }
}
