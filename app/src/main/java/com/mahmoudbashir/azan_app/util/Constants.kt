package com.mahmoudbashir.azan_app.util

import android.R.drawable
import com.mahmoudbashir.azan_app.R

class Constants {
    companion object{
        const val BASE_URL = "https://api.pray.zone/"
        const val QURAN_BASE_URL = "http://api.alquran.cloud/"

        fun getListStringDrawable():List<String>{
            val mlist:ArrayList<String> = ArrayList();
            for (elem in 1..604){
                val m = "p${elem}"
                mlist.add(m)
            }
            return mlist
        }
        val namesList = arrayOf(
            "al-fatha",
            "al-baqarah",
            "al-emran",
            "al-nesaa",
            "al-maedah",
            "al-anaam",
            "al-aaraf",
            "al-anfal",
            "al-tawbah",
            "yunis",
            "hude",
            "yusif",
            "al-raad",
            "ebrahim",
            "al-hajar",
            "al-nahl",
            "al-esraa",
            "al-kahf",
            "mariam",
            "taha",
            "al-anbiaa",
            "al-haje",
            "al-moamnon",
            "al-nor",
            "al-forqan",
            "al-shoaraa",
            "al-naml",
            "al-qasas",
            "al-aanqabot",
            "al-rom",
            "loqman",
            "al-sajdah",
            "al-ahzab",
            "al-sabae",
            "fater",
            "yasine",
            "al-safat",
            "sad",
            "al-zomor",
            "ghafr",
            "foslt",
            "al-shoraa",
            "al-zokrof",
            "al-dokhan",
            "al-jasia",
            "al-ahqaf",
            "mohamed",
            "al-fath",
            "al-hojrat",
            "qaf",
            "al-zariat",
            "al-tor",
            "al-najm",
            "al-qamar",
            "al-rhman",
            "al-waqeaa",
            "al-haded",
            "al-mojadalah",
            "al-hashr",
            "al-momtahana",
            "al-saf",
            "al-jomoah",
            "al-mnafqon",
            "al-tagabn",
            "al-talaq",
            "al-tahrim",
            "al-molk",
            "al-qalam",
            "al-haqah",
            "al-maarj",
            "nouh",
            "al-jn",
            "al-mozaml",
            "al-modasr",
            "al-qiamah",
            "al-ensan",
            "al-morsalat",
            "al-nabaa",
            "al-nazaat",
            "abasah",
            "al-taqweer",
            "al-enftar",
            "al-motaffen",
            "al-enshqaq",
            "al-brooj",
            "al-tarq",
            "al-aalaa",
            "al-gashiah",
            "al-fajr",
            "al-balad",
            "al-shams",
            "al-liel",
            "al-daha",
            "al-sharh",
            "al-teen",
            "al-alaq",
            "al-qadr",
            "al-bienah",
            "al-zalzalah",
            "al-aadiat",
            "al-qareah",
            "al-taqasor",
            "al-asr",
            "al-homzah",
            "al-feel",
            "qurish",
            "al-maaon",
            "al-qawsr",
            "al-kafron",
            "al-nasr",
            "al-masad",
            "al-ekhlas",
            "al-falaq",
            "al-nas"
        )
    }


}