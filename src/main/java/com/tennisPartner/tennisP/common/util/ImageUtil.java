package com.tennisPartner.tennisP.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.tennisPartner.tennisP.common.Exception.CustomException;
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
                return Paths.get(getYyyyMmDd(), saveName).toString();
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

    public static String getEncodeUserPhotoPath(String originalPath) {
        return "/api/users/" + Base64Utils.encodeToString(originalPath.getBytes());
    }

    public static String getDecodeUserPhotoPath(String encodePath) {
        return new String(Base64Utils.decode(encodePath.getBytes()));
    }
}
