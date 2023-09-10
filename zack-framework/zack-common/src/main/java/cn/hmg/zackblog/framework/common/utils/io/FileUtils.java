package cn.hmg.zackblog.framework.common.utils.io;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import org.apache.tika.Tika;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-09-06 19:33
 * @description:
 */
public class FileUtils {
    private static Tika TIKA = new Tika();

    public static byte[] convertByte(InputStream inputStream) {
        return IoUtil.readBytes(inputStream);
    }

    public static String getType(InputStream inputStream, String filename) throws IOException {
        return TIKA.detect(inputStream, filename);
    }

    public static String convertOssFilename(String originalFilename) {
        String extName = FileUtil.extName(originalFilename);
        return IdUtil.fastSimpleUUID() + "." + extName;
    }
}
