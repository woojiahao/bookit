package team.android.projects.com.booktit;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class DiscoverRowAdapter extends RecyclerView.Adapter<DiscoverRowAdapter.ViewHolder>{
    private ArrayList<Book> books;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public ViewHolder(CardView v){
            super(v);
            mCardView = v;
        }
    }

    public DiscoverRowAdapter(ArrayList<Book> books){
        this.books = books;
    }

    @Override
    public DiscoverRowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discover_tile,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView layout = holder.mCardView;
        ImageView thumbnail = (ImageView) layout.findViewById(R.id.discoverTileImage);
        TextView title = (TextView) layout.findViewById(R.id.discoverTileTitle);
        TextView genre = (TextView) layout.findViewById(R.id.discoverTileGenre);
        TextView price = (TextView) layout.findViewById(R.id.discoverTilePrice);
        TextView rating = (TextView) layout.findViewById(R.id.discoverTileRating);

        Book book = books.get(position);

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        thumbnail.setImageResource(book.getResID());
        title.setText(book.getName());
        genre.setText(book.getGenre().getGenreTitle());
        price.setText(currencyFormatter.format(book.getPrice()));
        rating.setText(((Double)book.getRating()).toString()+" \u2605");
    }

    public int getItemCount() {
        return books.size();
    }
}
