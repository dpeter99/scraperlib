package com.aper_lab.scraperlib

import com.aper_lab.scraperlib.api.RecipeScraper

class ScraperRegistry {

    var registy = mutableListOf<RecipeScraper>();
    var mapping = mutableMapOf<String,RecipeScraper>();


    fun Register(urls: List<String>,scraper: RecipeScraper){
        registy.add(scraper);
        for (url in urls){
            mapping.put(url,scraper);
        }
    }


}