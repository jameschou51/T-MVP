package com.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.C;
import com.base.util.InstanceUtil;
import com.view.viewholder.CommFooterVH;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baixiaokang on 16/12/27.
 */

public class CoreAdapter<M> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<M> mItemList = new ArrayList<>();
    public boolean isHasMore = true;
    public int viewType, isHasFooter = 1, isHasHeader = 0, mHeadViewType, mFooterViewType = CommFooterVH.LAYOUT_TYPE;
    public Object mHeadData, mFootData;
    public Class<? extends BaseViewHolder> mItemViewClass, mHeadViewClass, mFooterViewClass = CommFooterVH.class;
    public Context context;

    public CoreAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (RecyclerView.ViewHolder) InstanceUtil.getInstance(getVHClassByType(viewType), LayoutInflater.from(context).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder) holder).onBindViewHolder(holder.itemView, getItem(position));
    }

    public void setViewType(int i, Class<? extends BaseViewHolder> cla) {
        this.isHasMore = true;
        this.viewType = i;
        this.mItemList = new ArrayList<>();
        this.mItemViewClass = cla;
    }

    public void setHeadViewType(int i, Class<? extends BaseViewHolder> cla, Object data) {
        this.isHasHeader = cla == null ? 0 : 1;
        if (isHasHeader == 1) {
            this.isHasHeader = 1;
            this.mHeadViewType = i;
            this.mHeadViewClass = cla;
            this.mHeadData = data;
        }
    }

    public void setFooterViewType(int i, Class<? extends BaseViewHolder> cla, Object data) {
        this.isHasFooter = cla == null ? 0 : 1;
        if (isHasFooter == 1) {
            this.mFootData = data;
            this.isHasFooter = 1;
            this.mFooterViewType = i;
            this.mFooterViewClass = cla;
        }
    }

    public Object getItem(int position) {
        return isHasFooter == 1 && position + 1 == getItemCount()
                ? (mFootData == null ? (isHasMore ? new Object() : null) : mFootData)
                : isHasHeader == 1 && position == 0 ? mHeadData : mItemList.get(position - isHasHeader);
    }

    @Override
    public int getItemViewType(int position) {
        int mViewType = getViewType(mItemViewClass, viewType, getItem(position));
        int mFooterType = isHasFooter == 1 ? getViewType(mFooterViewClass, mFooterViewType, mFootData) : mFooterViewType;
        int mHeaderType = isHasHeader == 1 ? getViewType(mHeadViewClass, mHeadViewType, mHeadData) : mHeadViewType;
        return isHasHeader == 1 && position == 0 ? mHeaderType : (isHasFooter == 1 && position + 1 == getItemCount() ? mFooterType : mViewType);
    }

    public int getViewType(Class VHClass, int viewType, Object item) {
        BaseViewHolder BVH = InstanceUtil.getInstance(VHClass, new LinearLayout(context));
        return BVH instanceof BaseMultiVH ? ((BaseMultiVH) BVH).getMultiType(item) : viewType;
    }

    @Override
    public int getItemCount() {
        return mItemList.size() + isHasFooter + isHasHeader;
    }

    public void setBeans(List<M> datas, int begin) {
        if (datas == null) datas = new ArrayList<>();
        this.isHasMore = datas.size() >= C.PAGE_COUNT;
        if (begin > 1) this.mItemList.addAll(datas);
        else this.mItemList = datas;
        notifyDataSetChanged();
    }

    public Class getVHClassByType(int type) {
        if (type == mHeadViewType) return mHeadViewClass;
        else if (type == mFooterViewType) return mFooterViewClass;
        else return mItemViewClass;
    }
}