/**
 * @author : Will Teeple
 * @version : 0.3
 * @since 04/02/2016
 * Description : Town class. This class stores the current town map and contains
 * the methods for manipulating and displaying the players position
 */

 import java.util.Scanner;

 public class Town implements Place
 {
   //dimension and coordinate constants
   private int startLocX; //x-coordinate start location
   private int startLocY; //y-coordinate start location
   private int exitLocX; //x-coordinate exit location
   private int exitLocY; //y-coordinate exit location

   //constant variables for easier code reading
   private final char shop = 'S'; //character for shop
   private final char arena = 'A'; //character for arena
   private final char inn = 'I'; //character for inn
   private final char library = 'L'; //character for library
   private final char townBorder = 'E'; //town border for entry/exit

   //current location variable set
   private int curPosX = 0;
   private int curPosY = 0;
   private int prevPosX = 0;
   private int prevPosY = 0;
   private boolean stillInArea = true;

   //current map with player token
   private char[][] areaArrBase = new char[areaYDim][areaXDim];
   private char[][] curAreaArr = new char[areaYDim][areaXDim];

   //constructors

   /**
    * @param : (pre) None
    * @param : (post) Creates a new object of type Town with set parameters
    * @return : None
    */
   public Town(int x, int y, char[][] townBase)
   {
     startLocX = x;
     startLocY = y;
     curPosX = startLocX;
     curPosY = startLocY;

     areaArrBase = setBaseArea(townBase);
     curAreaArr = resetArea(curAreaArr, areaArrBase);
     curAreaArr[curPosY][curPosX] = player;
   }

   //get/set methods

   public char[][] getArea()
   {
     return curAreaArr;
   }

   public void setCurrentToPrevious()
   {
     curPosX = prevPosX;
     curPosY = prevPosY;
   }

   //checking methods

   public boolean inArea()
   {
     return stillInArea;
   }

   public boolean atExit(int x, int y)
   {
     if(x == exitLocX && y == exitLocY)
     {
       return true;
     }
     else
     {
       return false;
     }
   }

   public boolean menuInputCheck(String str, int min, int max, boolean moveFlag)
   {
     double selDouble; //convert input safely
     int sel; //convert double to int
     if(isNumeric(str)) //check if input was a number
     {
       selDouble = Double.parseDouble(str); //parse to double safely
       sel = (int) selDouble;
       if(sel < min || sel > max) //if outside the menu range, false
       {
         return false;
       }
       else
       {
         if (moveFlag)
         {
           return validMoveCheck(sel);
         }
         else
         {
           return true;
         }
       }
     }
     else
     {
       return false;
     }
   }

   public boolean validMoveCheck(int sel)
   {
     int tempX = 0, tempY = 0;

     switch(sel)
     {
       case 1: //up
         tempX = curPosX;
         tempY = curPosY - 1;
         break;
       case 2: //down
         tempX = curPosX;
         tempY = curPosY + 1;
         break;
       case 3: //left
         tempX = curPosX - 1;
         tempY = curPosY;
         break;
       case 4: //right
         tempX = curPosX + 1;
         tempY = curPosY;
         break;
     }
     if(tempY < 0 || tempY > (areaYDim - 1))
     {
       return false;
     }
     else if(tempX < 0 || tempX > (areaXDim - 1))
     {
       return false;
     }
     else
     {
       if(curAreaArr[tempY][tempX] == path ||
          curAreaArr[tempY][tempX] == townBorder ||
          curAreaArr[tempY][tempX] == shop ||
          curAreaArr[tempY][tempX] == arena ||
          curAreaArr[tempY][tempX] == inn ||
          curAreaArr[tempY][tempX] == library)
       {
         return true;
       }
       else
       {
         return false;
       }
     }
   }

   //interaction methods

   public void menuInteraction()
   {
     final String menu = "\n\nYou are currently at the coordinate (" + curPosX + ", " + curPosY + "). Which direction would you like to move?\n" +
                            "   1. Up\n" +
                            "   2. Down\n" +
                            "   3. Left\n" +
                            "   4. Right\n" +
                            "   Your Choice: ";
     String input; //input as string
     double selDouble; //menu selection as double
     int selection; //menu selection as integer
     Scanner in = new Scanner(System.in); //input scanner
     System.out.print(menu); //display menu options
     input = in.next(); //get user input

     while(!menuInputCheck(input, 1, 4, true)) //check if entry is valid, repeat input if not
     {
       System.out.print("Invalid menu selection, please choose an integer between 1 and 4 and a\ndestination along the path denoted by P.\nYour choice: ");
       input = in.next();
     }

     selDouble = Double.parseDouble(input); //safe parse
     selection = (int) selDouble; //set selection

     stillInArea = characterMove(selection); //store if still in town
     clearScreen();

     while(townInteraction(areaArrBase[curPosY][curPosX])){}
   }

   public boolean characterMove(int sel)
   {
     int tempX = 0, tempY = 0;
     switch(sel)
     {
       case 1: //up
         tempX = curPosX;
         tempY = curPosY - 1;
         break;
       case 2: //down
         tempX = curPosX;
         tempY = curPosY + 1;
         break;
       case 3: //left
         tempX = curPosX - 1;
         tempY = curPosY;
         break;
       case 4: //right
         tempX = curPosX + 1;
         tempY = curPosY;
         break;
     }

     prevPosX = curPosX;
     prevPosY = curPosY;
     curPosX = tempX;
     curPosY = tempY;

     curAreaArr = resetArea(curAreaArr, areaArrBase);
     curAreaArr[tempY][tempX] = player;
     if (areaArrBase[tempY][tempX] == townBorder)
     {
       return false;
     }
     else
     {
       return true;
     }
   }

   public boolean townInteraction(char spot)
   {
     boolean inBuilding = false;

     switch(spot)
     {
       case shop:
          inBuilding = shopMenu();
          break;
       case arena:
          inBuilding = arenaMenu();
          break;
       case inn:
          inBuilding = innMenu();
          break;
       case library:
          inBuilding = libraryMenu();
          break;
       default:
          inBuilding = false;
     }

     clearScreen();
     return inBuilding;
   }

   public boolean shopMenu()
   {
     final String menu = "\nWelcome to the Shop!\n" +
                            "   1. Buy\n" +
                            "   2. Sell\n" +
                            "   3. Leave\n" +
                            "   Your Choice: ";
     String input; //input as string
     double selDouble; //menu selection as double
     int selection; //menu selection as integer
     Scanner in = new Scanner(System.in); //input scanner
     System.out.print(menu); //display menu options
     input = in.next(); //get user input

     boolean inStore = true;

     while(!menuInputCheck(input, 1, 3, false)) //check if entry is valid, repeat input if not
     {
       System.out.print("Invalid menu selection, please choose an integer between 1 and 3.\nYour choice: ");
       input = in.next();
     }

     selDouble = Double.parseDouble(input); //safe parse
     selection = (int) selDouble; //set selection

     switch(selection)
     {
       case 1:
          System.out.println("\n\nInsert interaction here:\n\n");
          break;
       case 2:
          System.out.println("\n\nInsert interaction here:\n\n");
          break;
       case 3:
          inStore = false;
          setCurrentToPrevious();
          curAreaArr = resetArea(curAreaArr, areaArrBase);
          curAreaArr[curPosY][curPosX] = player;
          break;
     }

     return inStore;
   }

   public boolean arenaMenu()
   {
     final String menu = "\nWelcome to the Arena!\n" +
                            "   1. Battle\n" +
                            "   2. Leave\n" +
                            "   Your Choice: ";
     String input; //input as string
     double selDouble; //menu selection as double
     int selection; //menu selection as integer
     Scanner in = new Scanner(System.in); //input scanner
     System.out.print(menu); //display menu options
     input = in.next(); //get user input

     boolean inArena = true;

     while(!menuInputCheck(input, 1, 2, false)) //check if entry is valid, repeat input if not
     {
       System.out.print("Invalid menu selection, please choose an integer between 1 and 2.\nYour choice: ");
       input = in.next();
     }

     selDouble = Double.parseDouble(input); //safe parse
     selection = (int) selDouble; //set selection

     switch(selection)
     {
       case 1:
          System.out.println("\n\nInsert interaction here:\n\n");
          break;
       case 2:
          inArena = false;
          setCurrentToPrevious();
          curAreaArr = resetArea(curAreaArr, areaArrBase);
          curAreaArr[curPosY][curPosX] = player;
          break;
     }

     return inArena;
   }

   public boolean innMenu()
   {
     final String menu = "\nWelcome to the Inn!\n" +
                            "   1. Rest\n" +
                            "   2. Leave\n" +
                            "   Your Choice: ";
     String input; //input as string
     double selDouble; //menu selection as double
     int selection; //menu selection as integer
     Scanner in = new Scanner(System.in); //input scanner
     System.out.print(menu); //display menu options
     input = in.next(); //get user input

     boolean inInn = true;

     while(!menuInputCheck(input, 1, 2, false)) //check if entry is valid, repeat input if not
     {
       System.out.print("Invalid menu selection, please choose an integer between 1 and 2.\nYour choice: ");
       input = in.next();
     }

     selDouble = Double.parseDouble(input); //safe parse
     selection = (int) selDouble; //set selection

     switch(selection)
     {
       case 1:
          System.out.println("\n\nInsert interaction here:\n\n");
          break;
       case 2:
          inInn = false;
          setCurrentToPrevious();
          curAreaArr = resetArea(curAreaArr, areaArrBase);
          curAreaArr[curPosY][curPosX] = player;
          break;
     }

     return inInn;
   }

   public boolean libraryMenu()
   {
     final String menu = "\nWelcome to the Library!\n" +
                            "   1. Learn Skill\n" +
                            "   2. Leave\n" +
                            "   Your Choice: ";
     String input; //input as string
     double selDouble; //menu selection as double
     int selection; //menu selection as integer
     Scanner in = new Scanner(System.in); //input scanner
     System.out.print(menu); //display menu options
     input = in.next(); //get user input

     boolean inLibrary = true;

     while(!menuInputCheck(input, 1, 2, false)) //check if entry is valid, repeat input if not
     {
       System.out.print("Invalid menu selection, please choose an integer between 1 and 2.\nYour choice: ");
       input = in.next();
     }

     selDouble = Double.parseDouble(input); //safe parse
     selection = (int) selDouble; //set selection

     switch(selection)
     {
       case 1:
          System.out.println("\n\nInsert interaction here:\n\n");
          break;
       case 2:
          inLibrary = false;
          setCurrentToPrevious();
          curAreaArr = resetArea(curAreaArr, areaArrBase);
          curAreaArr[curPosY][curPosX] = player;
          break;
     }

     return inLibrary;
   }
 }
