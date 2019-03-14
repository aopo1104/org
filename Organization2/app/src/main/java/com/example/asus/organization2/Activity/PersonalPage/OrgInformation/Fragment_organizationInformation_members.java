package com.example.asus.organization2.Activity.PersonalPage.OrgInformation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.organization2.R;

/**
 * Created by ASUS on 2019/2/12.
 */

public class Fragment_organizationInformation_members extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_organizationinformation_members, container, false);
        return rootView;
    }
}
