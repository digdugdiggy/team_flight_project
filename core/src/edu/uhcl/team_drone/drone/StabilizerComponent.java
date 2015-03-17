package edu.uhcl.team_drone.drone;

public class StabilizerComponent {

    private Drone owner;
    
    private PIDController rollPID, pitchPID;
    

    public StabilizerComponent(Drone ownerIn) {
        this.owner = ownerIn;
        rollPID = new PIDController(40, 0, 0);
        pitchPID = new PIDController(80, 0, 0);
    }

    public void update(float dt, boolean isKeyPressed) {        
        updateRoll(dt);
        updatePitch(dt);
    }

    private void updateRoll(float dt) {
        rollPID.updateProcessVariable(-owner.gyro.getCurrentRoll());
        rollPID.setSetPoint(owner.getControlRollAmt());        

        float rollResult = (float) rollPID.performPID(dt);
        owner.roll(rollResult);        
    }

    private void updatePitch(float dt) {
        pitchPID.updateProcessVariable(-owner.gyro.getCurrentPitch());
        pitchPID.setSetPoint(owner.getControlPitchAmt());       

        float pitchResult = (float) pitchPID.performPID(dt);
        owner.pitch(pitchResult);
    }
    
    public PIDController getRollPID(){
        return rollPID;
    }

}
