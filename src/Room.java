import java.util.HashMap;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room
{
    private String roomDescription;
    private HashMap<String, Room> exits;

    /**
 * Create a room described "description "Initially, it
 * has no exits. "description" is something like "a
 * kitchen" or "an open courtyard".
 */
public Room(String roomDescription)
{
    this.roomDescription = roomDescription;
    exits = new HashMap<String, Room>();
}
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction.
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }
    /**
     * Return the room that is reached if we go from this
     * room in direction "direction "If there is no room in
     * that direction, return null.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }
    /**
     * Return the description of the room (the one that was
     * defined in the constructor).
     */
    public String getRoomDescription()
    {
        return roomDescription;
    }
    /**
     * Return a description of the room’s exits,
     * for example, "Exits: north west".
     * @return A description of the available exits.
     */
    public String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    /**
     * Return a long description of this room, of the form:
     * You are in the kitchen.
     * Exits: north-west
     * @return A description of the room, including exits.
     */
    public String getLongDescription()
    {
        return "You are " + roomDescription + ".\n" + getExitString();
    }
}
