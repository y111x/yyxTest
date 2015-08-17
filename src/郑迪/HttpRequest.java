package Ö£µÏ;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {
	public static String sendGet(String url,String param,String encoding){
		String result = "";
		BufferedReader in = null;
		try{
			String urlNameString = url + "?size=10000&q_json=" + param;
			System.out.println(urlNameString);
            URL realUrl = new URL(urlNameString);
            
            URLConnection connection = realUrl.openConnection();
            
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            //connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                        
            connection.connect();
            
            Map<String, List<String>> map = connection.getHeaderFields();
            
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),encoding));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
		}catch(Exception e){
            e.printStackTrace();
            result = "Error!";
		}finally{
			try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
		}
		return result;
	}
	 public static String doPost(String jjson, String myurl) throws Exception {

	        URL localURL = new URL(myurl);
	        URLConnection connection = localURL.openConnection( );
	        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;

	        httpURLConnection.setDoOutput(true);
	        httpURLConnection.setRequestMethod("POST");
	        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
	        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(jjson.length()));
	        httpURLConnection.setRequestProperty("content", jjson);


	        OutputStream outputStream = null;
	        OutputStreamWriter outputStreamWriter = null;
	        InputStream inputStream = null;
	        InputStreamReader inputStreamReader = null;
	        BufferedReader reader = null;
	        StringBuffer resultBuffer = new StringBuffer();
	        String tempLine = null;

	        try {
	            outputStream = httpURLConnection.getOutputStream();
	            outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");

	            outputStreamWriter.write(jjson);
	            outputStreamWriter.flush();

	            if (httpURLConnection.getResponseCode() >= 300) {
	                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
	            }

	            inputStream = httpURLConnection.getInputStream();
	            inputStreamReader = new InputStreamReader(inputStream);
	            reader = new BufferedReader(inputStreamReader);
	            while ((tempLine = reader.readLine()) != null) {
	                resultBuffer.append(tempLine);
	            }

	        } finally {

	            if (outputStreamWriter != null) {
	                outputStreamWriter.close();
	            }

	            if (outputStream != null) {
	                outputStream.close();
	            }

	            if (reader != null) {
	                reader.close();
	            }

	            if (inputStreamReader != null) {
	                inputStreamReader.close();
	            }

	            if (inputStream != null) {
	                inputStream.close();
	            }

	        }

	        return resultBuffer.toString();
	    }
}
