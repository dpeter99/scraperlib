package com.aper_lab.scraperlib.data

import com.aper_lab.scraperlib.api.IHasID
import com.aper_lab.scraperlib.util.ScrappingHelper
import com.aper_lab.scraperlib.util.jsonAdapters.TimeadApter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import org.threeten.bp.Duration
import java.util.*

//open data class Recipe(var id: String = "");


open class Recipe(
    var id: String = "",

    var name: String = "",
    var link: String = "",

    var time: String = "",
    var cookTime : Long = 0,
    var prepTime : Long  = 0,
    var totalTime : Long = 0,

    var yields: String = "",
    var image: String = "",
    var description: String = "",
    var ingredients: List<Ingredient> = listOf(),
    var directions: List<RecipeStep> = listOf(),

    var keywords: List<String> = listOf(),
    var recipeCategory: List<String> = listOf(),
    var recipeCuisine: List<String> = listOf(),

    var nutrition: NutritionInformation = NutritionInformation(),
    var tools: List<String> = listOf(),

    var datePublished: Date = Date()

) : Data(), IHasID{

    override fun toString(): String {
        var res = "";

        res +=  "name: " + this.name + "\n" +
                "link: " + this.link + "\n" +
                "datePublished: " + this.datePublished + "\n" +
                "time: " + this.time + "\n" +
                "cookTime: " + this.cookTime + "\n" +
                "prepTime: " + this.prepTime + "\n" +
                "totalTime: " + this.totalTime + "\n" +
                "yields: " + this.yields + "\n" +
                "image: " + this.image + "\n" +
                "description: " + this.description + "\n";

        res += "keywords: \n";
        if(keywords.isNotEmpty())
            for (ing in keywords)
            {
                res += "\t" + ing + "\n";
            }

        res += "recipeCategory: \n";
        if(recipeCategory.isNotEmpty())
            for (ing in recipeCategory)
            {
                res += "\t" + ing + "\n";
            }

        res += "recipeCuisine: \n";
        if(recipeCuisine.isNotEmpty())
            for (ing in recipeCuisine)
            {
                res += "\t" + ing + "\n";
            }

        res += "tools: \n";
        if(tools.isNotEmpty())
            for (ing in tools)
            {
                res += "\t" + ing + "\n";
            }

        res += "ingredients: \n";
        if(ingredients.isNotEmpty())
            for (ing in ingredients)
            {
                res += "\t" + ing.name + " " + ing.amount + "\n";
            }

        res += "directions: \n";
        if(directions.isNotEmpty())
            for (dir in directions)
            {
                res += "\t" + dir.num.toString() + " " + dir.text + "\n";
            }

        res += "nutrition: \n";
        res += "\t calories: "+nutrition.calories;


        res += " \n##################################################################################";

        return res;
    }

    override fun GetID(): String{
        return id;
    }

    fun copy(rec:Recipe){
        rec.id = this.id;
        rec.name = this.name;
        rec.link = this.link;

        rec.time = this.time;
        rec.cookTime = this.cookTime;
        rec.prepTime = this.prepTime;
        rec.totalTime = this.totalTime;

        rec.yields = this.yields;
        rec.image = this.image;
        rec.description = this.description;
        rec.ingredients = this.ingredients;
        rec.directions = this.directions;

        rec.keywords = this.keywords;
        rec.recipeCategory = this.recipeCategory;
        rec.recipeCuisine = this.recipeCuisine;
        rec.nutrition = this.nutrition;
        rec.tools = this.tools;
        rec.datePublished = this.datePublished;

    }

    companion object{
        fun create(): Recipe{
            val res = Recipe();
            res.version = DATA_VERSION;
            return res;
        }
    }
}
