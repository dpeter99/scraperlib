package com.aper_lab.scraperlib;

import com.aper_lab.scraperlib.api.RecipeScraper
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.scrapers.*
import com.aper_lab.scraperlib.util.HashUtils
import com.aper_lab.scraperlib.util.URLutils
import java.net.URL
/*
@Deprecated("Moved to the service object")
class Scraper {
    //var scrapers: MutableMap<String, RecipeScraper> = mutableMapOf();

    var registry = ScraperRegistry();

    init {
        Allrecipes.Register(registry);
        Delish.Register(registry);
        Mindmegette.Register(registry);
        Nosalty.Register(registry);
        Tasty.Register(registry);
    }

    fun scrape(path: String): Recipe?{
        val url_parsed = URLutils.HTTPToHTTPS(path);
        var url = URL(url_parsed);

        val rec = registry.mapping[url.host]?.scrapFromLink(url);
        if(rec != null) {
            rec.id = HashUtils.md5(rec.name);
            println("-------------------------------------------")
            println(rec.toString());
        }





        return rec;
    }
}
*/