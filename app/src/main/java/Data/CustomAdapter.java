package Data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.saisreenivas.inputcontrols.myseekbar.MainActivity;
import com.saisreenivas.inputcontrols.myseekbar.R;

import java.util.ArrayList;

import Model.ColorModel;

/**
 * Created by Sai sreenivas on 2/15/2017.
 */

public class CustomAdapter extends ArrayAdapter<ColorModel> {

    private Activity activity;
    private int layoutResource;
    private ArrayList<ColorModel> colorsData;


    public CustomAdapter(Activity act, int resource, ArrayList<ColorModel> totalColors) {
        super(act, resource, totalColors);
        this.activity = act;
        this.layoutResource = resource;
        this.colorsData = totalColors;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return colorsData.size();
    }

    @Nullable
    @Override
    public ColorModel getItem(int position) {
        return colorsData.get(position);
    }

    @Override
    public int getPosition(ColorModel item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder = null;

        if(rowView == null || rowView.getTag() == null){
            LayoutInflater inflater = LayoutInflater.from(activity);
            rowView = inflater.inflate(layoutResource, null);
            viewHolder = new ViewHolder();

            viewHolder.colorImage = (ImageView) rowView.findViewById(R.id.row_image);

            rowView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) rowView.getTag();
        }

        viewHolder.color = getItem(position);
        viewHolder.colorImage.setBackgroundColor(Color.parseColor("#" + viewHolder.color.getColor()));

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.colorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String hexaColor = "#" + finalViewHolder.color.getColor();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("#" + finalViewHolder.color.getColor());
                builder.setPositiveButton("Set Background", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.findViewById(android.R.id.content).setBackgroundColor(
                                Color.parseColor(hexaColor));
                        ((MainActivity)activity).colorCheck();
                    }
                });
                builder.setNeutralButton("Copy to clipboard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText(hexaColor, hexaColor);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(activity, "text copied to clipboard", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


        return rowView;
    }

    private class ViewHolder{
        ColorModel color;
        ImageView colorImage;
    }
}
