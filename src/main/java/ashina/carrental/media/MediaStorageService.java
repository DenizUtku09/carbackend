package ashina.carrental.media;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Stores user-uploaded images on the local filesystem under
 * {@code app.media.upload-dir} and returns relative URLs ({@code /uploads/...})
 * that the static-resource handler in {@code WebConfig} serves back.
 *
 * <p>Photos persist on disk independent of any database listing — wire up a
 * {@code Listing} entity later if you need a foreign-key relationship.</p>
 */
@Service
public class MediaStorageService {

    private static final long MAX_BYTES = 5L * 1024 * 1024; // 5 MB per file
    private static final int MAX_FILES_PER_REQUEST = 8;
    private static final Map<String, String> ALLOWED = Map.of(
            "image/jpeg", "jpg",
            "image/png", "png",
            "image/webp", "webp",
            "image/gif", "gif"
    );

    private final Path root;

    public MediaStorageService(@Value("${app.media.upload-dir}") String uploadDir) {
        this.root = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @PostConstruct
    void init() throws IOException {
        Files.createDirectories(root);
    }

    public Path rootPath() { return root; }

    public List<String> store(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            throw bad("No files provided.");
        }
        if (files.length > MAX_FILES_PER_REQUEST) {
            throw bad("Max " + MAX_FILES_PER_REQUEST + " photos per upload.");
        }

        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) continue;
            if (file.getSize() > MAX_BYTES) {
                throw bad("'" + safeName(file) + "' exceeds 5 MB.");
            }
            String ct = file.getContentType() == null ? "" : file.getContentType().toLowerCase();
            String ext = ALLOWED.get(ct);
            if (ext == null) {
                throw bad("'" + safeName(file) + "' must be JPG/PNG/WEBP/GIF (got " + ct + ").");
            }

            String name = UUID.randomUUID().toString().replace("-", "") + "." + ext;
            Path target = root.resolve(name).normalize();
            // defense-in-depth: ensure we never escape the uploads dir
            if (!target.startsWith(root)) {
                throw bad("Invalid filename.");
            }

            try (var in = file.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to save photo: " + e.getMessage());
            }
            urls.add("/uploads/" + name);
        }

        if (urls.isEmpty()) {
            throw bad("No valid files were uploaded.");
        }
        return urls;
    }

    private static String safeName(MultipartFile file) {
        String n = file.getOriginalFilename();
        return n == null ? "file" : n;
    }

    private static ResponseStatusException bad(String msg) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }
}
