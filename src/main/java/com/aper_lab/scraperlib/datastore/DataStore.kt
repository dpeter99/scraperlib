package com.aper_lab.scraperlib.datastore

import com.aper_lab.scraperlib.api.DatabaseConnection
import com.aper_lab.scraperlib.data.Producte
import com.aper_lab.scraperlib.data.Recipe
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
//import com.google.api.client.json.gson.GsonFactory
//import com.google.api.services.kgsearch.v1.Kgsearch
//import com.google.api.services.kgsearch.v1.KgsearchRequest

class DataStore (){

    var recipes = mutableListOf<Recipe>()
    var ingredients = mutableListOf<Producte>()

    private var databaseConnection : DatabaseConnection? = null;

    fun Init(db: DatabaseConnection){
        databaseConnection = db;
    }

    fun addRecipe(recipe: Recipe){
        databaseConnection?.storeRecipe(recipe);
    }

    fun updateRecipe(recipe: Recipe){
        databaseConnection?.updateRecipe(recipe);
    }

    suspend fun getRecipebyID(id: String) :Recipe?{
        return databaseConnection?.getRecipeByID(id);
    }

    suspend fun getRecipebyURL(url: String):Recipe?{
        val res = databaseConnection?.getRecipeByURL(url)
        if(res == null) {
            return null;
        }
        else {
            return res;
        }
    }

    fun getIngredientByName(name: String){
        this.ingredients.find {
            ingredient -> ingredient.isAcceptableName(name)
        }

        /*
        var kgsearch = Kgsearch.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            GsonFactory(),
            null
        ).build()

        kgsearch.Entities().search()

         */

        println("ERROR: No suitable ingridient")
    }
}