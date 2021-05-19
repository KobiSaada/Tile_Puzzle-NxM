# Tile_Puzzle-NxM
 Extended of the game tile puzzle in AI course with search algorithms :mag_right:

## General overview

This projects goal is to solve (2 or 1)-hole NxM tile puzzle using various known graph search algorithms.
The problem consists of a matrix which is sized NxM which contains (N*M - 2) or (N*M - 1)  numbers and two or one  empty places as follows:


| 1 | 4 | 2 | 3 |
| ------------- | ------------- | ------------- | ------------- |   
| 9  |  | 10 | 6 |
| 8  | 5 |   | 7 |

The final answer should be the moves(moving numbers up/down/left/right into the empty space) until reach a sorted matrix in the follwoing way:

| 1 | 2 | 3 | 4 |
| ------------- | ------------- | ------------- | ------------- |
| 5  | 6 | 7 | 8 |
| 9  | 10 |   |  |

In this project the follwing algorithms were used to solve the above problem:
1. BFS 
2. DFID
3. A-star
4. IDA-star
5. DFBnB

* The huristic function used for A-star, IDA-star and DFBnB is Manahatten distance.

## Implementation

The program recives a text file in the follwong representing the problem and the requested algorithm to use inorder to solve it.
For example:

A*
with time
no open
3x3
1,_,4
3,5,6
2,_,7
Goal state:
1,3,4
0,2,4
7,5,6


Stating to use A-star, to monitor the time it took to solve, the size of the puzzle and the puzzle intself.
(See attached input examples)

The program will then create an output.txt file with the answer stating the moves for solving the puzzle, the time and effort in took to solve. For example(for the above example):

6U-10L-10L-11&12U<br>
Num: 233<br>
Cost: 22<br>
0.093 seconds<br>

meaning the answer for the given puzzle is: move 6 up then move 10 left then move 10 left then move 11 and 12 up.
Num = the number of nodes created in the proccess.
Cost = the "cost" of the solution i.e moving one block costs 5, moving 2 blocks horizontly costs 6 and moving 2 blocks verticaly costs 7.

## How to run:


first navigate to bin folder
```
cd bin
```
create a input.txt file as instructed above(or by using given examples) in bin directory.
Run following command to run program:
```
java Main input.txt output.txt
```
This above command will take your input.txt created, run the program and algorithms chosen on it and create output.txt with the answers.

Enjoy 	:smiley:
