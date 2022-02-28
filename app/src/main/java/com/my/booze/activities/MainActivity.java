package com.my.booze.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.my.booze.R;
import com.my.booze.utilities.Utilities;

public class MainActivity extends AppCompatActivity {

        NavController navController;
        ImageView ivDrawer;
        private DrawerLayout drawerLayout;
        NavigationView navigationView;
        CardView main_Container;
        RelativeLayout btn_Logout;
        ImageView btn_Cross;
        private static final float END_SCALE = 0.7f;
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.drawer_Nav);
            setTopStatusBarColor();

            ivDrawer = findViewById(R.id.menu_ic);
            main_Container = findViewById(R.id.main_Container);

            initNavigation();

            Utilities.saveString(MainActivity.this, "user_CartItemsCount", ""+0);

            View headerView =  navigationView.getHeaderView(0);
//            TextView userName = headerView.findViewById(R.id.text_userName);
//            TextView userEmail = headerView.findViewById(R.id.text_userEmail);
//            userName.setText(Utilities.getString(getApplicationContext(), "userName"));
//            userEmail.setText(Utilities.getString(getApplicationContext(), "userEmail"));
            btn_Logout = navigationView.findViewById(R.id.btn_Logout);
//            btn_Logout.setOnClickListener(v -> {
//                Utilities.clearSharedPref(getApplicationContext());
//                Intent mainIntent = new Intent(MainActivity.this, LoginActivityRegister.class);
//                startActivity(mainIntent);
//                finish();
//            });
            // Scale the View based on current slide offset
            // Translate the View, accounting for the scaled width

            ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                    this,
                    drawerLayout,
                    R.string.open,
                    R.string.closed
            ) {

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    // Scale the View based on current slide offset
                    final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                    final float offsetScale = 1 - diffScaledOffset;
                    main_Container.setScaleX(offsetScale);
                    main_Container.setScaleY(offsetScale);


                    // Translate the View, accounting for the scaled width
                    final float xOffset = drawerView.getWidth() * slideOffset;
                    final float xOffsetDiff = main_Container.getWidth() * diffScaledOffset / 2;
                    final float xTranslation = xOffset - xOffsetDiff;
                    main_Container.setTranslationX(xTranslation);
                }
            };

            drawerLayout.addDrawerListener(mDrawerToggle);

            drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));

            /*Remove navigation drawer shadow/fadding*/
            drawerLayout.setDrawerElevation(0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawerLayout.setElevation(0f);
                main_Container.setCardElevation(10.0f);
            }
        }

        private void initNavigation() {
            ivDrawer.setOnClickListener(v -> {
                drawerLayout.openDrawer(GravityCompat.START, true);
                /*Navigation drawer with transparent background*/
            });
            navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(navigationView, navController);
            View mView = LayoutInflater.from(MainActivity.this).inflate(R.layout.nav_header_main, null);
//            RelativeLayout item = (RelativeLayout)findViewById(R.id.nav_header_main);
//            View child = getLayoutInflater().inflate(R.layout.nav_header_main, null);
            btn_Cross = mView.findViewById(R.id.btn_Cross);
            btn_Cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    drawerLayout.close();
//                    Toast.makeText(MainActivity.this, "vmkv", Toast.LENGTH_SHORT).show();
                }
            });
//            navigationView.getMenu().findItem(R.id.navigation_about).setOnMenuItemClickListener(item -> {
//                startActivity(new Intent(MainActivity.this, AboutActivity.class));
//                drawerLayout.closeDrawer(GravityCompat.START, false);
//                return true;
//            });
//            navigationView.getMenu().findItem(R.id.navigation_ourCollections).setOnMenuItemClickListener(item -> {
//                startActivity(new Intent(MainActivity.this, ArtsCollectionActivity.class));
//                drawerLayout.closeDrawer(GravityCompat.START, false);
//                return true;
//            });
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void setTopStatusBarColor(){
            Window window = MainActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.colorPrimaryDark));
        }
}