import java.security.MessageDigest;


public class MD5Util {
    protected static String MD5(String str) {
    	byte[] arrby = str.getBytes();  	
        try {
            MessageDigest messageDigest = null;
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(arrby);
            return a(messageDigest.digest(), "").toLowerCase();
        }
        catch (Exception var1_2) {
            var1_2.printStackTrace();
            return "";
        }
    }
    
    protected static String a(byte[] arrby, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] arrby2 = arrby;
        int n = arrby2.length;
        for (int i = 0; i < n; ++i) {
            byte by;
            String string2;
            if ((string2 = Integer.toHexString(255 & (by = arrby2[i]))).length() == 1) {
                stringBuilder.append("0");
            }
            stringBuilder.append(string2).append(string);
        }
        return stringBuilder.toString();
    }
}
