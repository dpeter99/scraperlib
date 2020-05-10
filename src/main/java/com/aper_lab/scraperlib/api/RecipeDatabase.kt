package com.aper_lab.scraperlib.api

import com.aper_lab.scraperlib.data.Recipe

interface DatabaseConnection {

    @Deprecated("Use the data specific functions")
    fun storeData(path: String, data: IHasID);

    //#################################
    //RECIPE
    //#################################
    suspend fun getRecipeByURL(url: String):Recipe?;

    suspend fun getRecipeByID(id: String):Recipe?;

    fun updateRecipe(recipe: Recipe);

    fun storeRecipe(recipe: Recipe);
}