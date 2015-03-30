package edu.uhcl.team_drone.drone.pid;

import com.badlogic.gdx.Gdx;

public class PIDController {

    private final double MAX_CHANGE = 40;

    // tunable settings
    private double pGain = 0;
    private double iGain = 0;
    private double dGain = 0;

    private double processVariable = 0;// Drone current Tilt
    private double setPoint = 0; // Desired tilt (TRUE_UP)
    private double proportional = 0; // current error (Setpoint - processVar )
    private double previousError = 0; // previous error for integration
    private double integral = 0;
    private double derivative = 0;

    private double result = 0;
    private double previousResult = 0;

    private boolean isActive;

    public PIDController() {
    }

    ;
    public PIDController(double pGainIn, double iGainIn, double dGainIn) {
        this.pGain = pGainIn;
        this.iGain = iGainIn;
        this.dGain = dGainIn;
        this.isActive = true;
    }

    public double performPID(float dt) {
        calculatePID(dt);
        return result;
    }

    private void calculatePID(float dt) {
        if (!isActive) {
            return;
        }
        // calculate the difference between
        // the desired value and the actual value
        proportional = setPoint - processVariable;

        // track error over time, scaled to the timer interval
        integral += proportional * dt;

        // determine the amount of change from the last time checked
        derivative = (proportional - previousError) / dt;

        // perform main PID calculation
        // calculate how much to drive the output in order to get to the 
        // desired setpoint. 
        result = (pGain * proportional) // P
                + (iGain * integral) // I
                + (dGain * derivative); // D

        // Keep output within max change limits
        if ((result - previousResult) > MAX_CHANGE) {
            result = previousResult + MAX_CHANGE;
        } else if ((previousResult - result) > MAX_CHANGE) {
            result = previousResult - MAX_CHANGE;
        }
        if (result > 1.0) {
            result = 1.0;
        } else if (result < -1.0) {
            result = -1.0;
        }

        // set up error for next run
        previousError = proportional;
        previousResult = result;

    }

    public void setGains(double p, double i, double d) {
        pGain = p;
        iGain = i;
        dGain = d;
    }

    public void updateProcessVariable(double pvIn) {
        processVariable = pvIn;
    }

    public void setSetPoint(double setPointIn) {
        setPoint = setPointIn;
    }

    public void reset() {
        integral = 0;
        derivative = 0;
    }

    public void setActive(boolean param) {
        isActive = param;
    }

    public double getProportional() {
        return pGain * proportional;
    }

    public double getIntegral() {
        return iGain * integral;
    }

    public double getDerivative() {
        return dGain * derivative;
    }
    

}
