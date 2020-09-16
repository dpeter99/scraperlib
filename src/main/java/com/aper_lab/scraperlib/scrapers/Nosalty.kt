package com.aper_lab.scraperlib.scrapers

import com.aper_lab.scraperlib.ScraperRegistry
import com.aper_lab.scraperlib.api.RecipeScraper
import com.aper_lab.scraperlib.api.RecipeScraperAnotation
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep
import com.aper_lab.scraperlib.util.ScrappingHelper
import org.jsoup.Jsoup
/*import org.threeten.bp.Duration
import org.threeten.bp.temporal.Temporal
import org.threeten.bp.temporal.TemporalAmount*/
import java.net.URL
import java.nio.charset.Charset
import java.time.Duration

@RecipeScraperAnotation()
class Nosalty : RecipeScraper{

    override fun scrapFromLink(link: URL):Recipe {
        val doc = ScrappingHelper.getDocFromURL(link);


        val recipe = Recipe.create();
        recipe.link = link.toString();

        var recipeFragment = doc.select("[itemtype=\"https://data-vocabulary.org/Recipe\"]");

        recipe.name = recipeFragment.select("h1[itemprop=name]")[0].text();

        var timeString = recipeFragment.select(".clearfix.nosalty-recept-bottom-section div.right-text.dont-print").text()

        var regex = Regex("(?<value>[0-9]*) perc")
        var match = regex.findAll(timeString).toList();
        recipe.totalTime = Duration.ofMinutes(match[0].groups[1]?.value?.toLong()?:0).seconds
        recipe.prepTime = Duration.ofMinutes(match[1].groups[1]?.value?.toLong()?:0).seconds
        recipe.cookTime = Duration.ofMinutes(match[2].groups[1]?.value?.toLong()?:0).seconds


        recipe.yields = recipeFragment.select("[itemprop=yield]")[0].text()
        recipe.image = doc.select("link[rel=image_src]").attr("href")
        recipe.description = recipeFragment.select("[itemprop=summary]").select("p").text()

        recipe.ingredients = recipeFragment.select("[itemprop=\"ingredient\"]").map {
                element -> Ingredient(element.text(),"");
        }

        recipe.directions = recipeFragment.select(".recept-elkeszites.dont-print .column-block-content ol").select("li").mapIndexed {
                id, element -> RecipeStep(id+1,element.text());
        }

        return recipe;
    }

    override fun getSourceID(): String {
        return "nosalty"
    }


    companion object{
        val urls = listOf<String>(
            "www.nosalty.hu",
            "nosalty.hu",
            "m.nosalty.hu"
        )

        fun Register(registry: ScraperRegistry){

            registry.Register(urls,Nosalty())
        }
    }

}