package weifen.com.common.base;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基础Fragment
 */
public abstract class BaseFragment extends Fragment {

    protected abstract int getLayout();

    protected abstract void init(View view);

    protected Activity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(getLayout(),null);
        activity = getActivity();
        init(view);
        return view;
    }
}
