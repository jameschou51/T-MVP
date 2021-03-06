package com.view.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.C;
import com.app.annotation.apt.Instance;
import com.app.annotation.aspect.SingleClick;
import com.base.BaseViewHolder;
import com.base.util.ImageUtil;
import com.data.entity.CommentInfo;
import com.ui.article.ArticleActivity;
import com.ui.main.R;
import com.ui.user.UserActivity;

import butterknife.Bind;

/**
 * Created by baixiaokang on 16/5/4.
 */
@Instance(type = Instance.typeVH)
public class CommentItemVH extends BaseViewHolder<CommentInfo> {
    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.im_user)
    ImageView im_user;

    public CommentItemVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_comment;
    }

    @Override
    public void onBindViewHolder(View view, final CommentInfo data) {
        tv_content.setText(Html.fromHtml("<font color='#ff7200'>" + data.creater.username + ":<br/><br/>" + "</font>" + data.content));
        ImageUtil.loadRoundImg(im_user, data.creater.face);
        im_user.setOnClickListener(
                new View.OnClickListener() {
                    @SingleClick
                    public void onClick(View view) {
                        ActivityCompat.startActivity((Activity) mContext, new Intent(mContext, UserActivity.class).putExtra(C.HEAD_DATA, data.creater)
                                , ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, im_user, ArticleActivity.TRANSLATE_VIEW).toBundle());
                    }
                }
        );
    }
}
