package com.example.foodplanner.search.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.NavGraphDirections;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CounteriesSearchAdapter extends RecyclerView.Adapter<CounteriesSearchAdapter.ItemViewHolder> {
    Context context;
    public ArrayList<Country> countries;/////
    private ArrayList<Country> filteredCountries;/////
    private static final String TAG = "RecyclerView";
    OnItemClickListener listener;
    public CounteriesSearchAdapter(Context context, ArrayList<Country> countries, OnItemClickListener listener) {
        this.context = context;
        this.countries = countries;
        this.listener = listener;
        this.filteredCountries = new ArrayList<>(countries);
    }
    public void setList(List<Country> meals) {
        this.countries.clear();
        this.filteredCountries.clear();
        this.countries.addAll((ArrayList<Country>) meals);
        this.filteredCountries.addAll((ArrayList<Country>) meals);
    }
    @NonNull
    @Override
    public CounteriesSearchAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =inflater.inflate(R.layout.country_item,parent,false);
        CounteriesSearchAdapter.ItemViewHolder itemViewHolder=new CounteriesSearchAdapter.ItemViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: ");
        itemViewHolder.btnAddToFav.setText("View");
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CounteriesSearchAdapter.ItemViewHolder holder, int position) {
        Country country=(Country) filteredCountries.get(position);
        Log.i(TAG, "onBindViewHolder: "+position);
        holder.mealName.setText(country.getStrArea());

        holder.btnAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener.onItemClick(category);
////                HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action=HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(category.getIdCountry());
////                Navigation.findNavController(v).navigate(action);

                listener.onCountyClick(country);
                NavGraphDirections.ActionToHomeFragment action=NavGraphDirections.actionToHomeFragment(country.getStrArea());
                Navigation.findNavController(v).navigate(action);
            }

        });
    }

    @Override
    public int getItemCount() {
        Log.d("SearchAdapter", "Number of items: " + countries.size()+"  "+filteredCountries.size());

        if (filteredCountries == null)
            return 0;
        return filteredCountries.size();
    }
    public void filter(String query) {
        filteredCountries.clear();
        if (query.isEmpty()) {
            filteredCountries.addAll(countries);
        } else {
            for (Country category : countries) {
                if (category.getStrArea().toLowerCase().contains(query.toLowerCase())) {
                    filteredCountries.add(category);
                }
            }
        }
        notifyDataSetChanged();
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView mealImage;
        TextView mealName;
        Button btnAddToFav;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage=itemView.findViewById(R.id.mealImage);
            mealName=itemView.findViewById(R.id.mealName);
            btnAddToFav=itemView.findViewById(R.id.btnAddToFavourite);

        }

    }
}
