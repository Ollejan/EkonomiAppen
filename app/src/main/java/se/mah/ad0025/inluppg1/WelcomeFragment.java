package se.mah.ad0025.inluppg1;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

/**
 * Fragment som visar och hanterar komponenter för att växla mellan inloggning och skapa ny användare.
 * Detta är fragmentet som visas först, när applikationen startas.
 * @author Jonas Dahlström 5/10-14
 */
public class WelcomeFragment extends Fragment {
    private RadioButton rbLogin;
    private LoginController loginController;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        rbLogin = (RadioButton)view.findViewById(R.id.rbLogin);
        RadioButton rbCreate = (RadioButton)view.findViewById(R.id.rbCreate);
        rbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginController.rbLoginClicked();
            }
        });

        rbCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginController.rbCreateClicked();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginController.rbLoginClicked();
        rbLogin.setChecked(true);
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
