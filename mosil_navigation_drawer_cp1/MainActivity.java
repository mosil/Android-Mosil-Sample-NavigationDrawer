/*
 * Copyright (C) 2013 Mosil(http://mosil.biz)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drw_layout);
		// 設定 Drawer 的影子
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
		mDrawerToggle = new ActionBarDrawerToggle(
		        this, 
		        mDrawerLayout,    // 讓 Drawer Toggle 知道母體介面是誰
		        R.drawable.ic_drawer, // Drawer 的 Icon
		        R.string.open_left_drawer, // Drawer 被打開時的描述
		        R.string.close_left_drawer // Drawer 被關閉時的描述
		        ) {
                    //被打開後要做的事情
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // 將 Title 設定為自定義的文字
                        getSupportActionBar().setTitle(R.string.open_left_drawer);
                    }
                    
                    //被關上後要做的事情
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // 將 Title 設定回 APP 的名稱
                        getSupportActionBar().setTitle(R.string.app_name);
                    }
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		//顯示 Up Button (位在 Logo 左手邊的按鈕圖示)
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//打開 Up Button 的點擊功能 
		getSupportActionBar().setHomeButtonEnabled(true);
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
