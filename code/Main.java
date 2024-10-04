import java.awt.*;
import java.awt.event.KeyEvent;
/**
 * Program is a simple 2D shooting game
 * @author Alaaddin Eren Namlı
 * @since Date: 20.03.2024
 */
public class Main {
    public static void main(String[] args) {
        // Game Parameters
        int width = 1600; // screen width
        int height = 600; // screen height
        int pauseDuration1 = 16; // pause duration for deeper while loop
        int pauseDuration2 = 80; // pause duration for main while loop
        double gravity = 9.80665; // gravity
        double x0 = 120; // x and y coordinates of the bullet’s starting position on the platform
        double y0 = 120;
        double positionXofBullet = 120; // x coordinate of the bullet
        double positionYofBullet = 120; // y coordinate of the bullet
        double bulletVelocity = 180; // initial velocity
        double bulletAngle = 45.0; // initial angle
        double initialTime; // initial time

// Box coordinates for obstacles and targets
// Each row stores a box containing the following information:
// x and y coordinates of the lower left rectangle corner, width, and height
        double[][] obstacleArray = {
                {1200, 0, 60, 220},
                {1000, 0, 60, 160},
                {600, 0, 60, 80},
                {600, 180, 60, 160},
                {220, 0, 120, 180}
        };
        double[][] targetArray = {
                {1160, 0, 30, 30},
                {730, 0, 30, 30},
                {150, 0, 20, 20},
                {1480, 0, 60, 60},
                {340, 80, 60, 30},
                {1500, 600, 60, 60}
        };

        StdDraw.setCanvasSize(width,height);
        StdDraw.setXscale(0,1600);
        StdDraw.setYscale(0,800);
        StdDraw.enableDoubleBuffering(); // enables double buffering for faster animations

        while (true) { // main animation loop

            drawObstaclesAndTargets(obstacleArray,targetArray); // draws obstacles,targets and the shooting platform
            StdDraw.setPenColor(StdDraw.WHITE);
            String bulletAngleString = Double.toString(bulletAngle);
            String bulletVelocityString = Double.toString(bulletVelocity);
            StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
            StdDraw.text(55,60,"a: " + bulletAngleString); // draws the bulletAngleString
            StdDraw.text(60,35,"v: " + bulletVelocityString); // draws the bulletVelocityString
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(); // sets pen radius to default

            int counter = 1; // counter for the bullet trajectory

            if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) { // if up arrow is pressed, it increases the bullet angle
                bulletAngle += 1;
            }

            if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) { // if down arrow is pressed, it decreases the bullet angle
                bulletAngle -= 1;
            }

