### Use Cases 
===================

### Game-Player
-------------
1. Start game and animate waves:
> During the animationTimer, call updateElements() at every frame. Call placeTerrain(), placeUnit(), and placePath() at every frame, animate according to their animation state, and display relevant information such as health. 

2. Add towers to playing field: 
> During the animationTimer, an event handler will call addTower(int towerIndex) based on the tower being added to the board, which tells the engine that another tower is in play. 

3. Modify current towers (delete, add power-ups, etc.):
> During the animationTimer, an event handler will call modifyTower()  to change the properties of the towers in the engine (the changes will be applied in order)

4. Save game to be played later:
> An event handler will call the saveGame() method, which tells the engine to save the current game state and returns a list of files that represents that state. The list of files will be saved in the data source so the user can select them later. 
 
5. Restart current game:
> An event handler will call the playLevel() at the current level so that the engine will start playing the current level from the initial conditions. 
 
6. Switch to completely different game: 
> Event handler will call playGame(), which calls setUpEngine() with the specified game files as well as playLevel() and playWave() accordingly.
 
7. Changing cosmetic elements of the view such as color schemes:
> Event handler will call changeColorScheme(), which changes the current color scheme in the data source.

8. Adjusting the game speed:
> Event handler will call changeGameSpeed(), which changes the speed of the animationTimer

9. Pausing the game and restarting it later:
> Event handler will call toggleGame(), which stops or starts the animationTimer depending on its current state.

10. Displaying high score and other game status information: 
> Calls getGameStatus() from the engine and displays that information using a GUI element .

###Game-Engine
-------------

1. Player finishes the first wave in a level, hits continue on screen, and the next wave plays: 
IGameInterface method playWave(2) is executed from an event handler in the game player, which gets the current Level and calls current.playNextWave(), and allows the user to play the next wave until current.isWaveFinished() returns true, at which point the screen returns  back to the “wave finished” text.

2. Player is playing the game, while he places a tower on the rocky terrain, which adds +3 to range for the tower. During the next tick of the game, terrain.getAffectedUnits(), which will be a collection of units that are considered “inside” the terrain. The game will then call terrain.applyEffect(), which will call the Affector.apply(unit) method to apply the effect to each unit.

3. Projectile from a tower flies across the map and eventually collides with an enemy, which applies a freeze effect: for each tick of the game, the Projectile.update() method is called, which updates the position of the projectile. Whenever, projectile.update() is called, a check for collisions happens, which returns true whenever the projectile collides with the enemy. The update method then calls the applyEffects(unit), which applies the affector for the projectile to the enemy. The projectile is then removed from a list of active projectiles.

4. Player places down a tower, which promptly fires a projectile: IGameInterface method addTower(index) is called to specify a tower to add. At the next tick of the game, the tower.fire() method is called, which adds a projectile to the list of active projectiles in the game.

5. Player decides to upgrade a tower:
IGameInterface method upgradeTower(index) is called to update the Barrel property of the tower in question, which also deducts the appropriate money from the player via the deductFunds(double amount) method. Updating the Barrel property calls the getEffect() method that returns an affector which affect.apply(unit) is then called to change the properties of the tower. 

6. Player is unable to kill all of the enemies and loses all of his lives:
IGameInterface method checkLives(current lives) is called to determine whether or not the player has lost all of his lives. In this case, the player loses all of his lives so IGameInterface method showLossScreen() is executed which shows a summary screen of how the player did during the playthrough.

7. The game is paused then resumed or the game engine needs to update element properties due to a new tick (example code):
Game player calls nextTimeStep() in Timer then updateElements() in IPlayerEngineInterface
. The game engine then internally calls the update() method in Unit for each current game element, which calls apply(UnitProperties myProperties) for each Affector in the Unit’s List<Affector> myAffectors in order to apply actual affectors to the elements, then applies this change by calling update(properties) for the Unit’s UnitProperties. The list of affectors is then cleared for the next tick.

8. An enemy shoots a tower and deals damage to it: 
The Enemy method fire() launches a projectile at a tower. The IGameInterface method detectCollision() determines that projectile has damaged a tower. The method getEffect() returns an affector (which is the projectile launched by the enemy) which is then used to apply on the targeted tower via the affect.apply(tower.getProperties), which lowers the tower’s health.

9. It’s a new tick and enemies need to move to the next position on their paths:
Enemy calls getNextPosition() in Path, which looks at the list of possible next positions, and due to game logic selects among the next positions the position to move. An affector is then internally created that updates the position of the Unit within its UnitProperties. This affector is added to the Unit’s list of affectors to be applied at the tick.

