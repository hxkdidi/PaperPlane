package com.marktony.zhihudaily.refactor.favorites;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marktony.zhihudaily.R;
import com.marktony.zhihudaily.refactor.data.DoubanMomentPosts;
import com.marktony.zhihudaily.refactor.data.GuokrHandpickNewsResult;
import com.marktony.zhihudaily.refactor.data.ZhihuDailyNewsQuestion;
import com.marktony.zhihudaily.refactor.interfaze.OnRecyclerViewItemOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaotailang on 2017/6/14.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final Context mContext;

    @NonNull
    private final LayoutInflater mLayoutInflater;

    @NonNull
    private final List<ZhihuDailyNewsQuestion> mZhihuList;

    @NonNull
    private final List<DoubanMomentPosts> mDoubanList;

    @NonNull
    private final List<GuokrHandpickNewsResult> mGuokrList;

    private final List<ItemWrapper> mWrapperList;

    private OnRecyclerViewItemOnClickListener mListener;

    public FavoritesAdapter(@NonNull Context context,
                            @NonNull List<ZhihuDailyNewsQuestion> zhihuDailyNewsList,
                            @NonNull List<DoubanMomentPosts> doubanMomentNewsList,
                            @NonNull List<GuokrHandpickNewsResult> guokrHandpickNewsList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mZhihuList = zhihuDailyNewsList;
        mDoubanList = doubanMomentNewsList;
        mGuokrList = guokrHandpickNewsList;

        mWrapperList = new ArrayList<>();

        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_CATEGORY));
        if (mZhihuList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for (int i = 0; i < mZhihuList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_ZHIHU);
                iw.index = i;
                mWrapperList.add(iw);
            }
        }

        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_CATEGORY));
        if (mDoubanList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for (int i = 0; i < mDoubanList.size(); i++) {
                if (mDoubanList.get(i).getThumbs().isEmpty()) {
                    ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_DOUBAN_NO_IMG);
                    iw.index = i;
                    mWrapperList.add(iw);
                } else {
                    ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_DOUBAN);
                    iw.index = i;
                    mWrapperList.add(iw);
                }
            }
        }

        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_CATEGORY));
        if (mGuokrList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for (int i = 0; i < mGuokrList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_GUOKR);
                iw.index = i;
                mWrapperList.add(iw);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ItemWrapper.TYPE_EMPTY:
                viewHolder = new EmptyViewHolder(mLayoutInflater.inflate(R.layout.item_empty, viewGroup, false));
                break;
            case ItemWrapper.TYPE_CATEGORY:
                viewHolder = new CategoryViewHolder(mLayoutInflater.inflate(R.layout.item_category, viewGroup, false));
                break;
            case ItemWrapper.TYPE_ZHIHU:
                viewHolder = new ZhihuItemViewHolder(mLayoutInflater.inflate(R.layout.home_list_item_layout, viewGroup, false), mListener);
                break;
            case ItemWrapper.TYPE_DOUBAN:
                viewHolder = new DoubanItemHolder(mLayoutInflater.inflate(R.layout.home_list_item_layout, viewGroup, false), mListener);
                break;
            case ItemWrapper.TYPE_DOUBAN_NO_IMG:
                viewHolder = new DoubanNoImageHolder(mLayoutInflater.inflate(R.layout.home_list_item_without_image, viewGroup, false), mListener);
                break;
            case ItemWrapper.TYPE_GUOKR:
                viewHolder = new GuokrViewHolder(mLayoutInflater.inflate(R.layout.home_list_item_layout, viewGroup, false), mListener);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mWrapperList == null ? 0 : mWrapperList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mWrapperList.get(position).viewType;
    }

    public void setOnItemClickListener(OnRecyclerViewItemOnClickListener listener) {
        this.mListener = listener;
    }

    public int getOriginalIndex(int position) {
        return mWrapperList.get(position).index;
    }

    public void updateData(List<ZhihuDailyNewsQuestion> zhihuDailyNewsList,
                           List<DoubanMomentPosts> doubanMomentNewsList,
                           List<GuokrHandpickNewsResult> guokrHandpickNewsList) {
        mZhihuList.clear();
        mDoubanList.clear();
        mGuokrList.clear();
        mWrapperList.clear();

        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_CATEGORY));
        if (zhihuDailyNewsList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for (int i = 0; i < zhihuDailyNewsList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_ZHIHU);
                iw.index = i;
                mWrapperList.add(iw);
            }
        }

        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_CATEGORY));
        if (doubanMomentNewsList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for (int i = 0; i < doubanMomentNewsList.size(); i++) {
                if (doubanMomentNewsList.get(i).getThumbs().isEmpty()) {
                    ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_DOUBAN_NO_IMG);
                    iw.index = i;
                    mWrapperList.add(iw);
                } else {
                    ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_DOUBAN);
                    iw.index = i;
                    mWrapperList.add(iw);
                }
            }
        }

        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_CATEGORY));
        if (guokrHandpickNewsList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for (int i = 0; i < guokrHandpickNewsList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_GUOKR);
                iw.index = i;
                mWrapperList.add(iw);
            }
        }

        notifyDataSetChanged();
    }

    public class ZhihuItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        OnRecyclerViewItemOnClickListener listener;

        public ZhihuItemViewHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_cover);
            textView = itemView.findViewById(R.id.text_view_title);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.OnItemClick(view, getLayoutPosition());
            }
        }
    }

    public class DoubanItemHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView imageView;
        TextView textView;

        OnRecyclerViewItemOnClickListener listener;

        public DoubanItemHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_cover);
            textView = itemView.findViewById(R.id.text_view_title);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.OnItemClick(view, getLayoutPosition());
            }
        }
    }

    public class DoubanNoImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        OnRecyclerViewItemOnClickListener listener;

        public DoubanNoImageHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_title);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.OnItemClick(view, getLayoutPosition());
            }
        }
    }

    public class GuokrViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;

        OnRecyclerViewItemOnClickListener listener;

        public GuokrViewHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_cover);
            textView = itemView.findViewById(R.id.text_view_title);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.OnItemClick(view, getLayoutPosition());
            }
        }
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textViewCategory;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.text_view_category);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textViewEmpty;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            textViewEmpty = itemView.findViewById(R.id.text_view_empty);
        }
    }

    public static class ItemWrapper {

        public final static int TYPE_ZHIHU = 0x00;
        public final static int TYPE_DOUBAN = 0x01;
        public final static int TYPE_GUOKR = 0x02;
        public final static int TYPE_CATEGORY = 0x03;
        public final static int TYPE_EMPTY = 0x04;
        public final static int TYPE_DOUBAN_NO_IMG = 0x05;

        public int viewType;
        public int index;

        public ItemWrapper(int viewType) {
            this.viewType = viewType;
        }

    }

}
