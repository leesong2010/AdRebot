

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import android.util.Base64;

public class FlowCountState {
    public static String encode(String string, String string2) {
        try {
            DESedeKeySpec dESedeKeySpec = new DESedeKeySpec(Base64.decode((byte[])string2.getBytes(), (int)0));
            SecretKey secretKey = SecretKeyFactory.getInstance("desede").generateSecret(dESedeKeySpec);
            Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
            cipher.init(1, secretKey);
            String string3 = new String(Base64.encode((byte[])cipher.doFinal(string.getBytes("utf-8")), (int)0), "utf-8");
            return string3;
        }
        catch (Exception var4_7) {
            var4_7.printStackTrace();
            return null;
        }
    }
}
