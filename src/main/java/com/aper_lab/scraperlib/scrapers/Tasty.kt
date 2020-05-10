package com.aper_lab.scraperlib.scrapers

import com.aper_lab.scraperlib.ScraperRegistry
import com.aper_lab.scraperlib.api.RecipeScraper
import com.aper_lab.scraperlib.api.RecipeScraperAnotation
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.util.ScrappingHelper
import com.aper_lab.scraperlib.util.schemaOrg.fromSchemaOrg
import com.google.gson.GsonBuilder
import java.net.URL


@RecipeScraperAnotation()
class Tasty : RecipeScraper{

    val gson = GsonBuilder().create();

    override fun scrapFromLink(link: URL):Recipe {
        val doc = ScrappingHelper.getDocFromURL(link);

        var recipe = Recipe.create()

        var jsonLdRecipe = ScrappingHelper.checkWebsiteForJsonLDRecipe(doc);
        if(jsonLdRecipe != null){
            recipe = Recipe.fromSchemaOrg(jsonLdRecipe);
        }

        var recipeFragment = doc.select(".recipe-page");

        recipe.link = link.toString();

        recipe.name = recipeFragment.select("h1.recipe-name").text();

        return recipe;
    }

    override fun getSourceID(): String {
        return "tasty"
    }

    companion object{
        val urls = listOf<String>(
            "tasty.co"
        )

        fun Register(registry: ScraperRegistry){

            registry.Register(urls,Tasty())
        }
    }

}