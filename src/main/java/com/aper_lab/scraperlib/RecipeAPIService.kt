package com.aper_lab.scraperlib

import com.aper_lab.scraperlib.api.DatabaseConnection
import com.aper_lab.scraperlib.data.Data
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.datastore.DataStore
import com.aper_lab.scraperlib.scrapers.*
import com.aper_lab.scraperlib.util.HashUtils
import com.aper_lab.scraperlib.util.URLutils
import kotlinx.coroutines.*
//import com.sun.org.apache.xpath.internal.operations.Bool
import java.net.URL

object RecipeAPIService {

    //val scraper = Scraper();

    var registry = ScraperRegistry();

    val dataStore = DataStore();

    val scope = MainScope();

    init {
        Allrecipes.Register(registry);
        Delish.Register(registry);
        //Mindmegette.Register(registry);
        Nosalty.Register(registry);
        Tasty.Register(registry);
    }

    fun initApi(db: DatabaseConnection){
        dataStore.Init(db);
    }


    fun getRecipeFromURLAsync(url:String, storeRecipe: Boolean): Deferred<Recipe?> {
        return GlobalScope.async{

            var rec = dataStore.getRecipebyURL(url);
            if(rec == null) {
                rec = scrape(url);

                if (rec != null && storeRecipe) {
                    dataStore.addRecipe(rec);
                }

            }

            rec;
        };
    }

    fun getRecipeFromUrl(url:String, storeRecipe: Boolean): Recipe?{
        var r:Recipe? = null;
        runBlocking {
            r = getRecipeFromURLAsync(url, storeRecipe).await()
        }
        return r;
    }

    fun saveRecipeToDB(rec: Recipe){
        dataStore.addRecipe(rec);
    }

    fun getRecipeByIDAsync(id: String): Deferred<Recipe?>{
        return GlobalScope.async{

            var rec = dataStore.getRecipebyID(id);
            if(rec == null) {
                rec = Recipe.create();
            }
            rec = checkRecipeVersion(rec);
            rec;
        };
    }


    fun getSourceIDfromURL(url:String): String{
        val link = URL(url);
        return registry.mapping[link.host]?.getSourceID()?: "";
    }



    private fun checkRecipeVersion(rec: Recipe): Recipe?{
        var result: Recipe? = rec;

        if (rec.version != Data.DATA_VERSION || rec.version == "") {
            result = scrape(rec.link);
            if (result != null) {
                result.id = HashUtils.md5(rec.name);
                result.version = Data.DATA_VERSION;

                dataStore.updateRecipe(result);
            }
        }
        return result;
    }

    private fun scrape(path: String): Recipe?{
        val url_parsed = URLutils.HTTPToHTTPS(path);
        var url = URL(url_parsed);

        val rec = registry.mapping[url.host]?.scrapFromLink(url);
        if(rec != null) {
            rec.id = HashUtils.md5(rec.name);
            rec.version = Data.DATA_VERSION;

            //println("-------------------------------------------")
            //println(rec.toString());
        }

        return rec;
    }
}