package com.tennisPartner.tennisP.board.controller;

import com.tennisPartner.tennisP.common.util.ImageUtil;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {

    @GetMapping("/api/images")
    public UrlResource getImage(
            @RequestParam("qi") String encodePath
    ) throws MalformedURLException {
        String decodePhotoPath = ImageUtil.getDecodePhotoPath(encodePath);
        return new UrlResource("file:"+decodePhotoPath);
    }
}
