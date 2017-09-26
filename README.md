# Maze World
by Michelle Pine and Austin Kim

A maze generator/solver game that utilizes Kruskal's algorithm, breadth-first search, and depth-first search. 

To start the Maze Game, download the zip of these files into an Eclipse Java src and open the ExamplesMazeWorld file. Then, run configurations on that class. The canvas should open automatically, allowing the game to start.

The player is indicated by the yellow circle. The starting square is indicated by a green square, and the goal (target) square is indicated by a purple square. The goal is always the lower right point of the screen. The lines indicate walls in the Maze that cannot be crossed by the user. Thus, the user must navigate between them (hence, navigating a maze). A new random maze is generated every time the code is run. The default maze is always random. 

The user can move using the up, down, left, and right arrows to move in those directions. However, the maze requires time to fully construct. For extra credit, we implemented an animation that knocks down a wall of the maze on each tick to simulate construction. Depending on the size of the maze, this could take some time to complete, since large mazes have more walls. Thus, the player cannot move or solve the maze until the maze is done constructing. Complete construction is indicated by when the canvas says "GO!". 

Once the maze is done, motion is permitted. Additionally, the player can use breadth-first and depth-first searches to complete the maze, activating them by pressing "b" and "d", respectively. Or, the maze can be searched manually. When the player travels a vertex for the first time, it turns blue to indicate that it has been explored. If the player has reached the target purple cell, it must travel back to the green cell to win the game. Cells that have been travelled twice turn a darker blue, while all other cells remain the same. The user wins when it has travelled to the target cell and back, and such will be indicated by the displayed words "The Maze is Solved."

Once a path is established, the user can toggle between all of the explored cells and the final path. Pressing "1" will turn off viewing of all the explored (travelled once) cells if they are visible, and display them if they were not visible. Pressing "2" will turn off viewing of all the path cells (travelled twice) if they are visible, and display them if they were not visible. This was implemented for extra credit. 

Pressing "r" allows a new maze to be generated without restarting the canvas. All values and player position are reset when this occurs. This was implemented for extra credit. 

Pressing "v" creates a maze with a bias for vertical corridors. This was implemented for extra credit. All other features apply to this maze. The same goes for pressing "h", which creates a maze with a bias for horizontal corridors. These were implemented for extra credit. 
