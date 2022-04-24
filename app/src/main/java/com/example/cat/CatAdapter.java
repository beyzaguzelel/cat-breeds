package com.example.cat;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cat.model.Cat;

import java.util.ArrayList;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder>  {
//implements Filterable
    private final IRecyclerView iRecyclerView;
    private List<Cat> breedsList;
    private List<Cat> breedsListFiltered;
    private Context context;
    private Button favBtn;


    public CatAdapter(Context context, List<Cat> breedsList, IRecyclerView iRecyclerView) {
        this.context = context;
        this.breedsList = breedsList;
        this.breedsListFiltered=breedsList;
        this.iRecyclerView = iRecyclerView;
    }

   public void setBreedsList(Context applicationContext, List<Cat> breedsList) {
        this.context = applicationContext;
        if (this.breedsList == null) {
            this.breedsList = breedsList;
            this.breedsListFiltered = breedsList;
            notifyItemChanged(0, breedsListFiltered.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return CatAdapter.this.breedsList.size();
                }

                @Override
                public int getNewListSize() {
                    return breedsList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return CatAdapter.this.breedsList.get(oldItemPosition).getName() == breedsList.get(newItemPosition).getName();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    Cat newCat = CatAdapter.this.breedsList.get(oldItemPosition);

                    Cat oldCat = breedsList.get(newItemPosition);

                    return newCat.getName() == oldCat.getName();
                }
            });
            this.breedsList = breedsList;
            this.breedsListFiltered = breedsList;
            result.dispatchUpdatesTo(this);
        }
    }


    @NonNull
    @Override
    public CatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cat_row, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CatAdapter.ViewHolder holder, int position) {
        try {
            Glide.with(context).load(breedsList.get(position).getImage().getUrl()).apply(RequestOptions.circleCropTransform()).into(holder.imageView);
            holder.book_author_txt.setText(breedsList.get(position).getName());

        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
        }

    }


    @Override
    public int getItemCount() {

        return breedsList.size();

    }

  /*  @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    breedsListFiltered = breedsList;
                } else {
                    List<Cat> filteredList = new ArrayList<>();
                    for (Cat cat : breedsList) {
                        if (cat.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(cat);
                        }
                    }
                    breedsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = breedsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                breedsListFiltered = (List<Cat>) filterResults.values;

                notifyDataSetChanged();
            }
        };

    }*/
        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView book_author_txt;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = (ImageView) itemView.findViewById(R.id.imageView);
                book_author_txt = itemView.findViewById(R.id.book_author_txt);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (iRecyclerView != null) {
                            int pos = getAbsoluteAdapterPosition();//getAdapterPosition();

                            if (pos != RecyclerView.NO_POSITION) {
                                iRecyclerView.onItemClick(pos);
                            }
                        }
                    }
                });
            }


    }

}