package com.example.countries.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.countries.R;
import com.example.countries.data.database.CountryMapper;
import com.example.countries.util.ImageLoader;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.net.URI;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.RecyclerViewHolder> {

    private final Context context;

    public CountryAdapter(Context context) {
        this.context = context;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView name, capital, region, subregion, population, borders, languages;
        private ShapeableImageView flag;

        public RecyclerViewHolder(View view) {
            super(view);

            //reference views
            name = (TextView) view.findViewById(R.id.name);
            capital = (TextView) view.findViewById(R.id.capital);
            region = (TextView) view.findViewById(R.id.region);
            subregion = (TextView) view.findViewById(R.id.subregion);
            population = (TextView) view.findViewById(R.id.population);
            borders = (TextView) view.findViewById(R.id.borders);
            languages = (TextView) view.findViewById(R.id.languages);
            flag = (ShapeableImageView) view.findViewById(R.id.flag);
        }

        public TextView getName() {
            return name;
        }

        public TextView getCapital() {
            return capital;
        }

        public TextView getRegion() {
            return region;
        }

        public TextView getSubregion() {
            return subregion;
        }

        public TextView getPopulation() {
            return population;
        }

        public TextView getBorders() {
            return borders;
        }

        public TextView getLanguages() {
            return languages;
        }

        public ShapeableImageView getFlag() {
            return flag;
        }
    }

    private final DiffUtil.ItemCallback<CountryMapper> differCallback = new DiffUtil.ItemCallback<CountryMapper>() {
        @Override
        public boolean areItemsTheSame(CountryMapper oldItem, CountryMapper newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(CountryMapper oldItem, CountryMapper newItem) {
            return oldItem.equals(newItem);
        }
    };

    public AsyncListDiffer differ = new AsyncListDiffer(this,differCallback);

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.country_item, viewGroup, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountryAdapter.RecyclerViewHolder holder, int position) {

        CountryMapper mapper = (CountryMapper) differ.getCurrentList().get(position);

        if (mapper != null) {

            Spannable spannableCapital = new SpannableString("Capital: " + mapper.getCapital());
            Spannable spannableRegion = new SpannableString("Region: " + mapper.getRegion());
            Spannable spannableSubregion = new SpannableString("Subregion: " + mapper.getSubregion());
            Spannable spannablePopulation = new SpannableString("Population: " + mapper.getPopulation());
            Spannable spannableBorders = new SpannableString("Borders: " + mapper.getBorders());
            Spannable spannableLang = new SpannableString("Languages: " + mapper.getLanguages());

            StyleSpan bold = new StyleSpan(Typeface.BOLD);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.BLACK);

            spannableCapital.setSpan(bold,0,7, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannableCapital.setSpan(colorSpan,0,7,Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            spannableRegion.setSpan(bold,0,7, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannableRegion.setSpan(colorSpan,0,7,Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            spannableSubregion.setSpan(bold,0,9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannableSubregion.setSpan(colorSpan,0,9,Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            spannablePopulation.setSpan(bold,0,10, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannablePopulation.setSpan(colorSpan,0,10,Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            spannableBorders.setSpan(bold,0,8, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannableBorders.setSpan(colorSpan,0,8,Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            spannableLang.setSpan(bold,0,11, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannableLang.setSpan(colorSpan,0,11,Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            holder.name.setText(mapper.getName());
            holder.capital.setText(spannableCapital);
            holder.region.setText(spannableRegion);
            holder.subregion.setText(spannableSubregion);
            holder.population.setText(spannablePopulation);
            holder.borders.setText(spannableBorders);
            holder.languages.setText(spannableLang);

            float radius = context.getResources().getDimension(R.dimen.image_corner_radius);
            ShapeAppearanceModel shapeAppearanceModel = holder.flag.getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, radius)
                    .build();

            holder.flag.setShapeAppearanceModel(shapeAppearanceModel);

            ImageLoader.fetchSvg(context,mapper.getFlag(), holder.flag);

        }
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }
}
