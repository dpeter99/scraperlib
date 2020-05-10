package com.aper_lab.scraperlib.util.jsonAdapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.Duration

//import org.threeten.bp.Duration

class TimeadApter : TypeAdapter<Long>() {
    override fun write(out: JsonWriter?, value: Long?) {

        out?.beginObject()?.value(Duration.ofSeconds(value?:0).seconds);

    }

    override fun read(reader: JsonReader?): Long {

        return Duration.parse(reader.toString()).seconds;

    }

}