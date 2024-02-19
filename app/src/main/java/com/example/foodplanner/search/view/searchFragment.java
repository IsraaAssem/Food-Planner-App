package com.example.foodplanner.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.MealLocalDataSourceImpl;
import com.example.foodplanner.network.MealRemoteDataSourceImpl;
import com.example.foodplanner.network.MealRepositoryImpl;
import com.example.foodplanner.search.presenter.SearchPresenter;
import com.example.foodplanner.search.presenter.SearchPresenterImpl;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class searchFragment extends Fragment implements OnItemClickListener, SearchMealView {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    CategoriesSearchAdapter categoriesSearchAdapter;
    CounteriesSearchAdapter countriesSearchAdapter;
    SearchPresenter searchPresenter;
    Button viewMore;
    ChipGroup chipGroup;
    SearchView searchView;

    public searchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getContext());
        categoriesSearchAdapter = new CategoriesSearchAdapter(getContext(), new ArrayList<>(), this);
        countriesSearchAdapter = new CounteriesSearchAdapter(getContext(), new ArrayList<>(), this);
        recyclerView = view.findViewById(R.id.recyclerViewSearch);
        searchPresenter = new SearchPresenterImpl(this, MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImpl.getInstance(getContext())));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(categoriesSearchAdapter);
        searchPresenter.getCategories();
        searchPresenter.getCountries();
        chipGroup = view.findViewById(R.id.chipGroup);
        searchView=view.findViewById(R.id.searchView);
        chipGroup.setSelectionRequired(true);
        setupSearch();
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // RecyclerView selectedRecyclerView = null;

            if (checkedId != View.NO_ID) {
                Chip selectedChip = group.findViewById(checkedId);
                String selectedText = selectedChip.getText().toString();
                if (selectedText.equals("Category")) {
                    recyclerView.setAdapter(categoriesSearchAdapter);
                    if (categoriesSearchAdapter.categories.size() == 0) {
                        searchPresenter.getCategories();
                    }
                    categoriesSearchAdapter.filter(searchView.getQuery().toString());
                }
                if (selectedText.equals("Country")) {
                    recyclerView.setAdapter(countriesSearchAdapter);
                    if (countriesSearchAdapter.countries.size() == 0) {
                        searchPresenter.getCountries();
                    }
                    countriesSearchAdapter.filter(searchView.getQuery().toString());
                }
            }
//            else if (selectedText.equals("Option 2")) {
//                    selectedRecyclerView = recyclerView2;
//                } else if (selectedText.equals("Option 3")) {
//                    selectedRecyclerView = recyclerView3;
//                }
//            }
//        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
//            if (checkedId != View.NO_ID) {
//                // A chip has been selected
//                Chip selectedChip = view.findViewById(checkedId);
//                String selectedText = selectedChip.getText().toString();
//
//                // Do something with the selected text
//                // For example, display it in a TextView
//                TextView textView = view.findViewById(R.id.textViewResult);
//                textView.setText("Selected: " + selectedText);
//            }
        });

////////////////////////
//        ChipGroup chipGroup = view.findViewById(R.id.chipGroup);
//
//        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
//            RecyclerView selectedRecyclerView = null;
//
//            if (checkedId != View.NO_ID) {
//                Chip selectedChip = group.findViewById(checkedId);
//                String selectedText = selectedChip.getText().toString();
//
//                // Determine which RecyclerView to show based on the selected chip
//                if (selectedText.equals("Option 1")) {
//                    selectedRecyclerView = recyclerView1;
//                } else if (selectedText.equals("Option 2")) {
//                    selectedRecyclerView = recyclerView2;
//                } else if (selectedText.equals("Option 3")) {
//                    selectedRecyclerView = recyclerView3;
//                }
//            }
//
//            // Update visibility of RecyclerViews
//            updateRecyclerViewVisibility(recyclerView1, selectedRecyclerView);
//            updateRecyclerViewVisibility(recyclerView2, selectedRecyclerView);
//            updateRecyclerViewVisibility(recyclerView3, selectedRecyclerView);
//        });
//        private void updateRecyclerViewVisibility(RecyclerView recyclerView, RecyclerView selectedRecyclerView) {
//            if (recyclerView == selectedRecyclerView) {
//                recyclerView.setVisibility(View.VISIBLE);
//            } else {
//                recyclerView.setVisibility(View.GONE);
//            }
//        }

    }
    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                categoriesSearchAdapter.filter(newText);
                countriesSearchAdapter.filter(newText);
                return true;
            }
        });
    }


    @Override
    public void onItemClick(Category meal) {
        searchPresenter.viewMoreDetails(meal);//category
    }

    @Override
    public void ShowMeals(List<Category> meals) {
        categoriesSearchAdapter.setList(meals);
        categoriesSearchAdapter.notifyDataSetChanged();
    }
    @Override
    public void showCountries(List<Country> meals) {

         countriesSearchAdapter.setList(meals);
        countriesSearchAdapter.notifyDataSetChanged();
    }
    @Override
    public void showErrMsg(String errMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(errMsg).setTitle("Error:NOT connected to Network");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}