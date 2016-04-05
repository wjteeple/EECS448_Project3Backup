/**
 * @author : Will Teeple
 * @version : 0.3
 * @since 03/30/2016
 * Description : Driver file to test the World Class
 */

import java.io.*;

public class WorldDriver
{
  public static void main(String[] args)
  {
    //file names
    String worldMapFile = "worldLayout.txt";
    String villagevilleFile = "villageville.txt";
    String awesometownFile = "awesometown.txt";

    //create area arrays
    char[][] worldMap = populateArea(worldMapFile);
    char[][] villageville = populateArea(villagevilleFile);
    char[][] awesometown = populateArea(awesometownFile);

    //create area objects
    World world = new World(0, 3, worldMap);
    Town villagevilleTown = new Town(0, 3, villageville);
    Town awesometownTown = new Town(0, 3, awesometown);

    //town array coordinates (x,y)
    int[] vvCoor = {6, 1};
    int[] atCoor = {6, 5};

    boolean gameIsActive = true;

    //begin game Welcome
    System.out.println("Welcome to Generic RPG!\n");

    while(gameIsActive)
    {
      //begin game with clear terminal screen
      clearScreen();

      //run world
      do
      {
        world.displayArea(world.getArea());
        world.menuInteraction();
        gameIsActive = !world.getGameExit();
      } while(world.inArea() && gameIsActive);

      //visit VillageVille
      if (world.getCurrentLoc()[0] == vvCoor[0] && world.getCurrentLoc()[1] == vvCoor[1])
      {
        do
        {
          villagevilleTown.displayArea(villagevilleTown.getArea());
          villagevilleTown.menuInteraction();
        } while (villagevilleTown.inArea());
      }
      //visit AwesomeTown
      else if (world.getCurrentLoc()[0] == atCoor[0] && world.getCurrentLoc()[1] == atCoor[1])
      do
      {
        awesometownTown.displayArea(awesometownTown.getArea());
        awesometownTown.menuInteraction();
      } while (awesometownTown.inArea());

      //return to world, return to previous coordinates
      world.setCurrentToPrevious();
    }

    System.out.println("\n\nYou have exited the game. Have a good day!");
  }

  public static char[][] populateArea(String fileName)
  {
    //file i/o
    FileReader in;
    BufferedReader inbr;
    char[][] tempWorld = new char[7][7];

    try
    {
      in = new FileReader(fileName);
      inbr = new BufferedReader(in);
      int a = 0;

      for (int i = 0; i < 7; i++)
      {
        for (int j = 0; j < 7; j++)
        {
          if ((a = inbr.read()) != -1)
          {
            char b = (char) a;
            if (b != '\r') // '\r' will be followed by '\n'
            {
              tempWorld[i][j] = b;
            }
            else
            {
              b = (char) inbr.read(); //skip '\r'
              b = (char) inbr.read(); //skip '\n'
              tempWorld[i][j] = b; //grab first character on new line
            }
          }
        }
      }

      //close reader
      in.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    return tempWorld;
  }

  public static void clearScreen() //assisted code from StackOverflow, ---->
  //url: http://stackoverflow.com/questions/4888362/commands-in-java-to-clear-the-screen
  {
    final String ANSI_CLS = "\u001b[2J";
    final String ANSI_HOME = "\u001b[H";
    System.out.print(ANSI_CLS + ANSI_HOME);
    System.out.flush();
  }
}
