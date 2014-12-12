

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Time;
import java.util.Calendar;
import java.util.Random;

public class HttpRequest {
    public static String OpenUrl( String string, String string2, String string3) {
        String string4;
        String string5 = string3.replace((CharSequence)"\n", (CharSequence)"").replace((CharSequence)"\r", (CharSequence)"").replace((CharSequence)"\t", (CharSequence)"");
        String string6 = string.replace((CharSequence)";", (CharSequence)"");
        //System.out.println("url=" + string6);
        //System.out.println("method=" + string2);
        if (string2.equals("GET")) {
            string6 = String.valueOf(string6) + string5;
        }
        //System.out.println("params=" + string5);
        try {
            InputStream inputStream;
            String string7 = string6.replace((CharSequence)" ", (CharSequence)"%20");
            URL uRL = new URL(string7);
            HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
            
            
            Calendar c = Calendar.getInstance(); 
            int time = c.getTime().getDate();
            //String string8 = StaticXML.getCookie(context);
            //System.out.println("Cookie=" + string8);
            //String string9 = StaticXML.getTime(context);
            if (!string2.equals("GET")) {
                //if (!(string8 == null || string8.length() == 0 || string9 == null || string9.length() == 0 || string9.equalsIgnoreCase("0") || Integer.parseInt(string9) == time.monthDay)) {
                //    string8 = null;
                //}
                //if (string8 == null || string8.length() == 0) {
                    String string10 = HttpRequest.getRandomString(30 + new Random().nextInt(50));
                    String string11 = MD5Util.MD5(String.valueOf(string10) + string5.getBytes().length);
                    httpURLConnection.setRequestProperty("cookie", "tag=" + string10 + ";value=" + string11 + ";");
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.getOutputStream().write(string5.getBytes("utf-8"));
                    httpURLConnection.getOutputStream().flush();
                    //StaticXML.saveCookie(context, HttpRequest.getCookie(context, httpURLConnection));
                    //StaticXML.saveTime(context, time.monthDay);
                //} else if (string8 != null) {
                //    httpURLConnection.setRequestProperty("cookie", string8);
                //    httpURLConnection.setRequestMethod("POST");
                //    httpURLConnection.setDoOutput(true);
                //    httpURLConnection.getOutputStream().write(string5.getBytes("utf-8"));
                //    httpURLConnection.getOutputStream().flush();
                //}
            } //else if (string8 != null) {
              // httpURLConnection.setRequestProperty("cookie", string8);
            //}
                       
            byte[] arrby = new byte[1024];
            InputStream inputStream2 = httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : (inputStream = httpURLConnection.getInputStream());
            string4 = read(arrby, inputStream2);
            inputStream2.close();
            httpURLConnection.disconnect();
        }
        catch (ProtocolException var8_17) {
        	var8_17.printStackTrace();
            return null;
        }
        catch (UnsupportedEncodingException var7_18) {
            var7_18.printStackTrace();
            return null;
        }
        catch (IOException var6_20) {
            var6_20.printStackTrace();
            return null;
        }
        //System.out.println("服务器成功返回数错误的Unicode字符串!:\r\n" + string4);
        return string4;
    }
    
    private static String read(byte[] arrby, InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        int n;
        while ((n = inputStream.read(arrby)) != -1) {
            stringBuffer.append(new String(arrby, 0, n, "utf-8"));
        }
        return stringBuffer.toString();
    }
    
    public static String getRandomString(int n) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        while (n2 < n) {
            stringBuffer.append("abcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt("abcdefghijklmnopqrstuvwxyz0123456789".length())));
            ++n2;
        }
        return stringBuffer.toString();
    }    
}
