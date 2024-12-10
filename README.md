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
CircuitTracerGUI.java - GUI class to help display my CircuitTracer
CircuitTracerTester.java - Tester class provided 
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

At the core of the program is the CircuitTracer class, which handles the overall runtime and ties together the program's components. It ensures the provided arguments comply with the usage requirements and initiates the circuit-solving process. Most of the functionality resides within its constructor. The CircuitBoard class is also a key concept to understand. This class ensures that the provided input file is valid by checking the following conditions: The first line of the file must contain exactly two integers, representing the number of rows and columns in the grid.
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

 I used the CircuitTracerTester provided. I tested various files to ensure exceptions are thrown appropiately. At first, my files were missing start/end points and had invalid characers and many bugs. I also had to test the pathfinding logic. Verifying that the solver produces correct results is very challenging. I first tried to verify that the Circuit board could retrieve a file and check for errors or anything wrong. My next step was to turn my focus to the CircuitTracer. I would test these files over and over until I could verify that it was running. Although I do not believe there are many bugs, there could still very well be bugs in my program. 


Analysis:

1. How does the choice of Storage configuration (stack vs queue) affect the sequence in which paths are explored in the search algorithm? (This requires more than a "stacks are LIFOs and queues are FIFOs" answer.)

A stack organizes paths by placing new paths on top of the previous ones, which leads to the most recently explored path being checked first. This is useful for situations where the algorithm wants to keep digging deeper into a single path before backtracking. In contrast, a queue adds new paths to the end of the line, meaning paths are explored in the order they were discovered. Essentially, a stack focuses on deeper, narrower exploration, while a queue promotes broader, level-by-level exploration.

2. Is the total number of search states (possible paths) affected by the choice of stack or queue?

The total number of search states, or the discovered valid paths, is not inherently affected by whether a stack or a queue is used. Both configurations will ultimately explore the same set of paths, but the order in which these paths are explored differs. A queue will continuously add newly discovered paths to the end, keeping track of all possibilities at each depth level, while a stack may discard longer paths more quickly as it pushes newer, potentially shorter paths to the top.

3. Is using one of the storage structures likely to find a solution in fewer steps than the other? Always?

I do not believe the use of a stack or queue will find a path quicker. The structure merely dictates how the paths are stored and retrieved. The underlying logic of the search algorithm is what determines how quickly a solution is found. However, a queue might be more likely to find the shortest path first, especially in a scenario where paths are explored level by level. A stack may explore deeper paths first, potentially missing shorter solutions along the way, but this is not always the case.

4. Does using either of the storage structures guarantee that the first solution found will be a shortest path?

No, using either a stack or a queue does not guarantee that the first solution found will be the shortest path. In the case of a queue, the first solution found is more likely to be the shortest because it explores all possible moves at each level. However, with a stack, the first solution might not be the shortest, as it explores deeper paths first, potentially overlooking shorter paths.

5. How is memory use (the maximum number of states in Storage at one time) affected by the choice of underlying structure?

Memory usage is influenced by the choice of storage structure. A stack (LIFO) may use less memory at any given time because it discards paths as new, potentially better solutions are found. It focuses on the most recent path and can replace it with shorter ones as it progresses. In contrast, a queue (FIFO) stores all discovered paths and may require more memory, especially when the number of possible paths grows large. This is because it keeps all the paths at each depth level in memory until the search moves on to the next level.

6. What is the Big-Oh runtime order for the search algorithm?
What does the order reflect? (Maximum size of Storage? Number of board positions? Number of paths explored? Maximum path length? Something else?)
What is 'n', the single primary input factor that increases the difficulty of the task?

The runtime order for the search algorithm is O(n), the path is relatively simple to traverse, either by moving all the way to the right and then down, or down and then to the right. However, the algorithm still needs to perform a comparison at each step using a loop. Since there is no need to backtrack, it avoids using multiple loops. This complexity reflects the initial loop that checks each position on the board. Since the size of the grid is not known beforehand (as it's read from a file), a while loop is used to check for available spots (denoted by "O") or blocked spots ("X"). The loop must account for an unknown grid size, which adds a variable growth factor to the overall complexity.


DISCUSSION:
 
 The most significant challenge was understanding the differences between stack and queue configurations and how they affect path traversal. A stack prioritizes the most recently explored path, allowing for a more focused depth-first search. In contrast, a queue explores all possible paths equally, leading to broader exploration but higher memory usage. A major issue was debugging the testing class provided, which lacked clarity about its purpose and error reporting. I also struggled with finalizing my constructor, as I had left out some important parts when first starting up. With the help of tutoring sessions, the cause of errors was identified and resolved. I also struggled with creating my GUI, as this is not a strong point of mine. It was very tough figuring out how to implement it and make sure it ran well with my code. 
 
 
EXTRA CREDIT:

 I created a GUI called CicuitTracerGUI.java to create my GUI. 


----------------------------------------------------------------------------
