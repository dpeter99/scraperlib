package com.aper_lab.scraperlib.data

import java.util.*

class Ingredient (name:String, amount:String){
    var name:String =name;
    var amount: String = amount;

    constructor() : this("","") {

    }

    companion object{
        fun from(text: String, locale: Locale){

        }
    }
}