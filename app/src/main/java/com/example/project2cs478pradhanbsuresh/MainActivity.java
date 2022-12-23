package com.example.project2cs478pradhanbsuresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.LinkAddress;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> animalList; // animalList is a list that stores the names of the animals
    List<Integer> imageList; // imageList is a list that stores the images of the animals
    String URL; // URL stores the wikipedia page url for each animal
    String layoutState = "GridView"; // layoutState keeps track of change in device configuration
    String layout = "Grid";
    RecyclerViewListener recyclerViewListener;
    protected static final String EXTRA_RES_ID = "POS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view); //Recycler View Object is created

        animalList = Arrays.asList("Raccoon","Dog","Cat","Tiger","Vulture","Cheetah","Lion","Panther","Fox","Panda");
        imageList = Arrays.asList(R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5,R.drawable.image6,R.drawable.image7,R.drawable.image8,R.drawable.image9,R.drawable.image10);

//        RecyclerViewListener
        if(savedInstanceState!=null){
//            if()
//            {
//
//            }
            layout = savedInstanceState.getString("layout");
            if(savedInstanceState.getString("layoutState").equals("GridView") && layout.equals("Grid")){
                recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            }
            else{
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }// whenever the device orientation is changed it is tracked and maintained here
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
                recyclerViewListener = (view, position)->{ // Define the recycler view listener with lambda that handles the image click event and redirects to the wikipedia page of the corresponding animal using implicit intent
            URL = "https://en.wikipedia.org/wiki/"+animalList.get(position); //wikipedia url for the corresponding animal
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL)); //Created an implicit intent
            startActivity(intent);
        };
        AdapterItem adapterItem = new AdapterItem(animalList,imageList,recyclerViewListener,EXTRA_RES_ID,layout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterItem);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        String currentLayoutManager = recyclerView.getLayoutManager().toString(); //currentLayoutManager stores the layout manager as a string
        if((item.getItemId() == R.id.listView) && (currentLayoutManager.substring(29,currentLayoutManager.indexOf('@')).equalsIgnoreCase("LinearLayoutManager"))){ //Checking if the menu option selected is same as the current layout view to display the toast message
            Toast.makeText(this, "Already using ListView", Toast.LENGTH_SHORT).show(); // Displays the toast message "Already using ListView" if already in list view
        }
        if((item.getItemId() == R.id.gridView) && (currentLayoutManager.substring(29,currentLayoutManager.indexOf('@')).equalsIgnoreCase("GridLayoutManager"))){ //Checking if the menu option selected is same as the current layout view to display the toast message
            Toast.makeText(this, "Already using GridView", Toast.LENGTH_SHORT).show(); // Displays the toast message "Already using GridView" if already in grid view
        }
switch (item.getItemId()){ // Based on the option selected list or grid view is set
    case R.id.listView:
        layoutState = "ListView";
        layout = "List";
        AdapterItem ladapterItem = new AdapterItem(animalList,imageList,recyclerViewListener,EXTRA_RES_ID,layout);
        recyclerView.setAdapter(ladapterItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // LinearLayoutManager is set if the option selected is list view
        return true;
    case R.id.gridView:
        layoutState = "GridView";
        layout = "Grid";
        AdapterItem gadapterItem = new AdapterItem(animalList,imageList,recyclerViewListener,EXTRA_RES_ID,layout);
        recyclerView.setAdapter(gadapterItem);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2)); // GridLayoutManager is set with 2 columns if the option selected is grid view
        return true;
    default:
        return false;
}
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) { //onSaveInstanceState method saves the state of the variables during the change in configuration of the device
        super.onSaveInstanceState(outState);
        outState.putString("layoutState",layoutState);
        outState.putString("layout",layout);
    }
}