
package edu.uhcl.team_drone.drone;

public interface DroneInterface {
    
    public void yaw(float yawIn);
    public void roll(float pitchIn);
    public void pitch(float rollIn);
    public void altitude(float altitudeIn);

}
