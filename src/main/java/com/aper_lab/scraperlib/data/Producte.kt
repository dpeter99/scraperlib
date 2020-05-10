package com.aper_lab.scraperlib.data

class Producte() {
    var name = "";
    var alternativeNames: MutableList<String> = mutableListOf();

    var description = "";
    var barcode = "";

    var productGroup = "";

    var picture = "";



    fun isAcceptableName(name: String):Boolean{
        if(this.name == name){
            return true;
        }
        return alternativeNames.find { alter_name -> alter_name == name }.isNullOrBlank()
    }
}