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
package com.example.commonlib.view.panel.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Window;
import android.widget.RelativeLayout;

import com.example.commonlib.view.panel.IFSPanelConflictLayout;
import com.example.commonlib.view.panel.IPanelHeightTarget;
import com.example.commonlib.view.panel.handler.KPSwitchFSPanelLayoutHandler;
import com.example.commonlib.view.panel.util.ViewUtil;


public class KPSwitchFSPanelRelativeLayout extends RelativeLayout implements IPanelHeightTarget,
        IFSPanelConflictLayout {

    private KPSwitchFSPanelLayoutHandler panelHandler;

    public KPSwitchFSPanelRelativeLayout(Context context) {
        super(context);
        init();
    }

    public KPSwitchFSPanelRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KPSwitchFSPanelRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KPSwitchFSPanelRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr,
                                         int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        panelHandler = new KPSwitchFSPanelLayoutHandler(this);
    }

    @Override
    public void refreshHeight(int panelHeight) {
        ViewUtil.refreshHeight(this, panelHeight);
    }

    @Override
    public void onKeyboardShowing(boolean showing) {
        panelHandler.onKeyboardShowing(showing);
    }


    @Override
    public void recordKeyboardStatus(final Window window) {
        panelHandler.recordKeyboardStatus(window);
    }
}
