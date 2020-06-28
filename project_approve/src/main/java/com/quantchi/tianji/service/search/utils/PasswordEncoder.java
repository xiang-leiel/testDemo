package com.quantchi.tianji.service.search.utils;

import java.security.MessageDigest;

public class PasswordEncoder {

        private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

        private static Object salt = "deqing";
        private static String algorithm = "MD5";

        public PasswordEncoder(Object salt, String algorithm) {
            this.salt = salt;
            this.algorithm = algorithm;
        }

    /**
     * 对明文密码进行加密,加密
     * @param rawPass 明文密码
     * @return 加密后密码
     */
        public static String encode(String rawPass) {
            String result = null;
            try {
                MessageDigest md = MessageDigest.getInstance(algorithm);
                //加密后的字符串
                result = byteArrayToHexString(md.digest(mergePasswordAndSalt(rawPass).getBytes("utf-8")));
            } catch (Exception ex) {
            }
            return result;
        }

    /**
     * 验证密码正确性
     * @param encPass 加密后密码
     * @param rawPass 明文密码
     * @return boolean
     */
        public boolean isPasswordValid(String encPass, String rawPass) {
            String pass1 = "" + encPass;
            String pass2 = encode(rawPass);

            return pass1.equals(pass2);
        }

    /**
     * 将明文密码加盐
     * @param password 明文密码
     * @return 加盐后密码
     */
    private static String mergePasswordAndSalt(String password) {
            if (password == null) {
                password = "";
            }

            if ((salt == null) || "".equals(salt)) {
                return password;
            } else {
                return password + "{" + salt.toString() + "}";
            }
        }

        /**
         * 转换字节数组为16进制字串
         * @param b 字节数组
         * @return 16进制字串
         */
        private static String byteArrayToHexString(byte[] b) {
            StringBuffer resultSb = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                resultSb.append(byteToHexString(b[i]));
            }
            return resultSb.toString();
        }

    /**
     * 转换字节为16进制字串
     * @param b 字节
     * @return 16进制字串
     */
        private static String byteToHexString(byte b) {
            int n = b;
            if (n < 0)
                n = 256 + n;
            int d1 = n / 16;
            int d2 = n % 16;
            return HEX_DIGITS[d1] + HEX_DIGITS[d2];
        }
}
