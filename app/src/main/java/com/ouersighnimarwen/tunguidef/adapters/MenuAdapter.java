package com.ouersighnimarwen.tunguidef.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ouersighnimarwen.tunguidef.R;
import com.ouersighnimarwen.tunguidef.entity.MenuModel;

import java.io.InputStream;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>

    {
        private MenuModel[] listHotels;
        private Context context;

        /*
        private List<Integer> mImage;
        private List<String> mNom;
        private LayoutInflater mInflater;
        private ItemClickListener mClickListener; */

        // data is passed into the constructor
        public MenuAdapter(MenuModel[] listHotels, Context context) {
            this.listHotels = listHotels;
            this.context = context;
        }



        // inflates the row layout from xml when needed
        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.menu_row, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return  viewHolder;
        }

        // binds the data to the view and textview in each row
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position)
        {
            final MenuModel mylistHotels = listHotels[position];



            DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(holder.imageMenu);
            downloadTask.execute("http://10.0.2.2:1337/images/"+listHotels[position].menuImageUrl);
            holder.textMenu.setText(listHotels[position].getMenuName());

    }

        // total number of rows
        @Override
        public int getItemCount() {
        return listHotels.length;
    }

        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageMenu;
            TextView textMenu;
            RatingBar note;
////////////////////////////// aayet ll rating bar
            public LinearLayout layout;

            ViewHolder(View itemView) {
                super(itemView);
                imageMenu = itemView.findViewById(R.id.imageMenu);
                textMenu = itemView.findViewById(R.id.textMenu);
                layout = itemView.findViewById(R.id.layout_menu_item);
                note = itemView.findViewById(R.id.ratingBarHotel);
                note.setStepSize(1);
                note.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        System.out.println(rating);
                    }
                });
            }

        }

        // Load image from server url
        private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageWithURLTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String pathToFile = urls[0];
                Bitmap bitmap = null;
                try {
                    InputStream in = new java.net.URL(pathToFile).openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }

}

