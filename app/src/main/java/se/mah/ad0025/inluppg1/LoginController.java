package se.mah.ad0025.inluppg1;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.Toast;

import java.util.LinkedList;

/**
 * Controller-klass som sköter logiken angående inloggningen och skapandet av nya användare.
 * @author Jonas Dahlström 5/10-14
 */
public class LoginController {
    private WelcomeFragment welcomeFragment;
    private LoginFragment loginFragment;
    private CreateFragment createFragment;
    private MenuFragment menuFragment;
    private FragmentManager fragmentManager;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private DatabaseController databaseController;

    public LoginController(SharedPreferences prefs, FragmentManager fragmentManager, DatabaseController databaseController, WelcomeFragment welcomeFragment, LoginFragment loginFragment, CreateFragment createFragment, MenuFragment menuFragment) {
        this.prefs = prefs;
        this.fragmentManager = fragmentManager;
        this.databaseController = databaseController;
        this.welcomeFragment = welcomeFragment;
        this.loginFragment = loginFragment;
        this.createFragment = createFragment;
        this.menuFragment = menuFragment;

        this.welcomeFragment.setLoginController(this);
        this.loginFragment.setLoginController(this);
        this.createFragment.setLoginController(this);

        this.editor = prefs.edit();

        this.fragmentManager.beginTransaction().replace(R.id.masterLayout, this.welcomeFragment).commit();
        this.fragmentManager.beginTransaction().replace(R.id.frameLayoutWelcome, this.loginFragment).commit();
    }

    public void rbLoginClicked() {
        fragmentManager.beginTransaction().replace(R.id.frameLayoutWelcome, loginFragment).commit();
    }

    public void rbCreateClicked() {
        fragmentManager.beginTransaction().replace(R.id.frameLayoutWelcome, createFragment).commit();
    }

    public void btnCreateClicked() {
        String firstName = createFragment.getEtFirstname();
        String lastName = createFragment.getEtLastname();

        if(firstName.length() > 0 && lastName.length() > 0) {
            boolean userExist = false;
            LinkedList<User> allUsers = getAllUsersFromDatabase();
            for(int i = 0; i < allUsers.size(); i++) {
                if(allUsers.get(i).getFirstName().equals(firstName) && allUsers.get(i).getLastName().equals(lastName)) {
                    userExist = true;
                    break;
                }
            }
            if(userExist) {
                Toast.makeText(welcomeFragment.getActivity(), R.string.errorUserAlreadyExist, Toast.LENGTH_SHORT).show();
            } else {
                editor.putString("firstName", firstName);
                editor.putString("lastName", lastName);
                editor.commit();
                insertUserIntoDatabase(firstName, lastName);
                fragmentManager.beginTransaction().replace(R.id.masterLayout, menuFragment).commit();
                new MenuController(this, prefs, databaseController, fragmentManager, menuFragment);
            }
        } else if(firstName.length() > 0) {
            Toast.makeText(welcomeFragment.getActivity(), R.string.errorLastName, Toast.LENGTH_SHORT).show();
        } else if(lastName.length() > 0) {
            Toast.makeText(welcomeFragment.getActivity(), R.string.errorFirstName, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(welcomeFragment.getActivity(), R.string.errorFirstLastName, Toast.LENGTH_SHORT).show();
        }
    }

    public void insertUserIntoDatabase(String firstName, String lastName) {
        databaseController.open();
        databaseController.insertUser(firstName, lastName);
        databaseController.close();
    }

    public LinkedList<User> getAllUsersFromDatabase() {
        databaseController.open();
        Cursor c = databaseController.getAllUsers();
        LinkedList<User> allUsersFromDatabase = new LinkedList<User>();

        if(c.moveToFirst()){
            do{
                allUsersFromDatabase.add(new User(c.getString(1), c.getString(2)));
            }while(c.moveToNext());
        }

        c.close();
        databaseController.close();
        return allUsersFromDatabase;
    }

    public void listItemClicked(String item) {
        String[] parts = item.split(" ");
        editor.putString("firstName", parts[0]);
        editor.putString("lastName", parts[1]);
        editor.commit();
        fragmentManager.beginTransaction().replace(R.id.masterLayout, menuFragment).commit();
        new MenuController(this, prefs, databaseController, fragmentManager, menuFragment);
    }

    public void btnLogoutClicked() {
        fragmentManager.beginTransaction().remove(loginFragment).commit();
        fragmentManager.beginTransaction().replace(R.id.masterLayout, welcomeFragment).commit();
    }
}
