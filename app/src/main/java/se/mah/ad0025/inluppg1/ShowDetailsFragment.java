package se.mah.ad0025.inluppg1;

import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment som visar och hanterar komponenter för att visa detaljerad information om en inkomst/utgift.
 * @author Jonas Dahlström 5/10-14
 */
public class ShowDetailsFragment extends Fragment {
    private MenuController menuController;
    private TextView tvDate, tvTitle, tvCategory, tvAmountOrExpenseText, tvAmountExpense;

    public ShowDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_details, container, false);
        initializeComponents(view);
        CustomListItem customListItem = menuController.getCustomListItem();
        String incomeOrExpense = menuController.getIncomeOrExpense();
        setTvDate(customListItem.getDate());
        setTvTitle(customListItem.getTitle());
        setTvCategory(customListItem.getCategory());
        setTvAmountExpense(customListItem.getSmallTitle());
        Resources res = getResources();
        if(incomeOrExpense.equals("income")) {
            setTvAmountOrExpenseText(res.getString(R.string.amountText));
        } else {
            setTvAmountOrExpenseText(res.getString(R.string.priceText));
        }
        return view;
    }

    private void initializeComponents(View view) {
        tvDate = (TextView)view.findViewById(R.id.tvDetailDate);
        tvTitle = (TextView)view.findViewById(R.id.tvDetailTitle);
        tvCategory = (TextView)view.findViewById(R.id.tvDetailCategory);
        tvAmountOrExpenseText = (TextView)view.findViewById(R.id.tvDetailAmountOrExpense);
        tvAmountExpense = (TextView)view.findViewById(R.id.tvDetailAmountExpense);
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setTvDate(String date) {
        tvDate.setText(date);
    }

    public void setTvTitle(String title) {
        tvTitle.setText(title);
    }

    public void setTvCategory(String category) {
        tvCategory.setText(category);
    }

    public void setTvAmountOrExpenseText(String amountOrExpenseText) {
        tvAmountOrExpenseText.setText(amountOrExpenseText);
    }

    public void setTvAmountExpense(String amountExpense) {
        tvAmountExpense.setText(amountExpense);
    }
}
