package team.android.projects.com.booktit;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// todo: remember to add onFinish listeners when closing out the activities to prevent state losses
public class BottomBarFragment extends Fragment {
	private BottomNavigationView mNavigationView;
	private View mView;
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
							  Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_bottom_bar, container, false);
		
		init();
		connectListeners();
		
		return mView;
	}
	
	private void init () {
		mNavigationView = mView.findViewById(R.id.navigationDrawer);
		BottomNavigationHelper.removeShiftMode(mNavigationView);
	}
	
	private void connectListeners () {
		mNavigationView.setOnNavigationItemSelectedListener(item -> {
			int itemSelected = item.getItemId();
			Class cls = null;
			
			switch (itemSelected) {
				case R.id.navigationDiscover:
					cls = Discover.class;
					break;
				case R.id.navigationSearch:
					cls = Search.class;
					break;
				case R.id.navigationScanner:
					break;
				case R.id.navigationFavourites:
					break;
				case R.id.navigationSettings:
					break;
			}
			if (cls != null) {
				startActivity(new Intent(getActivity(), cls));
			}
			return true;
		});
	}
}
