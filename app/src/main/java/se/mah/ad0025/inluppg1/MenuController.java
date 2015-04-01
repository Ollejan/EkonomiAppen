package se.mah.ad0025.inluppg1;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Controller-klass som sköter logiken angående allt som sker i huvudmenyn och dess undermenyer.
 * @author Jonas Dahlström 5/10-14
 */
public class MenuController {
    private LoginController loginController;
    private SharedPreferences prefs;
    private DatabaseController databaseController;
    private FragmentManager fragmentManager;
    private MenuFragment menuFragment;
    private AddIncomeFragment addIncomeFragment;
    private AddExpenseFragment addExpenseFragment;
    private ShowIncomeFragment showIncomeFragment;
    private ShowExpensesFragment showExpensesFragment;
    private ShowDetailsFragment showDetailsFragment;
    private ShowOverviewFragment showOverviewFragment;
    private Calendar myCalendar;
    private CustomListItem customListItem;
    private String incomeOrExpense;

    public MenuController(LoginController loginController, SharedPreferences prefs, DatabaseController databaseController, FragmentManager fragmentManager, MenuFragment menuFragment) {
        this.loginController = loginController;
        this.prefs = prefs;
        this.databaseController = databaseController;
        this.fragmentManager = fragmentManager;
        this.menuFragment = menuFragment;

        addIncomeFragment = new AddIncomeFragment();
        addExpenseFragment = new AddExpenseFragment();
        showIncomeFragment = new ShowIncomeFragment();
        showExpensesFragment = new ShowExpensesFragment();
        showDetailsFragment = new ShowDetailsFragment();
        showOverviewFragment = new ShowOverviewFragment();

        setMenuControllers();
    }

    private void setMenuControllers() {
        this.menuFragment.setMenuController(this);
        this.addIncomeFragment.setMenuController(this);
        this.addExpenseFragment.setMenuController(this);
        this.showIncomeFragment.setMenuController(this);
        this.showExpensesFragment.setMenuController(this);
        this.showDetailsFragment.setMenuController(this);
        this.showOverviewFragment.setMenuController(this);
    }

    public void btnLogoutClicked() {
        loginController.btnLogoutClicked();
    }

    public void btnAddIncomeClicked() {
        fragmentManager.beginTransaction().replace(R.id.masterLayout, addIncomeFragment).addToBackStack(null).commit();
    }

    public void btnAddExpenseClicked() {
        fragmentManager.beginTransaction().replace(R.id.masterLayout, addExpenseFragment).addToBackStack(null).commit();
    }

