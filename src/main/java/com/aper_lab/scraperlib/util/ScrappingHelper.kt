package com.aper_lab.scraperlib.util

import com.aper_lab.scraperlib.util.gsonAdapters.AlwaysListTypeAdapterFactory
import com.aper_lab.scraperlib.util.gsonAdapters.RuntimeTypeAdapterFactory
import com.aper_lab.scraperlib.util.schemaOrg.Image
import com.aper_lab.scraperlib.util.schemaOrg.JsonLDData
import com.aper_lab.scraperlib.util.schemaOrg.Recipe
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URL
import java.nio.charset.Charset
import java.util.*

object ScrappingHelper {

    val recipeTypeRegex = Regex("\"@type\":( *)\"Recipe\"")

    val gson = GsonBuilder().create();

    /*
    * Downloads the html content at the given URL
     */
    fun getDocFromURL(link: URL): Document {
        val doc = Jsoup.connect(link.toString()).followRedirects(true).get()
        doc.charset(Charset.forName("UTF-8"))
        return doc
    }

    ///Finds the script tag containing the recipe type (" "@context":"http://schema.org","@type":"Recipe" ") tag
    ///returns the text inside the script tag
    fun findJsonLD(doc: Document):String?{
        val jsonLDTags = doc.select("script[type=\"application/ld+json\"]");
        for (tag in jsonLDTags){
            val text = tag.data();
            if(text.contains(recipeTypeRegex)){
                //This does contain a recipe description
                return text;
            }
        }
        return null;
    }

    /*
    * Parses the json into a SchemaORg.Recipe
     */
    fun recipeFromJsonLD(jsonLD: String): Recipe? {

        val runtimeTypeAdapterFactory: RuntimeTypeAdapterFactory<JsonLDData> = RuntimeTypeAdapterFactory
            .of(JsonLDData::class.java, "@type")
            .registerSubtype(Recipe::class.java, "Recipe")
            //.registerDefaultSubtype(JsonLDData::class.java)
            .registerSubtype(JsonLDData::class.java, "BreadcrumbList")



        val test = AlwaysListTypeAdapterFactory<ArrayList<JsonLDData>>();

        val gson = GsonBuilder()
            .registerTypeAdapterFactory(test)
            .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
            .create()

        val requestListTypeToken: TypeToken<List<JsonLDData>> =
            object : TypeToken<List<JsonLDData>>() {}

        val turnsType = object : TypeToken<List<JsonLDData>>() {}.type
        var a = gson.fromJson<List<JsonLDData>>(jsonLD, turnsType);

        for (item in a){
            if(item is Recipe){
                return item;
            }
        }
        return null
    }

    /*
    * Checks and parses the Json LD recipe in the given document
     */
    fun checkWebsiteForJsonLDRecipe(doc: Document): Recipe?{
        val jsonLDText = findJsonLD(doc);
        if(jsonLDText != null)
        return recipeFromJsonLD(jsonLDText);

        return null;
    }

    fun getImageURLFromAny(obj: Any?): String{
        if(obj is Map<*, *>){
            return obj["url"].toString();
        }
        if(obj is String){
            return obj
        }
        return "";
    }
}

inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)