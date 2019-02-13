/*
 * Copyright (C) 2015-2017 Jacksgong(blog.dreamtobe.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.commonlib.view.panel.handler;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.example.commonlib.R;
import com.example.commonlib.view.panel.IPanelConflictLayout;
import com.example.commonlib.view.panel.util.ViewUtil;


public class KPSwitchPanelLayoutHandler implements IPanelConflictLayout {
    private final View panelLayout;

    /**
     * The real status of Visible or not
     *
     * @see #handleHide()
     * @see #filterSetVisibility(int) (int)
     * <p/>
     * if true, the status is non-Visible or will
     * non-Visible(may delay and handle in {@link #processOnMeasure(int, int)})
     * <p/>
     * The value of {@link View#getVisibility()} maybe be assigned dully for cover the
     * keyboard->panel.
     * In this case, the {@code mIsHide} will mark the right status.
     * Handle by {@link #filterSetVisibility(int)} & {@link #processOnMeasure(int, int)}
     */
    private boolean mIsHide = false;

    /**
     * Whether ignore the recommend panel height, what would be equal to the height of keyboard in
     * most situations.
     * <p/>
     * If the value is true, the panel's height will not be follow the height of the keyboard.
     * <p/>
     * Default is false.
     *
     * @attr ref cn.dreamtobe.kpswitch.R.styleable#KPSwitchPanelLayout_ignore_recommend_height
     */
    @SuppressWarnings("JavaDoc")
    private boolean mIgnoreRecommendHeight = false;

    public KPSwitchPanelLayoutHandler(final View panelLayout, final AttributeSet attrs) {
        this.panelLayout = panelLayout;
        if (attrs != null) {
            TypedArray typedArray = null;
            try {
                typedArray = panelLayout.getContext().
                        obtainStyledAttributes(attrs, R.styleable.KPSwitchPanelLayout);
                mIgnoreRecommendHeight = typedArray.
                        getBoolean(R.styleable.KPSwitchPanelLayout_ignore_recommend_height,
                                false);
            } finally {
                if (typedArray != null) {
                    typedArray.recycle();
                }
            }
        }
    }

    /**
     * Filter the {@link View#setVisibility(int)} for handling Keyboard->Panel.
     *
     * @param visibility {@link View#setVisibility(int)}
     * @return whether filtered out or not.
     */
    public boolean filterSetVisibility(final int visibility) {
        if (visibility == View.VISIBLE) {
            this.mIsHide = false;
        }

        if (visibility == panelLayout.getVisibility()) {
            return true;
        }

        /**
         * For handling Keyboard->Panel.
         *
         * Will be handled on {@link KPSwitchRootLayoutHandler#handleBeforeMeasure(int, int)} ->
         * {@link IPanelConflictLayout#handleShow()} Delay show, until the
         * {@link KPSwitchRootLayoutHandler} discover
         * the size is changed by keyboard-show. And will show, on the next frame of the above
         * change discovery.
         */
        //noinspection RedundantIfStatement
        if (isKeyboardShowing() && visibility == View.VISIBLE) {
            return true;
        }

        return false;
    }


    /**
     * Handle Panel -> Keyboard.
     * <p/>
     * Process the {@link View#onMeasure(int, int)} for handling the case of Panel->Keyboard.
     *
     * @return the processed measure-width-spec and measure-height-spec.
     * @see #handleHide()
     */
    public int[] processOnMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mIsHide) {
            panelLayout.setVisibility(View.GONE);
            /*
             * The current frame will be visible nil.
             */
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
        }

        final int[] processedMeasureWHSpec = new int[2];
        processedMeasureWHSpec[0] = widthMeasureSpec;
        processedMeasureWHSpec[1] = heightMeasureSpec;

        return processedMeasureWHSpec;
    }

    private boolean mIsKeyboardShowing = false;

    public void setIsKeyboardShowing(final boolean isKeyboardShowing) {
        mIsKeyboardShowing = isKeyboardShowing;
    }

    @Override
    public boolean isKeyboardShowing() {
        return mIsKeyboardShowing;
    }


    @Override
    public boolean isVisible() {
        return !mIsHide;
    }

    @Override
    public void handleShow() {
        throw new IllegalAccessError("You can't invoke handle show in handler,"
                + " please instead of handling in the panel layout, maybe just need invoke "
                + "super.setVisibility(View.VISIBLE)");
    }

    /**
     * @see #processOnMeasure(int, int)
     */
    @Override
    public void handleHide() {
        this.mIsHide = true;
    }

    /**
     * @param recommendPanelHeight the recommend panel height, in the most situations, the value
     *                             would be equal to the height of the keyboard.
     * @see cn.dreamtobe.kpswitch.util.KeyboardUtil#getValidPanelHeight
     */
    public void resetToRecommendPanelHeight(int recommendPanelHeight) {
        if (mIgnoreRecommendHeight) {
            // In this way, the panel's height will be not follow the height of keyboard.
            return;
        }

        ViewUtil.refreshHeight(panelLayout, recommendPanelHeight);
    }

    /**
     * @param ignoreRecommendHeight Whether ignore the recommend panel height, what would be equal
     *                              to the height of keyboard in most situations.
     * @attr ref cn.dreamtobe.kpswitch.R.styleable#KPSwitchPanelLayout_ignore_recommend_height
     * @see #resetToRecommendPanelHeight(int)
     */
    @SuppressWarnings("JavaDoc")
    public void setIgnoreRecommendHeight(boolean ignoreRecommendHeight) {
        this.mIgnoreRecommendHeight = ignoreRecommendHeight;
    }
}
