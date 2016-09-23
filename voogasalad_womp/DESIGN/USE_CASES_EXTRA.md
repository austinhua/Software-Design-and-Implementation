1. Detecting Collisions and applying affectors from projectiles to enemies
  - EngineWorkspace: calls to `resolveEnemyCollisions(myProjectiles, myTerrains);` in the `CollisionDetector.java` class.
  - CollisionDetector: loops through each of the enemies and calls from the `CollisionDetector.java` class.
    - `updateEnemies(t, myProjectiles)`
    - `terrainHandling(t, myTerrains)`
  - CollisionDetector: Performs collision detection, and:
    - for each collided enemy calls:
      - `unit.addAffectors(myProjectiles.get(i).getAffectorsToApply());`
    - for each collided projectile calls:
      - `setHasCollided(true);`
      - `setElapsedTimeToDeath();`
  - During the next tick, the affector is applied ot the enemy because it is in the list of affectors that get applied.
  - During the next tick, the projectiles are removed from the workspace because they have already collided.

2. Player clicks on a position, and the EngineWorkspace adds a specific type of tower to that position
  - GameView: detects a click event, makes a call to `playerEngineInterface.addTower(e.getX(), e.getY(), 0);`
  - EngineWorkspace: `addTower(double x, double y, int towerTypeIndex)` references the tower types, and creates a new tower in the list of towers for the level.
  - During the next tick, GameView makes a call to `placeUnits(List<Unit> list, List<ImageViewPicker> imageViews)`, which reads all active units in the workspace, and plots them on screen, which includes the new tower.

3. Player pauses the game
  - GameView: calls `myScene.setOnKeyPressed(e -> setUpKeyPressed(e.getCode()));`
  - GameView: `setUpKeyPressed(KeyCode code)` checks to see if `code` is the space key, which then calls `toggleGame();`
  - GameView: `toggleGame()` stops the timer for the animation, which pauses the game.

4. A Path is created for enemy units to follow
  - Path constructor is called: `Path p = new Path(String name);`
  - Points are added to the path: `p.addPosition(Position append)`
  - Enemy sets path: `enemy.setPath(p);`

5. An enemy follows a path 
  - Enemy: makes a call to `update();` which calls `myAffectors.forEach(a -> a.apply(this));` thus applying a movement affector
  - Affector obtains the path for the Enemy by calling `Path myPath = u.getProperties().getPath();`
  - The next position of the enemy is set to `Position next = myPath.getNextPosition(curr);`, which moves it along the path
  - `myPath.getNextPosition(curr);` obtains the next pixel-level position that the unit would move to in a single game tick
  
6. A wave of enemies is created on the back-end (Game Engine).
  - EnemyFactory: multiple calls are made to `createSpecifiedEnemy(String name, String behavior, String property) `
  - Wave: a new Wave object is constructed using `Wave(String name, int time)`
  - Wave: enemies are added to the wave by using ` addEnemy(Enemy e, int spawnTime)` repeatedly
  
7. A level is composed from several different waves
  - Level: constructor is used to generate a level object `Level (String name, Wave first, int myLives)`
  - Level: waves are added by making repeated calls to `addWave (Wave newWave) `
  
8. Engine detects when a wave has finished, and pauses in order to let the user make purchases
  - EngineWorkspace: makes consecutive calls to `updateElements()`
  - EngineWorkspace: `updateElements()` makes checks to `myCurrentLevel.getCurrentWave().isFinished()` which checks if wave has finished
  - If wave has finished, EngineWorkspace calls 
    - `clearProjectiles();`
    - `pause = true;`
  - During next call to `update()`, EngineWorkspace understands game is paused, and blocks calculations until next level or wave is played 
  
9. An enemy traverses its path, and decrements health
  - EngineWorkspace: makes a call to `updateElements()`
  - EngineWorkspace: `updateElements()` makes a call to `updateLives()`
  - EngineWorkspace: `updateLives()` checks `isUnitAtLastPosition(myEnemys.get(i))`
  - if enemy is at the last position in the path, `myLives` is decremented.

