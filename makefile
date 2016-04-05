JCC = javac
JFLAGS = -g

default: Place.class World.class Town.class Actor.class PlayerActor.class EnemyActor.class Battle.class Skill.class Item.class Shop.class WorldDriver.class main.class

Place.class: Place.java
	$(JCC) $(JFLAGS) Place.java

World.class: World.java
	$(JCC) $(JFLAGS) World.java

Town.class: Town.java
	$(JCC) $(JFLAGS) Town.java

Actor.class: Actor.java
	$(JCC) $(JFLAGS) Actor.java

PlayerActor.class: PlayerActor.java
	$(JCC) $(JFLAGS) PlayerActor.java

EnemyActor.class: EnemyActor.java
	$(JCC) $(JFLAGS) EnemyActor.java

Battle.class: Battle.java
	$(JCC) $(JFLAGS) Battle.java

Skill.class: Skill.java
	$(JCC) $(JFLAGS) Skill.java

Item.class: Item.java
	$(JCC) $(JFLAGS) Item.java

Shop.class: Shop.java
	$(JCC) $(JFLAGS) Shop.java

WorldDriver.class: WorldDriver.java
	$(JCC) $(JFLAGS) WorldDriver.java

main.class: main.java
	$(JCC) $(JFLAGS) main.java

clean:
	$(RM) *.class
