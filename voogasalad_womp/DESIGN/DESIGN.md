##Design
Design: Write your planned design in a single file, DESIGN.md, as described below. Note, this file can link to external image files (such as UML diagrams, planned GUI layouts, example game screens, etc.).

###Introduction 

This section describes the problem your team is trying to solve by writing this program, the primary design goals of the project (i.e., where is it most flexible), and the primary architecture of the design (i.e., what is closed and what is open). Describe your chosen game genre and what qualities make it unique that your design will need to support. Discuss the design at a high-level (i.e., without referencing specific classes, data structures, or code).

We are creating womp for the purpose of having a robust and flexible Tower Defense game creator capable of generating distinctive games. In other words, our primary goals involve implementing enough functionality within the program that will allow developers to produce substantially different experiences. Tower defense games have a general set of specific characteristics that separate them from other types. In essence, tower defense games consistently involve creating a protection system from some set of enemies by placing units that will shoot enemies down. Therefore, in order to encourage diversity, womp will allow developers to specify the behaviors of towers, effects within the game, and interactions between in-game units. These will be our most flexible areas, and we hope that this will be sufficient to create a diverse set of games. In terms of open-closed principle, our game engine classes will be closed for modification, but open for extension. Our purpose for these classes will be to provide the authoring environment with a basic framework with which they can create specific game units, define interactions, behaviors, and events within their game through the creation of subclasses of our basic framework components. Furthermore, authoring environment, game data, and game player will all consist of closed classes that provide external APIs for interactions between each of these components. 

###Overview 

This section serves as a map of your design for other programmers to gain a general understanding of how and why the program was divided up, and how the individual parts work together to provide the desired functionality. Describe specific modules you intend to create, their purpose with regards to the program's functionality, and how they collaborate with each other, focusing specifically on each one's API. Include a picture of how the modules are related (these pictures can be hand drawn and scanned in, created with a standard drawing program, or screen shots from a UML design program). Discuss specific classes, methods, and data structures, but not individual lines of code.

####Game Engine

The game engine is responsible for providing a framework with which the authoring environment can create substantial diversity between games. Furthermore, the engine should contain basic functionality that will allow it to run games created by the authoring environment. This will involve being able to instantiate game elements, such as towers and enemies, from files that are created by the authoring environment in order to specify behavior and parameters of the game elements (these files will be responsible for specifying characteristics of the game, such as how towers attack, how much damage enemies take, in-game effects that are applied to towers based on terrain, and so on). 

In order to accomplish these tasks, the game engine will be responsible for implementing multiple classes and APIs for use by other classes and the developers. First and foremost, the game engine will implement classes to represent in-game elements that will be closed for modification, but open for extension. These classes will be used by the authoring environment in order to create subclasses that will specify behaviors and characteristics of in-game units such as towers, enemies, and effects. Furthermore, the game engine will have an IPlayerEngineInterface API, which will be responsible for communicating with the game player. This interface will allow the the person playing the game to communicate with the game engine from the game player in order to place down new towers, select upgrades, and play different games. Essentially, this interface will be used for all communication between the game player and the game engine in order to play and switch games.
 
####Game Data

In a broad sense, the game data group is responsible for creating a module that is capable of encoding, decoding, and manipulating any information that is necessary for the authoring environment to create a game and save it in a useable format. The game data module also needs to be able to use previously saved information in the form of files when running a game (or at the very least, it needs to be able to pass this information to the game player in order for the game player to be able to run a specific game type that was created).
Game data will be responsible for creating classes that will allow the authoring environment to save information related to the creation of games. This could potentially include code that is involved in saving game-related classes (specific implementations of towers, effects, level data, etc), to files for use by the game player. This would also include a class that decodes the information for specific towers, power-ups, enemies, and so on from these files for use in the game engine so that the game logic can run, interactions can be viewed between units, and the player can select between a specific set of towers that was specified by the developer that created the game.

####Game Player

