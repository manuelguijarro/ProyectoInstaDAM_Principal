package com.example.instadamfinal.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.instadamfinal.R;
import com.example.instadamfinal.controllers.FireStorageController;
import com.example.instadamfinal.listeners.DescargaImagenUsuarioListener;
import com.example.instadamfinal.models.Publicacion;

import java.util.List;

public class PublicacionAdapter extends RecyclerView.Adapter<PublicacionAdapter.PublicacionViewHolder> {
    private List<Publicacion> publicacionList;

    public PublicacionAdapter(List<Publicacion> publicacionList) {
        this.publicacionList = publicacionList;
    }


    @NonNull
    @Override
    public PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_publicaciones, parent, false);
        return new PublicacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionViewHolder holder, int position) {
        holder.bind(publicacionList.get(position));
    }

    @Override
    public int getItemCount() {
        return publicacionList.size();
    }

    public class PublicacionViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenPublicacion;
        TextView autorPublicacion;
        TextView tituloPublicacion;
        TextView descripcionPublicacion;
        ProgressBar progressBar;


        PublicacionViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenPublicacion = itemView.findViewById(R.id.imagenPublicacion);
            autorPublicacion = itemView.findViewById(R.id.autorPublicacion);
            tituloPublicacion = itemView.findViewById(R.id.tituloPublicacion);
            descripcionPublicacion = itemView.findViewById(R.id.descripcionPublicacion);
            progressBar = itemView.findViewById(R.id.progressBar6);
        }

        public void  bind(Publicacion publicacion) {

            Context context = itemView.getContext();


            FireStorageController.descargarImagen(context, publicacion.getUrlImagenPublicacion(), new DescargaImagenUsuarioListener() {


                @Override
                public void imagenDescargada(Bitmap bitmap) {
                    progressBar.setVisibility(View.GONE);
                    cargarDatos(bitmap,context,publicacion);
                }
            });

        }

        private void cargarDatos(Bitmap bitmap, Context context, Publicacion publicacion) {
            this.imagenPublicacion.setImageBitmap(bitmap);
            this.autorPublicacion.setText(publicacion.getUrlImagenPublicacion());
            this.tituloPublicacion.setText(publicacion.getTitulo());
            this.descripcionPublicacion.setText(publicacion.getDescripcion());
        }
    }

}
