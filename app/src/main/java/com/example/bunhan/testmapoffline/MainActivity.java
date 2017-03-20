package com.example.bunhan.testmapoffline;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polygon;


public class MainActivity extends Activity {
    Button bt1;
    double count = 0.000005;
    int c = 0;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MapView map = (MapView) findViewById(R.id.mapview);
        bt1 = (Button)findViewById(R.id.bt1);
       //map.setTileSource(TileSourceFactory.MAPNIK);
        map.setTileSource(new XYTileSource("Google-hybridni-256", 0, 20, 256, ".jpg", new String[] {}));
        //....
        map.setUseDataConnection(false); //optional, but a good way to prevent loading from the network and test your zip loading.
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        final IMapController mapX = map.getController();

        mapX.setZoom(20);
        GeoPoint startPoint = new GeoPoint(18.7830806,98.9789106);
        mapX.setCenter(startPoint);


        Polygon circle = new Polygon();
        circle.setPoints(Polygon.pointsAsCircle(startPoint, 0.3));

        GeoPoint spTest = new GeoPoint(18.7830856,98.9789106);
        Polygon sq = new Polygon();
        sq.setPoints(Polygon.pointsAsCircle(spTest, 0.3));

        circle.setFillColor(0x2F00FF00);  //transparent green
        circle.setStrokeColor(0x00000000);//  transparent
        circle.setStrokeWidth(1);

        sq.setFillColor(0x2FFF0000);  //transparent red //
        sq.setStrokeColor(0x00000000);//  transparent
        sq.setStrokeWidth(1);

        //map.getOverlays().add(circle);
        //map.getOverlays().add(sq);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GeoPoint x = new GeoPoint(18.7830856 + count,98.9789106);
                Polygon sq2 = new Polygon();
                if(c % 2 == 0){
                    sq2.setFillColor(0x2FFF0000);  //transparent red //
                }else{
                    sq2.setFillColor(0x2F00FF00);  //transparent green //
                }

                sq2.setStrokeColor(0x00000000);//  transparent
                sq2.setStrokeWidth(1);
                sq2.setPoints(Polygon.pointsAsCircle(x, 0.3));

                map.getOverlays().add(sq2);
                map.invalidate();
                count += 0.000005;
                c++;
                Log.i("Connected","Now I'm connected with netpie");
            }
        });






    }




    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }
}