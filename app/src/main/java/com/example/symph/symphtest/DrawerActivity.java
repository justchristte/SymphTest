package com.example.symph.symphtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.symph.symphtest.fragment.FragmentDrawer;
import com.example.symph.symphtest.fragment.FragmentFollowers;
import com.example.symph.symphtest.fragment.FragmentRepo;
import com.example.symph.symphtest.helper.Helper;
import com.example.symph.symphtest.object.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class DrawerActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    CircleImageView image;
    TextView userName;
    User user;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        image= (CircleImageView) findViewById(R.id.fragment_navigation_drawer_image);
        userName= (TextView) findViewById(R.id.fragment_navigation_drawer_username);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        actionBar=getSupportActionBar();
        if(actionBar!=null)
            actionBar.setDisplayShowHomeEnabled(true);

        FragmentDrawer drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        user=new User(getIntent().getExtras());
        image.setImageBitmap(Helper.decodeImage(user.getByteArray()));
        userName.setText(user.getLogin());
        displayView(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        Bundle bundle;
        bundle=new Bundle();
        bundle.putInt("userId",user.getId());
        switch (position) {
            case 0:
                fragment = new FragmentFollowers();
                title = "Followers";
                bundle.putBoolean("isFollower",true);
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new FragmentFollowers();
                title = "Followed";
                bundle.putBoolean("isFollower",false);
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment = new FragmentRepo();
                title = "Repos";
                fragment.setArguments(bundle);
                break;
            case 3:
                Helper.viewOnBrowser(this,"http://github.com/"+user.getLogin());
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            if(actionBar!=null)
                actionBar.setTitle(title);
        }
    }
}