    public void etDateClicked(final String incomeOrExpense) {
        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText(incomeOrExpense);
            }
        };

        new DatePickerDialog(menuFragment.getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDateText(String incomeOrExpense) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        if(incomeOrExpense.equals("addIncome")) {
            addIncomeFragment.setEtDate(sdf.format(myCalendar.getTime()));
        } else if(incomeOrExpense.equals("addExpense")) {
            addExpenseFragment.setEtDate(sdf.format(myCalendar.getTime()));
        } else if(incomeOrExpense.equals("showIncomeFrom")) {
            showIncomeFragment.setEtFrom(sdf.format(myCalendar.getTime()));
            showIncomeFragment.populateListBetweenDates();
        } else if(incomeOrExpense.equals("showIncomeTo")) {
            showIncomeFragment.setEtTo(sdf.format(myCalendar.getTime()));
            showIncomeFragment.populateListBetweenDates();
        } else if(incomeOrExpense.equals("showExpenseFrom")) {
            showExpensesFragment.setEtFrom(sdf.format(myCalendar.getTime()));
            showExpensesFragment.populateListBetweenDates();
        } else if(incomeOrExpense.equals("showExpenseTo")) {
            showExpensesFragment.setEtTo(sdf.format(myCalendar.getTime()));
            showExpensesFragment.populateListBetweenDates();
        }

    }

    public void btnSaveIncomeClicked() {
        String date = addIncomeFragment.getEtDate();
        String title = addIncomeFragment.getEtTitle();
        String category = addIncomeFragment.getSpinnerCategory();
        String amount = addIncomeFragment.getEtAmount();

        if(date.length() > 0 && title.length() > 0 && category.length() > 0 && amount.length() > 0) {
            databaseController.open();
            Cursor cUsers = databaseController.getAllUsers();
            String firstName = prefs.getString("firstName", "");
            String lastName = prefs.getString("lastName", "");
            int userID = 0;
            if(cUsers.moveToFirst()){
                do{
                    if(cUsers.getString(1).equals(firstName) && cUsers.getString(2).equals(lastName)) {
                        userID = cUsers.getInt(0);
                        break;
                    }
                }while(cUsers.moveToNext());
            }
            databaseController.insertIncome(userID, date, title, category, amount);
            cUsers.close();
            databaseController.close();
            addIncomeFragment.resetAllFields();
            fragmentManager.beginTransaction().replace(R.id.masterLayout, menuFragment).commit();
            Toast.makeText(addIncomeFragment.getActivity(), R.string.toastSaved, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(addIncomeFragment.getActivity(), R.string.errorAddNew, Toast.LENGTH_SHORT).show();
        }
    }

    public void btnSaveExpenseClicked() {
        String date = addExpenseFragment.getEtDate();
        String title = addExpenseFragment.getEtTitle();
        String category = addExpenseFragment.getSpinnerCategory();
        String price = addExpenseFragment.getEtPrice();

        if(date.length() > 0 && title.length() > 0 && category.length() > 0 && price.length() > 0) {
            databaseController.open();
            Cursor cUsers = databaseController.getAllUsers();
            String firstName = prefs.getString("firstName", "");
            String lastName = prefs.getString("lastName", "");
            int userID = 0;
            if(cUsers.moveToFirst()){
                do{
                    if(cUsers.getString(1).equals(firstName) && cUsers.getString(2).equals(lastName)) {
                        userID = cUsers.getInt(0);
                        break;
                    }
                }while(cUsers.moveToNext());
            }
            databaseController.insertExpenses(userID, date, title, category, price);
            cUsers.close();
            databaseController.close();
            addExpenseFragment.resetAllFields();
            fragmentManager.beginTransaction().replace(R.id.masterLayout, menuFragment).commit();
            Toast.makeText(addExpenseFragment.getActivity(), R.string.toastSaved, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(addExpenseFragment.getActivity(), R.string.errorAddNew, Toast.LENGTH_SHORT).show();
        }
    }

    public LinkedList<Income> getAllIncomeFromDatabase() {
        String firstName = prefs.getString("firstName", "");
        String lastName = prefs.getString("lastName", "");
        int userID = 0;

        databaseController.open();
        Cursor cUsers = databaseController.getAllUsers();
        Cursor cIncome = databaseController.getAllIncome();
        LinkedList<Income> allIncomeFromDatabase = new LinkedList<Income>();

        if(cUsers.moveToFirst()){
            do{
                if(cUsers.getString(1).equals(firstName) && cUsers.getString(2).equals(lastName)) {
                    userID = cUsers.getInt(0);
                    break;
                }
            }while(cUsers.moveToNext());
        }

        if(cIncome.moveToFirst()){
            do{
                if(cIncome.getInt(0) == userID) {
                    allIncomeFromDatabase.add(new Income(cIncome.getString(1), cIncome.getString(2), cIncome.getString(3), cIncome.getString(4)));
                }
            }while(cIncome.moveToNext());
        }

        cUsers.close();
        cIncome.close();
        databaseController.close();
        return allIncomeFromDatabase;
    }

    public LinkedList<Expense> getAllExpensesFromDatabase() {
        String firstName = prefs.getString("firstName", "");
        String lastName = prefs.getString("lastName", "");
        int userID = 0;

        databaseController.open();
        Cursor cUsers = databaseController.getAllUsers();
        Cursor cExpenses = databaseController.getAllExpenses();
        LinkedList<Expense> allExpensesFromDatabase = new LinkedList<Expense>();

        if(cUsers.moveToFirst()){
            do{
                if(cUsers.getString(1).equals(firstName) && cUsers.getString(2).equals(lastName)) {
                    userID = cUsers.getInt(0);
                    break;
                }
            }while(cUsers.moveToNext());
        }

        if(cExpenses.moveToFirst()){
            do{
                if(cExpenses.getInt(0) == userID) {
                    allExpensesFromDatabase.add(new Expense(cExpenses.getString(1), cExpenses.getString(2), cExpenses.getString(3), cExpenses.getString(4)));
                }
            }while(cExpenses.moveToNext());
        }

        cUsers.close();
        cExpenses.close();
        databaseController.close();
        return allExpensesFromDatabase;
    }

    public LinkedList<Expense> getExpensesBetweenDates(String fromDate, String toDate) {
        String firstName = prefs.getString("firstName", "");
        String lastName = prefs.getString("lastName", "");
        int userID = 0;

        databaseController.open();
        Cursor cUsers = databaseController.getAllUsers();
        Cursor cExpenses = databaseController.getSpecificExpense(fromDate, toDate);
        LinkedList<Expense> specificExpenses = new LinkedList<Expense>();

        if(cUsers.moveToFirst()){
            do{
                if(cUsers.getString(1).equals(firstName) && cUsers.getString(2).equals(lastName)) {
                    userID = cUsers.getInt(0);
                    break;
                }
            }while(cUsers.moveToNext());
        }

        if(cExpenses.moveToFirst()){
            do{
                if(cExpenses.getInt(0) == userID) {
                    specificExpenses.add(new Expense(cExpenses.getString(1), cExpenses.getString(2), cExpenses.getString(3), cExpenses.getString(4)));
                }
            }while(cExpenses.moveToNext());
        }

        cUsers.close();
        cExpenses.close();
        databaseController.close();
        return specificExpenses;
    }

    public LinkedList<Income> getIncomeBetweenDates(String fromDate, String toDate) {
        String firstName = prefs.getString("firstName", "");
        String lastName = prefs.getString("lastName", "");
        int userID = 0;

        databaseController.open();
        Cursor cUsers = databaseController.getAllUsers();
        Cursor cIncome = databaseController.getSpecificIncome(fromDate, toDate);
        LinkedList<Income> specificIncome = new LinkedList<Income>();

        if(cUsers.moveToFirst()){
            do{
                if(cUsers.getString(1).equals(firstName) && cUsers.getString(2).equals(lastName)) {
                    userID = cUsers.getInt(0);
                    break;
                }
            }while(cUsers.moveToNext());
        }

        if(cIncome.moveToFirst()){
            do{
                if(cIncome.getInt(0) == userID) {
                    specificIncome.add(new Income(cIncome.getString(1), cIncome.getString(2), cIncome.getString(3), cIncome.getString(4)));
                }
            }while(cIncome.moveToNext());
        }

        cUsers.close();
        cIncome.close();
        databaseController.close();
        return specificIncome;
    }

    public void btnShowIncomeClicked() {
        fragmentManager.beginTransaction().replace(R.id.masterLayout, showIncomeFragment).addToBackStack(null).commit();
    }

    public void btnShowExpensesClicked() {
        fragmentManager.beginTransaction().replace(R.id.masterLayout, showExpensesFragment).addToBackStack(null).commit();
    }

    public void listViewDetailClicked(CustomListItem customListItem, String incomeOrExpense) {
        fragmentManager.beginTransaction().replace(R.id.masterLayout, showDetailsFragment).addToBackStack(null).commit();
        this.customListItem = customListItem;
        this.incomeOrExpense = incomeOrExpense;
    }

    public CustomListItem getCustomListItem() {
        return customListItem;
    }

    public String getIncomeOrExpense() {
        return incomeOrExpense;
    }

    public void btnShowOverviewClicked() {
        fragmentManager.beginTransaction().replace(R.id.masterLayout, showOverviewFragment).addToBackStack(null).commit();
    }

    public void drawPieChart(Bitmap bmp, int[] colors, float[] slices){
        //Canvas att måla på
        Canvas canvas = new Canvas(bmp);
        RectF box = new RectF(2, 2,bmp.getWidth()-2 , bmp.getHeight()-2);

        //Få värden för vad som är 100%
        float sum = 0;
        for (float slice : slices) {
            sum += slice;
        }
        //Initialiserar Paint
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        float start = 0;

        //Ritar bitarna
        for(int i =0; i < slices.length; i++){
            paint.setColor(colors[i]);
            float angle;
            angle = ((360.0f / sum) * slices[i]);
            canvas.drawArc(box, start, angle, true, paint);
            start += angle;
        }
    }
}