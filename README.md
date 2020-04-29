
# Debug the Land
This is a tower defense game-project written in 2020 for the software development lessons.

## What is Towerdefense?
Towerdefense is a real time strategy game. In which you have to defend your tower. It's a map with a fixed route and with an entry and exit, where your tower is positioned. Every round there spawn some hostiles, which means to destroy them and protect your tower. After every round you get some money, with which you can buy new allies and so on.

In our game the allies are the "debuggers" and the hostiles are the "errors". After every 20 round there will be a final enemy, which is stronger than the other enemies. This enemy is named after a not popular programming languages. If a certain number of errors reach your programm(tower) you lose. There are four different difficulties: easy, medium, hard, undebugable.

The number of lives your programm has scales with the difficulty of the game. Also the health of the enemies increases.

## Details
|subject  |details  |
|--|--|
|Game type  |tower-defense game, round-based|
|Allies types  |coming soon|
|Hostile types  |coming soon|
|coins  |per eliminated enemy, start-coins: 180|
|design  |different 2D maps, fixed route where enemies run|

### Allies types
Here are the different types of allies listed
|names|type|costs|ability|weapon|damage|radius|delay in ms|image|
|--|--|--|--|--|--|--|--|--|--|
|byte|-|50|-|arrow|50|-  |500|-  |
|boolean|-  |75|-|shotgun|110|-  |1000|-  |
|char|-  |150|-20%|slows down enemy speed|50|-  |200|-  |
|integer|-  |120|sees private exceptions|long range arrow|80  |-  |500|-  |
|short|-  |225|short range|pumpgun  |300  |very short  |500  |-  |
|float|-  |300|poison damage|highspeed   |50|-  |750  |-  |
|long|-  |500|morter|more range - more damage  |1000/number of enemies in range|infinite|3000|-|
more coming soon

ideas: string, arrays, 5d array


## Implementation
|  |  |
|--|--|
|Language|Java  |
|Framework|[libGDX](https://libgdx.badlogicgames.com/) |
|IDEA|IntelliJ |
