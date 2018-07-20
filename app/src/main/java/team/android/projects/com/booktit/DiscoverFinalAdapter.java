package team.android.projects.com.booktit;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DiscoverFinalAdapter extends ArrayAdapter<BookCategory>{
    private Context context;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public DiscoverFinalAdapter(Context context, ArrayList<BookCategory> bookcats){
        super(context,0,bookcats);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent){
        BookCategory cat = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.discover_row,parent,false);
            mRecyclerView = (RecyclerView) convertView.findViewById(R.id.discoverRecyclerView);
            mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new DiscoverRowAdapter(cat.getBooks());
            mRecyclerView.setAdapter(mAdapter);
        }
        TextView catTitle = (TextView) convertView.findViewById(R.id.discoverCategoryTitle);
        catTitle.setText(cat.getTitle());

        return convertView;
    }
}
