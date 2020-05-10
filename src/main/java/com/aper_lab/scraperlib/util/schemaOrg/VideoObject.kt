package com.aper_lab.scraperlib.util.schemaOrg

class VideoObject : JsonLDData() {

    var contentUrl: String = "";
    var description: String = "";

    var duration: String = "";

    var embedUrl: String = "";
    var name: String = "";
    var thumbnailUrl: String = "";
    var uploadDate: String = "";


    override fun getType(): String {
        return "VideoObject"
    }
}