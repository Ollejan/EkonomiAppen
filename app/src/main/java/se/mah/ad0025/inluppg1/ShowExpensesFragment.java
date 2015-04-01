package se.mah.ad0025.inluppg1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Fragment som visar och hanterar komponenter för att visa samtliga utgifter för den inloggade personen.
 * @author Jonas Dahlström 5/10-14
 */
public class ShowExpensesFragment extends Fragment {
    private TextView tvShowExpensesText;
    private EditText etExpenseFrom, etExpenseTo;
    private ListView lvExpenses;
    private LinkedList<CustomListItem> customListItems = new LinkedList<CustomListItem>();
    private MenuController menuController;

    public ShowExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_expenses, container, false);
        tvShowExpensesText = (TextView)view.findViewById(R.id.tvShowExpensesText);
        etExpenseFrom = (EditText)view.findViewById(R.id.etExpenseFrom);
        etExpenseFrom.setFocusable(false);
        etExpenseTo = (EditText)view.findViewById(R.id.etExpenseTo);
        etExpenseTo.setFocusable(false);
        lvExpenses = (ListView)view.findViewById(R.id.lvExpenses);
        etExpenseFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuController.etDateClicked("showExpenseFrom");
            }
        });
        etExpenseTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuController.etDateClicked("showExpenseTo");
            }
        });
        populateList();
        lvExpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                menuController.listViewDetailClicked(customListItems.get(i), "expense");
            }
        });
        setTvShowExpensesText();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        etExpenseFrom.setText("");
        etExpenseTo.setText("");
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    private void setTvShowExpensesText() {
        SharedPreferences prefs = getActivity().getSharedPreferences("myloginprefs", Context.MODE_PRIVATE);
        String firstName = prefs.getString("firstName", "");
        tvShowExpensesText.setText("Hej " + firstName + "! Här är dina samtliga utgifter. Klicka på någon av dem för mer information.");
    }

    public void setEtFrom(String date) {
        etExpenseFrom.setText(date);
    }

    public void setEtTo(String date) {
        etExpenseTo.setText(date);
    }

    public void populateList() {
        customListItems.clear();
        String category;
        int image;
        LinkedList<Expense> allExpenses = menuController.getAllExpensesFromDatabase();
        for (int i = 0; i < allExpenses.size(); i++) {
            category = allExpenses.get(i).getCategory();
            if(category.equals("Livsmedel")) {
                image = R.drawable.groceries;
            } else if(category.equals("Fritid")) {
                image = R.drawable.leisure;
            } else if(category.equals("Resor")) {
                image = R.drawable.travel;
            } else if(category.equals("Boende")) {
                image = R.drawable.living;
            } else {
                image = R.drawable.other;
            }
            customListItems.add(new CustomListItem(allExpenses.get(i).getDate(), allExpenses.get(i).getTitle(), allExpenses.get(i).getPrice()+" kr", image, category));
        }
        lvExpenses.setAdapter(new CustomListAdapter(this.getActivity(), customListItems, "expense"));
    }

    public void populateListBetweenDates() {
        customListItems.clear();
        String category;
        int image;
        LinkedList<Expense> specificExpenses = menuController.getExpensesBetweenDates(etExpenseFrom.getText().toString(), etExpenseTo.getText().toString());
        for (int i = 0; i < specificExpenses.size(); i++) {
            category = specificExpenses.get(i).getCategory();
            if(category.equals("Livsmedel")) {
                image = R.drawable.groceries;
            } else if(category.equals("Fritid")) {
                image = R.drawable.leisure;
            } else if(category.equals("Resor")) {
                image = R.drawable.travel;
            } else if(category.equals("Boende")) {
                image = R.drawable.living;
            } else {
                image = R.drawable.other;
            }
            customListItems.add(new CustomListItem(specificExpenses.get(i).getDate(), specificExpenses.get(i).getTitle(), specificExpenses.get(i).getPrice()+" kr", image, category));
        }
        lvExpenses.setAdapter(new CustomListAdapter(this.getActivity(), customListItems, "expense"));
    }
}
