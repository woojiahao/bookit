package team.android.projects.com.booktit;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class DiscoverRowAdapter extends RecyclerView.Adapter<DiscoverRowAdapter.ViewHolder>{
    private ArrayList<Book> books;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ConstraintLayout mConstraintLayout;
        public ViewHolder(ConstraintLayout v){
            super(v);
            mConstraintLayout = v;
        }
    }

    public DiscoverRowAdapter(ArrayList<Book> books){
        this.books = books;
    }

    @Override
    public DiscoverRowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discover_tile,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ConstraintLayout layout = holder.mConstraintLayout;
        ImageView thumbnail = (ImageView) layout.findViewById(R.id.imageView);
        TextView title = (TextView) layout.findViewById(R.id.textViewBookTitle);
        TextView genre = (TextView) layout.findViewById(R.id.textViewGenre);
        TextView price = (TextView) layout.findViewById(R.id.textViewPrice);
        TextView rating = (TextView) layout.findViewById(R.id.textViewRating);

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
