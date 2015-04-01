package se.mah.ad0025.inluppg1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Fragment som visar och hanterar komponenter för att klicka sig in till undermenyer.
 * Visas även en text med nuvarande inloggade persons namn.
 * @author Jonas Dahlström 5/10-14
 */
public class MenuFragment extends Fragment {
    private TextView tvConnectedUser;
    private Button btnLogout, btnAddIncome, btnAddExpense, btnOverview, btnSeeIncome, btnSeeExpenses;
    private MenuController menuController;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        initializeComponents(view);
        setButtonListeners();

        SharedPreferences prefs = getActivity().getSharedPreferences("myloginprefs", Context.MODE_PRIVATE);
        setTvConnectedUser(prefs.getString("firstName", "") + " " + prefs.getString("lastName", ""));

        return view;
    }

    private void initializeComponents(View view) {
        tvConnectedUser = (TextView)view.findViewById(R.id.tvConnectedUser);
        btnLogout = (Button)view.findViewById(R.id.btnLogout);
        btnAddIncome = (Button)view.findViewById(R.id.btnAddIncome);
        btnAddExpense = (Button)view.findViewById(R.id.btnAddExpense);
        btnOverview = (Button)view.findViewById(R.id.btnOverview);
        btnSeeIncome = (Button)view.findViewById(R.id.btnSeeIncome);
        btnSeeExpenses = (Button)view.findViewById(R.id.btnSeeExpenses);
    }

    private void setButtonListeners() {
        btnLogout.setOnClickListener(new BL());
        btnAddIncome.setOnClickListener(new BL());
        btnAddExpense.setOnClickListener(new BL());
        btnOverview.setOnClickListener(new BL());
        btnSeeIncome.setOnClickListener(new BL());
        btnSeeExpenses.setOnClickListener(new BL());
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setTvConnectedUser(String user) {
        tvConnectedUser.setText("Inloggad som: " + user);
    }

    private class BL implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case (R.id.btnLogout): menuController.btnLogoutClicked(); break;
                case (R.id.btnAddIncome): menuController.btnAddIncomeClicked(); break;
                case (R.id.btnAddExpense): menuController.btnAddExpenseClicked(); break;
                case (R.id.btnOverview): menuController.btnShowOverviewClicked(); break;
                case (R.id.btnSeeIncome): menuController.btnShowIncomeClicked(); break;
                case (R.id.btnSeeExpenses): menuController.btnShowExpensesClicked(); break;
            }
        }
    }
}
