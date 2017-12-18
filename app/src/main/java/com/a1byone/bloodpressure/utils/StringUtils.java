package com.a1byone.bloodpressure.utils;

import java.io.ByteArrayOutputStream;

/**
 * 字符串工具类
 * Created by Administrator on 2017-12-18.
 */

public class StringUtils {
    public static String getBCC(byte[] data) {
        String ret = "";
        byte BCC[] = new byte[1];
        for (int i = 0; i < data.length; i++) {
            BCC[0] = (byte) (BCC[0] ^ data[i]);
        }
        String hex = Integer.toHexString(BCC[0] & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        ret += hex.toUpperCase();
        return ret;
    }

    /**字节数组转字符串*/
    public static String bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {

            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex;
        }

        return ret;
    }

    /**16进制转10进制*/
    public static int hexToTen(String hex) {
        if (null == hex || (null != hex && "".equals(hex))) {
            return 0;
        }
        return Integer.valueOf(hex, 16);
    }

    /**16进制字符串转字节数组*/
    public static byte[] hexStringToByteArray(String digits) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            for (int i = 0; i < digits.length(); i += 2) {
                char c1 = digits.charAt(i);
                if ((i + 1) >= digits.length()) {
                    throw new IllegalArgumentException("hexUtil.odd");
                }
                char c2 = digits.charAt(i + 1);
                byte b = 0;
                if ((c1 >= '0') && (c1 <= '9'))
                    b += ((c1 - '0') * 16);
                else if ((c1 >= 'a') && (c1 <= 'f'))
                    b += ((c1 - 'a' + 10) * 16);
                else if ((c1 >= 'A') && (c1 <= 'F'))
                    b += ((c1 - 'A' + 10) * 16);
                else
                    throw new IllegalArgumentException("hexUtil.bad");

                if ((c2 >= '0') && (c2 <= '9'))
                    b += (c2 - '0');
                else if ((c2 >= 'a') && (c2 <= 'f'))
                    b += (c2 - 'a' + 10);
                else if ((c2 >= 'A') && (c2 <= 'F'))
                    b += (c2 - 'A' + 10);
                else
                    throw new IllegalArgumentException("hexUtil.bad");
                baos.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return (baos.toByteArray());
    }
}
