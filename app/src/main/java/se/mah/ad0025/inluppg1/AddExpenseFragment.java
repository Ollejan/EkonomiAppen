package se.mah.ad0025.inluppg1;

import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Fragment som visar och hanterar komponenter för att lägga till en ny utgift.
 * @author Jonas Dahlström 5/10-14
 */
public class AddExpenseFragment extends Fragment {
    private EditText etDate, etTitle, etPrice;
    private Spinner spinnerCategory;
    private MenuController menuController;

    public AddExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
        etDate = (EditText)view.findViewById(R.id.etDate);
        etTitle = (EditText)view.findViewById(R.id.etTitle);
        etPrice = (EditText)view.findViewById(R.id.etPrice);
        spinnerCategory = (Spinner)view.findViewById(R.id.spinnerCategory);
        Button btnSaveExpense = (Button)view.findViewById(R.id.btnSaveExpense);
        addSpinnerCategories();
        etDate.setFocusable(false);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuController.etDateClicked("addExpense");
            }
        });

        btnSaveExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuController.btnSaveExpenseClicked();
            }
        });

        return view;
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    private void addSpinnerCategories() {
        Resources res = getResources();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);
        spinnerAdapter.add(res.getString(R.string.categoryGroceries));
        spinnerAdapter.add(res.getString(R.string.categoryLeisure));
        spinnerAdapter.add(res.getString(R.string.categoryTravel));
        spinnerAdapter.add(res.getString(R.string.categoryLiving));
        spinnerAdapter.add(res.getString(R.string.categoryOther));
    }

    public void setEtDate(String date) {
        etDate.setText(date);
    }

    public String getEtDate() {
        return etDate.getText().toString();
    }

    public String getEtTitle() {
        return etTitle.getText().toString();
    }

    public String getSpinnerCategory() {
        return spinnerCategory.getSelectedItem().toString();
    }

    public String getEtPrice() {
        return etPrice.getText().toString();
    }

    public void resetAllFields() {
        etDate.setText("");
        etTitle.setText("");
        etPrice.setText("");
    }
}
