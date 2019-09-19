package com.ermile.khadijehapp.Adaptor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ermile.khadijehapp.Item.item_del;
import com.ermile.khadijehapp.R;
import com.ermile.khadijehapp.api.apiV6;
import com.ermile.khadijehapp.utility.SaveManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.ermile.khadijehapp.R.drawable.woman;

public class Adaptor_del extends RecyclerView.Adapter<Adaptor_del.ViewHolder> {

    private List<item_del> mData;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    public Adaptor_del(Context context, List<item_del> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_delneveshte, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String text = mData.get(position).getText();
        String day = mData.get(position).getDay();
        String plus = mData.get(position).getPlus();
        String avatar = mData.get(position).getSex();
        String id = mData.get(position).getId();
        holder.text.setText(text);
        holder.day.setText(day);
        holder.plus.setText(plus);

/*        switch (avatar){
            case "women":

                break;
            case "men":

                break;
                default:

                    break;
        }*/

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String apikey = SaveManager.get(context).getstring_appINFO().get(SaveManager.apiKey);
                apiV6.like_del(apikey, mData.get(position).getId(), new apiV6.likeListener() {
                    @Override
                    public void liked(String respone) {
                        try {
                            JSONArray array = new JSONArray(respone);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String text = object.getString("text");
                                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void filed(String error) {

                    }
                });
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text,day,plus;
        ImageView avatar,btnLike;

        ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text_del);
            day = itemView.findViewById(R.id.day_del);
            plus = itemView.findViewById(R.id.plus_del);
            avatar = itemView.findViewById(R.id.imgSex_del);
            btnLike = itemView.findViewById(R.id.btn_like);
        }

    }


}