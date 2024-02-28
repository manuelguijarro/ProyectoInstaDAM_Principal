// Generated by view binder compiler. Do not edit!
package com.example.instadamfinal.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.instadamfinal.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSettingsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonPublicarPublicacion;

  @NonNull
  public final Button buttonSubirImagen;

  @NonNull
  public final EditText editTextTextEmailUsuario;

  @NonNull
  public final EditText editTextTextPassword2;

  @NonNull
  public final EditText editTextTextTituloPublicacion;

  @NonNull
  public final ImageView imageViewPerfilUsuario;

  @NonNull
  public final ImageView imageViewSubirImagenSubirPost;

  @NonNull
  public final ProgressBar progressBar3;

  @NonNull
  public final TextView textView10;

  @NonNull
  public final TextView textView11;

  @NonNull
  public final TextView textView12;

  @NonNull
  public final TextView textView13;

  @NonNull
  public final TextView textView14;

  @NonNull
  public final TextView textView15;

  @NonNull
  public final TextView textView6;

  @NonNull
  public final TextView textView7;

  @NonNull
  public final TextView textViewEmailUsuario;

  @NonNull
  public final TextView textViewMensajeAlertaSettings;

  @NonNull
  public final TextView textViewNombreUsuario;

  private FragmentSettingsBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button buttonPublicarPublicacion, @NonNull Button buttonSubirImagen,
      @NonNull EditText editTextTextEmailUsuario, @NonNull EditText editTextTextPassword2,
      @NonNull EditText editTextTextTituloPublicacion, @NonNull ImageView imageViewPerfilUsuario,
      @NonNull ImageView imageViewSubirImagenSubirPost, @NonNull ProgressBar progressBar3,
      @NonNull TextView textView10, @NonNull TextView textView11, @NonNull TextView textView12,
      @NonNull TextView textView13, @NonNull TextView textView14, @NonNull TextView textView15,
      @NonNull TextView textView6, @NonNull TextView textView7,
      @NonNull TextView textViewEmailUsuario, @NonNull TextView textViewMensajeAlertaSettings,
      @NonNull TextView textViewNombreUsuario) {
    this.rootView = rootView;
    this.buttonPublicarPublicacion = buttonPublicarPublicacion;
    this.buttonSubirImagen = buttonSubirImagen;
    this.editTextTextEmailUsuario = editTextTextEmailUsuario;
    this.editTextTextPassword2 = editTextTextPassword2;
    this.editTextTextTituloPublicacion = editTextTextTituloPublicacion;
    this.imageViewPerfilUsuario = imageViewPerfilUsuario;
    this.imageViewSubirImagenSubirPost = imageViewSubirImagenSubirPost;
    this.progressBar3 = progressBar3;
    this.textView10 = textView10;
    this.textView11 = textView11;
    this.textView12 = textView12;
    this.textView13 = textView13;
    this.textView14 = textView14;
    this.textView15 = textView15;
    this.textView6 = textView6;
    this.textView7 = textView7;
    this.textViewEmailUsuario = textViewEmailUsuario;
    this.textViewMensajeAlertaSettings = textViewMensajeAlertaSettings;
    this.textViewNombreUsuario = textViewNombreUsuario;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_settings, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSettingsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonPublicarPublicacion;
      Button buttonPublicarPublicacion = ViewBindings.findChildViewById(rootView, id);
      if (buttonPublicarPublicacion == null) {
        break missingId;
      }

      id = R.id.buttonSubirImagen;
      Button buttonSubirImagen = ViewBindings.findChildViewById(rootView, id);
      if (buttonSubirImagen == null) {
        break missingId;
      }

      id = R.id.editTextTextEmailUsuario;
      EditText editTextTextEmailUsuario = ViewBindings.findChildViewById(rootView, id);
      if (editTextTextEmailUsuario == null) {
        break missingId;
      }

      id = R.id.editTextTextPassword2;
      EditText editTextTextPassword2 = ViewBindings.findChildViewById(rootView, id);
      if (editTextTextPassword2 == null) {
        break missingId;
      }

      id = R.id.editTextTextTituloPublicacion;
      EditText editTextTextTituloPublicacion = ViewBindings.findChildViewById(rootView, id);
      if (editTextTextTituloPublicacion == null) {
        break missingId;
      }

      id = R.id.imageViewPerfilUsuario;
      ImageView imageViewPerfilUsuario = ViewBindings.findChildViewById(rootView, id);
      if (imageViewPerfilUsuario == null) {
        break missingId;
      }

      id = R.id.imageViewSubirImagenSubirPost;
      ImageView imageViewSubirImagenSubirPost = ViewBindings.findChildViewById(rootView, id);
      if (imageViewSubirImagenSubirPost == null) {
        break missingId;
      }

      id = R.id.progressBar3;
      ProgressBar progressBar3 = ViewBindings.findChildViewById(rootView, id);
      if (progressBar3 == null) {
        break missingId;
      }

      id = R.id.textView10;
      TextView textView10 = ViewBindings.findChildViewById(rootView, id);
      if (textView10 == null) {
        break missingId;
      }

      id = R.id.textView11;
      TextView textView11 = ViewBindings.findChildViewById(rootView, id);
      if (textView11 == null) {
        break missingId;
      }

      id = R.id.textView12;
      TextView textView12 = ViewBindings.findChildViewById(rootView, id);
      if (textView12 == null) {
        break missingId;
      }

      id = R.id.textView13;
      TextView textView13 = ViewBindings.findChildViewById(rootView, id);
      if (textView13 == null) {
        break missingId;
      }

      id = R.id.textView14;
      TextView textView14 = ViewBindings.findChildViewById(rootView, id);
      if (textView14 == null) {
        break missingId;
      }

      id = R.id.textView15;
      TextView textView15 = ViewBindings.findChildViewById(rootView, id);
      if (textView15 == null) {
        break missingId;
      }

      id = R.id.textView6;
      TextView textView6 = ViewBindings.findChildViewById(rootView, id);
      if (textView6 == null) {
        break missingId;
      }

      id = R.id.textView7;
      TextView textView7 = ViewBindings.findChildViewById(rootView, id);
      if (textView7 == null) {
        break missingId;
      }

      id = R.id.textViewEmailUsuario;
      TextView textViewEmailUsuario = ViewBindings.findChildViewById(rootView, id);
      if (textViewEmailUsuario == null) {
        break missingId;
      }

      id = R.id.textViewMensajeAlertaSettings;
      TextView textViewMensajeAlertaSettings = ViewBindings.findChildViewById(rootView, id);
      if (textViewMensajeAlertaSettings == null) {
        break missingId;
      }

      id = R.id.textViewNombreUsuario;
      TextView textViewNombreUsuario = ViewBindings.findChildViewById(rootView, id);
      if (textViewNombreUsuario == null) {
        break missingId;
      }

      return new FragmentSettingsBinding((ConstraintLayout) rootView, buttonPublicarPublicacion,
          buttonSubirImagen, editTextTextEmailUsuario, editTextTextPassword2,
          editTextTextTituloPublicacion, imageViewPerfilUsuario, imageViewSubirImagenSubirPost,
          progressBar3, textView10, textView11, textView12, textView13, textView14, textView15,
          textView6, textView7, textViewEmailUsuario, textViewMensajeAlertaSettings,
          textViewNombreUsuario);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
