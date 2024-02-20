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
import com.example.foodplanner.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesSearchAdapter extends RecyclerView.Adapter<CategoriesSearchAdapter.ItemViewHolder> {
    Context context;
    public ArrayList<Category> categories;/////
    private ArrayList<Category> filteredCategories;/////
    private static final String TAG = "RecyclerView";
    OnItemClickListener listener;
    public CategoriesSearchAdapter(Context context, ArrayList<Category> categories, OnItemClickListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
        this.filteredCategories = new ArrayList<>(categories);
    }
    public void setList(List<Category> meals) {
        this.categories.clear();
        this.filteredCategories.clear();
        this.categories.addAll((ArrayList<Category>) meals);
        this.filteredCategories.addAll((ArrayList<Category>) meals);
    }
    @NonNull
    @Override
    public CategoriesSearchAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =inflater.inflate(R.layout.meal_item2,parent,false);
        CategoriesSearchAdapter.ItemViewHolder itemViewHolder=new CategoriesSearchAdapter.ItemViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: ");
        itemViewHolder.btnAddToFav.setText("View");
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesSearchAdapter.ItemViewHolder holder, int position) {
        Category category=(Category) filteredCategories.get(position);
        Log.i(TAG, "onBindViewHolder: "+position);
        holder.mealName.setText(category.getStrCategory());

        Glide.with(context).load(filteredCategories.get(position).getStrCategoryThumb())
                .apply(new RequestOptions().override(270,255))
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mealImage);
        holder.btnAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategoryClick(category);
                NavGraphDirections.ActionToHomeFragment action=NavGraphDirections.actionToHomeFragment(category.getStrCategory());
                Navigation.findNavController(v).navigate(action);
            }

        });
    }

    @Override
    public int getItemCount() {
        Log.d("SearchAdapter", "Number of items: " + categories.size()+"  "+filteredCategories.size());

        if (filteredCategories == null)
            return 0;
        return filteredCategories.size();
    }
    public void filter(String query) {
        filteredCategories.clear();
        if (query.isEmpty()) {
            filteredCategories.addAll(categories);
        } else {
            for (Category category : categories) {
                if (category.getStrCategory().toLowerCase().contains(query.toLowerCase())) {
                    filteredCategories.add(category);
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
