package com.example.foodplanner.meal_details.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.meal_details.presenter.MealDetailsPresenter;
import com.example.foodplanner.meal_details.presenter.MealDetailsPresenterImpl;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealLocalDataSourceImpl;
import com.example.foodplanner.model.PlanMeal;
import com.example.foodplanner.network.MealRemoteDataSourceImpl;
import com.example.foodplanner.network.MealRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsFragment extends Fragment implements MealDetailsView, OnMealDetailsClickListener {
    MealDetailsPresenter mealDetailsPresenter;
    String cookingSteps[];
    int count = 0;
    Button btnAddToFavourite;
    Button btnAddToWeekPlan;
    ImageView detailMealImage;
    TextView mealName, ingredients, mealCountry, actualSteps, steps, actualIngredients;
    WebView webView;
    private String TAG = "Ingredient";
    int id;
    List<String> ingredientsMeasures=new ArrayList<>();

    public MealDetailsFragment() {
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
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealName = view.findViewById(R.id.mealName);
        ingredients = view.findViewById(R.id.ingredients);
        mealCountry = view.findViewById(R.id.mealCountry);
        actualSteps = view.findViewById(R.id.actualSteps);
        steps = view.findViewById(R.id.steps);
        actualIngredients = view.findViewById(R.id.actualIngredients);
        webView = view.findViewById(R.id.webView);
        btnAddToFavourite = view.findViewById(R.id.btnAddToFavourite);
        btnAddToWeekPlan = view.findViewById(R.id.btnAddToWeekPlan);
        detailMealImage = view.findViewById(R.id.detailMealImage);

        id = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
        mealDetailsPresenter = new MealDetailsPresenterImpl(this, MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(),
                MealLocalDataSourceImpl.getInstance(getContext())));
        mealDetailsPresenter.getMeal(id);
    }

    @Override
    public void showData(Single<Meal> meal) {
        meal.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                mealItem -> {try{
                    //update ui
                    btnAddToFavourite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addToFavOnMealDetailListener(mealItem);
                        }
                    });
                    btnAddToWeekPlan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addToPlanOnMealDetailListener(mealItem);
                        }
                    });
                    mealName.append(mealItem.getStrMeal());
                    if (mealCountry != null &mealItem.getStrArea() != null)
                        mealCountry.append(mealItem.getStrArea());
                    Glide.with(getContext())
                            .load(mealItem.getStrMealThumb())
                            .apply(new RequestOptions().override(200, 200))
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.app_logo)
                            .into(detailMealImage);

                    if(mealItem .getStrInstructions() != null)
                    cookingSteps = mealItem.getStrInstructions().split("\\.");
                    if(cookingSteps != null)
                    for (String step : cookingSteps) {
                        count++;
                        actualSteps.append("Step " + count + ": " + step + "*\n");
                    }
                    getIngredients(mealItem);
                    if(ingredientsMeasures != null)
                    for(String ingred:ingredientsMeasures){
                        if(ingred!=null && !ingred.isEmpty()){
                            actualIngredients.append(ingred+"\n");
                        }}
                    //videoView.setVideoURI(Uri.parse(mealItem.getStrYoutube()));
                    //webView = view.findViewById(R.id.webView);
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webSettings.setLoadWithOverviewMode(true);
                    webSettings.setUseWideViewPort(true);
                    // Set a WebChromeClient to enable video playback
                    webView.setWebChromeClient(new WebChromeClient());
                    String videoUrl ="https://www.youtube.com/embed/IxhIa3eZxz8"; //mealItem.getStrYoutube();
                    Log.e(TAG, "showData: "+videoUrl );
                    String html = "<iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe>";
                    webView.loadData(html, "text/html", "utf-8");
                }catch(Exception e){
                    e.printStackTrace();

                }
                },
                error -> {
                    showErrMsg(error.getMessage());
                }
        );
    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(error).setTitle("An error occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void addToFavOnMealDetailListener(Meal meal) {
        mealDetailsPresenter.addToFav(meal);
        Toast.makeText(getContext(), "Added to Favourites", Toast.LENGTH_LONG).show();
    }

    @Override
    public void addToPlanOnMealDetailListener(Meal meal) {
        //showDatePicker
        PlanMeal planMeal=new PlanMeal(meal.getIdMeal(),meal.getStrMealThumb(),meal.getStrMeal(),meal.getCreatedAt(),meal.getUserEmail());
        System.out.println(planMeal);
        mealDetailsPresenter.addToWeekPlan(planMeal);
        Toast.makeText(getContext(), "Added to Week Plan", Toast.LENGTH_LONG).show();

    }

    private List<String> getIngredients(Meal meal) {

        if ((meal.getStrIngredient1() != null && meal.getStrIngredient1() != "") && (meal.getStrMeasure1() != null && meal.getStrMeasure1() != ""))
            ingredientsMeasures.add(meal.getStrMeasure1() + "  " + meal.getStrIngredient1());
        if ((meal.getStrIngredient2() != null && meal.getStrIngredient2() != "") && (meal.getStrMeasure2() != null && meal.getStrMeasure2() != ""))
            ingredientsMeasures.add(meal.getStrMeasure2() + "  " + meal.getStrIngredient2());
        if ((meal.getStrIngredient3() != null && meal.getStrIngredient3() != "") && (meal.getStrMeasure3() != null && meal.getStrMeasure3() != ""))
            ingredientsMeasures.add(meal.getStrMeasure3() + "  " + meal.getStrIngredient3());
        if ((meal.getStrIngredient4() != null && meal.getStrIngredient4() != "") && (meal.getStrMeasure4() != null && meal.getStrMeasure4() != ""))
            ingredientsMeasures.add(meal.getStrMeasure4() + "  " + meal.getStrIngredient4());
        if ((meal.getStrIngredient5() != null && meal.getStrIngredient5() != "") && (meal.getStrMeasure5() != null && meal.getStrMeasure5() != ""))
            ingredientsMeasures.add(meal.getStrMeasure5() + "  " + meal.getStrIngredient5());
        if ((meal.getStrIngredient6() != null && meal.getStrIngredient6() != "") && (meal.getStrMeasure6() != null && meal.getStrMeasure6() != ""))
            ingredientsMeasures.add(meal.getStrMeasure6() + "  " + meal.getStrIngredient6());
        if ((meal.getStrIngredient7() != null && meal.getStrIngredient7() != "") && (meal.getStrMeasure7() != null && meal.getStrMeasure7() != ""))
            ingredientsMeasures.add(meal.getStrMeasure7() + "  " + meal.getStrIngredient7());
        if ((meal.getStrIngredient8() != null && meal.getStrIngredient8() != "") && (meal.getStrMeasure8() != null && meal.getStrMeasure8() != ""))
            ingredientsMeasures.add(meal.getStrMeasure8() + "  " + meal.getStrIngredient8());
        if ((meal.getStrIngredient9() != null && meal.getStrIngredient9() != "") && (meal.getStrMeasure9() != null && meal.getStrMeasure9() != ""))
            ingredientsMeasures.add(meal.getStrMeasure9() + "  " + meal.getStrIngredient9());
        if ((meal.getStrIngredient10() != null && meal.getStrIngredient10() != "") && (meal.getStrMeasure10() != null && meal.getStrMeasure10() != ""))
            ingredientsMeasures.add(meal.getStrMeasure10() + "  " + meal.getStrIngredient10());
        if ((meal.getStrIngredient11() != null && meal.getStrIngredient11() != "") && (meal.getStrMeasure11() != null && meal.getStrMeasure11() != ""))
            ingredientsMeasures.add(meal.getStrMeasure11() + "  " + meal.getStrIngredient11());
        if ((meal.getStrIngredient12() != null && meal.getStrIngredient12() != "") && (meal.getStrMeasure12() != null && meal.getStrMeasure12() != ""))
            ingredientsMeasures.add(meal.getStrMeasure12() + "  " + meal.getStrIngredient12());
        if ((meal.getStrIngredient13() != null && meal.getStrIngredient13() != "") && (meal.getStrMeasure13() != null && meal.getStrMeasure13() != ""))
            ingredientsMeasures.add(meal.getStrMeasure13() + "  " + meal.getStrIngredient13());
        if ((meal.getStrIngredient14() != null && meal.getStrIngredient14() != "") && (meal.getStrMeasure14() != null && meal.getStrMeasure14() != ""))
            ingredientsMeasures.add(meal.getStrMeasure14() + "  " + meal.getStrIngredient14());
        if ((meal.getStrIngredient15() != null && meal.getStrIngredient15() != "") && (meal.getStrMeasure15() != null && meal.getStrMeasure15() != ""))
            ingredientsMeasures.add(meal.getStrMeasure15() + "  " + meal.getStrIngredient15());
        if ((meal.getStrIngredient16() != null && meal.getStrIngredient16() != "") && (meal.getStrMeasure16() != null && meal.getStrMeasure16() != ""))
            ingredientsMeasures.add(meal.getStrMeasure16() + "  " + meal.getStrIngredient16());
        if ((meal.getStrIngredient17() != null && meal.getStrIngredient17() != "") && (meal.getStrMeasure17() != null && meal.getStrMeasure17() != ""))
            ingredientsMeasures.add(meal.getStrMeasure17() + "  " + meal.getStrIngredient17());
        if ((meal.getStrIngredient18() != null && meal.getStrIngredient18() != "") && (meal.getStrMeasure18() != null && meal.getStrMeasure18() != ""))
            ingredientsMeasures.add(meal.getStrMeasure18() + "  " + meal.getStrIngredient18());
        if ((meal.getStrIngredient19() != null && meal.getStrIngredient19() != "") && (meal.getStrMeasure19() != null && meal.getStrMeasure19() != ""))
            ingredientsMeasures.add(meal.getStrMeasure19() + "  " + meal.getStrIngredient19());
        if ((meal.getStrIngredient20() != null && meal.getStrIngredient20() != "") && (meal.getStrMeasure20() != null && !meal.getStrMeasure20().isEmpty()))
            ingredientsMeasures.add(meal.getStrMeasure20() + "  " + meal.getStrIngredient20());
        return ingredientsMeasures;
    }
}