package com.ouaday.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ouaday.R;
import com.ouaday.entities.Employe;

import java.util.ArrayList;
import java.util.List;

public class EmployeAdapter extends BaseAdapter {
    private List<Employe> professeurs;
    private LayoutInflater inflater;

    public EmployeAdapter(List<Employe> professeurs, Activity activity) {
        this.professeurs = professeurs;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void updateStudentsList(List<Employe> newEmployes) {
        professeurs.clear();
        professeurs.addAll(newEmployes);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return professeurs.size();
    }

    @Override
    public Object getItem(int i) {
        return professeurs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return professeurs.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = inflater.inflate(R.layout.employe_item, null);

        TextView id = view.findViewById(R.id.idEmployeItem);
        TextView nom = view.findViewById(R.id.idEmployeNomItem);
        TextView prenom = view.findViewById(R.id.idEmployePrenomItem);
        TextView dateEmbauche = view.findViewById(R.id.idEmployeDateItem);

        id.setText(professeurs.get(i).getId()+"");
        nom.setText(professeurs.get(i).getNom());
        prenom.setText(professeurs.get(i).getPrenom());
        dateEmbauche.setText(professeurs.get(i).getDateNaissance());
        return view;
    }
}
