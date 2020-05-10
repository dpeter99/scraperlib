package com.aper_lab.scraperlib.util.schemaOrg

import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.NutritionInformation
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep
import com.aper_lab.scraperlib.util.ScrappingHelper
import com.aper_lab.scraperlib.util.gsonAdapters.AlwaysListTypeAdapterFactory
import com.google.gson.annotations.JsonAdapter
import com.google.gson.internal.LinkedTreeMap
import java.time.Duration

//import org.threeten.bp.Duration

class Recipe : JsonLDData() {
    companion object{
        var Type = "Recipe";
    }

    var name:String = "";
    var description:String = "";

    var image: Any? = null;
    @JsonAdapter(AlwaysListTypeAdapterFactory::class)
    var video: List<VideoObject> = listOf();

    var keywords: String = "";

    @JsonAdapter(AlwaysListTypeAdapterFactory::class)
    var recipeCategory: List<String> = listOf();

    @JsonAdapter(AlwaysListTypeAdapterFactory::class)
    var recipeCuisine: List<String> = listOf();

    @JsonAdapter(AlwaysListTypeAdapterFactory::class)
    var recipeIngredient:List<String> = listOf()
    @JsonAdapter(AlwaysListTypeAdapterFactory::class)
    var recipeInstructions:List<Any> = listOf()

    var recipeYield: String = "";

    var cookTime: String = "";
    var prepTime: String = "";
    var totalTime: String = "";

    var nutrition: NutritionInformation? = null;

    var tool: Any? = null;

    var datePublished: String = "";

    override fun getType():String{
        return Type;
    }

}

fun Recipe.Companion.fromSchemaOrg(json : com.aper_lab.scraperlib.util.schemaOrg.Recipe): Recipe{
    var rec = Recipe.create();

    rec.name = json.name;
    rec.time = json.totalTime;

    if(json.cookTime != "")
        rec.cookTime = Duration.parse(json.cookTime).seconds;
    if(json.prepTime != "")
        rec.prepTime = Duration.parse(json.prepTime).seconds;
    if(json.totalTime != "")
        rec.totalTime = Duration.parse(json.totalTime).seconds;

    rec.yields = json.recipeYield.removeSuffix("servings")
    rec.image = ScrappingHelper.getImageURLFromAny(json.image);
    rec.description = json.description;

    rec.keywords = json.keywords.split(", ");

    if(json.tool is String)
        rec.tools = listOf(json.tool as String);

    rec.recipeCuisine = json.recipeCuisine;
    rec.recipeCategory = json.recipeCategory;


    rec.ingredients = json.recipeIngredient.map {
            element -> Ingredient(element.replace("<p>","").replace("</p>",""),"");
    }

    rec.directions = json.recipeInstructions.mapIndexed {
            id, element ->
                when (element) {
                    is Map<*,*> -> RecipeStep(id+1,(element as LinkedTreeMap<String, String>)["text"].toString())
                    is String -> RecipeStep(id+1, element)
                    else -> RecipeStep( id+1, "")
                };
    }

    rec.nutrition = json.nutrition?: NutritionInformation();

    return rec;
}