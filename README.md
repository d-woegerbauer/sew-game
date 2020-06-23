

# Debug the Land
This is a tower defense game-project written in 2020 for the software development lessons.

## What is Towerdefense?
Towerdefense is a real time strategy game. In which you have to defend your tower. It's a map with a fixed route and with an entry and exit, where your tower is positioned. Every round there spawn some hostiles, which means to destroy them and protect your tower. After every round you get some money, with which you can buy new allies and so on.

In our game the allies are the "debuggers" and the hostiles are the "errors". After every 20 round there will be a final enemy, which is stronger than the other enemies. This enemy is named after a not popular programming languages. If a certain number of errors reach your programm(tower) you lose. There are four different difficulties: easy, medium, hard, undebugable.

The number of lives your programm has scales with the difficulty of the game. Also the health of the enemies increases.

## Map Preview
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
|names|type|costs|damage|radius|
|--|--|--|--|--|
|byte|-|50|10|200|
|boolean|-|75|15|175|
|char|-|150|50|100|
|integer|-|120|25|150|


### Hostile types
Different hostiles with increasing damage and different speed

## Implementation
|  |  |
|--|--|
|Language|Java  |
|Framework|[libGDX](https://libgdx.badlogicgames.com/) |
|IDEA|IntelliJ |

## TODO
upgrades for all allies types + description, remove refund, all hostile types, special abilities 
