package app;

import data.Robot;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import threads.ReadData;
import threads.RunLego;
import threads.SendData;

public class LegoApp {

    public static void main(String[] args) {

        LCD.clear();
        LCD.drawString("Lego Web Robot", 0, 0);
        LCD.drawString("Grade 2 demo", 0, 1);

        Thread readThread = new Thread(new ReadData());
        Thread runThread = new Thread(new RunLego());
        Thread sendThread = new Thread(new SendData());

        readThread.start();
        runThread.start();
        sendThread.start();

        while (!Button.ESCAPE.isDown()) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Robot.setRun(0);
        Robot.stopProgram();

        LCD.clear();
        LCD.drawString("Stopped", 0, 0);
    }
}