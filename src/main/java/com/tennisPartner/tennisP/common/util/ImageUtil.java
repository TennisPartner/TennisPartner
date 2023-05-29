package com.tennisPartner.tennisP.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

import com.tennisPartner.tennisP.common.Exception.CustomException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class ImageUtil {

    private ImageUtil() throws InstantiationException {
        throw new InstantiationException("인스턴스 생성하면 안됩니다.");
    }

    public static String imageSave(String saveDir, Long idx, MultipartFile saveImage)
            throws IOException {
        if (!saveImage.isEmpty()) {
            if (getImageExt(saveImage) != null) {
                saveDir = Paths.get(saveDir, getYyyyMmDd()).toString();
                createFolder(saveDir);

                String seq = String.valueOf(getFileSequence(saveDir));

                String saveName = seq + "_" + idx + "." + getImageExt(saveImage);
                String savePath = Paths.get(saveDir, saveName).toString();
                saveImage.transferTo(new File(savePath));
                return getEncodePhotoPath(savePath);
            } else {
                throw new NullPointerException("이미지를 업로드 하시지 않았습니다.");
            }
        } else {
            throw new NullPointerException("잘못된 이미지를 업로드 하셨습니다.");
        }

    }

    public static String getImageExt(MultipartFile image) {
        String contentType = image.getContentType();

        if (StringUtils.hasText(contentType)) {
            if (contentType.equals("image/png")) {
                return "png";
            } else if (contentType.equals("image/jpeg")) {
                return "jpg";
            }
        }
        return null;
    }

    public static void createFolder(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            if (dir.mkdirs() == false) {
                throw new CustomException("폴더 생성 중 오류 발생", 500);
            }
        }
    }

    public static String getYyyyMmDd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        String today = sdf.format(cal.getTime());

        return today;
    }

    public static Integer getFileSequence(String dirName) {
        File dir = new File(dirName);
        File[] files = dir.listFiles();

        return files.length;
    }

    public static String getEncodePhotoPath(String originalPath) {
        return "/api/images?qi="+Base64Utils.encodeToString(originalPath.getBytes());
    }

    public static String getDecodePhotoPath(String encodePath) {
        return new String(Base64Utils.decode(encodePath.getBytes()));
    }

    public static String blobSave(String blob, String saveDir) throws IOException {
        saveDir = Paths.get(saveDir, getYyyyMmDd()).toString();
        createFolder(saveDir);

        String seq = String.valueOf(getFileSequence(saveDir));
        if (blob.split(",").length > 2) {
            throw new CustomException("blob split 길이 2 이상", HttpServletResponse.SC_LENGTH_REQUIRED);
        }
        String ext = getBlobExt(blob.split(",")[0]);
        String saveName = seq + ext;
        Path savePath = Paths.get(saveDir, saveName);
        byte[] decodeByte = Base64.getDecoder().decode(blob.split(",")[1]);
        Files.write(savePath, decodeByte);
        return null;

    }

    public static String getBlobExt(String dataHeader) {

        if (dataHeader.contains("jpeg")) {
            return ".jpg";
        } else if (dataHeader.contains("png")) {
            return ".png";
        }
        return null;
    }
}
