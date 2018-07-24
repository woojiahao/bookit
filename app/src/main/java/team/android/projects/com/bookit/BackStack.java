package team.android.projects.com.bookit;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static team.android.projects.com.bookit.FragmentID.Discover;

// todo: document this class
public class BackStack {
	private static List<FragmentID> mBackStack = new ArrayList<>();
	private static FragmentID mCurrentFragment = Discover;
	
	public static void setCurrentFragment(FragmentID currentFragment) {
		mCurrentFragment = currentFragment;
	}
	
	public static FragmentID getCurrentFragment() {
		return mCurrentFragment;
	}
	
	public static void addToBackStack(FragmentID toAdd) {
		if (toAdd != null) {
			mBackStack.add(toAdd);
		}
		getBackStackStatus();
	}
	
	public static void pop() {
		FragmentID latest = peek();
		if (latest != null) {
			mBackStack.remove(latest);
		}
		getBackStackStatus();
	}
	
	public static FragmentID peek() {
		return mBackStack.size() == 0 ? null : mBackStack.get(mBackStack.size() - 1);
	}
	
	private static void getBackStackStatus() {
		Log.d("Backstack", "Backstack " + mBackStack.toString());
	}
	
	public static List<FragmentID> getBackStack() {
		return mBackStack;
	}
	
	public static int getBackStackSize() {
		return mBackStack.size();
	}
}