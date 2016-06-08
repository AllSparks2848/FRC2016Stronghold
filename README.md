# Team 2848's 2016 Robot Code
This repository contains the Team 2848 2016 FRC codebase. 

Unlike previous years, we used Java instead of Labview, and were very happy with the results. Our members learn Java in class, and more students were ultimately able to contribute to the codebase. It also meant a simpler, OS-independent build process that made development easier.

## Contents

This year's codebase can be opened and browsed in the Eclipse IDE. Code lives in the src/ directory and is organized in directories based on functionality and Java package.

### org.usfirst.frc.team2848
 * **Definitions.java** contains constants correspondong to ports, PID values, and shooter speed. These can be accessed, but not modified, by the rest of the codebase.
 * **Robot.java** is the IterativeRobot template in order to inherit periodic and disabled states. It handles logic for disabled, autonomous, and tele-operated states.
