/*
 * Copyright (C) 2013 Mosil(http://mosil.biz)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.mosil.demo.navigationdrawer;

import biz.mosil.demo.appcompat.R;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private LinearLayout mLlvDrawerContent;
    private ListView mLsvDrawerMenu;
    // 記錄被選擇的選單指標用
    private int mCurrentMenuItemPosition = -1;
    // 選單項目
    public static final String[] MENU_ITEMS = new String[] { "Item1", "Item2",
            "Item3", "Item4", "Item5" };
    
    private LinearLayout mLlvDrawerRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drw_layout);
        // 設定 Drawer 的影子
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,    // 讓 Drawer Toggle 知道母體介面是誰
                R.drawable.ic_drawer, // Drawer 的 Icon
                R.string.open_left_drawer, // Drawer 被打開時的描述
                R.string.close_left_drawer // Drawer 被關閉時的描述
        ) {
            // 被打開後要做的事情
            @Override
            public void onDrawerOpened(View drawerView) {                
                // 將 Title 設定為自定義的文字
                if(mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) { 
                    getSupportActionBar().setTitle(R.string.open_right_drawer);
                } else {
                    getSupportActionBar().setTitle(R.string.open_left_drawer);
                }
                
                if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    // 若是左側被打開，便將右側給鎖在關閉的狀態
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
                } else {
                    // 反之，便是將左側給鎖在關閉的狀態
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
                }
            }

            // 被關上後要做的事情
            @Override
            public void onDrawerClosed(View drawerView) {
                if (mCurrentMenuItemPosition > -1) {
                    getSupportActionBar().setTitle(
                            MENU_ITEMS[mCurrentMenuItemPosition]);
                } else {
                    // 將 Title 設定回 APP 的名稱
                    getSupportActionBar().setTitle(R.string.app_name);
                }
                
                if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    // 解開左邊鎖定的狀態
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
                } else if(mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    // 解開右邊鎖定的狀態
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.RIGHT);
                } else {
                    // 代表兩邊都被關上，以防萬一將兩邊的進行解鎖的動作
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.RIGHT);
                }
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // 顯示 Up Button (位在 Logo 左手邊的按鈕圖示)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 打開 Up Button 的點擊功能
        getSupportActionBar().setHomeButtonEnabled(true);

        setDrawerMenu();
    }

    /**
     * 設定側邊欄 - Drawer 的動作
     * */
    private void setDrawerMenu() {
        // 定義新宣告的兩個物件：選項清單的 ListView 以及 Drawer內容的 LinearLayou
        mLsvDrawerMenu = (ListView) findViewById(R.id.lsv_drawer_menu);
        mLlvDrawerContent = (LinearLayout) findViewById(R.id.llv_left_drawer);
        
        // 當清單選項的子物件被點擊時要做的動作
        mLsvDrawerMenu.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                selectMenuItem(position);
            }
        });
        // 設定清單的 Adapter，這裡直接使用 ArrayAdapter<String>
        mLsvDrawerMenu.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.drawer_menu_item,  // 選單物件的介面 
                MENU_ITEMS                  // 選單內容
        ));
    }

    /**
     * 設定當側邊欄裡的某個選項被點擊後要做的動作
     * */
    private void selectMenuItem(int position) {
        mCurrentMenuItemPosition = position;

        // 將選單的子物件設定為被選擇的狀態
        mLsvDrawerMenu.setItemChecked(position, true);

        // 關掉 Drawer
        mDrawerLayout.closeDrawer(mLlvDrawerContent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            // 判斷 DrawerLayout 裡的哪一個 Drawer 被打開
            if(mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                // 關掉 DrawerLayout 裡的某一個 Drawer
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
