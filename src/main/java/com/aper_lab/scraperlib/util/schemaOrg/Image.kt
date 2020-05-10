package com.aper_lab.scraperlib.util.schemaOrg

class Image : JsonLDData() {

    var url: String = "";

    override fun getType(): String {
        return "ImageObject"
    }
}