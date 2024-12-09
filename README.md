****************
* Project Circuit
* CS221
* 11/6/2024
* Daylen Mathews
**************** 

OVERVIEW:

 This program processes a grid-based circuit board to validate its contents and then determines the most optimal path(s) from a starting point to an endpoint. The board must have a single start point (1), a single endpoint (2), and only valid characters (O, X, T). Once validated, the program outputs the optimal path(s) based on user-selected data structures (stack or queue).


INCLUDED FILES:

CircuitTracer.java - Driver class responsible for executing the program and for managing user inputs
CircuitBoard.java - A class for representing and validating the circuit board from the input file
Storage.java - Interface for abstract data structures
TraceState.java - Class to encapsulate path states during transversal
InvalidFileFormatException.java - Exception class for handling invalid input files
OccupiedPositionException.java - Exception class for handling invalid moves on the circuit board. 
README.md - This file for explaining the code. 

COMPILING AND RUNNING:

 For compiling, from the directory containg the source files, compile the driver using
 $ javac CicuitTracer.java
Could also be efficiently ran with $ javac *.java 
 
 Then you execute the program using 
 $ java CircuitTracer -s|-q -c|-g filename

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

At the core of the program is the CircuitTracer class, which handles the overall runtime and ties together the program's components. It ensures the provided arguments comply with the usage requirements and initiates the circuit-solving process. Most of the functionality resides within its constructor.



TESTING:

 How did you test your program to be sure it works and meets all of the
 requirements? What was the testing strategy? What kinds of tests were run?
 Can your program handle bad input? Is your program  idiot-proof? How do you 
 know? What are the known issues / bugs remaining in your program?


DISCUSSION:
 
 Discuss the issues you encountered during programming (development)
 and testing. What problems did you have? What did you have to research
 and learn on your own? What kinds of errors did you get? How did you 
 fix them?
 
 What parts of the project did you find challenging? Is there anything
 that finally "clicked" for you in the process of working on this project?
 
 
EXTRA CREDIT:

 If the project had opportunities for extra credit that you attempted,
 be sure to call it out so the grader does not overlook it.


----------------------------------------------------------------------------

All content in a README file is expected to be written in clear English with
proper grammar, spelling, and punctuation. If you are not a strong writer,
be sure to get someone else to help you with proofreading. Consider all project
documentation to be professional writing for your boss and/or potential
customers.