The game player is responsible for displaying the events of the game engine as well as holding user information. When asked, the engine provides the updated elements (towers, paths, units, etc.) and properties which the gameplayer animates. The game player also allows the user to interact with the board by adding/modifying towers, which is sent to the game-engine. The game-engine processes the towers and units in the background and the game player asks for those updates at every frame. The game player simply sends any information that the game-engine might need that has been updated on the game board, such as the modifying of towers. Otherwise, the game-player passively reflects updates from the engine. It also receives game status information such as wave, level, etc. from the game-engine. The game player keeps track of user-specific information such as high-score, game-speed, and color schemes for the game. Lastly, the game player also allows the user to change, pause, restart, etc. games, which the game-player simply does by calling methods from the engine. For switching games, the game-player must keep information about the possible game options as well as the game data files to provide options for the user.

####Authoring Environment

The Authoring Environment is used by the Developer to write Game Data. This module creates new instances of Game Elements, interactions, and other parts of the Game Engine. This is done by prompting the Developer for parameters via ComboBoxes, TextFields, and other input methods inside a View window. Each ElementView window contains a reference to the appropriate Factory class that then takes these parameters to instantiate a new GameEngine GameElement class. Each individual class has the ability to be serialized to XML (by the Game Data group’s methods). Additionally, the class is passed over to the ‘PeriodicTable’ class in the Auth Environment which is responsible for holding all created Game Elements. These Game Elements can be clicked on to reveal their Properties or even placed on the MapDisplay if they are placeable. The MapDisplay is the main view of our Authoring Environment and displays the starting state of each wave or level. Certain GameElements such as Terrain and Enemy spawn points can be placed on this MapDisplay. The final state of the MapDisplay (including the locations of all the Game Elements placed on it) will be serialized to XML. Our AuthoringEnvironment has the ability to be saved and loaded to file. To save, we will go through our Auth Environment backend classes and write all contained classes to file. These would include each GameElement inside our PeriodicTable, the MapDisplay and its starting distribution of GameElements, as well as a Settings class that contains non-placeable initial GameElements such as background music and playback speed. 