10. Selling a tower returns money to the user (on the backend)
  - EngineWorkspace: makes a call to selects a specific tower and calls `Tower.sellTower()`, which removes the tower from `myTowers`
  - EngineWorkspace then makes a call to `Store.refundCost(Tower.getSellCost())`
  - `Store` then increments users money value, which can be used for future purchases with `buyTower(int towerType)` method

1. Auth Environment can drag in a Tower

  > Use the DragDelegate.java class we've created for Player, set possible Towers as sources, and locations on the Map as targets (using the DragDelegate's .setSource and .setTarget methods)

2. Auth Environment can load in a previous Game 

  > Open FileChooser to locate corresponding XML file. Update local IGameData instance by deserializing the XML. Tower Pickers and other Game Element containers will be updated by setting their contents from GameData. 

3. Auth Environment can edit placed Tower

  > Take target locations on Map and add mouse listener that would return the location's Unit instance. Then the Unit editor window is opened and has its fields populated with the selected Unit instance using setter methods.

4. Auth Environment can create multiple paths

  > Click a 'Place Path' button to begin selecting Path positions on the Map. Each subsequent click adds another Position to a growing List of Positions. Clicking a 'Save Path' will add this List of Positions to the IGameData instance. 

5. Auth Environment can view old (already placed) paths

  > The Map will be updated by calling the IGameData's method that returns a Collection of previous Paths. These Paths will then be displayed on the Map in an alternate color scheme (to be distinct from the currently placed Path).

6. Auth Environment can delete old Paths

  > A side panel of active Paths will be updated each time a Path is saved. Each item in that panel will have an event handler that will remove the given Path from the IGameData with a removePath method call.

7. Auth Environment can move placed Units

  > Map tiles can be both sources and targets (as setup with the DragDelegate using .setSource() and .setTarget() ). 

8. Auth Environment can change Map size
  > A Map customization Menu takes in doubles for .setMapWidth and .setMapHeight (either in pixels or grid count). This is then used to create the Map size. 

9. Auth Environment can change Path colors
  > Select a Path in the Path menu (mouse action), opens up a Color Picker, selection of a Color then calls setColor and update methods on the Path to update its color. 

10. Auth Environment can change CSS stylesheet
  > Appearance menu has a 'Change Skin' button that opens up a FileChooser. The File returned by the FileChooser (filtered to .css files) will then be used to update the View window's scene with a .setStylesheet() method call.

11. Auth Environment can add in AI 
  > When Units have the choice of taking multiple paths, instead of random selection, they can use AI to make their choice. In the Unit customization window, there will be a button with event handler that takes in the input AI name (ex. 'distanceHeuristicAI') and will instantiate an instance of that class using Reflection. The created Unit will then have its AI componenet set to that. 

12. Auth Environment can rename the Game
  > In a Settings menu item, an event handler for 'Rename Game' button will open up a new Window with a prompt for the Game Name. Upon completion, an event handler will call IGameData's .getSettings() method and then .setName() to update.

13. Auth Environment can clear/reset the current Game
  > The current IGameData object is replaced with a new default instance. All frontend views and editor windows that are populated from this central Game object will now be given the corresponding default version of their storage object. 

14. The authoring environment prompts the user to create a define a Tower.
  * `ElementMenu` class: Calls `defineTowerModel(TowerBuildingBlock block)` method in the `TowerFactory` class.
  * `TowerFactory` class: The above method takes in the user's custom inputs via the TowerBuildingBlock and returns a "template" Tower object back to the authoring environment. 

15. The authoring environment prompts the user to create a define an Enemy.
  * `ElementMenu` class: Calls `defineEnemyModel(EnemyBuildingBlock block)` method in the `EnemyFactory` class.
  * `EnemyFactory` class: The above method takes in the user's custom inputs via the EnemyBuildingBlock and returns a "template" Enemy object back to the authoring environment. 

