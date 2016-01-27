# game
First project for CompSci 308 Spring 2016

Name: Austin Hua

Date started: 1/17/16

Date finished: 1/24/16

Hours worked: 20

Resources used: 
 - http://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development
 - StackOverflow

Main class file: 
Main.java

Data (non Java) files needed:
HumanTCell.png
PurpleHumanTCell.png
Nanorobot.png
SplashScreen.jpg
defaultmap.cellcraft
obstaclemap.cellcraft

How to play the game:
At the start screen, '1' selects the beginning/default mode with no obstacles and normal units and '2' selects a map that contains obstacles and gives you a nanorobot (super unit). Enter begins the game.
To select friendly units (blue), either click on the unit, or click and drag over a group of units.
To deselect units, press the escape key.
The aim of the game is to destroy all the enemy units.
When opposing units come into attacking range they will automatically stop and attack each other until only one remains.
The file defaultmap.cellcraft can be edited to change the starting configuration of the Map. The letter 'O' represents empty space, the letter 'A' represents a friendly Unit, and the letter 'Z' represents an enemy Unit.

Keys/Mouse input:
Mouse click - select a single unit / direct selected units to a location
Mouse drag - select a group of units
Escape - deselect all currently selected units

Cheat Keys:
I - All your friendly units are invincible and won't take damage

Known bugs:

The SECONDS\_PER\_FRAME parameter can't be decreased beyond a certain point, depending on the map complexity, or there won't be enough time to process actions before the next step() is called.

Extra features:
The design of the classes allows for a lot of flexibility in extending the MapElement class and its subclass Unit to allow you to design more interesting MapElements as well as Units that have different attributes, including health, speed, damage, and range, and also different behaviors through polymorphism, such as movement and attacking behavior. 

Impressions/Suggestions:
This was an interesting project which was a good introduction to using JavaFX.