![Overview](http://i.imgur.com/CKsHvps.png)

###User Interface 

This section describes how the user will interact with your program (keep it simple to start). Describe the overall appearance of program's user interface components and how users interact with these components (especially those specific to your program, i.e., means of input other than menus or toolbars). Include one or more pictures of the user interface (these pictures can be hand drawn and scanned in, created with a standard drawing program, or screen shots from a dummy program that serves as an exemplar). Describe how a game is represented to the designer and what support is provided to make it easy to create a game. Finally, describe any erroneous situations that are reported to the user (i.e., bad input data, empty data, etc.).

Authoring Environment:
There is a main tool bar with three menu items: file, create, settings and help. In file, there are options such as open, save, and load files. In create, there are options to create an enemy, tower, path, and terrain. In settings, there would be options to set rules for the game. In the main window, there will be a display in the middle to show the current default stage. The user has the option to right click on the preview display and create enemy, tower, path and terrain (which means the x and y coordinates will be based on where the user the click). When an option window is clicked, an external window will pop up and includes the elements that the developer is allowed to modify.

![First](http://i.imgur.com/N3YsFAM.png)

####Enemy/Tower:
- A preview area
- Coordinates
- Damage
- Cannons/Number of bullets
- Attack Speed
- Movement Speed
- Health Points
- Cost/Reward
- Appearance
	- Load image/pick color
- Properties
	- This button will lead to another window that can set specific attributes associating with this property (i.e: burn, slow, etc).a

![EnemyTower](http://i.imgur.com/OJmEgQq.png)

####Path/Terrain:
- A preview area
- Coordinates
- Appearance
	- Load image/pick color
- Properties
	- This button will lead to another window that can set specific attributes associating with this property (i.e: more damage, more speed, more reward).

![PathTerrain](http://i.imgur.com/j1fHBS3.png)

####Game Player:

The user interface for the Game Player is similar to the interface for the Authoring Environment. The player will be able to play the game by placing towers on the board and starting the waves by pressing a button. The available towers and other player information, such as money and health, are all visible in a panel on the bottom of the player UI. This panel also includes game information such as the round number, level number, difficulty, and tower information (if a tower is selected). The game window is placed above the panel and this is where the player can place the towers and see the action of the game. Above the game window, there is a menu that gives the player the option to save, load, restart, or switch the game that they are playing. The last component of the player UI is the configuration panel. This panel allows the user to customize the player UI by changing things such as the UI color scheme, the game animation speed, etc. There will also be the ability to create multiple game players, using tabs, so that the player has the option of playing multiple games at once in the same window. 

Errors will be thrown in a variety of cases. If the player tries to place a tower in an invalid location, such as outside of the game window, an error will be thrown. Additionally, errors will be thrown if any errors occur with the XML loading/saving process. 

Sketch of the described player UI

![PlayerUI](http://i.imgur.com/DS5vzTS.png)

###Design Details 

This section describes each module introduced in the Overview in detail (as well as any other sub-modules that may be needed but are not significant to include in a high-level description of the program). Describe how each module handles specific features given in the assignment specification, what resources it might use, how it collaborates with other modules, and how each could be extended to include additional requirements (from the assignment specification or discussed by your team). Note, each sub-team should have its own API for others in the overall team or for new team members to write extensions. Finally, justify the decision to create each module with respect to the design's key goals, principles, and abstractions.

####Authoring Environment:

The authoring environment is a pretty closed off and self sufficient component as it will only be accessed by the Game Data. The user will be able to choose to create a tower, a enemy unit, a terrain, an affector, or a reaction, and they will also be able to place where the terrain at each location of the grid. Each of these Game Elements has its parameters set in a View class (such as TowerView) and this class will contain a reference to a corresponding Factory class (such as TowerFactory). This Factory class has the role of processing the parameters and instantiating an instance of the correct class. This class can then be written to XML or added to a table of existing Game Elements (PeriodicTable). The user will also be able to draw out the path of the enemy units and click on where they will be able to put towers during the game. To handle the creation of the tower and the enemy unit those will be too subclasses of the Element abstract superclass; there will also be Health, Speed, Defense, etc classes that each of the Element Objects will have a copy of to keep track of effects on speed and stuff.

####Game Data:

The GameData framework is built to provide a very transparent way to pass the game type created by the authoring environment to the frontend. This will be accomplished by taking the objects that define the gametype from the authoring environment, and serializing them using the XStream library. To deserialize, XStream will decode to the same classes that the authoring file created. The goal of this is to make the saving process as simple as possible. It will also facilitate collaboration between multiple authors, since the authoring environment can very easily write and read from the GameData package.
XStream can serialize most objects without a problem, but can have issues with polymorphism and complex objects. For example, JavaFX classes cannot be stored by XStream. Thus, some manual serialization will be necessary, and this can only be discovered in implementation.
The same process will be used for saving and reading the game state. XStream will be used to simply save objects to XML, and then recreate them later.
The API is extremely simple. It will only have two functions for the authoring environment, to save to XML, and to read from XML. Directly calling read on the return data from save should return the exact same class.

####Game Player:

The game player works with the game engine and game data to generate a game for the player. The game player uses the game data, written as XML files, to initialize a game based on the settings within in the XML file. Once all of the game settings are set up, the game player uses the game engine to start the game. The game player will call methods from the game engine API to run the waves/levels that are passed into the player as game data. The game player will update the GUI every frame to match the information that it receives from the game engine. 
In order to accommodate the additional features within the assignment specifications, the game player will store information such as high scores, player health, player money, and other game information. The information will be stored in a game player internal API which can be accessed at any time. This information will be displayed within the UI. In order to save and load games within the player, we will utilize the game engine to create and load XML files based on the current game state. To be able to switch between games, we would need to have XML files stored within the player, so that the user can choose to switch games at any time. In addition to these features, there will also be options to let the player change the configuration of the game player. The game player will have an internal API to create different GUI elements that will have event handlers to change things such as the game player animation speed or the game player color theme. 
We decided to create a game player module because the game player is an important part of the project. Once the game data is created by the game authoring environment, the data needs to be converted into a game that a player can actually interact with. The game engine serves as the back end for the actual game, so we need a front end for the game. This is where the game player comes ie. The game player serves as the front end/view for the game, and reflects changes in the model (the game engine), while also updating things such as the high score and game status information.


####Game Engine

In addition to providing the framework for the authoring environment to create the Game Data, the game engine also provides the backend-end framework for executing the game created  by the authoring environment. The Game Data is fed into the Game Player module where the Game Data is decoded to determine how each game element is instantiated. As mentioned, the game engine provides the back-end framework necessary for the authoring environment to create specific implementations of our game elements. This includes creation of custom units (such as towers and enemies) with user-selected properties. The available properties are specified in our implementation of Game Engine and are stored as individual classes within units. Specifically, we will have a superclass called Unit which has an instance variable of a UnitProperties class. Within UnitProperties, there will be private instance variables along with getters and setters that will allow us to get and set the values. The other main functionality that Game Engine provides to Womp is Affectors and reactions to the affectors per unit.

Within the Authoring Environment, users are able to specify implementations of these various affectors and how these affectors would affect specific types of units (also created within the Authoring Environment). For example, users could create a Poisoning Affector and then specify how the specific types of units would be affected by poisoning (how specific types of units would be affected is based on the unit reactions, which would also be specified by the Authoring Environment). This way, specific types of units could, for example, be immune to certain types of affectors, or more generally, different types of affectors could adjust differently to specific affectors. The reactions and affectors could, to reduce effort on the user interacting with the Authoring environment, could be implemented in hierarchies (i.e. any type of unit under a superclass could react the same as its superclass to all properties, or to specific properties, the reaction could be overridden).
Additionally, the Game Engine will also be responsible for the game logic that occurs, such as when certain events are triggered, collision detection between projectiles and enemies, and etc. Major components of game logic is the path-following that enemies will do when traversing through a map and the level progression as the player accomplishes his or her goal. Furthermore, the Game Engine will calculate player statistics as the game progresses, such as player score, level, experience, and towers that may or may not have been unlocked. 

Affectors and other back-end implementations will operate based on “time” in which a certain number of back-end game “ticks” is equivalent to a real-life amount of time. These time units will be used to apply mathematical functions based on time to units (i.e. exponentially increasing amount of damage an accessor applies over time).
	

###Example games 

Describe three example games from your genre in detail that differ significantly. Clearly identify how the functional differences in these games is supported by your design and enabled by your authoring environment. Use these examples to help make concrete the abstractions in your design.

Tower Defense which would be a general game where the towers would be created with a attack damage, range, speed, etc; and initialized with zero health and defense. The enemy units would be created with no attack and no range. The towers would not have the ability to move once placed on the grid, and a path would be created for the enemies to “walk” on.

Plants versus Zombies would pretty much be the same thing except the towers would now have defense and lives, and the enemy units would have attack and range abilities. The towers would also be able to be created on the path, which is not allowed in Tower Defense. 

Custom Chess. The map will consist of 64 tower spots in a grid. Each turn will be a wave where each player can move one of their towers to a different spot dictated by the tower’s path. Towers will have a range of 0 but will kill enemy towers once moved to the same location (since towers can only be moved to valid locations). It would be difficult to create an AI to play against so we would rely on networked games. Another difficulty would be programming in some of the complex rules of chess, even rules as vital as checkmates, so we plan on creating a simplified version of the rules. 

###Design Considerations 

This section describes any issues which need to be addressed or resolved before attempting to devise a complete design solution. Include any design decisions that each sub-team discussed at length (include pros and cons from all sides of the discussion) as well as any ambiguities, assumptions, or dependencies regarding the program that impact the overall design.

Where do we store the effects of affectors on game elements? We decided that each individual unit should keep track of its multipliers (for example, how much does health decrease from a burn affector). This way, an affector is distributed (perhaps as a terrain tile affecting a specific area) and then each unit is affected accordingly.

With regards to multipliers, we decided that instead of having a simple system in which affectors could only affect units linearly (i.e. a multiplier like reducing health by 10% every “time” unit), we would incorporate the ability for units to be affected by various mathematical functions based on time (which would actually just be based on a certain number of game ticks and be handled by game-engine). This way, units could be affected exponentially or by literally any form of mathematical function (i.e. a poison affector could exponentially increase the amount of health inflicted per unit of time over time).
