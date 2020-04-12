package com.example.ecommerceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ecommerceapp.Model.OnBoardScreenComponents;
import com.example.ecommerceapp.R;

import java.util.List;

public class OnBroadScreenPagerAdapter extends PagerAdapter {
    List<OnBoardScreenComponents> mList;
    Context mContext;

    public OnBroadScreenPagerAdapter(List<OnBoardScreenComponents> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_screen, null);
        ImageView imageView = view.findViewById(R.id.intro_imageview);
        TextView title = view.findViewById(R.id.intro_title);
        TextView description = view.findViewById(R.id.intro_description);

        imageView.setImageResource(mList.get(position).getImage());
        title.setText(mList.get(position).getTitle());
        description.setText(mList.get(position).getDescription());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
