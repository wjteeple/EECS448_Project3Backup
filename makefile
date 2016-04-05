JCC = javac
JFLAGS = -g

default: World.java WorldDriver.java

World.class: World.java
	$(JCC) $(JFLAGS) World.java

WorldDriver.class: WorldDriver.java
	$(JCC) $(JFLAGS) WorldDriver.java

clean:
	$(RM) *.class
