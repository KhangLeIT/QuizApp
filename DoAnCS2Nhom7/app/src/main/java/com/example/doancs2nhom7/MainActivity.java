package com.example.doancs2nhom7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doancs2nhom7.databinding.ActivityMainBinding;
import com.example.doancs2nhom7.fragment.AccountFragment;
import com.example.doancs2nhom7.fragment.CategoryFragment;
import com.example.doancs2nhom7.fragment.LeaderBoardFragment;
import com.example.doancs2nhom7.query.DbQuery;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemReselectedListener;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.NavigationUI;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private BottomNavigationView bottomNavigationView;
    private FrameLayout main_frame;
    private TextView drawerProfileName, drawerProfileText;



    private OnNavigationItemReselectedListener onNavigationItemReselectedListener =
            new OnNavigationItemReselectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {
                    switch (item.getItemId())
                    {
                        case R.id.navigation_home:
                            //bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                            setFragement(new CategoryFragment());
                            item.setChecked(true);
                            return;

                        case R.id.navigation_account:
                            //bottomNavigationView.setSelectedItemId(R.id.navigation_account);
                            setFragement(new AccountFragment());
                            item.setChecked(true);
                            return;

                        case  R.id.navigation_leaderboard:
                            //bottomNavigationView.setSelectedItemId(R.id.navigation_leaderboard);
                            setFragement(new LeaderBoardFragment());
                            item.setChecked(true);

                            return;
                    }
                    item.setChecked(false);
                    return;

                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Categories");

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        main_frame = findViewById(R.id.main_frame);

        bottomNavigationView.setOnNavigationItemReselectedListener(onNavigationItemReselectedListener);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

        drawerProfileName = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_name);
        drawerProfileText = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_text_img);

        String name = DbQuery.myProfile.getName();
        drawerProfileName.setText(name);
        drawerProfileText.setText(name.toUpperCase().substring(0,1));

        setFragement(new CategoryFragment());

    }

    private void setFragement(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(),fragment);
        transaction.commit();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            setFragement(new CategoryFragment());

        }else if (id == R.id.nav_account){
            setFragement(new AccountFragment());

        }else if (id == R.id.nav_leaderboard){
            setFragement(new LeaderBoardFragment());

        }else if (id == R.id.nav_bookmark)
        {
            Intent intent = new Intent(MainActivity.this, BookmarksActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
