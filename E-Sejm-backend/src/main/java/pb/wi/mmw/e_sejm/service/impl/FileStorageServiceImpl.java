package pb.wi.mmw.e_sejm.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.service.FileStorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${application.file.image-dir}")
    private String imageDir;

    @Override
    public String saveFile(MultipartFile file, String directory) {
        try {
            Path folderPath = Paths.get(imageDir + directory);

            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = folderPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return directory+fileName;
        } catch (IOException e) {
            throw new CustomException(BusinessErrorCodes.IMAGE_FETCH_FAILED);
        }
    }

    @Override
    public String updateFile(MultipartFile file, String existingFileName, String directory) {
        try {
            Path folderPath = Paths.get(imageDir + directory);

            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            String imagePath = imageDir + existingFileName;
            Path filePath = Paths.get(imagePath);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

            String newFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path newFilePath = folderPath.resolve(newFileName);

            Files.copy(file.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);

            return directory+newFileName;
        } catch (IOException e) {
            throw new CustomException(BusinessErrorCodes.IMAGE_NOT_FOUND);
        }
    }

    @Override
    public String saveByteFile(byte[] fileContent, String fileName, String directory) {
        try {
            Path folderPath = Paths.get(imageDir + directory);

            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            Path filePath = folderPath.resolve(uniqueFileName);
            Files.write(filePath, fileContent);

            return directory+uniqueFileName;
        } catch (IOException e) {
            throw new CustomException(BusinessErrorCodes.IMAGE_NOT_FOUND);
        }
    }

    @Override
    public void deleteFile(String filePath) {
        try {

            Path file = Paths.get(imageDir+filePath);
            if (Files.exists(file)) {
                Files.delete(file);
            }

        } catch (IOException e) {
            throw new CustomException(BusinessErrorCodes.IMAGE_NOT_FOUND);
        }
    }
}
