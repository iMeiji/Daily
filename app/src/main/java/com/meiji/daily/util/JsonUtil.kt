package com.meiji.daily.util

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Meiji on 2018/2/6.
 */

object JsonUtil {

    private const val JSON_INDENT = 2

    /**
     * @param jsonStr string param expect a json string
     * @return formatted json string if param is a json string,otherwise return the param
     */
    @Throws(JSONException::class)
    fun convert(jsonStr: String): String {
        val json = jsonStr.trim()
        return when {
            json.startsWith("{") && json.endsWith("}") -> {
                JSONObject(json).toString(JSON_INDENT)
            }

            json.startsWith("[") && json.endsWith("]") -> {
                JSONArray(json).toString(JSON_INDENT)
            }

            else -> json
        }
    }
}
