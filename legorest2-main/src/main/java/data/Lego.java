package data;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="lego")
public class Lego {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;


 	private String source;    // Source tells if data came from web UI or robot



	private int run;
	private int speed;
	private int turn;
	private int mode;
	private int obstacleLimit;
	private int avoidAction;
	private Timestamp aika=new Timestamp(System.currentTimeMillis());

	// Data sent by robot
	private int distance;
	private int obstacleDetected;
	private int lineDetected;
	private int battery; 
	private String message;

	public Lego() {
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSource() {
        return source;
    }
	public void setSource(String source) {
        this.source = source;
    }

	public int getRun() {
		return run;
	}
	public void setRun(int run) {
		this.run = run;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getMode() {
        return mode;
    }
	public void setMode(int mode) {
		this.mode = mode;
	}
	public int getObstacleLimit() {
		return obstacleLimit;
	}
	public void setObstacleLimit(int obstacleLimit) {
		this.obstacleLimit = obstacleLimit;
	}
	public int getAvoidAction() {
		return avoidAction;
	}
	public void setAvoidAction(int avoidAction) {
		this.avoidAction = avoidAction;
	}
	 public int getObstacleDetected() {
        return obstacleDetected;
    }
	public int getLineDetected() {
        return lineDetected;
    }

    public void setLineDetected(int lineDetected) {
        this.lineDetected = lineDetected;
    }

	public void setObstacleDetected(int obstacleDetected) {
		this.obstacleDetected = obstacleDetected;
	}
	 public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getAika() {
		return aika;
	}
	public void setAika(Timestamp aika) {
		this.aika = aika;
	}
}
