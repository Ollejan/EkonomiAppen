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
 * Fragment som visar och hanterar komponenter för att visa samtliga inkomster för den inloggade personen.
 * @author Jonas Dahlström 5/10-14
 */
public class ShowIncomeFragment extends Fragment {
    private TextView tvShowIncomeText;
    private EditText etIncomeFrom, etIncomeTo;
    private ListView lvIncome;
    private LinkedList<CustomListItem> customListItems = new LinkedList<CustomListItem>();
    private MenuController menuController;

    public ShowIncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_income, container, false);
        tvShowIncomeText = (TextView)view.findViewById(R.id.tvShowIncomeText);
        etIncomeFrom = (EditText)view.findViewById(R.id.etIncomeFrom);
        etIncomeFrom.setFocusable(false);
        etIncomeTo = (EditText)view.findViewById(R.id.etIncomeTo);
        etIncomeTo.setFocusable(false);
        lvIncome = (ListView)view.findViewById(R.id.lvIncome);
        etIncomeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuController.etDateClicked("showIncomeFrom");
            }
        });
        etIncomeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuController.etDateClicked("showIncomeTo");
            }
        });
        populateList();
        lvIncome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                menuController.listViewDetailClicked(customListItems.get(i), "income");
            }
        });
        setTvShowIncomeText();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        etIncomeFrom.setText("");
        etIncomeTo.setText("");
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    private void setTvShowIncomeText() {
        SharedPreferences prefs = getActivity().getSharedPreferences("myloginprefs", Context.MODE_PRIVATE);
        String firstName = prefs.getString("firstName", "");
        tvShowIncomeText.setText("Hej " + firstName + "! Här är dina samtliga inkomster. Klicka på någon av dem för mer information.");
    }

    public void setEtFrom(String date) {
        etIncomeFrom.setText(date);
    }

    public void setEtTo(String date) {
        etIncomeTo.setText(date);
    }

    public void populateList() {
        customListItems.clear();
        String category;
        LinkedList<Income> allIncome = menuController.getAllIncomeFromDatabase();
        for (int i = 0; i < allIncome.size(); i++) {
            category = allIncome.get(i).getCategory();
            customListItems.add(new CustomListItem(allIncome.get(i).getDate(), allIncome.get(i).getTitle(), allIncome.get(i).getAmount()+" kr", 0, category));
        }
        lvIncome.setAdapter(new CustomListAdapter(this.getActivity(), customListItems, "income"));
    }

    public void populateListBetweenDates() {
        customListItems.clear();
        String category;
        LinkedList<Income> allIncome = menuController.getIncomeBetweenDates(etIncomeFrom.getText().toString(), etIncomeTo.getText().toString());
        for (int i = 0; i < allIncome.size(); i++) {
            category = allIncome.get(i).getCategory();
            customListItems.add(new CustomListItem(allIncome.get(i).getDate(), allIncome.get(i).getTitle(), allIncome.get(i).getAmount()+" kr", 0, category));
        }
        lvIncome.setAdapter(new CustomListAdapter(this.getActivity(), customListItems, "income"));
    }
}
