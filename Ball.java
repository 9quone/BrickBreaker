import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {
	
	private double xVelocity;
	private double yVelocity;
	
	public Ball(double x, double y, double w, double h) {
		super(x, y, w, h);
	}
	
	public double getXVelocity() {
		return xVelocity;
	}
	
	public double getYVelocity() {
		return yVelocity;
	}
	
	public void setXVelocity(double value) {
		xVelocity = value;
	}
	
	public void setYVelocity(double value) {
		yVelocity = value;
	}
}
