package com.example.wechat;

import java.util.ArrayList;
import java.util.List;

import com.jauker.widget.BadgeView;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Build;

//ʹ���˹ȸ��ƽ��fragment�����viewPager��ʹ��
public class MainActivity extends FragmentActivity {
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mData;
	private TextView mCellTextView;
	private TextView mMailTextView;
	private LinearLayout mCellLinearLayout;
	private BadgeView mBadgeView;
	private int mHalfScreen;
	private ImageView mTabline;
	private int mCurrentPageIndex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ע��д��λ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initTabLine();
		initView();
	}
	private void initTabLine() {
		mTabline=(ImageView) findViewById(R.id.imageView_halfScreen);
		//tabline����С��--��ȡ��Ļ�ĸ߶ȺͿ��
		Display display=getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics=new DisplayMetrics();
		display.getMetrics(outMetrics);
		//����Ǵ�˵����Ļ��ȵ�һ��
		mHalfScreen=outMetrics.widthPixels/2;
		//����Ҳ��ҪѡViewGroup���
		LayoutParams layoutParams = mTabline.getLayoutParams();
		layoutParams.width=mHalfScreen;
		mTabline.setLayoutParams(layoutParams);
	}
	private void initView() {
		// �������ͻȻ�����뵽��iOS��ʦ
		mViewPager=(ViewPager) findViewById(R.id.viewpager1);
		mCellTextView=(TextView) findViewById(R.id.textView_cell);
		mMailTextView=(TextView) findViewById(R.id.textView_mail);
		mCellLinearLayout=(LinearLayout) findViewById(R.id.linearLayout_cell);
		mData=new ArrayList<Fragment>();
		CellMainTabFragment cell=new CellMainTabFragment();
		MailMainTabFragment mail=new MailMainTabFragment();
		mData.add(cell);
		mData.add(mail);
		mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mData.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return mData.get(arg0);
			}
		};
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// ����ǻ�������ʱ�ķ���
				resetTextView();
				switch (position) {
				case 0:
					if(mBadgeView!=null){
						mCellLinearLayout.removeView(mBadgeView);
					}
					mBadgeView=new BadgeView(MainActivity.this);
					mBadgeView.setBadgeCount(7);
					mCellLinearLayout.addView(mBadgeView);
					//���õ�һ�ַ�ʽ ��֪���Բ���--������ʦҲ��ô����
					mCellTextView.setTextColor(Color.parseColor("#20B2AA"));
					break;
				case 1:
					mMailTextView.setTextColor(Color.parseColor("#20B2AA"));
					break;
				}
				mCurrentPageIndex=position;
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPx) {
				// ҳ���л�ʱָʾ���ƶ����õķ���
				Log.e("tag", position+","+positionOffset+","+positionOffsetPx);
				LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) mTabline.getLayoutParams();
				//0��1ҳ
				if(mCurrentPageIndex==0&&position==0){
					layoutParams.leftMargin=(int) (positionOffset*mHalfScreen+mCurrentPageIndex*mHalfScreen);
				//0��1ҳ  Offset��0��0.9
				}else if(mCurrentPageIndex==1&&position==0){
					layoutParams.leftMargin=(int) (mCurrentPageIndex*mHalfScreen+(positionOffset-1)*mHalfScreen);
				}
				//�����3ҳ����(��1��2ҳ) mCurrentPageIndex==1 && position==1
				//layoutParams.leftMargin=(int) (mCurrentPageIndex*mHalfScreen+(positionOffset)*mHalfScreen);
				//����֮�⻹��2��1ҳ mCurrentPageIndex==2 && position==1
				//layoutParams.leftMargin=(int) (mCurrentPageIndex*mHalfScreen+(positionOffset-1)*mHalfScreen);
				mTabline.setLayoutParams(layoutParams);
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	protected void resetTextView() {
		// TODO Auto-generated method stub
		mCellTextView.setTextColor(Color.BLACK);
		mMailTextView.setTextColor(Color.BLACK);
	}
}
