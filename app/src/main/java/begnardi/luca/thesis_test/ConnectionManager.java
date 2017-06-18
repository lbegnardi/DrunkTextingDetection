package begnardi.luca.thesis_test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by begno on 06/09/15.
 */

public class ConnectionManager {

    HttpURLConnection connection;

    public String sendSample(String url, String s) {

        OutputStream os = null;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            if(connection != null) {
                os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(s);
                writer.flush();
                writer.close();

                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while((line = rd.readLine()) != null) {
                    response.append(line);
                }
                rd.close();
//                System.out.println("Http request response:  "+response.toString());
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}