package com.xuanyuan.epublib;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Created by yuanpk on 2018/4/26  8:43
 * <p>
 * @author LUOFAXIN
 */
public class StringUtils {


    @Nullable
    public static String convertStreamToString(@NonNull InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Nullable
    public static String bytes2Hex(@Nullable byte[] bs) {
        if (bs == null || bs.length <= 0) {
            return null;
        }
        Charset charset = Charset.defaultCharset();
        ByteBuffer buf = ByteBuffer.wrap(bs);
        CharBuffer cBuf = charset.decode(buf);
        return cBuf.toString();
    }
}
