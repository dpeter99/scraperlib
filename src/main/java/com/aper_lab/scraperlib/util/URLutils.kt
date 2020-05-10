package com.aper_lab.scraperlib.util

import java.net.URL

object URLutils {

    fun HTTPToHTTPS(url: String):String{
        if(url.startsWith("http://")){
            val res = url.replace("http", "https");
            return res;
        }

        return url;
    }

}