            if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) { // if right arrow is pressed, it increases the bullet velocity
                bulletVelocity += 1;
            }

            if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) { // if left arrow is pressed, it decreases the bullet velocity
                bulletVelocity -=1 ;
            }

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(x0,y0,x0 + Math.cos(Math.toRadians(bulletAngle)) * Math.pow(2,bulletVelocity/30.5)  ,y0 + Math.sin(Math.toRadians(bulletAngle)) * Math.pow(2,bulletVelocity/30.5)); // draws the shooting line
            StdDraw.setPenRadius(); // sets pen radius to default

            initialTime = System.currentTimeMillis(); // gets the current time in milliseconds and assign it to initialTime variable
            boolean isSpacePressed = false; // initialize the boolean variable to control while loop

            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) { // if space is pressed , the boolean variable isSpacePressed is assigned to be true
                isSpacePressed = true;
            }

            while (isSpacePressed) { // animation loop for shooting
                double currentTime = System.currentTimeMillis(); // gets the current time in milliseconds and assign it to currentTime variable
                double time = currentTime / 150 - initialTime / 150; // scales the time variables (currentTime and initialTime) and gets the time difference, then assigns it to time variable
                double formerPositionXofBullet = positionXofBullet; // former x position of the bullet
                double formerPositionYofBullet = positionYofBullet; // former y position of the bullet
                double bulletVelocityScaled = bulletVelocity/1.725; // scales the bullet velocity and assigns it to bulletVelocityScaled

                positionXofBullet = x0 + (bulletVelocityScaled) * Math.cos(Math.toRadians(bulletAngle)) * (time); // formula for the x position of the bullet
                positionYofBullet = y0 + (bulletVelocityScaled) * (time) * Math.sin(Math.toRadians(bulletAngle)) - ((1.0 / 2.0) * gravity * (time) * (time)); // formula for the y position of the bullet

                if (counter == 1) { // this if else blocks draws the bullet trajectory
                    StdDraw.line(x0, y0, positionXofBullet, positionYofBullet); // draws lines between current position of the bullet and the former position of the bullet
                    StdDraw.filledCircle(positionXofBullet, positionYofBullet, 4); // draws the bullet
                } else {
                    StdDraw.line(formerPositionXofBullet, formerPositionYofBullet, positionXofBullet, positionYofBullet); // draws lines between current position of the bullet and the former position of the bullet
                    StdDraw.filledCircle(positionXofBullet, positionYofBullet, 4); // draws the bullet
                }

                if (positionYofBullet<=0) { // if bullet touches the ground terminate the shooting animation
                    isSpacePressed = false;
                    while (!StdDraw.isKeyPressed(KeyEvent.VK_R)) { // while loop for displaying message and resetting the game parameters until r is pressed
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
                        StdDraw.textLeft(10, 770, "Hit the ground. Press 'r' to shoot again."); // displays the message
                        StdDraw.setFont();
                        bulletVelocity = 180; // sets bulletVelocity to the initial velocity
                        bulletAngle = 45.0;  // sets bulletAngle to the initial angle
                        StdDraw.show(); // shows the animation on the screen
                    }
                }

                if (positionXofBullet>=1600) { // if bullet reaches the maximum x value terminate the shooting animation
                    isSpacePressed = false;
                    while (!StdDraw.isKeyPressed(KeyEvent.VK_R)) { // while loop for displaying message and resetting the game parameters until r is pressed
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
                        StdDraw.textLeft(10, 770, "Max X reached. Press 'r' to shoot again."); // displays the message
                        StdDraw.setFont();
                        bulletVelocity = 180; // sets bulletVelocity to the initial velocity
                        bulletAngle = 45.0; // sets bulletAngle to the initial angle
                        StdDraw.show(); // shows the animation on the screen
                    }
                }

                if(isHitATarget(positionXofBullet,positionYofBullet,targetArray)) {   // if bullet hits a target terminate the shooting animation
                    isSpacePressed = false;
                    while (!StdDraw.isKeyPressed(KeyEvent.VK_R)) { // while loop for displaying message and resetting the game parameters until r is pressed
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
                        StdDraw.textLeft(10, 770, "Congratulations: You hit the target!"); // displays the message
                        StdDraw.setFont();
                        bulletVelocity = 180; // sets bulletVelocity to the initial velocity
                        bulletAngle = 45.0; // sets bulletAngle to the initial angle
                        StdDraw.show(); // shows the animation on the screen
                    }
                }

                if(isHitAnObstacle(positionXofBullet,positionYofBullet,obstacleArray)) {   // if bullet hits an obstacle terminate the shooting animation
                    isSpacePressed = false;
                    while (!StdDraw.isKeyPressed(KeyEvent.VK_R)) { // while loop for displaying message and resetting the game parameters until r is pressed
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
                        StdDraw.textLeft(10, 770, "Hit an obstacle. Press 'r' to shoot again."); // displays the message
                        StdDraw.setFont();
                        bulletVelocity = 180; // sets bulletVelocity to the initial velocity
                        bulletAngle = 45.0; // sets bulletAngle to the initial angle
                        StdDraw.show(); // shows the animation on the screen
                    }
                }

                counter++;
                StdDraw.show(); // shows the animation on the screen
                StdDraw.pause(pauseDuration1); // pause the animation at each iteration
            }

            positionXofBullet = 120; // resetting the x and y coordinates of the bullet
            positionYofBullet = 120;
            StdDraw.show();  // shows the animation on the screen
            StdDraw.clear(); // clear the background
            StdDraw.pause(pauseDuration2); // pause the animation at each iteration
        }
    }

    /**
     * Draws obstacles, targets and the shooting platform
     * @param obstacleArray array that contains coordinates,widths and heights of obstacles
     * @param targetArray array that contains coordinates,widths and heights of targets
     */
    public static void drawObstaclesAndTargets(double[][] obstacleArray, double[][] targetArray) {

        for (int i = 0; i < obstacleArray.length; i++) { // for each obstacle, it adjusts the data for filledRectangle method
            double halfWidth1 = obstacleArray[i][2] / 2;
            double halfHeight1 = obstacleArray[i][3] / 2;
            double xCoordinateOfCenter1 = obstacleArray[i][0] + halfWidth1; // gets x coordinate of the center of the obstacle
            double yCoordinateOfCenter1 = obstacleArray[i][1] + halfHeight1; // gets y coordinate of the center of the obstacle
            StdDraw.setPenColor(StdDraw.DARK_GRAY);
            StdDraw.filledRectangle(xCoordinateOfCenter1,yCoordinateOfCenter1,halfWidth1,halfHeight1); // draws the obstacle
        }

        StdDraw.setPenColor(StdDraw.BLACK); // sets pen color to black
        StdDraw.filledRectangle(60,60,60,60); // draws the shooting platform

        for (int j = 0; j < targetArray.length; j++) {  // for each target, it adjusts the data for filledRectangle method
            double halfWidth2 = targetArray[j][2] / 2;
            double halfHeight2 = targetArray[j][3] / 2;
            double xCoordinateOfCenter2 = targetArray[j][0] + halfWidth2; // gets x coordinate of the center of the target
            double yCoordinateOfCenter2 = targetArray[j][1] + halfHeight2; // gets y coordinate of the center of the target
            StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
            StdDraw.filledRectangle(xCoordinateOfCenter2,yCoordinateOfCenter2,halfWidth2,halfHeight2); // draws the target
        }
    }

    /**
     * Checks whether the bullet hits an obstacle or not
     * @param positionXofBullet x coordinate of the position of the bullet
     * @param positionYofBullet y coordinate of the position of the bullet
     * @param obstacleArray array that contains coordinates,widths and heights of obstacles
     * @return true or false
     */
    public static boolean isHitAnObstacle(double positionXofBullet,double positionYofBullet,double[][] obstacleArray) {
        boolean isHit = false;

        for (int i = 0; i < obstacleArray.length; i++) { // for each obstacle checks if the bullet hits it
            if( (obstacleArray[i][0] <= positionXofBullet) && (positionXofBullet <= obstacleArray[i][0] + obstacleArray[i][2]) ) { // x coordinate
                if ( (obstacleArray[i][1] <= positionYofBullet) && (positionYofBullet <= obstacleArray[i][1] + obstacleArray[i][3]) ) { // y coordinate
                    isHit = true; // the bullet hits an obstacle
                    break;
                }
            }
        }
        return isHit;
    }

    /**
     * Checks whether the bullet hits a target or not
     * @param positionXofBullet x coordinate of the position of the bullet
     * @param positionYofBullet y coordinate of the position of the bullet
     * @param targetArray array that contains coordinates,widths and heights of targets
     * @return true or false
     */
    public static boolean isHitATarget(double positionXofBullet,double positionYofBullet,double[][] targetArray) {
        boolean isHit = false;

        for (int i = 0; i < targetArray.length; i++) { // for each target checks if the bullet hits it
            if ( (targetArray[i][0] <= positionXofBullet) && (positionXofBullet <= targetArray[i][0] + targetArray[i][2]) ) { // x coordinate
                if ( (targetArray[i][1] <= positionYofBullet) && (positionYofBullet <= targetArray[i][1] + targetArray[i][3]) ) { // y coordinate
                    isHit = true; // the bullet hits a target
                    break;
                }
            }
        }
        return isHit;
    }

}