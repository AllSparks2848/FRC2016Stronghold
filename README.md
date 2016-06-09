# Team 2848's 2016 Robot Code
This repository contains the Team 2848 2016 FRC codebase. 

Unlike previous years, we used Java instead of Labview, and were very happy with the results. Our members learn Java in class, and more students were ultimately able to contribute to the codebase. It also meant a simpler, OS-independent build process that made development easier.

## Contents

This year's codebase can be opened and browsed in the Eclipse IDE. Code lives in the src/ directory and is organized in directories based on functionality and Java package.

### org.usfirst.frc.team2848
 * **Definitions.java** contains constants correspondong to ports, PID values, and shooter speed. These can be accessed, but not modified, by the rest of the codebase.
 * **Robot.java** is the IterativeRobot template in order to inherit periodic and disabled states. It handles logic for disabled, autonomous, and tele-operated states.
 
### org.usfirst.frc.team2848.subsystems
Representations of different systems of the robot with methods for checking / setting the state of the subsystem.

### org.usfirst.frc.team2848.navigation
Contains actions used during auton


### org.usfirst.frc.team2848.util
Miscellaneous utility classes.

* **ArduinoComm.java** Utility class, used to communicate between the roborio and the arduino-which was used to transmit data from the ADIS16448.The IMU is corrected using a Kalman Filter on the Arduino, and the corrected data is sent the the RoboRIO using I2C.

* **DigitDriver.java** Utility class for our auton selector. This year we decided to use the Rev Robotics Digit MXP Display as our auton selector. We were very happy with the results, as we had no delay in seleting our auton mode.





