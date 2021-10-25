package usecases;

import java.util.HashMap;
import entities.User;

public class UserManager {

    private HashMap<String, User> users;

    /**
     * Constructs a UserManage with an empty hashmap of users
     */
    public UserManager(){
        this.users = new HashMap<String, User>();
    }

    /**
     * Constructs a UserManage with a pre-existing hashmap of username to User
     * @param users hashmap that maps a username to an instance of User
     */
    public UserManager(HashMap<String, User> users){
        this.users = users;
    }

    /**
     * Adds a user to the hasmap
     * @param name name of the User
     * @param username username of the User
     * @param password password of the User
     * @return returns true if the user has been added and returns false when a user with a same username already exists
     */
    public boolean addUser(String name, String username, String password){
        if (hasUser(username)){
            return false;
        }
        else {
            User new_user = new User(name, username, password);
            users.put(username, new_user);
            return true;
        }
    }

    /**
     * Deletes a user from the hashmap
     * @param username username of the user that needs to be deleted
     * @throws UserNotFoundException thrown when a user with a given username does not exist
     */
    public void deleteUser(String username) throws UserNotFoundException {
        if (hasUser(username)){
            users.remove(username);
        }
        else{
            throw new UserNotFoundException("User Not Found: " + username);
        }
    }

    /**
     * Adds a game that has been played to a User's statistics
     * @param username username for the a User
     * @param win true if the game played was a win, false if not
     * @throws UserNotFoundException thrown when a user with a given username does not exist
     */
    public void addGamesPlayed(String username, boolean win) throws UserNotFoundException {
        if (hasUser(username)){
            User user = users.get(username);
            user.addPlayed();
            if (win) { user.addWin(); }
        }
        else {
            throw new UserNotFoundException("User Not Found: " + username);
        }
    }

    /**
     * Returns the number of wins for a given User
     * @param username username of the User
     * @return the number of wins for a given User
     * @throws UserNotFoundException thrown when a user with a given username does not exist
     */
    public int getWins(String username) throws UserNotFoundException {
        if (hasUser(username)){
            return users.get(username).getGamesWon();
        }
        else{
            throw new UserNotFoundException("User Not Found: " + username);
        }
    }

    /**
     * Returns the number of games played for a given User
     * @param username username of the User
     * @return the number of games played for a given User
     * @throws UserNotFoundException thrown when a user with a given username does not exist
     */
    public int getGamesPlayed(String username) throws UserNotFoundException {
        if (hasUser(username)){
            return users.get(username).getGamesPlayed();
        }
        else{
            throw new UserNotFoundException("User Not Found: " + username);
        }
    }

    /**
     * Checks if a User with a given username already exists
     * @param username the username to check the existence of the user
     * @return true if the user exists, false if the user does not exist
     */
    public boolean hasUser(String username) {
        if (users.containsKey(username)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Checks if the password is correct for a given user
     * @param username username for a User
     * @param password password for a User
     * @return true if password is correct, false if not
     * @throws UserNotFoundException thrown when a user with a given username does not exist
     */
    public boolean login(String username, String password) throws UserNotFoundException {
        if (hasUser(username)){
            if (users.get(username).checkPassword(password)){
                return true;
            }
            else{
                return false;
            }
        }
        else {
            throw new UserNotFoundException("User Not Found: " + username);
        }
    }

    public class UserNotFoundException extends Exception{
        public UserNotFoundException(String errorMessage){
            super(errorMessage);
        }
    }

    public static void main(String[] args) {
        UserManager test = new UserManager();
        System.out.println(test.addUser("Test", "test", "password"));
        System.out.println(test.hasUser("test"));
    }
}
