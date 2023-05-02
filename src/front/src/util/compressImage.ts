// 이미지 압축
import Compressor from "compressorjs";

export const compressImage = (file: any, callback: any) => {
  new Compressor(file, {
    quality: 0.6, // 이미지 품질
    maxWidth: 300, // 이미지 최대 너비
    maxHeight: 300, // 이미지 최대 높이
    success(result) {
      const reader = new FileReader();
      reader.readAsDataURL(result);
      reader.onloadend = () => {
        callback(reader.result);
      };
    },
    error(err) {
      console.error(err.message);
    },
  });
};
