package com.example.mobilecollection.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.HomeMenu;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private String[] titleList = {
            "To Do List", "Pending List", "Delivered List", "Settings"
    };

    private int[] imgList = {
                R.drawable.menu_to_dolist,
                R.drawable.menu_pending_list,
                R.drawable.menu_deliveredlist,
                R.drawable.menu_setting
    };

    private String[] descList = {
                "Contains list of contract that has been added by Admin Collection",
                "Contains list of contract that has been submitted, but not done yet",
                "Contains list of contract that has been submitted, and delivered",
                "Contains mobile settings information"
    };

    private MutableLiveData<List<HomeMenu>> mHomeMenu = new MutableLiveData<>();
    private List<HomeMenu> listHm = new ArrayList<>();

    public LiveData<List<HomeMenu>> getmHomeMenu() {
        listHm = initialize();
        mHomeMenu.postValue(listHm);
        return mHomeMenu;
    }

    private List<HomeMenu> initialize() {
        List<HomeMenu> allList = new ArrayList<>();
        for (int i=0 ; i<imgList.length ; i++) {
            allList.add(new HomeMenu(titleList[i], imgList[i], descList[i]));
        }
        return allList;
    }
    ////buat model string[]. int[]. string[] buat title gmbar desc, ntar dipanggil disini, diset disini
}
