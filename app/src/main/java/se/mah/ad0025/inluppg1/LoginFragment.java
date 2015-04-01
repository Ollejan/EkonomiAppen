package se.mah.ad0025.inluppg1;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;

/**
 * Fragment som visar och hanterar komponenter för att logga in med en befintlig användare.
 * @author Jonas Dahlström 5/10-14
 */
public class LoginFragment extends Fragment {
    private ListView lvLogin;
    private LinkedList<String> content = new LinkedList<String>();
    private LoginController loginController;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        lvLogin = (ListView)view.findViewById(R.id.lvLogin);
        lvLogin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                loginController.listItemClicked(content.get(i));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateList();
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    private void populateList() {
        String firstName, lastName;
        content.clear();
        LinkedList<User> allUsers = loginController.getAllUsersFromDatabase();
        for (int i = 0; i < allUsers.size(); i++) {
            firstName = allUsers.get(i).getFirstName();
            lastName = allUsers.get(i).getLastName();
            content.add(firstName + " " + lastName);
        }
        lvLogin.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, content));
    }
}
