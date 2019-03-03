package com.okusr.music.musicbox3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
* @date :2017/10/24
* @author lixiang
* @Description:
*/
public class MyReclerViewAdapter extends RecyclerView.Adapter<MyReclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private LayoutInflater inflate;
    private List<MusicInfo> list;
    private ISelect iSelect;

    public MyReclerViewAdapter(Context mContex, List<MusicInfo> mList, ISelect iSelect1) {
        inflate = LayoutInflater.from(mContex);
        this.mContext = mContex;
        this.list = mList;
        this.iSelect = iSelect1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflate.inflate(R.layout.ap_reclerview_item, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iSelect.selectItem(list.get(holder.getAdapterPosition()).getUrl(), holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getTitle());
        holder.singer_tv.setText(list.get(position).getArtist());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView singer_tv;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.song_name_tv);
            singer_tv = (TextView) view.findViewById(R.id.singer_tv);
        }
    }

    public interface ISelect {
        void selectItem(String s, int possion);
    }
}