16. The authoring environment prompts the user to create a define an Terrain.
  * `ElementMenu` class: Calls `defineTerrainModel(TerrainBuildingBlock block)` method in the `TerrainFactory` class.
  * `TerrainFactory` class: The above method takes in the user's custom inputs via the TerrainBuildingBlock and returns a "template" Terrain object back to the authoring environment. 

17. After creating a "template" tower, the user drags the tower onto the game    map. 
 *  `ElementPicker` class: Calls `createTower(Tower template, Position p, Bounds b)` method in the `TowerFactory` class.
 * `TowerFactory` class: The above method takes in the Tower template the user has already identified as well as Position and Bounds objects to actually define where the Tower is physically on the game map. The method then returns an "initialized" tower. 

18. After creating a "template" enemy, the user determines how many enemies to add to a wave.
  * `ElementPicker` class: Calls `createEnemy(Enemy e, Wave w)` method in the `EnemyFactory` class.
  * `EnemyFactory` class: The above method takes in the Enemy template that the user has already identified as well as a Wave object associated to the wave that the enemy will spawn on. The void method will then add that enemy to the wave. 

### Game Player Use Cases

1. Integrate Save
  * OptionsMenu: call saveGame() when the event handler is triggered.
  * We will use XStream to save the current game state of the game engine as an XML file.
    * This will be done by calling EngineWorkspace.saveGame().

2. Integrate Load
  * OptionsMenu: call loadGame() when the event handler is triggered.
  * We will use XStream to parse the selected XML file.
  * Once the information is parsed, the information will be sent to the EngineWorkspace class and an instance of the selected game will be created.
  * The GameView.playGame() method will then be called and the game will begin.

3. Integrate Restart
  * OptionsMenu: call restartGame() when the event handler is triggered.
  * We need to add a method within in EngineWorkspace called restartGame() that restarts the currently loaded game.
  * With this new method, we can call EngineWorkspace.restartGame() in the OptionsMenu.restartGame() method.

4. Display High Score
  * HighScoreDisplay: update the display with the updateNode() method with the current high score.
  * We need to create the backend for the game player that stores the game information. 
  * This information would be stored within the GameDataSource class.

5. Heads Up Display
  * Create new IGUIObject HeadsUpDisplay.
    * HeadsUpDisplay will contain a Label that is constantly updated every time the EngineWorkspace is updated.
  * We can potentially work with other groups who are working on the Heads Up Display game utility.
  * The information required for the HeadsUpDisplay will be stored within GameDataSource.

6. Set Preferences For Different Games
  * Using Reflection, we can add different components to the Configuration Panel by specifying the required components in a property file.
  * The preferences that the player is able to change is set by the game designer.

7. Integrate Cheat Codes
  * The cheat codes will be set up by the user when designing the game, and will be added to the game enginer from the game data.
  * The game player will have a text field of some kind that allows the player to enter a string to activate the cheat codes.
  * Add Class to game player: CheatCodeTextField that implements IGUIObject.
    * Additionally, contains method checkCheatCodes() that compares inputted string with backend codes.

8. Integrate Social Center
  * We would work with other groups on the Social Center utility.
  * Once the Social Center utility is created, we will use the Social Center API to integrate it into the game player.
  * We would need to create a new IGUIObject class called SocialCenter that uses the Social Center API.
  * The social center could be used to rate games created by users, such as the system in Super Mario Maker.

9. Add CSS to the player
  * We already started on adding CSS to the player to make the game more appealing, but we have not spent much time on this case.
  * The individual UI components will need to add Styles from CSS files.

10. Drag and Drop for Towers 
  * We need to create a TowerPalette class that implements IGUIObject.
  * When the user clicks on a tower within the TowerPalette, an EventHandler is triggered and the user will be able to drag the tower onto the Pane root of the player.


