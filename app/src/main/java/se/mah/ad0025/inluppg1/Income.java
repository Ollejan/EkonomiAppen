package se.mah.ad0025.inluppg1;

/**
 * Klass som har hand om inkomsternas information.
 * @author Jonas Dahlström 5/10-14
 */
public class Income {
    private String date, title, category, amount;

    public Income(String date, String title, String category, String amount) {
        this.date = date;
        this.title = title;
        this.category = category;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getAmount() {
        return amount;
    }
}
