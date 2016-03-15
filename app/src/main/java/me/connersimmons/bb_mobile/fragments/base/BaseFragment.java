package me.connersimmons.bb_mobile.fragments.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by connersimmons on 3/15/16.
 */
public class BaseFragment extends Fragment {

    public void showMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

}
