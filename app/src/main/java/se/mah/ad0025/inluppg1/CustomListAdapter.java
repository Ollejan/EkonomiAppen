package se.mah.ad0025.inluppg1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Klass som ärver ArrayAdapter. Används för att fylla en ListView med egengjorda ListView-items.
 * @author Jonas Dahlström 5/10-14
 */
public class CustomListAdapter extends ArrayAdapter<CustomListItem> {
    private LayoutInflater inflater;
    private String incomeOrExpense;

    public CustomListAdapter(Context context, LinkedList<CustomListItem> objects, String incomeOrExpense) {
        super(context, R.layout.listview_item, objects);
        this.incomeOrExpense = incomeOrExpense;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView title, smallTitle, category;
        ImageView imageView;
        if(convertView == null) {
            convertView = (LinearLayout)inflater.inflate(R.layout.listview_item, parent, false);
        }
        title = (TextView)convertView.findViewById(R.id.tvCustomListTitle);
        smallTitle = (TextView)convertView.findViewById(R.id.tvCustomListSmallTitle);
        category = (TextView)convertView.findViewById(R.id.tvCustomListCategory);
        imageView = (ImageView)convertView.findViewById(R.id.imageCustomList);
        title.setText(this.getItem(position).getTitle());
        smallTitle.setText(this.getItem(position).getSmallTitle());
        if(incomeOrExpense.equals("income")) {
            imageView.setImageBitmap(null);
            category.setText(this.getItem(position).getCategory());
        } else {
            category.setText("");
            imageView.setImageResource(this.getItem(position).getImage());
        }
        return convertView;
    }
}
