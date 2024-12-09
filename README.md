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
 Arguments:

-s or -q: Select stack (-s) or queue (-q) for path organization.
-c or -g: Choose console (-c) or GUI (-g) output. (GUI is not implemented in this version.)
filename: Provide the path to the input file containing the circuit board grid.

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

At the core of the program is the CircuitTracer class, which handles the overall runtime and ties together the program's components. It ensures the provided arguments comply with the usage requirements and initiates the circuit-solving process. Most of the functionality resides within its constructor.

The CircuitBoard class is also a key concept to understand. This class ensures that the provided input file is valid by checking the following conditions:

The first line of the file must contain exactly two integers, representing the number of rows and columns in the grid.
The remaining lines must adhere to these rules:
Exactly one start point, denoted as "1".
Exactly one end point, denoted as "2".
Only the characters "O", "X", and eventually "T" (when a path is traced) are allowed.
If any of these conditions are violated, appropriate exceptions are raised, such as when the number of columns does not match the first line's specification. If the file is valid, the CircuitTracer class receives the validated CircuitBoard object for processing.
The paths can be stored and retrieved in different ways based on the user’s preference:

If the user specifies "-s" in the command-line arguments, the paths are stored in a stack (LIFO structure), meaning the last found path is retrieved and displayed first.
If the user specifies "-q", the paths are stored in a queue (FIFO structure), meaning the first found path is retrieved and displayed first.
Recursion Process in CircuitTracer
The CircuitTracer uses recursion to explore possible paths:

The recursion continues until the program finds the optimal path. The method isSolution checks if the current position's adjacent cell is the end point ("2"). If so, the path is considered complete and added to the list of potential solutions.
The current solution will be replaced if it is shorter (more efficient) than previously found paths.
The recursion explores in all four directions (up, down, left, right) until it reaches the end point.
Before proceeding, the isOpen method is used to check if a move is valid, ensuring the cell is within bounds and not blocked by an "X".


TESTING:

 How did you test your program to be sure it works and meets all of the
 requirements? What was the testing strategy? What kinds of tests were run?
 Can your program handle bad input? Is your program  idiot-proof? How do you 
 know? What are the known issues / bugs remaining in your program?

Analysis:



DISCUSSION:
 
 The most significant challenge was understanding the differences between stack and queue configurations and how they affect path traversal. A stack prioritizes the most recently explored path, allowing for a more focused depth-first search. In contrast, a queue explores all possible paths equally, leading to broader exploration but higher memory usage.

A major issue was debugging the testing class provided, which lacked clarity about its purpose and error reporting. With the help of teaching assistants, the cause of errors was identified and resolved.
 
 
EXTRA CREDIT:

 If the project had opportunities for extra credit that you attempted,
 be sure to call it out so the grader does not overlook it.


----------------------------------------------------------------------------

All content in a README file is expected to be written in clear English with
proper grammar, spelling, and punctuation. If you are not a strong writer,
be sure to get someone else to help you with proofreading. Consider all project
documentation to be professional writing for your boss and/or potential
customers.