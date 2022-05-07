JFLAGS = -g
JC = javac
JVM = java
FILE =
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = Main.java Board.java Game.java javaPiece.java Pawn.java Square.java

MAIN = Main

default: classes

build:
	$(JC) Main.java	

run: build
	$(JVM) $(MAIN)

clean:
	$(RM) *.class
