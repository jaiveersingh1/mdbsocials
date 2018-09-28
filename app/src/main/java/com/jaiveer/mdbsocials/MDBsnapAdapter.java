package com.jaiveer.mdbsocials;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MDBsnapAdapter extends RecyclerView.Adapter<MDBsnapAdapter.MDBsnapViewHolder> {
    ArrayList<MDBsnap> data;
    private Context context;

    public MDBsnapAdapter(ArrayList<MDBsnap> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MDBsnapViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.snap_layout, viewGroup, false);
        return new MDBsnapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MDBsnapViewHolder mdBsnapViewHolder, int position) {
        mdBsnapViewHolder.bind(data.get(getItemCount() - 1 - position));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MDBsnapViewHolder extends RecyclerView.ViewHolder {
        TextView emailView;
        ImageView imageView;
        TextView captionView;
        MDBsnap mdBsnap;

        private MDBsnapViewHolder(@NonNull View itemView) {
            super(itemView);
            emailView = itemView.findViewById(R.id.email);
            imageView = itemView.findViewById(R.id.img);
            captionView = itemView.findViewById(R.id.caption);
            // TODO: 9/20/18 Click listener stuff from Pokemon!
        }

        void bind (MDBsnap mdBsnap) {
            this.mdBsnap = mdBsnap;
            emailView.setText(mdBsnap.getUserEmail());
            captionView.setText(mdBsnap.getCaption());

            //use Glide to set img
            RequestOptions myOptions = new RequestOptions()
                    .error(R.drawable.ic_launcher_foreground);
            Glide.with(context)
                    .load(mdBsnap.getImageURL())
                    .apply(myOptions)
                    .into(imageView);
        }
    }
}
