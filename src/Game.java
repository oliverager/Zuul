import java.security.Key;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Item currentItem;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        createItems();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, cellar, library;
      
        // create the rooms
        outside = new Room  ("outside the main entrance of the university");
        theater = new Room  ("in a lecture theater");
        pub = new Room      ("in the campus pub");
        lab = new Room      ("in a computing lab");
        office = new Room   ("in the computing admin office");
        cellar = new Room   ("in the cellar");
        library = new Room  ("in the campus library");
        
        // initialise room exits
        outside.setExit     ("west", theater);
        outside.setExit     ("east", pub);
        outside.setExit     ("south", lab);
        theater.setExit     ("east", outside);
        theater.setExit     ("west", library);
        library.setExit     ("east",theater);
        pub.setExit         ("west", outside);
        lab.setExit         ("north", outside);
        lab.setExit         ("east", office);
        office.setExit      ("west", lab);
        office.setExit      ("down", cellar);
        cellar.setExit      ("up", office);

        currentRoom = outside;  // start game outside
    }
    private void createItems()
    {
        Item book, key;

        // create the items
        book = new Item("Book", "an old, dusty book bound in gray leather", 1200);
        key = new Item("Key","is a key", 100);

        // Spawn locations
        //book.setSpawn("on a shelf hidden away in the corner", library);
        //key.setSpawn("in a drawer", office);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    private void printLocationInfo()
    {
        System.out.println("You are " + currentRoom.getRoomDescription());
        System.out.println(currentRoom.getExitString());

    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println("\nWelcome to the World of Zuul!");
        System.out.println("Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help. \n ");

        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            System.out.println("No");
        }
        else if (commandWord.equals("eat")) {
            System.out.println("You have eaten now and you are not hungry any more.");
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.\n");
        System.out.println("Your command words are:");
        parser.showCommands();

    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
