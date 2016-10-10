package com.register.application;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.register.application.view.BasicFragment;
import com.register.application.view.BillsToPayListScreenFragment;
import com.register.application.view.ClientsListScreenFragment;
import com.register.application.view.EmployeesListScreenFragment;
import com.register.application.view.ProductsListScreenFragment;
import com.register.application.view.ProvidersListScreenFragment;

public class MainScreenLayoutActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private FragmentTransaction transaction;
    private TextView mTitle;

    private ClientsListScreenFragment mClientsListScreenFragment = null;
    private EmployeesListScreenFragment mEmployeesListScreenFragment = null;
    private ProvidersListScreenFragment mProvidersListScreenFragment = null;
    private ProductsListScreenFragment mProductsListScreenFragment = null;
    private BillsToPayListScreenFragment mBillsToPayListScreenFragment = null;
    private BasicFragment basicFragment;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_custom);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer); //Setup drawer view

        if (savedInstanceState == null) {
            startConfiguration();
            initFragments();
        }

        //Tie DrawerLayout events to the ActionToggle
        //mDrawer.addDrawerListener(drawerToggle);

    }

    private void startConfiguration() {
        fragment = basicFragment = new BasicFragment();
        try {

            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flContent, basicFragment);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    public void initFragments() {

        transaction = getSupportFragmentManager().beginTransaction();

        mClientsListScreenFragment = new ClientsListScreenFragment();
        transaction.add(R.id.flContent, mClientsListScreenFragment);
        transaction.hide(mClientsListScreenFragment);

        mEmployeesListScreenFragment = new EmployeesListScreenFragment();
        transaction.add(R.id.flContent, mEmployeesListScreenFragment);
        transaction.hide(mEmployeesListScreenFragment);

        mProvidersListScreenFragment = new ProvidersListScreenFragment();
        transaction.add(R.id.flContent, mProvidersListScreenFragment);
        transaction.hide(mProvidersListScreenFragment);

        mProductsListScreenFragment = new ProductsListScreenFragment();
        transaction.add(R.id.flContent, mProductsListScreenFragment);
        transaction.hide(mProductsListScreenFragment);

        mBillsToPayListScreenFragment = new BillsToPayListScreenFragment();
        transaction.add(R.id.flContent, mBillsToPayListScreenFragment);
        transaction.hide(mBillsToPayListScreenFragment);

        transaction.commit();
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        transaction = getSupportFragmentManager().beginTransaction();

        switch (menuItem.getItemId()) {
            case R.id.nav_clients_fragment:
                showListClients();
                break;

            case R.id.nav_employees_fragment:
                showListEmployees();
                break;

            case R.id.nav_providers_fragment:
                showListProviders();
                break;

            case R.id.nav_products_fragment:
                showListProducts();
                break;

            case R.id.nav_bills_to_pay_fragment:
                showListBillsToPay();
                break;

            default:
                try {
                    transaction.hide(mClientsListScreenFragment);
                    transaction.hide(mEmployeesListScreenFragment);
                    transaction.hide(mProvidersListScreenFragment);
                    transaction.hide(mProductsListScreenFragment);
                    transaction.hide(mBillsToPayListScreenFragment);
                    transaction.show(basicFragment);
                    transaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return;
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    private void showListClients() {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(basicFragment);
        transaction.hide(mProductsListScreenFragment);
        transaction.hide(mEmployeesListScreenFragment);
        transaction.hide(mProvidersListScreenFragment);
        transaction.hide(mBillsToPayListScreenFragment);
        transaction.show(mClientsListScreenFragment);
        mClientsListScreenFragment.getListClients();
        transaction.addToBackStack("Back");
        transaction.commit();
    }

    private void showListEmployees() {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mClientsListScreenFragment);
        transaction.hide(mProvidersListScreenFragment);
        transaction.hide(mProductsListScreenFragment);
        transaction.hide(mBillsToPayListScreenFragment);
        transaction.hide(basicFragment);
        transaction.show(mEmployeesListScreenFragment);
        mEmployeesListScreenFragment.showData();
        transaction.addToBackStack("Back");
        transaction.commit();
    }

    private void showListProviders() {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mClientsListScreenFragment);
        transaction.hide(mEmployeesListScreenFragment);
        transaction.hide(mProductsListScreenFragment);
        transaction.hide(mBillsToPayListScreenFragment);
        transaction.hide(basicFragment);
        transaction.show(mProvidersListScreenFragment);
        mProvidersListScreenFragment.showData();
        transaction.addToBackStack("Back");
        transaction.commit();
    }

    private void showListProducts() {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mClientsListScreenFragment);
        transaction.hide(mEmployeesListScreenFragment);
        transaction.hide(basicFragment);
        transaction.hide(mProvidersListScreenFragment);
        transaction.hide(mBillsToPayListScreenFragment);
        transaction.show(mProductsListScreenFragment);
        mProductsListScreenFragment.showData();
        transaction.addToBackStack("Back");
        transaction.commit();
    }

    private void showListBillsToPay() {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mClientsListScreenFragment);
        transaction.hide(mEmployeesListScreenFragment);
        transaction.hide(basicFragment);
        transaction.hide(mProvidersListScreenFragment);
        transaction.hide(mProductsListScreenFragment);
        transaction.show(mBillsToPayListScreenFragment);
        mBillsToPayListScreenFragment.getListBillsToPay();
        transaction.addToBackStack("Back");
        transaction.commit();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        drawerToggle.onConfigurationChanged(newConfig);
    }

}