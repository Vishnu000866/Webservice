package threads;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import data.Robot;

public class ReadData implements Runnable {

    private URL url = null;
    private HttpURLConnection conn = null;
    private InputStreamReader isr = null;
    private BufferedReader br = null;

    @Override
    public void run() {

        while (Robot.isProgramRunning()) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                url = new URL("http://172.20.10.4:8080/rest/lego/getvalues");

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);

                InputStream is = conn.getInputStream();

                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);

                String s;

                while ((s = br.readLine()) != null) {

                    System.out.println("Data from server: " + s);

                    String[] values = s.split("#");

                    // Format:
                    // run#speed#turn#mode#obstacleLimit#avoidAction
                    if (values.length >= 6) {
                        Robot.setRun(values[0]);
                        Robot.setSpeed(values[1]);
                        Robot.setTurn(values[2]);
                        Robot.setMode(values[3]);
                        Robot.setObstacleLimit(values[4]);
                        Robot.setAvoidAction(values[5]);
                    }
                }

                br.close();
                isr.close();
                is.close();
                conn.disconnect();

            } catch (Exception e) {
                System.out.println("Cannot connect to web service.");
                e.printStackTrace();

                // Safety: stop motors if connection is lost
                Robot.setRun(0);
            }
        }
    }
}