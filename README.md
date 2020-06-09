
# Debug the Land
This is a tower defense game-project written in 2020 for the software development lessons.
## What is Tower defense?
Towerdefense is a real time strategy game. In which you have to defend your tower. It's a map with a fixed route and with an entry and exit, where your tower is positioned. Every round there spawn some hostiles, which means to destroy them and protect your tower. After every round you get some money, with which you can buy new allies and so on.

In our game the allies are the "debuggers" and the hostiles are the "errors". After every 20 round there will be a final enemy, which is stronger than the other enemies. This enemy is named after a not popular programming languages. If a certain number of errors reach your programm(tower) you lose. There are four different difficulties: easy, medium, hard, undebugable.

The number of lives your programm has scales with the difficulty of the game. Also the health of the enemies increases.
The number of lives your program has scales with the difficulty of the game. Also the health of the enemies increases.

## Map preview
![First preview map](https://raw.githubusercontent.com/dav-prog/sew-game/master/maps/finished%20maps/Ice%20Map%20-%20Test/map-test.png)
## Details
|subject|details|
|--|--|
|Game type  |tower-defense game, round-based|
|Allies types  |coming soon|
|Hostile types  |coming soon|
|coins  |per eliminated enemy, start-coins: 180|
|design  |different 2D maps, fixed route where enemies run|
### Allies types
Here are the different types of allies listed
|names|type|costs|ability|weapon|damage|radius|delay in ms|image|
|--|--|--|--|--|--|--|--|--|
|byte|-|50|-|arrow|50|-  |500|-|
|boolean|-|75|-||110|-  |1000|-  |
|char|-|150|-20%|slows down enemy speed|50|-  |200|-  |
|integer|-|120||long range arrow|80  |-  |500|-  |
|short|-|225|short range|pumpgun  |300  |very short  |500  |-  |
|float|-|300|poison damage|highspeed   |50|-  |750  |-  |
## Implementation
|  |  |
|--|--|
|Language|Java  |
|Framework|[libGDX](https://libgdx.badlogicgames.com/) |
|IDEA|IntelliJ |
