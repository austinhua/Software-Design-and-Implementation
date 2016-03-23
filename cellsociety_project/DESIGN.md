#Introduction
Our team is trying to implement a simulation of cellular automata, 2D cells that have an initial configuration of states that are changed depending on their surrounding cells. The simulation will take in an XML document that will specify rules for changing states as well as initial configuration parameters, such as size, initial states of the cells, and parameters specific to the simulation. These parameters may be adjusted by editing the XML file. The simulation will have a GUI that will display the grid of the simulation and represent different states of the cells in a graphically distinct manner. The GUI will have a user interface that allows the user to start, stop, reset, and step through the simulation, with the start option automatically stepping through the simulation at a certain speed. The ability to specify the parameters mentioned above by editing the XML file and the GUI's user interface will be open to the user, while the GUI, the logic that updates the simulation, and the data structures that represent the simulation will be closed. 

#Overview

![Overview](https://github.com/duke-compsci308-spring2016/cellsociety_team20/blob/master/Design.md_images/Overview_Image.jpg  "Overview")
#####Component - Main
Purpose - Responsible for starting and controlling the flow of the whole program.  
Collaborates -  initialize GraphicDisplay, call XMLParse based on the file chosed from GUI, and initialize the right type of Simulation based on the XMLParse result

#####Component - Cell
Purpose - Responsible for maintaining information about each cell’s current state and position in grid  
Collaborates - Stored in 2D array of cell objects within the Simulation SubClasses. If the positioning of cells changes within this grid, individual cells should be updated to reflect the changed position. The methods in the Simulation subclass can be called to determine if a cell should be updated (state and position) based on the simulation’s rules. 

#####Component - XMLParser
Purpose - Reads in simulation configuration information from XML file and uses that information to launch a Simulation based on those specifics, specifics such as type of simulation, size of grid, probability values etc...  
Collaborates - Reads in from locally saved XML file and stores it, where it can be read by the main method to create the proper simulation subclass.

#####Component - Simulation
Purpose - Abstract class that holds methods and attribute variables common to all simulation types. Extended to subclasses that hold specifications for specific simulation types. Maintains and updates a 2D array (grid) of Cell objects.  
Collaborates - Receives configuration parameters from the XMLParser class and generates grid/ creates rules based on them. Updates Cells based on the simulation logic it contains and the variables passed via the XML file. 

#####Component - GraphicsDisplay
Purpose - Initializes the GUI, Creates and updates the display of the cell grid based on changes in state and position, and advances simulation based on user input i.e. (Run Simulation, Step Simulation, Stop Simulation)  
Collaborates - Receives information about the position and state of each cell and updates the display based on changes. Sends user input to the Main method to instruct it to run, step, or stop. 



#User Interface


![UI](https://github.com/duke-compsci308-spring2016/cellsociety_team20/blob/master/Design.md_images/UI_Image.jpg "UI")
Our user interface will consist of a grid display that reflects the states of the Cells within the simulation’s 2D array. To the side of this grid will be buttons that change the behavior of the simulation. During our brainstorming session, we decided on having: Start, Step, Pause, Resume, Stop, Reset, and Load File. The load file button will call up the local file system to allow the user to select a new configuration file to load. The other buttons will simply respond like normal buttons, responding to a user’s mouse click. We also included a basic slider that would allow for the user changing the speed of the simulation. The GraphicsDisplay class will check that a file loaded via the Load File button is a XML file and inconsistent or incorrect formatting within that file will be handled within the XMLParser class. 

#Design Details 
This section describes each component introduced in the Overview in detail (as well as any other sub-components that may be needed but are not significant to include in a high-level description of the program). It should describe how each component handles specific features given in the assignment specification, what resources it might use, how it collaborates with other components, and how each could be extended to include additional requirements (from the assignment specification or discussed by your team). Include the steps needed to complete the Use Cases below to help make your descriptions more concrete. Finally, justify the decision to create each component with respect to the design's key goals, principles, and abstractions. This section should go into as much detail as necessary to cover all your team wants to say.

The following image contains most of the design details our group discussed. We included additional comments clarifying certain aspects of the design.
![Design Details](https://github.com/duke-compsci308-spring2016/cellsociety_team20/blob/master/Design.md_images/Design_Details.jpg "Design Details")
#####Use Case 1 and 2:
The method our program will call for advancing the simulation and updating the 2D Array (Grid) of Cell objects is the step() method. This method is housed within the Simulation Class. Inside the step() method is a for loop that iterates through every Cell in the 2D Array. For every Cell object(c) the method getNeighbors(c) will return a list of the Cell objects that border Cell object passed as a parameter. Then, a method called stateLogic(c, List<Cell>) will check the state of the current cell against the states of surrounding cells. It returns True if the conditions(according to the rules specific to the simulation type) returning boolean True if the conditions are met for a change in the state of the Cell object. This return value is passed to an if-statement 

``` Java
//if(stateLogic) 
//Inside the if-statement is a method from the Cell class that is called on the Cell object c.changeState(). Then the process repeats.  

Simulation
step()
for Cell c : Grid 
List<Cell> getNeighbors(Cell c)
boolean stateLogic(Cell c, List<Cell>)
if(stateLogic):
	c.changeState()
```
The difference in code between applying the rules to middle cells (Use Case 1) and edge cells (Use Case 2) occurs within the getNeighbors(c) and stateLogic(c, List<Cell>). The first uses the X and Y coordinates of the cell (c.getPoints) within the grid to decide whether the cell is a middle, edge, or corner. Then in stateLogic(Cell c, List<Cell>), the method will know which kind of cell it is based on the length of the neighboring cell list. Then it will decide on whether a change in state is necessary according to the guidelines for each cell location.  

#####Simulation
Simulation will be an abstract class that will be subclassed into FireSimulation, SegregationSimulation, GameOfLifeSimulation, and PredatorPreySimulation. 

#Design Considerations 
This section describes any issues which need to be addressed or resolved before attempting to devise a complete design solution. It should include any design decisions that the group discussed at length (include pros and cons from all sides of the discussion) as well as any assumptions or dependencies regarding the program that impact the overall design. This section should go into as much detail as necessary to cover all your team wants to say.

#####Location of the step() method 
* One idea was to include the step method that advances the simulation called inside the GraphicsDisplay class. This decision was made because of the necessity of user input. The GraphicsDisplay class will have a handleButtonPress() method that records which, if any of the buttons have been pressed and calls methods that correspond to that instruction within the Simulation subclasses ie( // if(Step Button Pressed){
simulationSubclass.step() } )  
* The other idea was to have these button presses sent to the Main method to control whether the program steps or stops. We decided against that because the DisplayGraphics class is going to have to be dependant on the Simulation class regardless, and we would rather have it interact with it again than interact with the Main class. That would create less independance for the classes. The downside is that the Simulation class could become too large/handle too many responsibilities. We will closely monitor it. 


#####Is the cell able to access the grid it’s in? 
* We decided not to have the Cell class be able to access its neighbors and instead have the Simulation class handle getting the neighbors of the cell. This way the Cell class doesn’t need to keep track of the grid. We thought it would be better for the Simulation class to handle finding neighbors since it’s also responsible for determining how to update each cell position, which depends on neighbors.

#Team Responsibilities
A module is a set of methods that work together as a whole to perform some task or set of related tasks. We divide the project into three modules:

1. Configuration module
Class: Main, XMLParser  
Primary: Jane Yu  
Secondary: Austin Hua, Justin Bergkamp  

2. GUI module:
Class: GraphicDisplay  
Primary: Austin Hua  
Secondary: Jane Yu, Justin Bergkamp

3. Simulation module  
Class: Simulation and its subclasses, Cell  
Primary: Justin Bergkamp  
Secondary: Jane Yu, Austin Hua

Each module will be encapsulated and can be accessed only through a documented interface (a set of public methods), by which each team member can develop their own module independently.


Use Cases
The following scenarios are provided to help test the completeness and flexibility of your design. By writing the steps needed to complete each case below you will be able to see how effectively your design handles these scenarios and others will be able to better understand how deeply you have considered the basic issues.

Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)

>Since the Simulation class maintains the 2-D grid by keeping a field Cell\[][] and controls all updates of the simulation. If want to update a cell in the middle, we can get access of its neighbors by calculating their relative indices. 

Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)

>Similarly, we can get access to/detect edge cell by its position, and calculate its neighbors based on its index.

Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically

>Since each cell keep track of its state by keeping a field called state, and have a method to changeState(), we can iterate over all the elements in the 2D grid Cell\[][], and update each one of the cell’s state.

Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire

>XMLParser() has a public method getProperties() that returns a map<string, double> to handle all parameters. A subclass of simulation constructor will construct a new Simulation object based on this map.

Switch simulations: use the GUI to change the current simulation from Game of Life to Wator

>GUI will have a LoadFile bottom that allows the user to switch simulation. Besides, it also has Start, Step, Stop, Pause, Resume, Reset bottom and a Speed Slider to allow user to control the simulation.

