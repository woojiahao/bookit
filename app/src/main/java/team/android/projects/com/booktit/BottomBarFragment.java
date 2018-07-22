package team.android.projects.com.booktit;

import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BottomBarFragment extends Fragment {
	private BottomNavigationView mNavigationView;
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
							  Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_bottom_bar, container, false);
		mNavigationView = v.findViewById(R.id.navigationDrawer);
		BottomNavigationHelper.removeShiftMode(mNavigationView);
		
		return v;
	}
}
