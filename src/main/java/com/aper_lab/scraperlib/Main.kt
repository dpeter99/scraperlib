package com.aper_lab.scraperlib

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main(args: Array<String>) = runBlocking{

    val sites = listOf<String>(
        //"https://www.allrecipes.com/recipe/83557/juicy-roasted-chicken/",
        //"https://tasty.co/recipe/one-pot-garlic-parmesan-pasta",
        //"https://tasty.co/recipe/white-chocolate-rainbow-frozen-banana",
        //"http://www.foodnetwork.co.uk/recipes/maple-glazed-ham.html",
        //"https://www.delish.com/cooking/recipe-ideas/recipes/a52182/hot-cross-buns-recipe/"
        //"http://www.mindmegette.hu/husgomboc-sajtos-tesztaval.recept/",
        //"https://www.nosalty.hu/recept/egeszben-sult-citromos-csirke"
        "https://tasty.co/recipe/parchment-tomato-pesto-salmon"
    )

    for (site in sites) {
        launch {
            var a = RecipeAPIService.getRecipeFromURLAsync(site, false).await();
            //print(a.toString())
        }
    }


}


