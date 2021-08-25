package com.aakash.bpibs.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.bpibs.Adapters.DrawerAdapter;
import com.aakash.bpibs.DrawerItem;
import com.aakash.bpibs.NavigationFragments.AboutUs;
import com.aakash.bpibs.NavigationFragments.DashboardFragment;
import com.aakash.bpibs.NavigationFragments.MyProfile;
import com.aakash.bpibs.R;
import com.aakash.bpibs.SimpleItem;
import com.aakash.bpibs.SpaceItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {


    private static final int STORAGE_PERMISSION_CODE = 4362;

    private boolean doubleBackToExitPressedOnce = false;
    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_MY_PROFILE = 2;
    private static final int POS_ABOUT_US = 3;
    private static final int POS_LOGOUT = 5;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkSession(currentUser);


        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();


        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_MY_PROFILE),
                createItemFor(POS_ABOUT_US),
                new SpaceItem(260),
                createItemFor(POS_LOGOUT)

        ));

        adapter.setListener(this);


        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);


        adapter.setSelected(POS_DASHBOARD);

    }

    private String[] loadScreenTitles() {


        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }


    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.blue))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.blue))
                .withSelectedTextTint(color(R.color.blue));


    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }


    private Drawable[] loadScreenIcons() {

        TypedArray typedArray = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[typedArray.length()];

        for (int i = 0; i < typedArray.length(); i++) {
            int id = typedArray.getResourceId(i, 0);

            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }

        typedArray.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
            return;


        ActivityCompat.requestPermissions(this, new String[]
                {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                }, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onItemSelected(int position) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (position == POS_DASHBOARD) {
            DashboardFragment dashboardFragment = new DashboardFragment();
            transaction.replace(R.id.container, dashboardFragment);
        }


        if (position == POS_MY_PROFILE) {
            MyProfile myProfile = new MyProfile();
            transaction.replace(R.id.container, myProfile);
        }


        if (position == POS_ABOUT_US) {
            AboutUs aboutUs = new AboutUs();
            transaction.replace(R.id.container, aboutUs);
        }

        if (position == POS_LOGOUT) {
            finish();
        }

        slidingRootNav.closeMenu();
        transaction.commit();

    }


    private void checkSession(FirebaseUser currentUser) {

        if (currentUser != null) {
            String mobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Student").child(mobile);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Boolean is_Verified = dataSnapshot.child("verified").getValue(Boolean.class);
                    if (is_Verified == null) {
                        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
                        finish();

                    } else if (!is_Verified) {
                        Rect displayRectangle = new Rect();
                        Window window = MainActivity.this.getWindow();
                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_view, viewGroup, false);
                        dialogView.setMinimumHeight((int) (displayRectangle.height() * 1f));
                        dialogView.setMinimumWidth((int) (displayRectangle.width() * 1f));
                        builder.setView(dialogView);
                        builder.setCancelable(false);
                        final AlertDialog alertDialog = builder.create();
                        Button exitButton = dialogView.findViewById(R.id.button_exit);
                        exitButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                closeNow();
                            }
                        });


                        try {
                            alertDialog.show();
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Please restart the App.", Toast.LENGTH_SHORT).show();
                            closeNow();
                        }


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Something unexpected happened.", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }

    private void closeNow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        } else {
            finish();
        }
    }


}