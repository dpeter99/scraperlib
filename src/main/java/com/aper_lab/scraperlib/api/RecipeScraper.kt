package com.aper_lab.scraperlib.api

import com.aper_lab.scraperlib.ScraperRegistry
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.scrapers.Allrecipes
import java.net.URL

interface RecipeScraper {

    fun scrapFromLink(link: URL):Recipe;

    fun getSourceID():String;

    fun processIngredient(){

    }
}