/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Week9;

/**
 *
 * @author David Hodge
 * finchController class extends the methods of the Finch class
 * by allowing different modes of input from the light sensors and
 * accelerometers.
 * 
 */


public class finchController extends Finch {
    
    private int leftCutoff;
    private int rightCutoff;
    private boolean leftPress;
    private boolean rightPress;
    private double prevXAccel;
    private double prevYAccel;
    private double prevZAccel;
    private double[] allPrevAccel;
    
    public finchController() { 
        super();
        prevXAccel = 0.0;
        prevYAccel = 0.0;
        prevZAccel = 0.0;
        allPrevAccel = new double[3];
        allPrevAccel[0] = prevXAccel;
        allPrevAccel[1] = prevYAccel;
        allPrevAccel[2] = prevZAccel;
    }
    
    public void calibrateButtons() {
        //prompt for calibration of upper and lower limits on both sides
        System.out.println("Pay attention human. Beginning left calibration process.");
        System.out.println("Calibrating lower left light limit. Place hand over left light sensor. Do not remove hand until you see the confirmation.");
         
        sleep(3000);
         
        int lowerLeftLightLimit = getLeftLightSensor();
        System.out.println("The lower left light limit is: " + lowerLeftLightLimit);
         
        sleep(2000);
         
        System.out.println("Calibrating upper left light limit. Remove all objects from view of left light sensor until you see the confirmation.");
         
        sleep(3000);
         
        int upperLeftLightLimit = getRightLightSensor();
        System.out.println("The upper left light limit is: " + upperLeftLightLimit);
        
        leftCutoff = lowerLeftLightLimit + ((upperLeftLightLimit - lowerLeftLightLimit)/2);
        //leftCutoff = (upperLeftLightLimit - lowerLeftLightLimit)/2;
        
        System.out.println("Pay attention human. Beginning right calibration process.");
        System.out.println("Calibrating lower right light limit. Place hand over right light sensor. Do not remove hand until you see the confirmation.");
         
        sleep(3000);
         
        int lowerRightLightLimit = getRightLightSensor();
        System.out.println("The lower right light limit is: " + lowerRightLightLimit);
         
        sleep(2000);
         
        System.out.println("Calibrating upper right light limit. Remove all objects from view of right light sensor until you see the confirmation.");
         
        sleep(3000);
         
        int upperRightLightLimit = getRightLightSensor();
        System.out.println("The upper light limit is: " + upperRightLightLimit);
        
        rightCutoff = lowerRightLightLimit + ((upperRightLightLimit - lowerRightLightLimit)/2);
        //rightCutoff = (upperRightLightLimit - lowerRightLightLimit)/2;
    }
    
    public boolean isLeftButtonDown() {
        leftPress = false;
        //evaluate current left light reading against the calibrated cutoff
        int currentLeftLight = getLeftLightSensor();
        if (currentLeftLight < leftCutoff) {
            leftPress = true;
        }
        else if (currentLeftLight > leftCutoff) {
            leftPress = false;
        }
        
        return(leftPress);
    }
    
    public boolean isRightButtonDown() {
        rightPress = false;
        //evaluate current right light setting against calibrated cutoff
        int currentRightLight = getRightLightSensor();
        if (currentRightLight < rightCutoff) {
            rightPress = true;
        }
        else if (currentRightLight > rightCutoff) {
            rightPress = false;
        }
        
        return(rightPress);
    }
    
    public double xMoveChange() {
        //get current x accel. and compare against previous, return the delta,
        //and set the current as the previous for the next call
        double currentXAccel = getXAcceleration();
        double dXAccel = currentXAccel - prevXAccel;
        prevXAccel = currentXAccel;
        return(dXAccel);
    }
    
    public double yMoveChange() {
        //get current y accel. and compare against previous, return the delta,
        //and set the current as the previous for the next call
        double currentYAccel = getYAcceleration();
        double dYAccel = currentYAccel - prevYAccel;
        prevYAccel = currentYAccel;
        return(dYAccel);
    }
    
    public double zMoveChange() {
        //get current z accel. and compare against previous, return the delta,
        //and set the current as the previous for the next call
        double currentZAccel = getZAcceleration();
        double dZAccel = currentZAccel - prevZAccel;
        prevZAccel = currentZAccel;
        return(dZAccel);
    }
    
    public double[] moveChange() {
        //same methodology as the individual axis accelerometers above, just
        //with some arrays
        double[] allCurrentAccel = getAccelerations();
        double[] dAllAccel = new double[3];
        for (int i = 0; i < 3; i++) {
            dAllAccel[i] = allCurrentAccel[i] - allPrevAccel[i];
        }
        System.arraycopy(allCurrentAccel, 0, allPrevAccel, 0, 3);
        return(dAllAccel);
    }
    
    
}
