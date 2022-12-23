package com.example.project2cs478pradhanbsuresh;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ViewHolder> {
    private List<String> animalNameList;// animalNameList is a list that stores the names of the animals
    private List<Integer> animalImageList;// animalImageList is a list that stores the images of the animals
    private RecyclerViewListener recyclerViewListener1;// listener defined in main activity
    private String EXTRA_RES_ID;
    private String layout;


// passing in the data and the listener defined in the main activity
    public AdapterItem(List<String> animalNameList, List<Integer> animalImageList, RecyclerViewListener recyclerViewListener1,String EXTRA_RES_ID,String layout) {
        this.animalNameList=animalNameList; // save list of names to be displayed passed by main activity
        this.animalImageList=animalImageList;// save list of images to be displayed passed by main activity
        this.recyclerViewListener1 = recyclerViewListener1; // save listener defined and passed by main activity
        this.EXTRA_RES_ID = EXTRA_RES_ID;
        this.layout = layout;

    }

    @NonNull
    @Override
    public AdapterItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        // get inflater and inflate XML layout file
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclegrid,parent,false);
        View view1 = layoutInflater.inflate(R.layout.recycle,parent,false);
        // create ViewHolder passing the view that it will wrap and the listener on the view
        ViewHolder viewHolder;
        if(layout.equals("Grid"))
        viewHolder= new ViewHolder(view,recyclerViewListener1);
        else{
            viewHolder = new ViewHolder(view1,recyclerViewListener1);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItem.ViewHolder holder, int position) {
        // populate the item with Text
        holder.animalName.setText(animalNameList.get(position));
        // populate the item with Image
        holder.animalImage.setImageResource(animalImageList.get(position));
        // setting the scale type
        holder.animalImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public int getItemCount() {
        return animalNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        public TextView animalName;
        public ImageView animalImage;
        private View itemView;
        private RecyclerViewListener recyclerViewListener1;

        public ViewHolder(@NonNull View itemView, RecyclerViewListener recyclerViewListener1) {
            super(itemView);
            animalName = itemView.findViewById(R.id.textView);
            animalImage = itemView.findViewById(R.id.imageView);
            this.itemView = itemView;
            this.recyclerViewListener1 = recyclerViewListener1;
            itemView.setOnClickListener(this);//setting short click listener
            itemView.setOnCreateContextMenuListener(this);//setting context menu for each list item
        }

        @Override
        public void onClick(View view) {
            // getAdapterPosition() returns the position of the current ViewHolder in the adapter.
            recyclerViewListener1.onClick(view, getAdapterPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            //inflate menu from xml
            MenuInflater menuInflater = new MenuInflater(view.getContext());
            menuInflater.inflate(R.menu.item_context_menu, contextMenu);
            contextMenu.getItem(0).setOnMenuItemClickListener(onClickWikiPageView);
            contextMenu.getItem(1).setOnMenuItemClickListener(onClickImageView);
        }
        //listener for first menu item clicked which redirects to wikipedia page of the animal
        public final MenuItem.OnMenuItemClickListener onClickWikiPageView = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String URL = "https://en.wikipedia.org/wiki/"+animalName.getText();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                itemView.getContext().startActivity(i);
                return true;
            }
        };
        //listener for second menu item clicked which opens a new activity with the selected image of the animal
        public final MenuItem.OnMenuItemClickListener onClickImageView = new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent(itemView.getContext(),ImageActivity.class); //Creating an explicit intent
                i.putExtra(EXTRA_RES_ID, animalImageList.get(getAdapterPosition()));// Passing image as the extra
                itemView.getContext().startActivity(i); // starts ImageActivity
                return true;
            }
        };
    }
}
