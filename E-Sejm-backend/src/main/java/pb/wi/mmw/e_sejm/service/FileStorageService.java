package pb.wi.mmw.e_sejm.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String saveFile(MultipartFile file, String directory);
    String updateFile(MultipartFile file, String existingFileName, String directory);
    String saveByteFile(byte[] fileContent, String fileName, String directory);
    void deleteFile(String filePath);
}
