package se.mah.ad0025.inluppg1;


/**
 * Klass som har hand om egengjorda ListView-items som ska läggas till i ListViews.
 * @author Jonas Dahlström 5/10-14
 */
public class CustomListItem {
    private String date, title, smallTitle, category;
    private int image;

    public CustomListItem(String date, String title, String smallTitle, int image, String category) {
        this.date = date;
        this.title = title;
        this.smallTitle = smallTitle;
        this.image = image;
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getSmallTitle() {
        return smallTitle;
    }

    public int getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }
}
