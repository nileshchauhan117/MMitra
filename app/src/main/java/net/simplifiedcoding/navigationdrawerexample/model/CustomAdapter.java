package net.simplifiedcoding.navigationdrawerexample.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.simplifiedcoding.navigationdrawerexample.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<ItemObject> listStorage;
    private Context context;

    public CustomAdapter(Context context, List<ItemObject> customizedListView) {
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.ddd, parent, false);
            listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.textView);
            listViewHolder.textInListView1 = (TextView)convertView.findViewById(R.id.textView1);
            listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.imageView);
            listViewHolder. layout_main= (RelativeLayout) convertView.findViewById(R.id.layout_main);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }

        listViewHolder.textInListView.setText(listStorage.get(position).getContent());
        listViewHolder.textInListView1.setText(listStorage.get(position).getImageResource());
        String flag=listStorage.get(position).getf();

        if(flag.equalsIgnoreCase("Y"))
        {

            listViewHolder.textInListView.setTextColor(context.getResources().getColor(R.color.blue));
            listViewHolder.textInListView1.setTextColor(context.getResources().getColor(R.color.blue));
            listViewHolder. layout_main.setBackgroundResource(R.color.blue_back);
        }
        else if(flag.equalsIgnoreCase("P"))
        {

            listViewHolder.textInListView.setTextColor(context.getResources().getColor(R.color.deeppink));
            listViewHolder.textInListView1.setTextColor(context.getResources().getColor(R.color.deeppink));
            listViewHolder. layout_main.setBackgroundResource(R.color.maroon_tra);
        }
        else
        {
            listViewHolder.textInListView.setTextColor(context.getResources().getColor(R.color.cadetblue));
            listViewHolder.textInListView1.setTextColor(context.getResources().getColor(R.color.cadetblue));
            listViewHolder. layout_main.setBackgroundResource(R.color.blue_t);
        }
      //  int imageResourceId = this.context.getResources().getIdentifier(listStorage.get(position).getImageResource(), "drawable", this.context.getPackageName());
      //  listViewHolder.imageInListView.setImageResource(imageResourceId);

        return convertView;
    }

    static class ViewHolder{
        TextView textInListView;
        TextView textInListView1;
        ImageView imageInListView;
        RelativeLayout layout_main;
    }

}
