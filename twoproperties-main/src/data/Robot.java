package data;

public class Robot {

    private static volatile int id;
    private static volatile int speed = 50;
    private static volatile int turn = 0;
    private static volatile int run = 0;

    private static volatile int mode = 0;
    private static volatile int obstacleLimit = 20;
    private static volatile int avoidAction = 0;

    private static volatile boolean programRunning = true;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Robot.id = id;
    }

    public static void setId(String id) {
        try {
            Robot.id = Integer.parseInt(id);
        } catch (Exception e) {
            // id remains unchanged
        }
    }

    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        if (speed < 0) {
            speed = 0;
        }
        if (speed > 100) {
            speed = 100;
        }
        Robot.speed = speed;
    }

    public static void setSpeed(String speed) {
        try {
            setSpeed(Integer.parseInt(speed));
        } catch (Exception e) {
            // speed remains unchanged
        }
    }

    public static int getTurn() {
        return turn;
    }

    public static void setTurn(int turn) {
        Robot.turn = turn;
    }

    public static void setTurn(String turn) {
        try {
            Robot.turn = Integer.parseInt(turn);
        } catch (Exception e) {
            // turn remains unchanged
        }
    }

    public static int getRun() {
        return run;
    }

    public static void setRun(int run) {
        Robot.run = run;
    }

    public static void setRun(String run) {
        try {
            Robot.run = Integer.parseInt(run);
        } catch (Exception e) {
            // run remains unchanged
        }
    }

    public static int getMode() {
        return mode;
    }

    public static void setMode(String mode) {
        try {
            Robot.mode = Integer.parseInt(mode);
        } catch (Exception e) {
            // mode remains unchanged
        }
    }

    public static int getObstacleLimit() {
        return obstacleLimit;
    }

    public static void setObstacleLimit(String obstacleLimit) {
        try {
            Robot.obstacleLimit = Integer.parseInt(obstacleLimit);
        } catch (Exception e) {
            // obstacle limit remains unchanged
        }
    }

    public static int getAvoidAction() {
        return avoidAction;
    }

    public static void setAvoidAction(String avoidAction) {
        try {
            Robot.avoidAction = Integer.parseInt(avoidAction);
        } catch (Exception e) {
            // avoid action remains unchanged
        }
    }

    public static boolean isProgramRunning() {
        return programRunning;
    }

    public static void stopProgram() {
        programRunning = false;
    }
}