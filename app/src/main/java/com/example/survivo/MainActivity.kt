package com.example.survivo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MainActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private var myLocationOverlay: MyLocationNewOverlay? = null

    private val shops = listOf(
        Shop("s1","Bakery Alnoor", 30.044420, 31.235712, "Cairo Downtown"),
        Shop("s2","Mini Market", 30.045000, 31.236500, "Near Park"),
        Shop("s3","Coffee Shop", 30.043500, 31.234000, "Mall Street")
    )

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                startLocationOverlay()
            } else {
                // للمبسّط: المستخدم رفض صلاحية الموقع، الخريطة ستظلّ تعمل دون موقع المستخدم
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // osmdroid configuration (cache dir)
        Configuration.getInstance().load(applicationContext, androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext))

        setContentView(R.layout.activity_main)

        map = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setZoom(15.0)
        map.controller.setCenter(GeoPoint(30.044420, 31.235712)) // مركز افتراضي

        // أضف markers للمتاجر
        addShopMarkers()

        // اطلب صلاحية الموقع إذا لم تُنحَ
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationOverlay()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun addShopMarkers() {
        shops.forEach { shop ->
            val marker = Marker(map)
            marker.position = GeoPoint(shop.lat, shop.lon)
            marker.title = shop.name
            marker.subDescription = shop.address
            marker.setOnMarkerClickListener { m, _ ->
                // عند النقر: عرض تفاصيل (هنا مؤقتًا تحريك الكاميرا)
                map.controller.animateTo(m.position)
                // لاحقًا: افتح Activity/BottomSheet لعرض قائمة المنتجات و زر الحجز
                true
            }
            map.overlays.add(marker)
        }
        map.invalidate()
    }

    private fun startLocationOverlay() {
        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), map)
        myLocationOverlay?.enableMyLocation()
        myLocationOverlay?.isDrawAccuracyEnabled = true
        map.overlays.add(myLocationOverlay)
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}