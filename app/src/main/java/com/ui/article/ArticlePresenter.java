package com.ui.article;

import com.app.annotation.apt.Instance;
import com.base.util.ApiUtil;
import com.base.util.SpUtil;
import com.data.entity.Image;
import com.data.entity._User;

/**
 * Created by baixiaokang on 16/5/4.
 */
@Instance
public class ArticlePresenter extends ArticleContract.Presenter {
    @Override
    public void createComment(String content, Image article, _User user) {
        if (null == SpUtil.getUser())
            mView.showLoginAction();
        else
            mCompositeSubscription.add(mModel
                    .createComment(content,
                            ApiUtil.getPointer(article),
                            ApiUtil.getPointer(user))
                    .subscribe(
                            res -> mView.commentSuc(),
                            e -> mView.commentFail())
            );
    }
}
