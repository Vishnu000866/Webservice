package threads;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import data.*;

public class ReadData implements Runnable {

    URL url = null;
    HttpURLConnection conn = null;
    InputStreamReader isr = null;
    BufferedReader br = null;

    String s = null;

    @Override
    public void run() {

        while (Robot.getRun() == 1) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                // Your laptop web service IP
                url = new URL("http://172.20.10.4:8080/rest/lego/getvalues");

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);

                InputStream is = null;

                try {
                    is = conn.getInputStream();
                } catch (Exception e) {
                    System.out.println("Cannot get InputStream!");
                    e.printStackTrace();
                }

                if (is != null) {
                    isr = new InputStreamReader(is);
                    br = new BufferedReader(isr);

                    while ((s = br.readLine()) != null) {

                        System.out.println("Data from server: " + s);

                        String[] values = s.split("#");

                        // Your web service returns:
                        // run#speed#turn#mode#obstacleLimit#avoidAction
                        if (values.length >= 3) {
                            Robot.setRun(values[0]);
                            Robot.setSpeed(values[1]);
                            Robot.setTurn(values[2]);
                        }
                    }

                    br.close();
                    isr.close();
                    is.close();
                }

                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Some problem connecting to web service!");
            }
        }
    }
}