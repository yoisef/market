package werpx.marketopia;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;

import retrofit2.Call;
import werpx.marketopia.RoomDatabase.Productltable;
import werpx.marketopia.productmodels.getallproductsroot;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Utils {

    Context con;
    private Call<getallproductsroot> callproducts;
   // private productViewmodel mWordViewModel;


    private productdatabase mydatabase;
    DbBitmapUtility dbBitmapUtility;
    List<Productltable> products;



    public Utils(Context context)
    {
        this.con=context;
        mydatabase = new productdatabase(context);
        dbBitmapUtility=new DbBitmapUtility();
//        mWordViewModel = ViewModelProviders.of((FragmentActivity) context).get(productViewmodel.class);

    }

    public String gettoken()
    {
        String token=con.getSharedPreferences("login",Context.MODE_PRIVATE).getString("token","");
        return token;
    }

    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    public String convertToEnglish (String value){

        String newValue = (((((((((((value + "")
                .replaceAll("١", "1")).replaceAll("٢", "2"))
                .replaceAll("٣", "3")).replaceAll("٤", "4"))
                .replaceAll("٥", "5")).replaceAll("٦", "6"))
                .replaceAll("٧", "7")).replaceAll("٨", "8"))
                .replaceAll("٩", "9")).replaceAll("٠", "0"));
        return newValue;
    }

    public void sslconnection()
    {
        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }





    public DbBitmapUtility getDbBitmapUtility() {
        return dbBitmapUtility;
    }





    public productdatabase getMydatabase() {
        return mydatabase;
    }

}