10. Player is playing the game and wants to move enemy from one Terrain to another:
GameEngine updates Position of player by creating an affector that updates player movement and is added to list of affectors with addAffector
(affector) call to Unit. Player then calls getAffectedUnits() and gets a list of affects units in a terrain; this will no longer have the unit in the old terrain, so a call on terrain.getAffectedUnits() for the new terrain will instead have the new unit. Then, terrain.applyEffect() will be called, which calls Affector.apply(unit) on the movement affector which will update the movement and the unit will now be in the new terrain without reference to it in the old terrain.

###Authoring-Environment
-------------
1. (Error in author designing tower properties)

2. Place GameElement on the map
In the ‘PeriodicTable’ view, the Developer can click on a place-able GameElement. An EventHandler then makes another instance of the GameElement with the same Property class. This ‘copy’ is then placed on the MapDisplay where a mouse is clicked. 

3. Create new tab/wave (example code) 
In our top bar menu, there is a Menu item that on action, will call createNewTab() that adds a new Workspace to the current list of tabs. 

4. Creating a New Element
If the user clicks to create a new element, then the create an element window will pop which is triggered by an EventHandler. Then the user would insert the values and press the Create Element button, which would trigger the makeElement() method that adds an element to the PeriodicTable

5. Choose location to load Game Data (example code) 
Clicking on a UI button within the SaveLoadDisplay class will call the load() method. This method then opens up a JavaFX filechooser that selects the save location.

6. Creating a New Affector
If the user clicks to create a new element, then the create an affector window will pop which is triggered by an EventHandler. Then the user would insert the values and then update the table with the Tower/Enemy Unit with the effect of the affect on each tower/enemy unit. When that table is updated, the individual towers/enemyunits will also be updated in the Health class of each Element with the addNewAffect(String name, double affect) and then that affector would be created with the makeElement() method to add to the PeriodicTable

7. Hide/display Periodic Table
A UI button opens up a new view for the PeriodicTable by calling its display() method. This shows existing Game Elements for the Developer to click on or view. 

8. Add new Terrain
If the user clicks to create a new Terrain, then the create an element window will pop which is triggered by an EventHandler. Then the user would insert the values and press the Create Terrain button along with uploading an image of the Terrain file, which would trigger the makeElement() method that adds that Terrain to the PeriodicTable.

9. Updating Old Element
Click onto the list of elements and press the one you want to edit, the create same create screen will pop up via the EventHandler and then you can change whatever you want and click the create button which will call the makeElement() method and add it to the PeriodicTable, when it gets to the PeriodicTable if the name is the same then it will be updated otherwise a new Element will be created.  
10. Creating terrain, path, or tower on the display
*When the user right clicks the main display, there will be a side menu with options to create terrain, path, or tower. if the user clicks to create a new element, then the create an element window will pop which is triggered by an EventHandler. Then the user would insert the values and press the Create Element button, which would trigger the makeElement() method that adds an element to the PeriodicTable. In this case, the x and y coordinates will already be filled up base on the location that the user clicked. 

11. Editing terrain, path, or tower on the display
*When the user right clicks the already created element on the main display, a side menu on the right will display the current stats. The user can enter the new data and the create button will call the makeElement() method will be updated. The display will also update the said element. 

12. Save the current settings and export the file
When the user press the save button, the current settings will be export it into an XML file and pass it onto game data.

13. Update the settings such that the user is able to see real time previews
When the user changes the values of the element, if the value is stored in the backend, the value will be changed and the front end will be updated based on the changed values. If the value is solely associated with frontend, then the preview display will update automatically.

###Game-Data
-------------
1. Game author wants to save a game they designed
> The game author clicks on save, in order to save the game they have created to a file. The authoring environment passes the data object to the GameData writing class, which serializes it and writes it to an XML file

2. Game player wants to load a game
> The game player clicks on open, and the GameData class reads in an XML file chosen by a selection dialog. The data is parsed into an object, and returned to the GameEngine class 

3. Game player wants to save game state in the middle of running
> The game player clicks to save the current state, and the GameEngine’s data object is sent to the GameData class, which writes it to an XML file of the player’s choosing.

4. Game player wants to save a user profile/preferences
> The game player clicks to load the current state, and the GameData class reads in an XML file of the user’s choosing. This file is then passed into the GameEngine, which resumes play

5. Error in parsing XML file when reading
> The GameData class will return null, which will indicate to the GameEngine that an error has occurred. The user will have the option to select a different file


