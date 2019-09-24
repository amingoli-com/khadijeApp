package com.ermile.khadijeapp.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ermile.khadijeapp.Item.item_del;
import com.ermile.khadijeapp.R;
import com.ermile.khadijeapp.api.apiV6;
import com.ermile.khadijeapp.utility.Database;
import com.ermile.khadijeapp.utility.SaveManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.ermile.khadijeapp.R.drawable.woman;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String plus = mData.get(position).getPlus();
        String text = mData.get(position).getText();
        String sex = mData.get(position).getSex();
        String name = mData.get(position).getName();
        Boolean last_liked = mData.get(position).isLast_liked();

        if (name != null){
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(name);
        }

        holder.text.setText(text);
        holder.plus.setText(plus);


        if (sex.equals(context.getString(R.string.sex_female))){
            holder.avatar.setImageResource(woman);
        }else {
            holder.avatar.setImageResource(R.drawable.man);
        }

        if (last_liked){
            holder.bg_img_plus.setColorFilter(Color.RED);
        }

        final String id = mData.get(position).getId();

        final String url = context.getString(R.string.url_del_like);

        View.OnClickListener clickLike = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase databases = new Database(context).getReadableDatabase();
                @SuppressLint("Recycle") Cursor checkID_del = databases.rawQuery(Database.select_del(id),null);
                if (checkID_del.getCount() == 0 ){
                    final String getPlusApp = holder.plus.getText().toString();
                    int plusApp = Integer.valueOf(getPlusApp);
                    ++plusApp;
                    holder.plus.setText(String.valueOf(plusApp));
                    holder.bg_img_plus.setColorFilter(Color.RED);

                    String apikey = SaveManager.get(context).getstring_appINFO().get(SaveManager.apiKey);
                    apiV6.like_del(url,apikey, id, new apiV6.likeListener() {
                        @Override
                        public void liked(String respone) {
                            try {
                                JSONArray array = new JSONArray(respone);
                                SQLiteDatabase database = new Database(context).getWritableDatabase();
                                database.execSQL(Database.insetTo_del(id,"true"));
                                database.close();

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
                databases.close();
                checkID_del.close();
            }
        };

        holder.bg_img_plus.setOnClickListener(clickLike);
        holder.plus.setOnClickListener(clickLike);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,text,plus;
        ImageView avatar,bg_img_plus;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_del);
            text = itemView.findViewById(R.id.text_del);
            plus = itemView.findViewById(R.id.plus_del);
            bg_img_plus = itemView.findViewById(R.id.img_like);
            avatar = itemView.findViewById(R.id.imgSex_del);
        }

    }


}