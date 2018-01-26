package com.impltech.web.rest.util;

import com.google.common.io.Files;
import com.impltech.security.SecurityUtils;
import com.impltech.web.rest.request.FileUploadRequest;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by dima
 */
public class ImageUtil {

    private final static Path projectPath = Paths.get(".").toAbsolutePath().getParent();

    private static String getCompanyId() {
        return SecurityUtils.getCurrentCompanyUser().getCompany().getId().toString();
    }

    public static void deleteImage(Long id,List<File> list) {
        if (list != null || list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                for (String extension : UploaderUtils.EXTENSIONS) {
                    if (list.get(i).getName().equals(id.toString().concat(extension))) {
                        list.get(i).delete();
                    }
                }
            }
        }
    }

    public static List<File> listFilteredFiles(Long id, File imageDir) {
        FileFilter fileFilter = pathname -> pathname.getName().startsWith(id.toString());
        return Arrays.asList(imageDir.listFiles(fileFilter));
    }

    public static File[] listDefaultFiles(File imageDir) {
        return imageDir.listFiles(pathname -> pathname.getName().startsWith("default"));
    }


    public static File[] listAllImageFile(File imageDir) {
        return imageDir.listFiles(pathname -> !pathname.getName().startsWith("default"));
    }


    public static Boolean checkIfExist(Long id, List<File> list) {
        for (int i = 0; i < list.size(); i++) {
            for (String extension : UploaderUtils.EXTENSIONS) {
                if (list.get(i).getName().equals(id.toString().concat(extension))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<File> numberingImageFileSorting(List<File> list) {
        Collections.sort(list, (f1, f2) -> {
            try {
                int i1 = Integer.parseInt(f1.getName().split("\\.")[0]);
                int i2 = Integer.parseInt(f2.getName().split("\\.")[0]);
                return i1 - i2;
            } catch (NumberFormatException e) {
                throw new AssertionError(e);
            }
        });
        return list;
    }

    public static Path getVarietyImagePath() {
        return Paths.get(projectPath + "/src/main/resources/farms/" + getCompanyId() +"/varieties/images/");
    }

    public static Path getCompanyUserImagePath() {
        return Paths.get(projectPath + "/src/main/resources/farms/" + getCompanyId() +"/company-users/images/");
    }

    public static Path getLogoPath() {
        return Paths.get(projectPath + "/src/main/resources/farms/" + getCompanyId() +"/companies/logo/");
    }

    public static File getCompanyUserImageDir() {
        return new File(getCompanyUserImagePath().toString().concat("/"));
    }

    public static File getVarietyImageDir() {
        return new File(getVarietyImagePath().toString().concat("/"));
    }

    public static File getLogoDir() {
        return new File(getLogoPath().toString().concat("/"));
    }


    public static void uploadFile(FileUploadRequest req, Path imagePath) {

        String[] parts = req.getBase64file().split(",");
        String imageString = parts[1];

        if (req.getBase64file().length() / 1024 / 1024 <= 10) {

            try (
                ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(imageString))) {
                BufferedImage image = ImageIO.read(bis);

                for (String extension : UploaderUtils.EXTENSIONS) {
                    String oldName = req.getName().split("\\.")[0].concat(extension);
                    File oldFile = new File(imagePath.toString() + "/" + oldName);
                    oldFile.delete();
                }

                File outputfile = new File(imagePath.toString() + "/" + req.getName());

                ImageIO.write(image, "png", outputfile);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ResponseEntity<List<String>> getDefaultImage(List<String> result, File[] defaultList) throws IOException {
        byte [] fileBytes = Files.toByteArray(defaultList[0]);
        String defaultImage = Base64.getEncoder().encodeToString(fileBytes);
        result.add(defaultImage);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    public static ResponseEntity<List<String>> getImage(List<String> result, File file) throws IOException{
        byte [] fileBytes = Files.toByteArray(file);
        String defaultImage = Base64.getEncoder().encodeToString(fileBytes);
        result.add(defaultImage);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
}
