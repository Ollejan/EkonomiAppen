package se.mah.ad0025.inluppg1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.LinkedList;

/**
 * Fragment som visar och hanterar komponenter för att visa en översikt över användarens sammanfattade inkomster/utgifter.
 * @author Jonas Dahlström 5/10-14
 */
public class ShowOverviewFragment extends Fragment {
    private MenuController menuController;
    private TextView tvOverviewInfo, tvOverviewIncome, tvOverviewExpenses, tvOverviewTotal;
    private ImageView imageViewPieChart;

    public ShowOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_overview, container, false);
        initializeComponents(view);
        setTvOverviewInfo();
        setTotalIncomeAndExpenses();
        return view;
    }

    private void initializeComponents(View view) {
        tvOverviewInfo = (TextView)view.findViewById(R.id.tvOverviewInfo);
        tvOverviewIncome = (TextView)view.findViewById(R.id.tvOverviewIncome);
        tvOverviewExpenses = (TextView)view.findViewById(R.id.tvOverviewExpenses);
        tvOverviewTotal = (TextView)view.findViewById(R.id.tvOverviewTotal);
        imageViewPieChart = (ImageView)view.findViewById(R.id.imageViewPieChart);
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    private void setTvOverviewInfo() {
        SharedPreferences prefs = getActivity().getSharedPreferences("myloginprefs", Context.MODE_PRIVATE);
        String firstName = prefs.getString("firstName", "");
        tvOverviewInfo.setText("Hej " + firstName + "! Här är en översikt på dina totala inkomster och utgifter.");
    }

    private void setTotalIncomeAndExpenses() {
        float totalIncome = 0, totalExpenses = 0, total;
        LinkedList<Income> allIncome = menuController.getAllIncomeFromDatabase();
        LinkedList<Expense> allExpenses = menuController.getAllExpensesFromDatabase();
        NumberFormat currency = NumberFormat.getCurrencyInstance();

        for (Income anAllIncome : allIncome) {
            totalIncome += Float.valueOf(anAllIncome.getAmount());
        }

        for (Expense allExpense : allExpenses) {
            totalExpenses += Float.valueOf(allExpense.getPrice());
        }

        total = (totalIncome - totalExpenses);

        tvOverviewIncome.setText(currency.format(totalIncome));
        tvOverviewExpenses.setText(currency.format(totalExpenses));
        tvOverviewTotal.setText(currency.format(total));

        int[] colors = {Color.GREEN, Color.RED};
        float[] slices = {totalIncome, totalExpenses};
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(300, 300, conf);
        menuController.drawPieChart(bitmap, colors, slices);
        imageViewPieChart.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
    }
}
