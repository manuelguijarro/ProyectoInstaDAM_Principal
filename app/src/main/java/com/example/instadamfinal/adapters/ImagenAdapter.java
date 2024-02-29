package com.example.instadamfinal.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.instadamfinal.R;

import java.util.List;

/**
 * Con este adaptador utilizamos el recyclerView con una lista de Imagenes en tipo Bitmap, previamente nos las hemos descargado
 * de la nube(firestorage) y una vez descargadas, procedemos a enviarlas a esta clase para setearlas cada una.
 */

public class ImagenAdapter  extends RecyclerView.Adapter<ImagenAdapter.ViewHolder> {

    private List<Bitmap> localDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView =  view.findViewById(R.id.imageView2);
        }
        public ImageView getImageView() {

            return imageView;
        }
    }


    public ImagenAdapter(List<Bitmap> dataSet) {
        localDataSet = dataSet;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.imagen_item, viewGroup, false);

        return new ViewHolder(view);
    }

    /**
     *
     * En este metodo es donde vamos seteando una a una el Bitmap en cada imageView.
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        viewHolder.getImageView().setImageBitmap(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}


