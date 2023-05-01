package com.tennisPartner.tennisP.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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
                String saveName = idx + "." + getImageExt(saveImage);
                String savePath = Paths.get(saveDir, saveName).toString();
                saveImage.transferTo(new File(savePath));
                return savePath;
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
}
