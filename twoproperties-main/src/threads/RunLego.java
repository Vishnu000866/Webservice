package threads;

import data.Robot;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;

public class RunLego implements Runnable {

    // Left and right driving motors
    UnregulatedMotor leftMotor = new UnregulatedMotor(MotorPort.B);
    UnregulatedMotor rightMotor = new UnregulatedMotor(MotorPort.C);

    @Override
    public void run() {

        while (Robot.isProgramRunning()) {

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int run = Robot.getRun();
            int speed = Robot.getSpeed();
            int turn = Robot.getTurn();

            leftMotor.setPower(speed);
            rightMotor.setPower(speed);

            if (run == 0) {
                stopMotors();
            }

            // Forward
            else if (turn == 0) {
                leftMotor.forward();
                rightMotor.forward();
            }

            // Left
            else if (turn == 1) {
                leftMotor.backward();
                rightMotor.forward();
            }

            // Right
            else if (turn == 2) {
                leftMotor.forward();
                rightMotor.backward();
            }

            // Backward
            else if (turn == 3) {
                leftMotor.backward();
                rightMotor.backward();
            }

            else {
                stopMotors();
            }
        }

        stopMotors();
    }

    private void stopMotors() {
        leftMotor.stop();
        rightMotor.stop();
    }
}