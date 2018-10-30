package in.varadhismartek.pathshalaerp.AddStudent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


class JsonParser {

    static String json = "";


    HttpURLConnection urlConnection = null;
    URL urlLink;

    public JSONObject getJSONFromUrl(String parentAddress , String stopAddress) {



        JSONObject jsonObject = new JSONObject();

        try {
            urlLink = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=" + parentAddress + "&destination=" + stopAddress + "&sensor=false&units=metric&mode=driving");
            URI uri = new URI(urlLink.getProtocol(), urlLink.getUserInfo(), urlLink.getHost(), urlLink.getPort(), urlLink.getPath(), urlLink.getQuery(), urlLink.getRef());
            urlLink = uri.toURL();
            urlConnection = (HttpURLConnection) urlLink.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            in.close();
            json = sb.toString();


            try {
                jsonObject = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

        }
        return jsonObject;

    }
}
