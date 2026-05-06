package threads;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import data.Robot;

public class SendData implements Runnable {

    @Override
    public void run() {

        while (Robot.isProgramRunning()) {

            try {
                Thread.sleep(2000);

                URL url = new URL("http://172.20.10.4:8080/rest/lego/senddata");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);

                String json = "{"
                        + "\"run\":" + Robot.getRun() + ","
                        + "\"speed\":" + Robot.getSpeed() + ","
                        + "\"turn\":" + Robot.getTurn() + ","
                        + "\"distance\":0,"
                        + "\"obstacleDetected\":0,"
                        + "\"lineDetected\":0,"
                        + "\"battery\":0,"
                        + "\"message\":\"EV3 robot connected\""
                        + "}";

                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes("UTF-8"));
                os.flush();
                os.close();

                conn.getInputStream().close();
                conn.disconnect();

                System.out.println("Robot data sent.");

            } catch (Exception e) {
                System.out.println("Cannot send robot data.");
                e.printStackTrace();
            }
        }
    }
}