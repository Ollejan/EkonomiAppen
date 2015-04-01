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
 * Fragment som visar och hanterar komponenter för att lägga till en ny inkomst.
 * @author Jonas Dahlström 5/10-14
 */
public class AddIncomeFragment extends Fragment {
    private EditText etDate, etTitle, etAmount;
    private Spinner spinnerCategory;
    private MenuController menuController;

    public AddIncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_income, container, false);
        etDate = (EditText)view.findViewById(R.id.etDate);
        etTitle = (EditText)view.findViewById(R.id.etTitle);
        etAmount = (EditText)view.findViewById(R.id.etAmount);
        spinnerCategory = (Spinner)view.findViewById(R.id.spinnerCategory);
        Button btnSaveIncome = (Button)view.findViewById(R.id.btnSaveIncome);
        addSpinnerCategories();
        etDate.setFocusable(false);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuController.etDateClicked("addIncome");
            }
        });

        btnSaveIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuController.btnSaveIncomeClicked();
            }
        });

        return view;
    }

    private void addSpinnerCategories() {
        Resources res = getResources();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);
        spinnerAdapter.add(res.getString(R.string.categoryPay));
        spinnerAdapter.add(res.getString(R.string.categoryOther));
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
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

    public String getEtAmount() {
        return etAmount.getText().toString();
    }

    public void resetAllFields() {
        etDate.setText("");
        etTitle.setText("");
        etAmount.setText("");
    }
}
