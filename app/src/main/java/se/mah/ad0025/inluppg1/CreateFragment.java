package se.mah.ad0025.inluppg1;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Fragment som visar och hanterar komponenter för att skapa en ny användare.
 * @author Jonas Dahlström 5/10-14
 */
public class CreateFragment extends Fragment {
    private EditText etFirstname, etLastname;
    private LoginController loginController;

    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        etFirstname = (EditText)view.findViewById(R.id.etFirstname);
        etLastname = (EditText)view.findViewById(R.id.etLastname);
        Button btnCreate = (Button)view.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginController.btnCreateClicked();
            }
        });
        return view;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public String getEtFirstname() {
        return etFirstname.getText().toString();
    }

    public String getEtLastname() {
        return etLastname.getText().toString();
    }
}
