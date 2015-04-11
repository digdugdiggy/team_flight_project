package edu.uhcl.team_drone.drone;

import edu.uhcl.team_drone.drone.pid.StabilizerComponent;
import com.badlogic.gdx.math.Vector3;
import edu.uhcl.team_drone.input.KeyboardControllerInputComponent;

public class Drone implements DroneInterface {

    private final static int MAX_SPEED = 3000;
    private final static int TILT_TO_MOVEMENT_FACTOR = 200;
    private final static float MAX_TILT = 0.30f;
    private final static float AIR_RESISTANCE = 6f;

    private Vector3 position, direction, up, right, temp;

    private float dx, dy, dz; // current accelerations
    private float speedX, speedY, speedZ; // current Speeds    

    private float controlRollAmt = 0, controlPitchAmt = 0;

    public StabilizerComponent stabilityCmpnt;
    public KeyboardControllerInputComponent input;
    public GyroComponent gyroCmpnt;
    public CollisionComponent collisionCmpnt;

    private boolean simulated; // whether this is a software drone or a physical one.

    public Drone(boolean simulatedIn) {
        this.simulated = simulatedIn;

        if (!simulated) {
            input = new KeyboardControllerInputComponent(this);
            gyroCmpnt = new GyroComponent(this);
        } else {

            position = new Vector3(1000, 1000, 1000);
            direction = new Vector3(1, 0, 0);
            up = new Vector3(0, 1, 0);
            right = direction.cpy().crs(up).nor();
            temp = new Vector3();

            stabilityCmpnt = new StabilizerComponent(this);
            input = new KeyboardControllerInputComponent(this);
            gyroCmpnt = new GyroComponent(this);
            collisionCmpnt = new CollisionComponent(this);

            dx = dy = dz = 0;
            speedX = speedY = speedZ = 0;
        }
    }

    public void update(float dt) {
        if (!simulated) {
            input.update(dt);

        } else {
            input.update(dt);
            updateVectors();
            gyroCmpnt.update(dt);
            collisionCmpnt.update();

            moveFromTilt();

            speedX += dx;
            speedY += dy;
            speedZ += dz;

            // air resistance
            if (speedX > 0) {
                speedX -= AIR_RESISTANCE;
            } else if (speedX < 0) {
                speedX += AIR_RESISTANCE;
            }
            //speedY -= AIR_RESISTANCE;
            if (speedZ > 0) {
                speedZ -= AIR_RESISTANCE;
            } else if (speedZ < 0) {
                speedZ += AIR_RESISTANCE;
            }

            checkSpeedLimits();
            // problems with faster linearSpeed in diagonals
            position.add(speedX * dt, speedY * dt, speedZ * dt);
            stabilityCmpnt.update(dt);

        }

    }

    private void moveFromTilt() {
        temp.set(up).nor();
        dx = temp.x * TILT_TO_MOVEMENT_FACTOR;
        dz = temp.z * TILT_TO_MOVEMENT_FACTOR;
    }

    private void updateVectors() {
        right = direction.cpy().crs(up).nor();
        direction = up.cpy().crs(right).nor();
    }

    private void checkSpeedLimits() {
        if (speedX > MAX_SPEED) {
            speedX = MAX_SPEED;
        } else if (speedX < -MAX_SPEED) {
            speedX = -MAX_SPEED;
        }
        if (speedZ > MAX_SPEED) {
            speedZ = MAX_SPEED;
        } else if (speedZ < -MAX_SPEED) {
            speedZ = -MAX_SPEED;
        }
        if (speedY > MAX_SPEED) {
            speedY = MAX_SPEED;
        } else if (speedY < -MAX_SPEED) {
            speedY = -MAX_SPEED;
        }

    }

    //-----------------------
    // Interface Methods
    //-----------------------
    // rotate view leftwards and rightwards
    @Override
    public void yaw(float yawIn) {
        direction.rotate(up, yawIn);
    }

    // rotate view UP and DOWN
    @Override
    public void roll(float rollIn) {

        if (gyroCmpnt.getCurrentRoll() > 0) {
            if (gyroCmpnt.getCurrentRoll() < MAX_TILT) {
                up.rotate(direction, rollIn);
            } else if (gyroCmpnt.getCurrentRoll() >= MAX_TILT) {
                if (rollIn > 0) {
                    up.rotate(direction, rollIn);
                }
            }

        } else if (gyroCmpnt.getCurrentRoll() < 0) {
            if (gyroCmpnt.getCurrentRoll() > -MAX_TILT) {
                up.rotate(direction, rollIn);
            } else if (gyroCmpnt.getCurrentRoll() <= MAX_TILT) {
                if (rollIn < 0) {
                    up.rotate(direction, rollIn);
                }
            }

        } else if (gyroCmpnt.getCurrentRoll() == 0) {
            up.rotate(direction, rollIn);
        }
    }

    // rotate view clockwise/counter-clockwise
    @Override
    public void pitch(float pitchIn) {
        if (gyroCmpnt.getCurrentPitch() > 0) {
            if (gyroCmpnt.getCurrentPitch() < MAX_TILT) {
                up.rotate(right, pitchIn);
            } else if (gyroCmpnt.getCurrentPitch() >= MAX_TILT) {
                if (pitchIn > 0) {
                    up.rotate(right, pitchIn);
                }
            }

        } else if (gyroCmpnt.getCurrentPitch() < 0) {
            if (gyroCmpnt.getCurrentPitch() > -MAX_TILT) {
                up.rotate(right, pitchIn);
            } else if (gyroCmpnt.getCurrentPitch() <= MAX_TILT) {
                if (pitchIn < 0) {
                    up.rotate(right, pitchIn);
                }
            }

        } else if (gyroCmpnt.getCurrentPitch() == 0) {
            up.rotate(right, pitchIn);
        }
    }

    @Override
    public void altitude(float altitudeIn) {        
        if (position.y > 200 && position.y < 60000) {
            position.add(0, altitudeIn * 10, 0);
        } else {
            if (altitudeIn > 0 && position.y < 1000) {
                position.add(0, altitudeIn * 10, 0);
            } else {
                if (altitudeIn < 0 && position.y > 4000) {
                    position.add(0, altitudeIn * 10, 0);
                }
            }
        }
    }

    public Vector3 getPosition() {
        return position.cpy();
    }

    public Vector3 getDirection() {
        return direction.cpy();
    }

    public Vector3 getUp() {
        return up.cpy();
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public float getDz() {
        return dz;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public float getSpeedZ() {
        return speedZ;
    }

    public float getControlRollAmt() {
        return controlRollAmt;
    }

    public float getControlPitchAmt() {
        return controlPitchAmt;
    }

    public void setControlRollAmt(float controlRollAmt) {
        this.controlRollAmt = controlRollAmt;
    }

    public void setControlPitchAmt(float controlPitchAmt) {
        this.controlPitchAmt = controlPitchAmt;
    }

    public void moveToPosition(float x, float y, float z) {
        this.position.set(x, y, z);
        dx = dy = dz = 0;
        speedX = speedY = speedZ = 0;
        direction.set(1, 0, 0);
        up = new Vector3(0, 1, 0);
        collisionCmpnt.update();
    }
}
