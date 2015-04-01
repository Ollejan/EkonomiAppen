package se.mah.ad0025.inluppg1;

/**
 * Klass som har hand om utgifternas information.
 * @author Jonas Dahlström 5/10-14
 */
public class Expense {
    private String date, title, category, price;

    public Expense(String date, String title, String category, String price) {
        this.date = date;
        this.title = title;
        this.category = category;
        this.price = price;
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

    public String getPrice() {
        return price;
    }
}

