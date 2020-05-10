package com.aper_lab.scraperlib.scrapers

import com.aper_lab.scraperlib.ScraperRegistry
import com.aper_lab.scraperlib.api.RecipeScraper
import com.aper_lab.scraperlib.api.RecipeScraperAnotation
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep
import com.aper_lab.scraperlib.util.ScrappingHelper
import com.aper_lab.scraperlib.util.schemaOrg.fromSchemaOrg
import org.jsoup.Jsoup
import java.net.URL
import java.nio.charset.Charset

@RecipeScraperAnotation()
class Delish : RecipeScraper{

    override fun scrapFromLink(link: URL):Recipe {
        val doc = Jsoup.connect(link.toString()).get()
        doc.charset(Charset.forName("UTF-8"))

        var recipe = Recipe.create();

        var jsonLdRecipe = ScrappingHelper.checkWebsiteForJsonLDRecipe(doc);
        if(jsonLdRecipe != null){
            recipe = Recipe.fromSchemaOrg(jsonLdRecipe);
        }
        else{

            var recipeFragment = doc.select(".site-content");

            recipe.name = recipeFragment.select("h1.recipe-hed").text();
            recipe.time = recipeFragment.select(".total-time-amount").text()
            recipe.yields = recipeFragment.select(".yields .yields-amount").text()
            recipe.image = doc.select("meta[name=og:image]").attr("content")
            recipe.description = "";

            recipe.ingredients = recipeFragment.select(".ingredient-lists .ingredient-item").map {
                    element -> Ingredient(element.text(),"");
            }



        }

        var recipeFragment = doc.select(".site-content");

        recipe.directions = recipeFragment.select(".direction-lists ol").select("li").mapIndexed {
                id, element -> RecipeStep(id+1,element.text());
        }

        recipe.link = link.toString();

        return recipe;
    }

    override fun getSourceID(): String {
        return "delish"
    }

    companion object{
        val urls = listOf<String>(
            "www.delish.com",
            "delish.com"
        )
        fun Register(registry: ScraperRegistry){

            registry.Register(urls,Delish())
        }
    }

}