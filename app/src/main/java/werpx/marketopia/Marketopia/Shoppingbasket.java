package werpx.marketopia.Marketopia;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import werpx.marketopia.R;
import werpx.marketopia.RoomDatabase.historytable;
import werpx.marketopia.RoomDatabase.mytable;
import werpx.marketopia.SwipeToDeleteCallback;
import werpx.marketopia.Utils;
import werpx.marketopia.adapters.Recycleadapter;

public class Shoppingbasket extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar mytoolbar;
    private NavigationView mynavigation;
    private Utils utils;
 //   public productViewmodel mWordViewModel;
    private  List<mytable> myproducts;
    private Recycleadapter mAdapter;
    private RecyclerView orderrecycle;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog downloadproductprogress;
    private BroadcastReceiver receiver;
    private IntentFilter filter;
    private ImageView camerascan;
    ImageView removeimg,saveimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingbasket);

        utils=new Utils(this);
        camerascan=findViewById(R.id.scancameraa);

        removeimg=findViewById(R.id.removeorderimg);
        saveimg=findViewById(R.id.saveorderimg);



        sharedPreferences=getSharedPreferences("marketids",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("categoryid","4BB24B08-6F74-4A30-9806-124293F2D262");
        editor.putString("producerid","E007875D-6CD1-4D3E-9CD0-0AE6DAF1AED7");
        editor.putString("unitid","F5A99CB5-924D-4D40-8404-E31C7EF6020F");
        editor.apply();


        mAdapter=new Recycleadapter(this);




        orderrecycle=findViewById(R.id.orderrecyclee);
        intilizeviews();

        utils.sslconnection();
        setUpRecyclerView();

        setupbutton();









    }



    public void setupbutton()
    {
        removeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          //      mWordViewModel.deleteallmenu();
            }
        });

        saveimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insetordertohistory();

            }
        });
    }


    public void insetordertohistory()
    {

        if (myproducts.size() == 0) {
            Toast.makeText(Shoppingbasket.this, getResources().getString(R.string.listempty), Toast.LENGTH_LONG).show();
        } else {

            ArrayList<String> barcodes, quantities, retailerids, productsids;
            ArrayList<Boolean> lives = new ArrayList<>();
            barcodes = new ArrayList<>();
            quantities = new ArrayList<>();
            retailerids = new ArrayList<>();
            productsids = new ArrayList<>();
            lives = new ArrayList<>();
            int totalorderitems = 0;
            Double totalordercoast = 0.0;
            int i;
            for (i = 0; i < myproducts.size(); i++) {

                String currentproduct = myproducts.get(i).getPbar();

                Double currentcoast = Double.parseDouble(myproducts.get(i).getPprice());
                int items = myproducts.get(i).getPitemn();
                totalorderitems = totalorderitems + items;
                Double totalproduct = currentcoast * items;
                totalordercoast = totalordercoast + totalproduct;
                barcodes.add(currentproduct);
                quantities.add(String.valueOf(items));
                lives.add(true);


            }


            Locale locale = new Locale("ar", "KW");
            SimpleDateFormat sdf = new SimpleDateFormat("E, dd-MMMM-yy");
            Date currDate = new Date();
            String formattedDate = sdf.format(currDate);
            historytable myhis = new historytable(1, formattedDate, myproducts, String.valueOf(totalordercoast), String.valueOf(totalorderitems));
          //  mWordViewModel.inserthis(myhis);
         //   mWordViewModel.deleteallmenu();


            }

        }





    public void intilizeviews()
    {
       final TextView ordercoast;






        ordercoast=findViewById(R.id.totalorder);
        mytoolbar = findViewById(R.id.toolbarbasket);





        SharedPreferences sharedPreferences=getSharedPreferences("consumerinfo",Context.MODE_PRIVATE);
        String name=sharedPreferences.getString("name","");
        String img=sharedPreferences.getString("img","");




        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

/*

        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);

        mWordViewModel.getAllWords().observe(this, new Observer<List<mytable>>() {
            @Override
            public void onChanged(@Nullable final List<mytable> words) {

                mAdapter.setWords(words);
                myproducts = words;
                Double allcoast = 0.0;

                // Update the cached copy of the words in the adapter.

                for (int i = 0; i < words.size(); i++) {

                    Double unitprice = Double.parseDouble(words.get(i).getPprice());
                    int quantity = words.get(i).getPitemn();
                    Double productcoast = unitprice * quantity;
                    allcoast = allcoast + productcoast;

                }
             ordercoast.setText(new DecimalFormat("##.##").format(allcoast));



            }


        });


*/


    }


    private void setUpRecyclerView() {
        orderrecycle.setAdapter(mAdapter);
        orderrecycle.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(mAdapter,this));
        itemTouchHelper.attachToRecyclerView(orderrecycle);
    }






    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return true;
    }

    public void scrolltoposition ( final int pos){


        orderrecycle.post(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getItemCount() == 0) {

                } else {
                    orderrecycle.smoothScrollToPosition(pos);
                }

            }
        });

    }

    public void scrolltodown () {

        orderrecycle.post(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getItemCount() == 0) {

                } else {
                    orderrecycle.smoothScrollToPosition(mAdapter.getItemCount() + 1);
                }

            }
        });

    }


}


