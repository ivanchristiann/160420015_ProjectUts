package id.ac.ubaya.informatika160420015_projectuts.model

import com.google.gson.annotations.SerializedName

data class Kost(
    val id:String?,
    val namaKost:String?,
    val alamatKost:String?,
    val nomorTelepon:String?,
    val jumlahKamar:Int?,
    val kamarTerpakai:Int?,
    val fasilitasKost:ArrayList<String>?,
    val harga:Int?,
    val tagLineKost:String?,
    val jenisKost:String?,
    val rating:Double,
    val urlPhoto:String?)
